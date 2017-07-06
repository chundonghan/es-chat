package com.es.chat.pojo;

public final class ChatMessage {
	private long id;
	private String from_account;
	private String to_account;
	private String msg;
	private String create_timestamp;
	private String from_enable_flag;
	private String to_enable_flag;
	private String last_chat_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom_account() {
		return from_account;
	}

	public void setFrom_account(String from_account) {
		this.from_account = from_account;
	}

	public String getTo_account() {
		return to_account;
	}

	public void setTo_account(String to_account) {
		this.to_account = to_account;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCreate_timestamp() {
		return create_timestamp;
	}

	public void setCreate_timestamp(String create_timestamp) {
		this.create_timestamp = create_timestamp;
	}

	public String getFrom_enable_flag() {
		return from_enable_flag;
	}

	public void setFrom_enable_flag(String from_enable_flag) {
		this.from_enable_flag = from_enable_flag;
	}

	public String getTo_enable_flag() {
		return to_enable_flag;
	}

	public void setTo_enable_flag(String to_enable_flag) {
		this.to_enable_flag = to_enable_flag;
	}

	public String getLast_chat_time() {
		return last_chat_time;
	}

	public void setLast_chat_time(String last_chat_time) {
		this.last_chat_time = last_chat_time;
	}


}
