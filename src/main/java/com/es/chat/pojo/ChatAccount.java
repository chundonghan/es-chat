package com.es.chat.pojo;

public final class ChatAccount {
	public ChatAccount() {

	}

	public ChatAccount(String account, String password) {
		this.account = account;
		this.password = password;
	}

	private long id;
	private String nickname;
	private String avatar;
	private String account;
	private String password;
	private int create_timestamp;
	private int login_timestamp;
	private String enable_flag;
	private String phone;
	private String smsCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCreate_timestamp() {
		return create_timestamp;
	}

	public void setCreate_timestamp(int create_timestamp) {
		this.create_timestamp = create_timestamp;
	}

	public int getLogin_timestamp() {
		return login_timestamp;
	}

	public void setLogin_timestamp(int login_timestamp) {
		this.login_timestamp = login_timestamp;
	}

	public String getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
