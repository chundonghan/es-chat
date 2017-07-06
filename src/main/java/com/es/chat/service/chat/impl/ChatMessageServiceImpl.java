package com.es.chat.service.chat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.chat.dao.chat.ChatMessageDao;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.BaseService;
import com.es.chat.service.chat.ChatMessageService;

@Service(value = "ChatMessageService")
public class ChatMessageServiceImpl extends BaseService implements ChatMessageService {
	
	@Autowired
	private ChatMessageDao chatMessageDao;
	@Autowired
	private RedisDao redisDao;
	@Override
	public List<Map<String,String>> getMessageList(String account) {
		List<Map<String,String>> msg = new ArrayList<>();
		msg = chatMessageDao.getMessageList(account);
		if(msg.size()>0){
			for(Map<String,String> ms:msg){
				String from_account = ms.get("account");
				String key = "msg_from_"+from_account +"_to_"+account;
				long len = redisDao.llen(key);
				if(len>0){
					ms.put("has_new_msg", "1");
					//redisDao.del(key);
				}else{
					ms.put("has_new_msg", "0");
				}
			}
			return msg;
		}else{
			return new ArrayList<>();
		}
		
	}
	@Override
	public List<Map<String, String>> getEarlierChat(String from_account, String to_account) {
		List<Map<String, String>> earlierChatList = chatMessageDao.getEarlierChat(from_account, to_account);
		List<Map<String, String>> earlierChatListCopy = new ArrayList<>();
		if(earlierChatList.size() == 0){
			return new ArrayList<>();
		}
		Map<String,String> map = null;
		for(Map<String,String> earlierChat : earlierChatList){
			map = new HashMap<>();
			map.put("chat_time_title", earlierChat.get("chat_time"));
			if(!earlierChatListCopy.contains(map)){
				earlierChatListCopy.add(map);
			}
			earlierChatListCopy.add(earlierChat);
		}
		String key = "msg_from_"+to_account +"_to_"+from_account;
		long len = redisDao.llen(key);
		if(len>0){
			redisDao.del(key);
		}
		return earlierChatListCopy;
	}
	@Override
	@Transactional
	public void recordMessage(Map<String,String> params){
		String key = "msg_from_"+params.get("from_account")+"_to_"+params.get("to_account");
		String msg = params.get("msg");
		if("0".equals(params.get("both_on_chat"))){
			redisDao.lpush(key, msg);
		}
		chatMessageDao.recordMessage(params);
	}
}
