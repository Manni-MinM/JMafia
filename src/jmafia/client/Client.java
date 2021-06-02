// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.* ;

public class Client {
	// Fields
	private static int port = 6969 ;
	// Methods
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1" , port) ;
			System.out.println("[CONNECTED TO SERVER]") ;

			ClientData data = new ClientData() ;
			Jesus jesus = new Jesus(data) ;
			SynchronousQueue<String> queue = new SynchronousQueue<String>() ;

			Thread writeThread = new Thread(new WriteThread(socket.getOutputStream() , queue)) ;
			Thread readThread = new Thread(new ReadThread(socket.getInputStream() , queue , jesus)) ;
			writeThread.start() ;
			readThread.start() ;

		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

