package com.dropbox.cloudoperations.model;

public class DropBoxGenerateSharingKeyRequest {

	private String userName;
	private String password;
	private String sharedUserName;
	private String fileName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSharedUserName() {
		return sharedUserName;
	}

	public void setSharedUserName(String sharedUserName) {
		this.sharedUserName = sharedUserName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
