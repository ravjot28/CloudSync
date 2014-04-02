package com.dropbox.util;

import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import RavBase64.Base64Decoder;
import RavBase64.Base64Encoder;

public class CustomEncryptionImpl {

	private static Logger logger = LoggerFactory
			.getLogger(CustomEncryptionImpl.class);

	private static CustomEncryptionImpl SINGLETON;

	private CustomEncryptionImpl() throws Exception,
			NoSuchAlgorithmException, NoSuchPaddingException {
		if (SINGLETON != null) {
			throw new Exception(
					CustomEncryptionImpl.class.getName());
		}
	}

	public static CustomEncryptionImpl getInstance()
			throws Exception, NoSuchAlgorithmException,
			NoSuchPaddingException {
		if (SINGLETON == null)

			SINGLETON = new CustomEncryptionImpl();

		return SINGLETON;
	}

	public String encryptNum(int userId,String password,String shareUserName,String fileName) {
		int finalNumber = 0;
		while (true) {
			finalNumber = finalNumber * 10 + userId % 10;
			userId = (userId - userId % 10) / 10;
			if (userId == 0)
				break;
		}
		logger.info("For Encryption for Number the parsed number is "
				+ finalNumber);
		return encryptText(new StringBuffer("" + finalNumber),password,shareUserName,fileName);
	}

	public String decryptNum(String text) {
		String decryptText = decryptText(new StringBuffer(text));
		StringTokenizer token = new StringTokenizer(decryptText,":");
		String password = token.nextToken();
		int number = Integer.parseInt(token.nextToken());
		String shareUserName = token.nextToken();
		String fileName = token.nextToken();
		int finalNumber = 0;
		while (true) {
			finalNumber = finalNumber * 10 + number % 10;
			number = (number - number % 10) / 10;
			if (number == 0)
				break;
		}
		logger.info("Decrypted Number is " + finalNumber);
		return password+":"+finalNumber+":"+shareUserName+":"+fileName;
	}

	public String encryptText(StringBuffer userId,String password,String shareUserName,String fileName) {
		userId = addPad(userId,password,shareUserName,fileName);
		Base64Encoder encoder = new Base64Encoder(userId.toString());
		return encoder.get();
	}

	public String decryptText(StringBuffer text) {
		Base64Decoder decoder = new Base64Decoder(text.toString());

		text = new StringBuffer(decoder.get());
		return text.toString();
	}

	private StringBuffer addPad(StringBuffer userId,String password,String shareUserName,String fileName) {
		userId.insert(0, password+":");

		userId.append(":"+shareUserName+":"+fileName);

		return userId;

	}
	
	public static void main(String ar[]) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception{
		System.out.println(CustomEncryptionImpl.getInstance().encryptNum(101, "ravjot", "harman","filename"));
		
		System.out.println(CustomEncryptionImpl.getInstance().decryptNum(CustomEncryptionImpl.getInstance().encryptNum(101, "ravjot", "harman","fileName")));
	}

}