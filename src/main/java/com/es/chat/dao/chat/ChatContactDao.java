package com.es.chat.dao.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.es.chat.dao.MyBatisRepository;
import com.es.chat.pojo.ChatContact;

@MyBatisRepository
public interface ChatContactDao {
	int applyContact(ChatContact chatContact);
	int contactIsExist(ChatContact chatContact);
	List<Map<String,Object>> contactList(@Param("account") String account);
	int validateIfMyFriend(@Param("account") String account,@Param("contact_account") String contact_account);
	List<Map<String,Object>> applyList(@Param("account") String account);	
}
