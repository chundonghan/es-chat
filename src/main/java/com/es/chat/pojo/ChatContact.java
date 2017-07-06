package com.es.chat.pojo;

public final class ChatContact {
	private long id;
	private long account_id;
	private long contact_account_id;
	private int create_timestamp;
	private int update_timestamp;
	private String enable_flag;
	private String validate_msg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public long getContact_account_id() {
		return contact_account_id;
	}

	public void setContact_account_id(long contact_account_id) {
		this.contact_account_id = contact_account_id;
	}

	public int getCreate_timestamp() {
		return create_timestamp;
	}

	public void setCreate_timestamp(int create_timestamp) {
		this.create_timestamp = create_timestamp;
	}

	public int getUpdate_timestamp() {
		return update_timestamp;
	}

	public void setUpdate_timestamp(int update_timestamp) {
		this.update_timestamp = update_timestamp;
	}

	public String getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}

	public String getValidate_msg() {
		return validate_msg;
	}

	public void setValidate_msg(String validate_msg) {
		this.validate_msg = validate_msg;
	}

}
