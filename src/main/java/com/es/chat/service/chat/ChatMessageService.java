package com.es.chat.service.chat;

import java.util.List;
import java.util.Map;

public interface ChatMessageService {
	List<Map<String,String>> getMessageList(String account);
	List<Map<String, String>> getEarlierChat(String from_account, String to_account);
	void recordMessage(Map<String,String> params);
}
