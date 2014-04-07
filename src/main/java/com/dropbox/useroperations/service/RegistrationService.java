package com.dropbox.useroperations.service;

import com.dropbox.exception.InvalidRequestException;
import com.dropbox.service.IService;
import com.dropbox.useroperations.dao.UserOperationsDAO;
import com.dropbox.useroperations.model.DropBoxRegistrationRequest;
import com.dropbox.useroperations.model.DropBoxRegistrationResponse;
import com.dropbox.util.SendMail;

public class RegistrationService implements IService {

	UserOperationsDAO dao;
	
	public Object processRequest(Object dto) {
		DropBoxRegistrationResponse response = null;
		try {
			validateRequest(dto);
			dao = new UserOperationsDAO();
			dao.registerUser((DropBoxRegistrationRequest) dto);
			response = new DropBoxRegistrationResponse();
			response.setStatus("SUCCESS");
			String[] to = { ((DropBoxRegistrationRequest) dto).getUserName() };
			String[] at = null;
			SendMail sm = new SendMail("cloudsharecisc839@gmail.com", "cloudshare1234",
					"Hi "+((DropBoxRegistrationRequest) dto).getUserName()+"\n Welcome"
					,"Welcome", at, to);
			sm.send();
		} catch (InvalidRequestException e1) {
			response = new DropBoxRegistrationResponse();
			response.setStatus("FAILURE");
			response.setErrorMessage(e1.getMessage());
		} catch (Exception e) {
			response = new DropBoxRegistrationResponse();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	public void validateRequest(Object dto) throws Exception {
		DropBoxRegistrationRequest request = (DropBoxRegistrationRequest) dto;

		if (request.getPassword() == null || request.getUserName() == null)
			throw new InvalidRequestException("Empty User name or Password");
		
		dao = new UserOperationsDAO();
		
		try {
			if(dao.searchUser(request.getUserName()) != null)
				throw new InvalidRequestException("UserName already exists");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		

	}
}
