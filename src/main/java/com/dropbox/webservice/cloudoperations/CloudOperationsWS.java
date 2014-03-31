package com.dropbox.webservice.cloudoperations;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxGetFilesRequest;
import com.dropbox.cloudoperations.model.DropBoxGetFilesResponse;
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
		response = (DropBoxUploadResponse) new CloudOperationsService().processRequest(request);
		return response;
	}

	@Override
	public DropBoxDownloadResponse downloadFiles(DropBoxDownloadRequest request) {
		DropBoxDownloadResponse response = null;
		response = (DropBoxDownloadResponse) new CloudOperationsService().processRequest(request);
		return response;
	}

	@Override
	public DropBoxGetFilesResponse getFiles(DropBoxGetFilesRequest request) {
		DropBoxGetFilesResponse response = null;
		response = (DropBoxGetFilesResponse) new CloudOperationsService().processRequest(request);
		return response;
	}
}
