package com.dropbox.webservice.contracts.cloudoperations;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyRequest;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyResponse;
import com.dropbox.cloudoperations.model.DropBoxGetFilesRequest;
import com.dropbox.cloudoperations.model.DropBoxGetFilesResponse;
import com.dropbox.cloudoperations.model.DropBoxGetSharedFileRequest;
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

	@WebMethod(operationName = "GetFiles")
	@WebResult(name = "GetFilesResponse")
	public DropBoxGetFilesResponse getFiles(
			@WebParam(name = "GetFilesRequest") DropBoxGetFilesRequest request);
	
	@WebMethod(operationName = "GetFileShareKey")
	@WebResult(name = "GetFileShareKeyResponse")
	public DropBoxGenerateSharingKeyResponse getFileShareKey(
			@WebParam(name = "GetFileShareKeyRequest") DropBoxGenerateSharingKeyRequest request);
	
	@WebMethod(operationName = "GetFileShareKey")
	@WebResult(name = "GetFileShareKeyResponse")
	public DropBoxDownloadResponse getSharedFile(
			@WebParam(name = "GetFileShareKeyRequest") DropBoxGetSharedFileRequest request);

}