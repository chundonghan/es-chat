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
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.service.chat.ChatGroupService;
import com.es.chat.service.chat.ChatMessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private ChatAccountService chatAccountService;
	@Autowired
	private ChatGroupService chatGroupService;
	@RequestMapping(value = "/chatList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Map<String,String>> chatList(HttpSession session) {
		String account = (String) session.getAttribute("currentUser");
		return chatMessageService.getMessageList(account);
	}
	
	@RequestMapping(value = "/fullychat", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView fullychat(HttpSession session,@RequestParam(value="contact_account",defaultValue="") String contact_account) {
		ModelAndView mv = new ModelAndView();
		
		Map<String, String> toAccountInfo = chatAccountService.getInfoByAccount(contact_account);
		if(toAccountInfo == null){
			return null;
		}
		String account = (String) session.getAttribute("currentUser");
		session.setAttribute("to_account", contact_account);
		Map<String, String> fromAccountInfo = chatAccountService.getInfoByAccount(account);
		mv.addObject("nickname", toAccountInfo.get("nickname"));
		mv.addObject("to_account", toAccountInfo.get("account"));
		mv.addObject("to_avatar", toAccountInfo.get("avatar"));
		mv.addObject("from_account", fromAccountInfo.get("account"));
		mv.addObject("from_avatar", fromAccountInfo.get("avatar"));
		mv.setViewName("message/fullychat");
		return mv;
	}
	@RequestMapping(value = "/earlierChat", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Map<String,String>> getEarlierChat(HttpSession session,@RequestParam(value="to_account",defaultValue="") String to_account) {
		String from_account = (String) session.getAttribute("currentUser");
		return chatMessageService.getEarlierChat(from_account, to_account);
	}
	
	@RequestMapping(value="/info",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, String> info(@RequestParam(value="account",defaultValue="") String account,@RequestParam(value="ig") boolean ig){
		if("".equals(account)){
			return new HashMap<>();
		}
		if(ig){
			return chatGroupService.getGroupInfo(account);
		}else{
			return chatAccountService.getInfoByAccount(account);
		}
	}
}
