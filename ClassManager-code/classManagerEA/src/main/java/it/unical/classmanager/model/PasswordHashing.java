package it.unical.classmanager.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashing {
	
	public PasswordHashing() {
	}
	
	
	/**
	 * Iterations for the hash
	 */
	private int ITERATIONS = 4096;
	
	/**
	 * 32 bytes key and salt length for 256 bit keys and salt
	 */
	private static final int KEYLENGTH = 32;
	
	
	/**
	 * The method computes the hash of the given password using a 128 bit salt string randomly generated
	 * @param password for which you intend to compute the hash
	 * @return
	 */
	public String getHashAndSalt(String password) {
	

		final Random r = new SecureRandom();
		byte[] salt = new byte[KEYLENGTH];
		r.nextBytes(salt);
		
		byte[] hash = hash(password,salt);
		
		return byteArrayToHex(hash)+":"+byteArrayToHex(salt);
	}
	
	public String getHash(String passwordString,String saltString) {
		
		byte[] salt = hexStringToByteArray(saltString);
		byte[] hash = hash(passwordString, salt);
		
		return byteArrayToHex(hash);
		
	}
	
	private byte[] hash(String password,byte[] salt) {
		
		byte[] hash = null;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEYLENGTH * 8);
		SecretKeyFactory f;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			hash = f.generateSecret(spec).getEncoded();
		} catch ( NoSuchAlgorithmException e ) {
			//TODO handle here 
			e.printStackTrace();
				
		} catch (InvalidKeySpecException e) {
			// TODO Honestly I don't know how to handle this
			e.printStackTrace();
		}
		
		return hash;
	}
	

	/**
	 * Utility method to convert a ByteArray to hex string, to be stored in the DB
	 * @param a byteArray to convert.
	 * @return hex String representing the given byteArray
	 */
	private String byteArrayToHex(byte[] a) {
	   StringBuilder sb = new StringBuilder(a.length * 2);
	   for(byte b: a)
	      sb.append(String.format("%02x", b & 0xff));
	   return sb.toString();
	}
	
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	
}
