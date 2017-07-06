package com.es.chat.controller.chat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.es.chat.controller.BaseController;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.interceptors.Token;
import com.es.chat.pojo.ChatAccount;
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.util.CommonFunc;
import com.es.chat.util.Constants;
import com.es.chat.util.ValidateUtil;

@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController {
	@Autowired
	private ChatAccountService chatAccountService;
	@Autowired
	private RedisDao redisDao;
	@RequestMapping(value="/tosignin",method={RequestMethod.GET})
	public ModelAndView toSignIn(){
		return new ModelAndView("chat/signin");
	}
	@RequestMapping(value="/tosignup",method={RequestMethod.GET})
	public ModelAndView toSignUp(){
		return new ModelAndView("chat/signup");
	}
	@RequestMapping(value="/toForgotPassword",method={RequestMethod.GET})
	public ModelAndView toForgotPassword(){
		return new ModelAndView("chat/forgotpassword");
	}
	@RequestMapping(value="/signup",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String signUp(ChatAccount chatAccount){
		String signUpFlag = chatAccountService.signUp(chatAccount);
		return signUpFlag;
	}
	@RequestMapping(value="/signin",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String signIn(ChatAccount chatAccount){
		 UsernamePasswordToken token = new UsernamePasswordToken(chatAccount.getAccount(), chatAccount.getPassword());
	        Subject subject = SecurityUtils.getSubject();
	        try {
	            subject.login(token);
	        } catch (IncorrectCredentialsException  | UnknownAccountException uae) {
	            // 捕获密码错误异常
	            return "{\"message\":\"账号或密码错误!\"}";
	        } catch (ExcessiveAttemptsException eae) {
	            // 捕获错误登录过多的异常
	            return "{\"message\":\"网络异常，请稍后!\"}";
	        }
	        return "{\"message\":\"login\"}";
	}
	@RequestMapping(value="/index",method={RequestMethod.POST,RequestMethod.GET})
	public String index(HttpServletRequest request,ChatAccount chatAccount,HttpSession session,@RequestParam(value="section",defaultValue="1") String section){
		@SuppressWarnings("unused")
		String account = (String) session.getAttribute("currentUser");
		request.setAttribute("section", section);
		return "chat/index";
	}
	
	@RequestMapping(value="/forgotpassword/code",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String forgotPasswordCode(HttpSession session,@RequestParam(value="phone",defaultValue="") String phone ,@RequestParam(value="smsCode",defaultValue="") String smsCode){
		String result = null;
		String phone_sms_key = CommonFunc.createPhoneSmsKey(CommonFunc.nullToEmpty(phone));
		String phone_sms_code = redisDao.get(phone_sms_key);
		if (!smsCode.equals(phone_sms_code)) {
			result = "failed" ;
		}else{
			result = "success";
			session.setAttribute("forgotPasswordPhone", phone);
		}
		return result;
	}
	@Token(save = true)
	@RequestMapping(value="/toResetPassword",method={RequestMethod.POST,RequestMethod.GET})
	public String toResetPassword(HttpServletRequest request,HttpSession session){
		
		return "chat/reset_password";
	}
	@Token(remove = true)
	@RequestMapping(value="/resetPassword",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String resetPassword(HttpSession session,@RequestParam(value="newpass",defaultValue="") String newpass ,@RequestParam(value="confirmpass",defaultValue="") String confirmpass){
		String phone = (String) session.getAttribute("forgotPasswordPhone");
		String result = "succ";
		if(!ValidateUtil.validatePassword(newpass)){
			return "phone_error";
		}
		if(!newpass.equals(confirmpass)){
			return "not_equal";
		}
		//update
		chatAccountService.resetPassword(phone,newpass);
		
		return result;
	}
	
}
