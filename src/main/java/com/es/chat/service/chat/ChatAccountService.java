package com.es.chat.service.chat;

import java.util.List;
import java.util.Map;

import com.es.chat.pojo.ChatAccount;

public interface ChatAccountService {
	String signUp(ChatAccount chatAccount);
	ChatAccount signIn(ChatAccount chatAccount);
	List<String> getAllAccounts();
	List<String> getAllPhones();
	Map<String,String> getInfoByAccount(String account);
	//token
	void updateToken(Map<String,Object> params) throws Exception;
	Map<String,Object> authToken(String account) throws Exception;
	void updateTokenExpire(Map<String, Object> params) throws Exception;
	//更新信息
	void updateInfo(String account,Integer gender);
	void updateInfo(String account,String nickname);
	void updateAvatar(String account,String Avatar);
	void resetPassword(String phone, String newpass);
}
