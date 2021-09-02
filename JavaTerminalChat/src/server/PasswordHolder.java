package server;

import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHolder {
	private final String salt;
	private final String hash;
	
	public PasswordHolder(String salt, String hash) {
		this.salt = salt;
		this.hash = hash;
	}
	
	public PasswordHolder(String password) {
		Random random = new Random();
		this.salt = Long.toString(Math.abs(random.nextLong()));
		
		//add salt to password
		String stringToHash = new String(salt + password);
		
		//use SHA-256 algorithm to create hash
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		messageDigest.update(stringToHash.getBytes());
		this.hash = new String(messageDigest.digest());	
	}
	
	public String getSalt() {
		return salt;
	}
	public String getHash() {
		return hash;
	}
}
