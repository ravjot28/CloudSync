package com.dropbox.cloudoperation.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyRequest;
import com.dropbox.cloudoperations.model.DropBoxGenerateSharingKeyResponse;
import com.dropbox.cloudoperations.model.DropBoxGetFilesRequest;
import com.dropbox.cloudoperations.model.DropBoxGetFilesResponse;
import com.dropbox.cloudoperations.model.DropBoxGetSharedFileRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadResponse;
import com.dropbox.exception.InvalidRequestException;
import com.dropbox.service.IService;
import com.dropbox.useroperations.bean.DropBoxUserBean;
import com.dropbox.useroperations.dao.UserOperationsDAO;
import com.dropbox.util.CustomEncryptionImpl;

public class CloudOperationsService implements IService {
	@Override
	public Object processRequest(Object dto) {
		Object response = null;
		if (dto instanceof DropBoxUploadRequest)
			response = uploadFile((DropBoxUploadRequest) dto);
		else if (dto instanceof DropBoxDownloadRequest)
			response = downloadFiles((DropBoxDownloadRequest) dto);
		else if (dto instanceof DropBoxGetFilesRequest)
			response = getFiles((DropBoxGetFilesRequest) dto);
		else if (dto instanceof DropBoxGenerateSharingKeyRequest)
			response = getSharedKey((DropBoxGenerateSharingKeyRequest) dto);
		else if (dto instanceof DropBoxGetSharedFileRequest)
			response = getSharedFile((DropBoxGetSharedFileRequest) dto);
		return response;
	}

	private Object getSharedFile(DropBoxGetSharedFileRequest request) {
		DropBoxDownloadResponse response = null;
		UserOperationsDAO dao = new UserOperationsDAO();
		try {
			dao.validateUserPasssword(request.getUserName(),
					request.getPassword());
			CustomEncryptionImpl impl = CustomEncryptionImpl.getInstance();

			String key = impl.decryptNum(request.getKey());
			StringTokenizer token = new StringTokenizer(key, ":");
			String password = token.nextToken();
			int userId = Integer.parseInt(token.nextToken());
			String sharedUserName = token.nextToken();
			String fileName = token.nextToken();

			if (sharedUserName.equals(request.getUserName())) {
				DropBoxUserBean bean = dao.searchUser(userId);
				DropBoxDownloadRequest req = new DropBoxDownloadRequest();
				req.setFileName(fileName);
				req.setPassword(password);
				req.setUserName(bean.getUserName());

				response = (DropBoxDownloadResponse) downloadFiles(req);
				response.setFileName(fileName);
			} else {
				response = new DropBoxDownloadResponse();
				response.setErrorMessage("Shared User Name is mismatching");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new DropBoxDownloadResponse();
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	private Object getSharedKey(DropBoxGenerateSharingKeyRequest request) {
		DropBoxGenerateSharingKeyResponse response = null;
		UserOperationsDAO dao = new UserOperationsDAO();
		try {
			DropBoxUserBean bean = dao.validateUserPasssword(
					request.getUserName(), request.getPassword());

			CustomEncryptionImpl impl = CustomEncryptionImpl.getInstance();

			String key = impl.encryptNum(bean.getUserId(),
					request.getPassword(), request.getSharedUserName(),
					request.getFileName());

			response = new DropBoxGenerateSharingKeyResponse();
			response.setKey(key);

		} catch (Exception e) {
			e.printStackTrace();
			response = new DropBoxGenerateSharingKeyResponse();
			response.setKey(null);
		}
		return response;
	}

	private Object getFiles(DropBoxGetFilesRequest request) {
		DropBoxGetFilesResponse response = null;
		AmazonCloudOperations aco = new AmazonCloudOperations();
		response = new DropBoxGetFilesResponse();
		response.setFilesName(aco.getFiles(request.getUserName()));
		return response;
	}

	private Object downloadFiles(DropBoxDownloadRequest request) {
		DropBoxDownloadResponse response = null;
		response = new DropBoxDownloadResponse();
		UserOperationsDAO dao = new UserOperationsDAO();

		try {
			validateRequest(request);
			if (dao.validateUserPasssword(request.getUserName(),
					request.getPassword()) == null) {
				response.setStatus("FAILURE");
				response.setErrorMessage("Either User doesn't exist or the credentials are incorrect");
			} else {
				AmazonCloudOperations aco = new AmazonCloudOperations();

				response.setStatus("SUCCESS");
				response.setFile(aco.downloadFile(request.getFileName(),
						request.getUserName()));
			}
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		}

		return response;
	}

	private Object uploadFile(DropBoxUploadRequest request) {
		DropBoxUploadResponse response = null;
		UserOperationsDAO dao = new UserOperationsDAO();

		try {
			validateRequest(request);
			response = new DropBoxUploadResponse();
			if (dao.validateUserPasssword(request.getUserName(),
					request.getPassword()) == null) {
				response.setStatus("FAILURE");
				response.setErrorMessage("Either User doesnt exists or the credentials are incorrect");
			} else {
				InputStream is = getFileObject(request.getFile());
				AmazonCloudOperations aco = new AmazonCloudOperations();
				aco.uploadFile(is, request.getFileName(), request.getUserName());
				response.setStatus("SUCCESS");
			}
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAILURE");
			response.setErrorMessage(e.getMessage());
		}

		return response;
	}

	public InputStream getFileObject(byte[] bytes) {
		InputStream stream = new ByteArrayInputStream(bytes);
		return stream;
	}

	@Override
	public void validateRequest(Object dto) throws Exception {
		if (dto instanceof DropBoxUploadRequest) {
			DropBoxUploadRequest request = (DropBoxUploadRequest) dto;

			if (request.getFile() == null)
				throw new InvalidRequestException("Empty File sent");

			if (request.getPassword() == null || request.getUserName() == null)
				throw new InvalidRequestException(
						"Empty username or password sent");
		} else if (dto instanceof DropBoxDownloadRequest) {
			DropBoxDownloadRequest request = (DropBoxDownloadRequest) dto;
			if (request.getFileName() == null)
				throw new InvalidRequestException("Empty File sent");

			if (request.getPassword() == null || request.getUserName() == null)
				throw new InvalidRequestException(
						"Empty username or password sent");
		}
	}

}
