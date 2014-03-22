package com.dropbox.webservice.contracts.cloudoperations;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxUploadRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadResponse;

@WebService(name = "CloudOperationsService")
public interface ICloudOperationsWS {

	@WebMethod(operationName = "UploadFile")
	@WebResult(name = "UploadResponse")
	public DropBoxUploadResponse uploadFile(
			@WebParam(name = "UploadRequest") DropBoxUploadRequest request)
			throws Exception;
	
	@WebMethod(operationName = "DownloadFile")
	@WebResult(name = "DownloadFileResponse")
	public DropBoxDownloadResponse downloadFiles(
			@WebParam(name = "DownloadFileRequest") DropBoxDownloadRequest request);

}