package com.es.chat.service.chat;

import java.util.List;
import java.util.Map;


public interface ChatGroupService {
	String makeGroup(String account,String[] contacts);
	
	List<Map<String,Object>> groupList(String account);
	
	Map<String,String> getGroupInfo(String group_id);
	
	List<Map<String,Object>> getEarlierChat(String account,String group_id);

	void recordMessage(Map<String, String> params);
}
