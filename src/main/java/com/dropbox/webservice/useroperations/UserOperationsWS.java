package com.dropbox.webservice.useroperations;

import javax.jws.WebService;

import com.dropbox.useroperations.model.DropBoxRegistrationRequest;
import com.dropbox.useroperations.model.DropBoxRegistrationResponse;
import com.dropbox.useroperations.service.RegistrationService;
import com.dropbox.webservice.contracts.useroperations.IUserOperationsWS;
import com.dropdox.service.IService;

@WebService(endpointInterface = "com.dropbox.webservice.contracts.useroperations.IUserOperationsWS", serviceName = "UserOperationsService", portName = "UserOperationsPort")
public class UserOperationsWS implements IUserOperationsWS {

	private IService service;

	public DropBoxRegistrationResponse registerUser(
			DropBoxRegistrationRequest request) throws Exception {
		DropBoxRegistrationResponse response = null;
		service = new RegistrationService();
		response = (DropBoxRegistrationResponse) service
				.processRequest(request);

		return response;
	}

}
