package com.es.chat.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.es.chat.dao.redis.RedisDao;
import com.es.chat.service.chat.ChatAccountService;
@Component
public class LocalInit implements ApplicationListener<ContextRefreshedEvent>
{
    @Autowired
    private ChatAccountService chatAccountService;
    @Autowired
    private RedisDao redisDao;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent  event)
    {
        if(event.getApplicationContext().getParent() == null&& event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){//root application context 没有parent，他就是老大.  
           //初始化参数 操作数据库初始化信息
        	List<String> allAccounts = chatAccountService.getAllAccounts();
        	if(allAccounts.size()>0){
        		for(int i = 0;i<allAccounts.size();i++){
        			redisDao.sadd("exist_account", allAccounts.get(i));
        			
        		}
        	}
        	
        	List<String> allPhones = chatAccountService.getAllPhones();
        	if(allPhones.size()>0){
        		for(int i = 0;i<allPhones.size();i++){
        			redisDao.sadd("exist_phone", allPhones.get(i));
        			
        		}
        	}
        	
       } 
       
    }

}
