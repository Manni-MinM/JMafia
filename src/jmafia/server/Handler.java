// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

import jmafia.util.* ;

public class Handler implements Runnable {
	// Fields
	private Socket socket ;
	private static God god = God.getInstance() ;
	// Constructor
	public Handler(Socket socket) {
		this.socket = socket ;
	}
	// Methods
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;	
			
			String clientResponse ;
			while ( true ) {
				clientResponse = reader.readLine() ;
				synchronized ( socket ) {
					god.process(socket , clientResponse) ;
					socket.notify() ;
				}
			}
		} catch ( SocketException exception ) {
			exception.printStackTrace() ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		} finally {
			try {
				socket.close() ;
			} catch ( IOException exception ) {
				exception.printStackTrace() ;
			}
		}
	}
}

