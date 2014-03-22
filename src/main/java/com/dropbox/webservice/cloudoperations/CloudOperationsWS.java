package com.dropbox.webservice.cloudoperations;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxUploadRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadResponse;
import com.dropbox.webservice.contracts.cloudoperations.ICloudOperationsWS;
import com.dropvbox.cloudoperation.service.CloudOperationsService;

@MTOM
@WebService(endpointInterface = "com.dropbox.webservice.contracts.cloudoperations.ICloudOperationsWS", serviceName = "CloudOperationsService", portName = "CloudOperationsPort")
public class CloudOperationsWS implements ICloudOperationsWS {

	@Override
	public DropBoxUploadResponse uploadFile(DropBoxUploadRequest request)
			throws Exception {
		DropBoxUploadResponse response = null;
		new CloudOperationsService().processRequest(request);
		return response;
	}

	@Override
	public DropBoxDownloadResponse downloadFiles(DropBoxDownloadRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
