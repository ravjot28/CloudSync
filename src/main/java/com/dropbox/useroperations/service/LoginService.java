package com.dropbox.useroperations.service;

import com.dropbox.exception.InvalidRequestException;
import com.dropbox.useroperations.dao.UserOperationsDAO;
import com.dropbox.useroperations.model.DropBoxLoginRequest;
import com.dropbox.useroperations.model.DropBoxLoginResponse;
import com.dropbox.useroperations.model.DropBoxRegistrationRequest;
import com.dropdox.service.IService;

public class LoginService implements IService {

	@Override
	public Object processRequest(Object dto) {
		DropBoxLoginResponse response = null;
		UserOperationsDAO dao = null;
		try {
			validateRequest(dto);
			dao = new UserOperationsDAO();
			dao.validateUserPasssword(
					((DropBoxLoginRequest) dto).getUserName(),
					((DropBoxLoginRequest) dto).getPassword());
			response = new DropBoxLoginResponse();
			response.setStatus("SUCCESS");
		} catch (InvalidRequestException e1) {
			response = new DropBoxLoginResponse();
			response.setStatus("FAILURE");
			response.setErrorMessage(e1.getMessage());
		} catch (Exception e) {
			response = new DropBoxLoginResponse();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public void validateRequest(Object dto) throws Exception {
		DropBoxRegistrationRequest request = (DropBoxRegistrationRequest) dto;
		UserOperationsDAO dao = null;

		if (request.getPassword() == null || request.getUserName() == null)
			throw new InvalidRequestException("Empty User name or Password");

		dao = new UserOperationsDAO();

		try {
			if (dao.searchUser(request.getUserName()) == null)
				throw new InvalidRequestException("UserName do not exists");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
