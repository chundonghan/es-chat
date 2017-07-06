package com.es.chat.dao.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.es.chat.dao.MyBatisRepository;

@MyBatisRepository
public interface ChatGroupDao {
	String shortUuid();

	void insertGroup(Map<String, Object> params);

	void insertGroupMember(List<Map<String, Object>> params);

	List<Map<String, Object>> groupList(@Param(value = "account") String account);

	Map<String, String> getGroupInfo(@Param(value = "group_id") String group_id);

	List<Map<String, Object>> getEarlierChat(@Param(value = "group_id") String group_id,
			@Param(value = "account") String account);
	
	void recordMessage(Map<String, String> params);
	List<String> getGroupMemberAccount(@Param(value = "group_id") String group_id);
}
