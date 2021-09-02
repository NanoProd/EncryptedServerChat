package chat;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
	private BigInteger n;
	private BigInteger e;
	private BigInteger d;
	
	public RSA() {
		//generate two large prime numbers p and q
		Random random = new Random();
		BigInteger p = new BigInteger(520,30, random);
		BigInteger q = new BigInteger(520,30,random);
		
		//calculate modulus n
		this.n = p.multiply(q);
		
		//calculate the totient of the modulus
		BigInteger totient = lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
		
		//private key --> e*d = 1 mod phi(n)
		this.d = coprime(totient);
		//public key --> (e,n) where e is [3, phi(n)]
		this.e = d.modInverse(totient);
		
		
	
	}

	public BigInteger getN() {
		return n;
	}
	public BigInteger getE() {
		return e;
	}
	
	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return (a.multiply(b)).divide(a.gcd(b));
	}
	
	public static BigInteger coprime(BigInteger a) {
		BigInteger candidate = BigInteger.valueOf(2);
		
		while(true) {
			if(a.gcd(candidate).equals(BigInteger.ONE)) {
				return candidate;
			} else {
				candidate = candidate.add(BigInteger.ONE);
			}
		}
	}
	
	//helper methods to encrypt/decrypt single char represented as a bigInt
	private BigInteger encryptBigInt(BigInteger message) {
		//F(message, key) = m^key * mod n
		return message.modPow(e,n);
	}
	
	private BigInteger decryptBigInt(BigInteger messageEncrypted) {
		return messageEncrypted.modPow(d, n);
	}
	
	//encrypt a message
	public String encryptString(String message) {
		byte[] data = message.getBytes();
		return encryptBigInt(new BigInteger(data)).toString();
	}
	public String decryptString(String messageEncrypted) {
		return new String(decryptBigInt(new BigInteger(messageEncrypted)).toByteArray());
	}
	
	
}
