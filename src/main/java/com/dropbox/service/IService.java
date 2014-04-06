package com.dropbox.service;


public interface IService {

	public Object processRequest(Object dto);

	public void validateRequest(Object dto) throws Exception;

}
