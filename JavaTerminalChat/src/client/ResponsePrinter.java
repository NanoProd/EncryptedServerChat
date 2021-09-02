package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import chat.RSA;

public class ResponsePrinter implements Runnable {
	private RSA decryptor;
	private BufferedReader socketReader;
	
	public ResponsePrinter(Socket socket, RSA decryptor) throws IOException {
		this.decryptor = decryptor;
		this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {
		try
		{
			String line = null;
			while(true) {
				line = socketReader.readLine();
				if(line != null) {
					System.out.println(decryptor.decryptString(line));
				} else {
					System.out.println("SERVER DISCONNECTED");
					System.exit(0);
				}
			}
		}catch(IOException e) {
			System.out.println("Thread Interruption: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public String readLine() throws IOException{
		return socketReader.readLine();
	}
}
