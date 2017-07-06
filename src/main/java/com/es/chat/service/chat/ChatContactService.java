package com.es.chat.service.chat;

import java.util.List;
import java.util.Map;

public interface ChatContactService {
	List<Map<String,Object>> contactList(String account);
	Map<String,String> getContactDetail(String account,String contact_account);
	List<Map<String,Object>> applyList(String account);
	String addContact(String account,String contact_account,String validate_msg);
	Map<String,String> searchNewContact(String account,String search_content);
}
