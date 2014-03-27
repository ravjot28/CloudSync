package com.dropvbox.cloudoperation.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.cloudoperations.model.DropBoxDownloadRequest;
import com.dropbox.cloudoperations.model.DropBoxDownloadResponse;
import com.dropbox.cloudoperations.model.DropBoxUploadRequest;
import com.dropbox.cloudoperations.model.DropBoxUploadResponse;
import com.dropbox.exception.InvalidRequestException;
import com.dropbox.useroperations.dao.UserOperationsDAO;
import com.dropdox.service.IService;

public class CloudOperationsService implements IService {
	@Override
	public Object processRequest(Object dto) {
		Object response = null;
		if (dto instanceof DropBoxUploadRequest)
			response = uploadFile((DropBoxUploadRequest) dto);
		else if (dto instanceof DropBoxDownloadRequest)
			response = downloadFiles((DropBoxDownloadRequest) dto);

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
				response.setErrorMessage("Either User doesnt exists or the credentials are incorrect");
			} else {
				List<Object> list = new ArrayList<Object>();
				AmazonCloudOperations aco = new AmazonCloudOperations();
				for (String fileName : request.getFileNames())
					list.add(aco.downloadFile(fileName, request.getUserName()));
				response.setStatus("SUCCESS");
				response.setList(list);
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
			if (request.getFileNames() == null)
				throw new InvalidRequestException("Empty File sent");

			if (request.getPassword() == null || request.getUserName() == null)
				throw new InvalidRequestException(
						"Empty username or password sent");
		}
	}

}
