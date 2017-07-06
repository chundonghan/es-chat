package com.es.chat.dao.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.es.chat.dao.MyBatisRepository;
import com.es.chat.pojo.ChatAccount;

@MyBatisRepository
public interface ChatAccountDao {
	int signUp(ChatAccount chatAccount);
	ChatAccount signIn(ChatAccount chatAccount);
	int updateLogin(ChatAccount chatAccount);
	List<String> getAllAccounts();
	List<String> getAllPhones();
	Map<String,String> getInfoByAccount(@Param("account") String account);
	Integer getIdByAccount(@Param("account") String account);
	
	Map<String, Object> authToken(@Param("account") String account);
	void updateToken(Map<String,Object> params);
	void updateTokenExpire(Map<String,Object> params);
	void updateInfo(Map<String,Object> params);
	void resetPassword(@Param("phone") String phone,@Param("newpass") String newpass);
}
