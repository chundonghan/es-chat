package com.es.chat.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

import com.es.chat.service.chat.ChatMessageService;
import com.es.chat.util.ApplicationContextRegister;

@ServerEndpoint(value = "/webchat", configurator = GetHttpSessionConfigurator.class)
@Component
public class WebChatServlet {
	
	/*private ChatMessageService chatMessageService;
	@Autowired
	public void setChatMessageService(ChatMessageService chatMessageService) {
		this.chatMessageService = chatMessageService;
	}*/
	
	ApplicationContext act = ApplicationContextRegister.getApplicationContext();
	ChatMessageService chatMessageService=act.getBean(ChatMessageService.class);
	
	 
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	private static ConcurrentHashMap<String, WebChatServlet> chatClients= new ConcurrentHashMap<>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private HttpSession httpSession;
	public WebChatServlet(){
		
	}
	public WebChatServlet(String key){
		chatClients.put(key, new WebChatServlet());
	}
	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		if(httpSession!=null)
			chatClients.put(getKey(httpSession), this);
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}
	private String getKey(HttpSession session){
		String from  =(String) httpSession.getAttribute("currentUser");
		String to  = (String)httpSession.getAttribute("to_account");
		String key = from+"@"+to;
		return key;
	}
	private String getReverseKey(HttpSession session ){
		String from  =(String) httpSession.getAttribute("currentUser");
		String to  = (String)httpSession.getAttribute("to_account");
		String key = to+"@"+from;
		return key;
	}
	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		chatClients.remove(getKey(httpSession));
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		// 1对1 发送消息
		String key = getReverseKey(httpSession);
		String both_on_chat  = "0";
		if(chatClients.containsKey(key)){
			WebChatServlet oneToOne = chatClients.get(key);
			try {
				oneToOne.sendMessage(message);
				
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			both_on_chat  = "1";
		}
		
		String _key =  getKey(httpSession);
		WebPushServlet webPushServlet = WebPushServlet.chatClients.get((String)httpSession.getAttribute("to_account"));
		if(webPushServlet !=null ){
			// msg/es@es2:message
			webPushServlet.onMessage("msg/"+_key+":"+message, session);
		}
		Map<String,String> params = new HashMap<>();
		params = setMsgParams(this.httpSession);
		params.put("msg", message);
		params.put("both_on_chat", both_on_chat);
		//保存到数据库
		chatMessageService.recordMessage(params);
		
	}
	private Map<String,String> setMsgParams(HttpSession session){
		Map<String,String> params = new HashMap<>();
		String from  =(String) httpSession.getAttribute("currentUser");
		String to  = (String)httpSession.getAttribute("to_account");
		params.put("from_account", from);
		params.put("to_account", to);
		return params;
	}
	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebChatServlet.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebChatServlet.onlineCount--;
	}
	
}
