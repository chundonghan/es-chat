package com.es.chat.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.es.chat.service.redis.RedisService;
import com.es.chat.util.ApplicationContextRegister;

@ServerEndpoint(value = "/webpush", configurator = GetHttpSessionConfigurator.class)
@Component
public class WebPushServlet {
	ApplicationContext act = ApplicationContextRegister.getApplicationContext();
	RedisService redisService=act.getBean(RedisService.class);
	static ConcurrentHashMap<String, WebPushServlet> chatClients= new ConcurrentHashMap<>();
	private Session session;
	private HttpSession httpSession;
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		if(httpSession!=null)
			chatClients.put(getKey(httpSession), this);
	}
	private String getKey(HttpSession session){
		return (String) httpSession.getAttribute("currentUser");
	}
	
	@OnClose
	public void onClose() {
		chatClients.remove(getKey(httpSession));
	}
	@OnMessage
	public void onMessage(String message, Session session) {
		//String from = message.substring(0,message.indexOf("@"));
		String to  = message.substring(message.indexOf("@")+1,message.indexOf(":"));
		if(chatClients.containsKey(to)){
			WebPushServlet oneToOne = chatClients.get(to);
			try {
				String head  = message.substring(0,message.indexOf("/"));
				if("msg".equals(head)){
					
				}else if("contact".equals(head)){
					message = message.replace("FROM_ACCOUNT", getKey(httpSession));
					redisService.newContactNotice(message);
				}
				oneToOne.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
		}
			
		
	}
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("WebPushServlet发生错误");
		error.printStackTrace();
	}
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
}
