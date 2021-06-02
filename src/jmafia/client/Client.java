// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Client {
	// Fields
	private static int port = 6969 ;
	// Methods
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1" , port) ;
			System.out.println("[CONNECTED TO SERVER]") ;

			Thread writeThread = new Thread(new WriteThread(socket.getOutputStream())) ;
			Thread readThread = new Thread(new ReadThread(socket.getInputStream())) ;
			writeThread.start() ;
			readThread.start() ;

		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

