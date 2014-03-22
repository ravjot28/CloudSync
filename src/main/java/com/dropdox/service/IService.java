package com.dropdox.service;


public interface IService {

	public Object processRequest(Object dto);

	public void validateRequest(Object dto) throws Exception;

}
