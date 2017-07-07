package com.es.chat.controller.chat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.es.chat.controller.BaseController;
import com.es.chat.interceptors.Token;
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.service.chat.ChatGroupService;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {
	@Autowired
	private ChatGroupService chatGroupService;
	@Autowired
	private ChatAccountService chatAccountService;
	@Token(save = true)
	@RequestMapping(value="/toMakeGroup",method={RequestMethod.GET})
	public ModelAndView toMakeGroup(){
		return new ModelAndView("group/make_group");
	}
	@Token(remove = true)
	@RequestMapping(value = "/makeGroup", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String makeGroup(HttpServletRequest request,HttpSession session,@RequestParam(value="contacts[]") String[] contacts) {
		String account = (String) request.getSession().getAttribute("currentUser");
		return chatGroupService.makeGroup(account, contacts);
	}
	@RequestMapping(value="/toGroupList",method={RequestMethod.GET})
	public ModelAndView toGroupList(){
		return new ModelAndView("group/group_list");
	}
	@RequestMapping(value = "/groupList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Map<String,Object>> groupList(HttpServletRequest request,HttpSession session) {
		String account = (String) request.getSession().getAttribute("currentUser");
		return chatGroupService.groupList(account);
	}
	
	@RequestMapping(value="/message/fullychat",method={RequestMethod.GET})
	public ModelAndView toGroupList(HttpSession session,@RequestParam(value="group_id",defaultValue="") String group_id){
		String account = (String)session.getAttribute("currentUser");
		if("".equals(group_id)){
			return null;
		}
		ModelAndView mv =  new ModelAndView();
		Map<String, String> fromAccountInfo = chatAccountService.getInfoByAccount(account);
		Map<String, String> groupInfo = chatGroupService.getGroupInfo(group_id);
		mv.addObject("from_account", fromAccountInfo.get("account"));
		mv.addObject("from_avatar", fromAccountInfo.get("avatar"));
		session.setAttribute("group_id", groupInfo.get("id"));
		mv.addObject("groupInfo", groupInfo);
		mv.setViewName("message/groupfullychat");
		return mv;
	}
	@RequestMapping(value = "/message/earlierChat", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Map<String,Object>> earlierChat(HttpServletRequest request,HttpSession session) {
		String account = (String) request.getSession().getAttribute("currentUser");
		String group_id = (String) session.getAttribute("group_id");
		return chatGroupService.getEarlierChat(account,group_id);
	}
	
}
