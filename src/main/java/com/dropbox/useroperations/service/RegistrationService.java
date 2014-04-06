package com.dropbox.useroperations.service;

import com.dropbox.exception.InvalidRequestException;
import com.dropbox.service.IService;
import com.dropbox.useroperations.dao.UserOperationsDAO;
import com.dropbox.useroperations.model.DropBoxRegistrationRequest;
import com.dropbox.useroperations.model.DropBoxRegistrationResponse;

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
