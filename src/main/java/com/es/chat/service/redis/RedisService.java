package com.es.chat.service.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.BaseService;
import com.es.chat.util.CommonFunc;
import com.es.chat.util.Constants;

@Service(value = "RedisService")
public class RedisService extends BaseService {
	@Autowired
	private RedisDao redisDao;

	public String sms(String phone) {
		String phone_sms_key = "sms_" + phone;
		String phone_sms_key_num = "sms_" + phone + "_num";
		String phone_sms_each_day_count = "sms_each_day_" + phone + "_count";
		//每天发短信次数 
		if (redisDao.exists(phone_sms_each_day_count) && Integer.valueOf(redisDao.get(phone_sms_each_day_count)) >= 5) {
			
			logger.info("{}", Constants.TOO_MANY_OPER_RET_CODE);
			return CommonFunc.retJsonString("message", Constants.TOO_MANY_OPER_RET_CODE);
		}
		//在短信发送时间内重复操作
		redisDao.incr(phone_sms_key_num);
		if (Integer.valueOf(redisDao.get(phone_sms_key_num)) > 1) {
			logger.info("{}", Constants.TOO_MUCH_FREQUENTLY_OPER_RET_CODE);
			return CommonFunc.retJsonString("message", Constants.TOO_MUCH_FREQUENTLY_OPER_RET_CODE);
		}
		//发送短信
		if (Integer.valueOf(redisDao.get(phone_sms_key_num)) == 1) {
			redisDao.expire(phone_sms_key_num, 60);
			redisDao.incr(phone_sms_each_day_count);
			redisDao.expire(phone_sms_each_day_count, CommonFunc.diffTomorrow());
			String smsCode = CommonFunc.generateSmsCode();
			redisDao.set(phone_sms_key, smsCode, 60);
			logger.info("{}：{}", Constants.PHONE_SMS_SUCC_RET_CODE,smsCode);
			// 调用短信接口

			return CommonFunc.retJsonString("message", Constants.PHONE_SMS_SUCC_RET_CODE);
		}
		return null;
	}

	public void newContactNotice(String msg){
		String head = msg.substring(0,msg.indexOf("/"));
		//String  from = msg.substring(msg.indexOf("/")+1,msg.indexOf("@"));
		String to = msg.substring(msg.indexOf("@")+1,msg.indexOf(":"));
		//String text = msg.substring(msg.indexOf(":")+1);
		String key = head+"_to_"+to;
		redisDao.set(key, "1");
		
	}
	
	public void groupMsgPush(String group_id,String msg,String to_account){
		String key = "msg_from_"+group_id+"_to_"+to_account;
		redisDao.lpush(key, msg);
	}
}
