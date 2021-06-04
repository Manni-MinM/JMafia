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
	private static boolean DEBUG = true ;
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

			// Request Username
			god.requestUsername(socket) ;
			clientResponse = reader.readLine() ;
			god.process(socket , clientResponse) ;
			// Greet User
			god.sendWelcomeMessage(socket) ;
			clientResponse = reader.readLine() ;
			god.process(socket , clientResponse) ;
			// Send Role to User
			god.sendRole(socket) ;
			clientResponse = reader.readLine() ;
			god.process(socket , clientResponse) ;
			
			while ( true ) {
				clientResponse = reader.readLine() ;
				if ( isCommandMsg(clientResponse) ) 
					god.process(socket , clientResponse) ;
				else {
					if ( DEBUG )
						System.out.println("RECV MSG : [USERNAME : " + god.data.clients.get(socket) + "] => " + clientResponse) ;
					god.broadcastUserMessage(socket , clientResponse) ;
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
	public boolean isCommandMsg(String msg) {
		return (msg.charAt(0) == '$') ;
        }
}

