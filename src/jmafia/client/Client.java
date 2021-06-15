// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.* ;

/**
 * The Client Class
 *
 * @author Manni Moghimi
 * @version v1.0
 */
public class Client {
	// Fields
	private static int port = 6969 ;
	// Methods
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		// Ask Port
		Scanner scanner = new Scanner(System.in) ;
		System.out.print("Port ? ") ;
		port = scanner.nextInt() ;
		// Run the Client
		try {
			Socket socket = new Socket("127.0.0.1" , port) ;
			System.out.println("[CONNECTED TO SERVER]") ;

			ClientData data = new ClientData() ;
			Jesus jesus = new Jesus(data) ;
			SynchronousQueue<String> queue = new SynchronousQueue<String>(true) ;

			Thread writeThread = new Thread(new WriteThread(socket.getOutputStream() , queue , jesus)) ;
			Thread readThread = new Thread(new ReadThread(socket.getInputStream() , queue , jesus)) ;
			writeThread.start() ;
			readThread.start() ;

		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

