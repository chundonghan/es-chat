package com.es.chat.service.chat.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.chat.dao.chat.ChatAccountDao;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.pojo.ChatAccount;
import com.es.chat.service.BaseService;
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.util.CommonFunc;
import com.es.chat.util.Constants;
import com.es.chat.util.TimeUtil;
import com.es.chat.util.ValidateUtil;

@Service(value = "ChatAccountService")
public class ChatAccountServiceImpl extends BaseService implements ChatAccountService {
	@Autowired
	private ChatAccountDao chatAccountDao;
	@Autowired
	private RedisDao redisDao;

	@Transactional
	@Override
	public String signUp(ChatAccount chatAccount) {
		String result = "";
		String smsCode = CommonFunc.nullToEmpty(chatAccount.getSmsCode());
		// 判断短信验证码 是否相同
		String phone_sms_key = CommonFunc.createPhoneSmsKey(CommonFunc.nullToEmpty(chatAccount.getPhone()));
		String phone_sms_code = redisDao.get(phone_sms_key);
		if (!smsCode.equals(phone_sms_code)) {
			result += "code_error" + ",";
		}
		// 判断账号 
		boolean exist_account = redisDao.sismember("exist_account", chatAccount.getAccount());
		if (!ValidateUtil.validateAccount(CommonFunc.nullToEmpty(chatAccount.getAccount())) || exist_account) {
			result += "account_error" + ",";
		}
		// 判断手机号
		boolean exist_phone = redisDao.sismember("exist_phone", chatAccount.getPhone());
		if (!ValidateUtil.validatePhone(CommonFunc.nullToEmpty(chatAccount.getPhone())) || exist_phone) {
			result += "phone_error" + ",";
		}
		// 判断密码
		if (!ValidateUtil.validatePassword(CommonFunc.nullToEmpty(chatAccount.getPassword()))) {
			result += "password_error" + ",";
		}
		if(!"".equals(result)){
			result = result.substring(0, result.length()-1);
			return result;
		}
		chatAccount.setPassword(CommonFunc.encryptPassword(chatAccount.getPassword()));
		chatAccount.setCreate_timestamp(TimeUtil.currentTimeSecs());
		chatAccount.setAvatar(Constants.DEFAULT_AVATAR);
		int flag = chatAccountDao.signUp(chatAccount);
		redisDao.sadd("exist_account", chatAccount.getAccount());
		redisDao.sadd("exist_phone", chatAccount.getPhone());
		if (flag == 1) {
			result = "success";
		} else {
			result = "failed";
		}
		return result;
	}

	@Override
	public ChatAccount signIn(ChatAccount chatAccount) {
		// TODO Auto-generated method stub
		ChatAccount chatLoginAccount = chatAccountDao.signIn(chatAccount);
		chatAccount.setLogin_timestamp(TimeUtil.currentTimeSecs());
		chatAccountDao.updateLogin(chatAccount);
		return chatLoginAccount;
	}

	@Override
	public List<String> getAllAccounts() {
		return chatAccountDao.getAllAccounts();
	}

	@Override
	public List<String> getAllPhones() {
		return chatAccountDao.getAllPhones();
	}

	@Override
	public Map<String, String> getInfoByAccount(String account) {
		return chatAccountDao.getInfoByAccount(account);
	}

	@Override
	public void updateToken(Map<String, Object> params) throws Exception {
		chatAccountDao.updateToken(params);
	}

	@Override
	public Map<String, Object> authToken(String account) throws Exception {
		return chatAccountDao.authToken(account);
	}

	@Override
	public void updateTokenExpire(Map<String, Object> params) throws Exception {
		chatAccountDao.updateTokenExpire(params);
	}

	@Override
	public void updateInfo(String account, Integer gender) {
		Map<String,Object> params = new HashMap<>();
		params.put("gender", gender);
		params.put("account", account);
		chatAccountDao.updateInfo(params);
	}

	@Override
	public void updateInfo(String account, String nickname) {
		Map<String,Object> params = new HashMap<>();
		params.put("nickname", nickname);
		params.put("account", account);
		chatAccountDao.updateInfo(params);
	}
	
	@Override
	public void updateAvatar(String account, String avatar) {
		Map<String,Object> params = new HashMap<>();
		params.put("avatar", avatar);
		params.put("account", account);
		chatAccountDao.updateInfo(params);
	}

	@Override
	public void resetPassword(String phone, String newpass) {
		chatAccountDao.resetPassword(phone,CommonFunc.encryptPassword(newpass));
	}
}
