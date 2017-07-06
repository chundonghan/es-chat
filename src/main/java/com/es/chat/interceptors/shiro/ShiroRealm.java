package com.es.chat.interceptors.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.es.chat.pojo.ChatAccount;
import com.es.chat.service.chat.ChatAccountService;
import com.es.chat.util.MD5;
import com.es.chat.util.SHA;
import com.es.chat.util.Salt;

public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private ChatAccountService chatAccountService;
	
	SimpleAuthorizationInfo simpleAuthorInfo = null;

	/**
	 * 权限认证，获取登录用户的权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("1");
		return null;
	}

	/**
	 * 登录认证，创建用户的登录信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		// 两个token的引用都是一样的
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		String password = MD5.md5(new String(usernamePasswordToken.getPassword())+com.es.chat.util.Constants.PASSWORD_SALT);
		ChatAccount chatAccount = new ChatAccount(username,password);
		ChatAccount signIn = chatAccountService.signIn(chatAccount);
		if(signIn!=null){
				Map<String, Object> params = new HashMap<>();
				String salt = Salt.salting();
	            String auth_token = SHA.sha(salt+username,"SHA-1");
	            long token_expire = System.currentTimeMillis()/1000+1000;
	            params.put("account", username);
	            params.put("salt", salt);
	            params.put("auth_token", auth_token);
	            params.put("token_expire", token_expire);
	            try {
	            	chatAccountService.updateToken(params);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return null; 
	            }
	            this.setSession("currentUser", username);
	            this.setSession("auth_token", auth_token);
			return new SimpleAuthenticationInfo(username, usernamePasswordToken.getPassword(), getName());
		}
		return null;
	}
	/** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject(); 
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  
	
}
