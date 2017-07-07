package com.es.chat.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.CopyOnWriteArraySet;

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

import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.service.chat.ChatGroupService;
import com.es.chat.util.ApplicationContextRegister;

@ServerEndpoint(value = "/webgroupchat", configurator = GetHttpSessionConfigurator.class)
@Component
public class WebGroupChatServlet {

	/*
	 * private ChatMessageService chatMessageService;
	 * 
	 * @Autowired public void setChatMessageService(ChatMessageService
	 * chatMessageService) { this.chatMessageService = chatMessageService; }
	 */

	ApplicationContext act = ApplicationContextRegister.getApplicationContext();
	ChatGroupService chatGroupService = act.getBean(ChatGroupService.class);
	ChatAccountService chatAccountService = act.getBean(ChatAccountService.class);
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	private static ConcurrentHashMap<String, CopyOnWriteArraySet<WebGroupChatServlet>> chatClients = new ConcurrentHashMap<>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private HttpSession httpSession;

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
		if (httpSession != null) {
			if (chatClients.isEmpty()) {
				CopyOnWriteArraySet<WebGroupChatServlet> chatClient = new CopyOnWriteArraySet<WebGroupChatServlet>();
				chatClient.add(this);
				chatClients.put(getKey(httpSession), chatClient);
			} else {
				CopyOnWriteArraySet<WebGroupChatServlet> chatClient = chatClients.get(getKey(httpSession));
				chatClient.add(this);
				chatClients.put(getKey(httpSession), chatClient);
			}

		}
		addOnlineCount(); // 在线数加1
		System.out.println("群聊有新连接加入！当前在线人数为" + getOnlineCount());
	}

	private String getKey(HttpSession session) {
		return (String) httpSession.getAttribute("group_id");
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
		System.out.println("群聊来自客户端的消息:" + message);
		// 1对1 发送消息
		String key = getKey(httpSession);
		String currentUser = (String) this.httpSession.getAttribute("currentUser");
		if (chatClients.containsKey(key)) {

			for (WebGroupChatServlet item : chatClients.get(key)) {
				String saveCurrentUser = (String) item.httpSession.getAttribute("currentUser");
				if (currentUser.equals(saveCurrentUser)) {
					continue;
				} else {
					try {
						item.sendMessage(message);
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					}
				}
			}

		}
		KeySetView<String, WebPushServlet> keySet = WebPushServlet.chatClients.keySet();
		Iterator<String> it = keySet.iterator();
		String _key;
		WebPushServlet webPushServlet;
		String wpskey;
		String account = message.substring(message.indexOf("@") + 1, message.indexOf(":"));
		String nickname = chatAccountService.getInfoByAccount(account).get("nickname");
		String send_msg = nickname + ":" + message.substring(message.indexOf(":") + 1);
		while (it.hasNext()) {
			wpskey = it.next();
			
			if (wpskey.equals(currentUser)) {
			} else {
				System.out.println(wpskey);
				webPushServlet = WebPushServlet.chatClients.get(wpskey);
				_key = "#"+key + "@" + wpskey;
				if (webPushServlet != null) {
					// msg/es@es2:message
					webPushServlet.onMessage("msg/" + _key + ":" + send_msg, session);
				}
			}

		}
		String msg = message.substring(message.indexOf(":") + 1);
		Map<String, String> params = new HashMap<>();
		params = setMsgParams(this.httpSession);
		params.put("msg", msg);
		// 保存到数据库
		chatGroupService.recordMessage(params);

	}

	private Map<String, String> setMsgParams(HttpSession session) {
		Map<String, String> params = new HashMap<>();
		String from = (String) httpSession.getAttribute("currentUser");
		String group_id = (String) httpSession.getAttribute("group_id");
		params.put("account", from);
		params.put("group_id", group_id);
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
		WebGroupChatServlet.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebGroupChatServlet.onlineCount--;
	}

}
