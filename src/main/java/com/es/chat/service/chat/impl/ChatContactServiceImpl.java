package com.es.chat.service.chat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.chat.dao.chat.ChatAccountDao;
import com.es.chat.dao.chat.ChatContactDao;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.pojo.ChatContact;
import com.es.chat.service.BaseService;
import com.es.chat.service.chat.ChatContactService;
import com.es.chat.util.CommonFunc;
import com.es.chat.util.Pinyin;
import com.es.chat.util.TimeUtil;

@Service(value = "ChatContactService")
public class ChatContactServiceImpl extends BaseService implements ChatContactService {
	@Autowired
	private ChatContactDao chatContactDao;
	@Autowired
	private ChatAccountDao chatAccountDao;
	@SuppressWarnings("unused")
	@Autowired
	private RedisDao redisDao;

	@Override
	public List<Map<String, Object>> contactList(String account) {
		List<Map<String, Object>> contactOriginList = chatContactDao.contactList(account);
		if(contactOriginList.size()==0){
			return new ArrayList<>();
		}
		Map<String, Object> initialMap;
		CommonFunc.sortList(contactOriginList);
		List<Map<String, Object>> contactList = new ArrayList<>();
		for (char i = 97; i <= 123; i++) {
			initialMap = new HashMap<>();
			String value;
			if (i == 123) {
				value = "#";
			} else {
				value = i + "";
			}
			initialMap.put("initial", value);
			for (int j = 0; j < contactOriginList.size(); j++) {
				if (Pinyin.getInitialChar((String) contactOriginList.get(j).get("nickname")) == i) {
					if(!contactList.contains(initialMap)){
						contactList.add(initialMap);
					}
					contactList.add(contactOriginList.get(j));
				} else {
					continue;
				}
			}
		}
		return contactList;
	}

	@Override
	public Map<String, String> getContactDetail(String account, String contact_account) {
		
		Map<String, String> contactDetail = chatAccountDao.getInfoByAccount(contact_account);
		if(contactDetail == null){
			return new HashMap<>();
		}
		int va = chatContactDao.validateIfMyFriend(account, contact_account);
		if(va == 1){
			return contactDetail;
		}else{
			return new HashMap<>();
		}
		
	}

	@Override
	public List<Map<String, Object>> applyList(String account) {
		List<Map<String, Object>> applyList = chatContactDao.applyList(account);
		if(applyList.size() == 0){
			return new ArrayList<>();
		}
		return applyList;
	}

	@Override
	public String addContact(String account, String contact_account, String validate_msg) {
		ChatContact chatContact = new ChatContact();
		Integer account_id = chatAccountDao.getIdByAccount(account);
		Integer contact_account_id = chatAccountDao.getIdByAccount(contact_account);
		if(account_id == null || contact_account_id == null){
			return "error";
		}
		chatContact.setAccount_id(account_id);
		chatContact.setContact_account_id(contact_account_id);
		chatContact.setValidate_msg(validate_msg);
		chatContact.setCreate_timestamp(TimeUtil.currentTimeSecs());
		int contactIsExist = chatContactDao.contactIsExist(chatContact);
		if(contactIsExist == 1){
			return "error";
		}else{
			chatContactDao.applyContact(chatContact);
		}
		
		return "succ";
	}

	@Override
	public Map<String, String> searchNewContact(String account, String search_content) {
		Map<String, String> result = new HashMap<>();
		Map<String, String> info = chatAccountDao.getInfoByAccount(search_content);
		if(info == null ){
			//不存在此用户
			result.put("status", "1");
			result.put("message", "不存在此用户");
			return result;
		}
		
		int friend_flag = chatContactDao.validateIfMyFriend(account, search_content);
		if(friend_flag == 1){
			//已添加好友
			result.put("status", "2");
			result.put("message", "已添加好友");
			return result;
		}else{
			//用户存在 未添加好友
			result.put("status", "3");
			result.put("message", "未添加好友");
			return result;
		}
	}
}
