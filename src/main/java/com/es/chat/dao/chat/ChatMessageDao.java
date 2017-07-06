package com.es.chat.dao.chat;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.es.chat.dao.MyBatisRepository;

@MyBatisRepository
public interface ChatMessageDao {
	List<Map<String, String>> getMessageList(@Param("account") String account);

	List<Map<String, String>> getEarlierChat(@Param("from_account") String from_account,
			@Param("to_account") String to_account);
	
	void recordMessage(Map<String,String> params);
}
