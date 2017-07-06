package com.es.chat.controller.chat;

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
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.util.ImageUtil;

@Controller
@RequestMapping("/me")
public class MeController extends BaseController {
	@Autowired
	private ChatAccountService chatAccountService;
	@RequestMapping(value = "/info", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,String> info(HttpSession session) {
		String account = (String) session.getAttribute("currentUser");
		return chatAccountService.getInfoByAccount(account);
	}
	@RequestMapping(value = "/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView detail(HttpSession session) {
		String account = (String) session.getAttribute("currentUser");
		ModelAndView mv = new ModelAndView();
		mv.addObject("me", chatAccountService.getInfoByAccount(account));
		mv.setViewName("me/me_detail");
		return mv;
	}
	@RequestMapping(value = "/changeGender", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void changeGender(HttpSession session,@RequestParam(value="gender") Integer gender) {
		String account = (String) session.getAttribute("currentUser");
		chatAccountService.updateInfo(account,gender);
	}
	@RequestMapping(value = "/changeNickname", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void changeNickname(HttpSession session,@RequestParam(value="nickname") String nickname) {
		String account = (String) session.getAttribute("currentUser");
		chatAccountService.updateInfo(account,nickname);
	}
	@RequestMapping(value = "/showAvatar", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showAvatar(HttpSession session) {
		String account = (String) session.getAttribute("currentUser");
		ModelAndView mv = new ModelAndView();
		mv.addObject("avatar", chatAccountService.getInfoByAccount(account).get("avatar"));
		mv.setViewName("me/show_avatar");
		return mv;
	}
	@RequestMapping(value = "/changeAvatar", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String changeAvatar(HttpServletRequest request,HttpSession session,@RequestParam(value="avatar") String avatar) {
		String account = (String) request.getSession().getAttribute("currentUser");
		boolean judgeImage = ImageUtil.judgeImage(avatar);
		if(!judgeImage){
			return "failed";
		}else{
			String avatar_url = ImageUtil.string2Image(avatar, request);
			if("error".equals(avatar_url)){
				return "failed";
			}else{
				chatAccountService.updateAvatar(account, avatar_url);
			}
			return "succ";
		}
	}
}
