package com.dropbox.webservice.contracts.useroperations;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.dropbox.useroperations.model.DropBoxLoginRequest;
import com.dropbox.useroperations.model.DropBoxLoginResponse;
import com.dropbox.useroperations.model.DropBoxRegistrationRequest;
import com.dropbox.useroperations.model.DropBoxRegistrationResponse;

@WebService(name = "UserOperationsService")
public interface IUserOperationsWS {

	@WebMethod(operationName = "RegisterUser")
	@WebResult(name = "RegistrationResponse")
	public DropBoxRegistrationResponse registerUser(
			@WebParam(name = "RegistrationRequest") DropBoxRegistrationRequest request)
			throws Exception;
	
	
	@WebMethod(operationName = "AuthenticateUser")
	@WebResult(name = "AuthenticationResponse")
	public DropBoxLoginResponse authenticateUser(
			@WebParam(name = "AuthenticationRequest") DropBoxLoginRequest request)
			throws Exception;

}