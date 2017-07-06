package com.es.chat.controller.validate;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.chat.controller.BaseController;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.redis.RedisService;
import com.es.chat.util.CommonFunc;
import com.es.chat.util.Constants;
import com.es.chat.util.ValidateUtil;
import com.es.chat.util.VerifyCodeUtils;

@Controller
@RequestMapping("/validate")
public class ValidateController extends BaseController {
	@Autowired
	private RedisService redisService;
	@Autowired
	private RedisDao redisDao;
	@RequestMapping(value="/captcha",method={RequestMethod.GET})
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //删除以前的
        session.removeAttribute("captcha");
        session.setAttribute("captcha", verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
	}
	
	@RequestMapping(value="/messageCode",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String messageCode(@RequestParam(value="phone",defaultValue="") String phone) throws IOException{
		if("".equals(phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_EMPTY_RET_CODE);
		}else if(!ValidateUtil.validatePhone(phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_ERROR_RET_CODE);
		}
		return redisService.sms(phone);
	}
	@RequestMapping(value="/account",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String account(@RequestParam(value="account",defaultValue="") String account) throws IOException{
		if(redisDao.sismember("exist_account", account)){
			return CommonFunc.retJsonString("message",Constants.ACCOUNT_EXIST_RET_CODE);
		}
		if(!ValidateUtil.validateAccount(account)){
			return CommonFunc.retJsonString("message",Constants.ACCOUNT_ERROR_RET_CODE);
		}
		return CommonFunc.retJsonString("message",Constants.ACCOUNT_SUCC_RET_CODE);
	}
	@RequestMapping(value="/password",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String password(@RequestParam(value="password",defaultValue="") String password,
			@RequestParam(value="account",defaultValue="") String account) throws IOException{
		if(password.equals(account)){
			return CommonFunc.retJsonString("message",Constants.PASSWORD_EQUALS_ACCOUNT_RET_CODE);
		}
		if(!ValidateUtil.validatePassword(password)){
			return CommonFunc.retJsonString("message",Constants.PASSWORD_ERROR_RET_CODE);
		}
		return CommonFunc.retJsonString("message",Constants.PASSWORD_SUCC_RET_CODE);
	}
	@RequestMapping(value="/phone",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String phone(@RequestParam(value="phone",defaultValue="") String phone) throws IOException{
		if(redisDao.sismember("exist_phone", phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_EXIST_RET_CODE);
		}
		if(!ValidateUtil.validatePhone(phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_ERROR_RET_CODE);
		}
		return CommonFunc.retJsonString("message",Constants.PHONE_SUCC_RET_CODE);
	}
	
	@RequestMapping(value="/forgotpassword/phone",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String forgotPasswordPhone(@RequestParam(value="phone",defaultValue="") String phone) throws IOException{
		if(redisDao.sismember("exist_phone", phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_SUCC_RET_CODE);
		}else{
			return CommonFunc.retJsonString("message",Constants.PHONE_ERROR_RET_CODE);
		}
	}
	@RequestMapping(value="/forgotpassword/messageCode",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String forgotPasswordMessageCode(@RequestParam(value="phone",defaultValue="") String phone) throws IOException{
		if("".equals(phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_EMPTY_RET_CODE);
		}else if(!ValidateUtil.validatePhone(phone)){
			return CommonFunc.retJsonString("message",Constants.PHONE_ERROR_RET_CODE);
		}
		return redisService.sms(phone);
	}
	@RequestMapping(value="/reset_password",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseBody
	public String resetPassword(@RequestParam(value="newpass",defaultValue="") String newpass ) throws IOException{
		if(!ValidateUtil.validatePassword(newpass)){
			return CommonFunc.retJsonString("message",Constants.PASSWORD_ERROR_RET_CODE);
		}
		
		return CommonFunc.retJsonString("message",Constants.PASSWORD_SUCC_RET_CODE);
	}
}
