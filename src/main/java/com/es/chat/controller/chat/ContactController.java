package com.es.chat.controller.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.es.chat.controller.BaseController;
import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.service.chat.ChatContactService;

@Controller
@RequestMapping("/contact")
public class ContactController extends BaseController {
	@Autowired
	private ChatContactService chatContactService;
	@Autowired
	private ChatAccountService chatAccountService;
	@Autowired
	private RedisDao redisDao;
	
	@RequestMapping(value="/contactList",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<Map<String,Object>> contactList(HttpSession session){
		String account =(String) session.getAttribute("currentUser");
		List<Map<String,Object>> contactList = chatContactService.contactList(account);
		return contactList;
	}
	@RequestMapping(value="/contactDetail",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView contactDetail(HttpSession session,@RequestParam(value="contact_account",defaultValue="") String contact_account){
		ModelAndView mv = new ModelAndView();
		if("".equals(contact_account)){
			return null;
		}
		String account =(String) session.getAttribute("currentUser");
		Map<String, String> contactDetail = chatContactService.getContactDetail(account, contact_account);
		
		mv.setViewName("contact/contact_detail");
		if(contactDetail==null){
			return null;
		}else{
			mv.addObject("contactDetail",contactDetail);
		}
		return mv;
	}
	@RequestMapping(value="/newContact",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView newContact(HttpSession session){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("contact/new_contact");
		String account =(String) session.getAttribute("currentUser");
		String key = "contact_to_"+account;
		redisDao.del(key);
		return mv;
	}
	@RequestMapping(value="/newContactDetail",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView newContactDetail(HttpSession session,@RequestParam(value="contact_account",defaultValue="") String contact_account){
		ModelAndView mv = new ModelAndView();
		if("".equals(contact_account)){
			return null;
		}
		String account =(String) session.getAttribute("currentUser");
		Map<String, String> contactDetail = chatAccountService.getInfoByAccount(contact_account);
		Map<String, String> accountDetail = chatAccountService.getInfoByAccount(account);
		mv.setViewName("contact/new_contact_detail");
		if(contactDetail==null){
			return null;
		}else{
			mv.addObject("accountDetail",accountDetail);
			mv.addObject("contactDetail",contactDetail);
		}
		return mv;
	}
	@RequestMapping(value="/applyList",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<Map<String,Object>> applyList(HttpSession session){
		String account =(String) session.getAttribute("currentUser");
		return chatContactService.applyList(account);
	}
	@RequestMapping(value="/addContact",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String addContact(HttpSession session,@RequestParam(value="contact_account",defaultValue="") String contact_account
			,@RequestParam(value="validate_msg",defaultValue="") String validate_msg){
		String account =(String) session.getAttribute("currentUser");
		
		return chatContactService.addContact(account, contact_account,validate_msg);
	}
	@RequestMapping(value="/newContactSearch",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,String> newContactSearch(HttpSession session,@RequestParam(value="search_content",defaultValue="") String search_content){
		String account =(String) session.getAttribute("currentUser");
		Map<String, String> result = new HashMap<>();
		if(account.equals(search_content)){
			result.put("status", "4");
			result.put("message", "不能搜索自己");
			return result;
		}
		if("".equals(search_content)){
			result.put("status", "0");
			result.put("message", "不能为空");
			return result;
		}
		return chatContactService.searchNewContact(account, search_content);
	}
	@RequestMapping(value="/getNewContact",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String getNewContact(HttpSession session){
		String account =(String) session.getAttribute("currentUser");
		String result = "hasnot";
		String key = "contact_to_"+account;
		if(redisDao.exists(key)){
			result = "has";
		}
		return result;
	}
	
}
