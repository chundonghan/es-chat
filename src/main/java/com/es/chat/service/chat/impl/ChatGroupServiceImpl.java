package com.es.chat.service.chat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.chat.dao.chat.ChatGroupDao;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.BaseService;
import com.es.chat.service.chat.ChatGroupService;
import com.es.chat.util.TimeUtil;

@Service(value = "ChatGroupService")
public class ChatGroupServiceImpl extends BaseService implements ChatGroupService {
	
	@Autowired
	private ChatGroupDao chatGroupDao;
	@Autowired
	private RedisDao redisDao;
	
	@Transactional
	@Override
	public String makeGroup(String account, String[] contacts) {
		Map<String,Object> param =  new HashMap<>();
		String group_id = chatGroupDao.shortUuid();
		int create_timestamp = TimeUtil.currentTimeSecs();
		param.put("id", group_id);
		param.put("group_name", account+"等");
		param.put("create_timestamp", create_timestamp);
		param.put("account", account);
		chatGroupDao.insertGroup(param);
		
		List<Map<String, Object>> params = setParams(group_id, contacts,account);
		if(params == null){
			return "error";
		}
		chatGroupDao.insertGroupMember(params);
		return "succ";
		
	}
	private List<Map<String,Object>> setParams(String group, String[] contacts,String account){
		List<Map<String,Object>> params = new ArrayList<>();
		Map<String,Object> param;
		if(contacts.length == 0){
			return null;
		}
		int create_timestamp = TimeUtil.currentTimeSecs();
		//自己
		param =  new HashMap<>();
		param.put("group_id", group);
		param.put("account", account);
		param.put("create_timestamp", create_timestamp);
		params.add(param);
		for(int i = 0; i<contacts.length; i++){
			param =  new HashMap<>();
			param.put("group_id", group);
			param.put("account", contacts[i]);
			param.put("create_timestamp", create_timestamp);
			params.add(param);
		}
		
		return params;
	}
	@Override
	public List<Map<String, Object>> groupList(String account) {
		if(chatGroupDao.groupList(account) == null){
			return new ArrayList<>();
		}
		return chatGroupDao.groupList(account);
	}
	@Override
	public Map<String, String> getGroupInfo(String group_id) {
		if(chatGroupDao.getGroupInfo(group_id) == null){
			return new HashMap<>();
		}
		return chatGroupDao.getGroupInfo(group_id);
	}
	@Override
	public List<Map<String, Object>> getEarlierChat(String account, String group_id) {
		List<Map<String, Object>> earlierChatList = chatGroupDao.getEarlierChat(group_id,account);
		List<Map<String, Object>> earlierChatListCopy = new ArrayList<>();
		if(earlierChatList.size() == 0){
			return new ArrayList<>();
		}
		Map<String,Object> map = null;
		for(Map<String,Object> earlierChat : earlierChatList){
			map = new HashMap<>();
			map.put("chat_time_title", earlierChat.get("chat_time"));
			if(!earlierChatListCopy.contains(map)){
				earlierChatListCopy.add(map);
			}
			earlierChatListCopy.add(earlierChat);
		}
		
		String key = "msg_from_"+group_id +"_to_"+account;
		long len = redisDao.llen(key);
		if(len>0){
			redisDao.del(key);
		}
		
		return earlierChatListCopy;
		
	}
	@Override
	public void recordMessage(Map<String, String> params) {
		String group_id = params.get("group_id");
		String account =params.get("account");
		
			
		List<String> groupMemberAccount = chatGroupDao.getGroupMemberAccount(group_id);
		if(groupMemberAccount == null || groupMemberAccount.size()== 0){
			return;
		}
		String key;
		String msg;
		for(String acc:groupMemberAccount){
			if(acc.equals(account)){
				continue;
			}
			key = "msg_from_"+group_id+"_to_"+acc;
			System.out.println(key);
			msg = params.get("msg");
			redisDao.lpush(key, msg);
		}
		chatGroupDao.recordMessage(params);
	}
}
