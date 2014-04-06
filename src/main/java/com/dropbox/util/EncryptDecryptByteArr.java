package com.dropbox.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecryptByteArr {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B',
			'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	public static byte[] encrypt(byte[] Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data);
		// String encryptedValue = new BASE64Encoder().encode(encVal);
		return encVal;
	}

	public static byte[] decrypt(byte[] encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);

		byte[] decValue = c.doFinal(encryptedData);
		return decValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

	public static void main(String[] args) throws Exception {
		byte[] array = "going to encrypt".getBytes();
		byte[] arrayEnc = EncryptDecryptByteArr.encrypt(array);
		byte[] arrayDec = EncryptDecryptByteArr.decrypt(arrayEnc);

		System.out.println("Plain Text : " + array);
		System.out.println("Encrypted Text : " + arrayEnc);
		System.out.println("Decrypted Text : " + new String(arrayDec));
	}

}