package com.dropbox.webservice.cloudoperations;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.dropbox.cloudoperation.service.CloudOperationsService;
import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyRequest;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyResponse;
import com.dropbox.cloudoperations.model.DropBoxGetFilesRequest;
import com.dropbox.cloudoperations.model.DropBoxGetFilesResponse;
import com.dropbox.cloudoperations.model.DropBoxGetSharedFileRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadResponse;
import com.dropbox.util.EncryptDecryptByteArr;
import com.dropbox.webservice.contracts.cloudoperations.ICloudOperationsWS;

@MTOM
@WebService(endpointInterface = "com.dropbox.webservice.contracts.cloudoperations.ICloudOperationsWS", serviceName = "CloudOperationsService", portName = "CloudOperationsPort")
public class CloudOperationsWS implements ICloudOperationsWS {

	@Override
	public DropBoxUploadResponse uploadFile(DropBoxUploadRequest request)
			throws Exception {
		DropBoxUploadResponse response = null;
		try {
			request.setFile(EncryptDecryptByteArr.encrypt(request.getFile()));
			response = (DropBoxUploadResponse) new CloudOperationsService().processRequest(request);
		} catch (Exception e) {
			response = new DropBoxUploadResponse();
			response.setErrorMessage("Encryption failed");
			response.setStatus("FAIL");
		}
		
		return response;
	}

	@Override
	public DropBoxDownloadResponse downloadFiles(DropBoxDownloadRequest request) {
		DropBoxDownloadResponse response = null;
		response = (DropBoxDownloadResponse) new CloudOperationsService().processRequest(request);
		try {
			response.setFile(EncryptDecryptByteArr.decrypt(response.getFile()));
		} catch (Exception e) {
			response.setErrorMessage("Decryption failed");
			response.setStatus("FAIL");
		}
		return response;
	}

	@Override
	public DropBoxGetFilesResponse getFiles(DropBoxGetFilesRequest request) {
		DropBoxGetFilesResponse response = null;
		response = (DropBoxGetFilesResponse) new CloudOperationsService().processRequest(request);
		return response;
	}

	@Override
	public DropBoxGenerateSharingKeyResponse getFileShareKey(
			DropBoxGenerateSharingKeyRequest request) {
		DropBoxGenerateSharingKeyResponse response = null;
		response = (DropBoxGenerateSharingKeyResponse) new CloudOperationsService().processRequest(request);
		return response;
	}

	@Override
	public DropBoxDownloadResponse getSharedFile(
			DropBoxGetSharedFileRequest request) {
		DropBoxDownloadResponse response = null;
		response = (DropBoxDownloadResponse) new CloudOperationsService().processRequest(request);
		
		try {
			response.setFile(EncryptDecryptByteArr.decrypt(response.getFile()));
		} catch (Exception e) {
			response.setErrorMessage("Decryption failed");
			response.setStatus("FAIL");
		}
		
		
		return response;
	}

	
}
