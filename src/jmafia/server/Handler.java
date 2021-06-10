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
			while ( !god.data.clients.containsKey(socket) ) {
				clientResponse = reader.readLine() ;
				god.process(socket , clientResponse) ;
			}
			// Run Handler
			while ( true ) {
				clientResponse = reader.readLine() ;
				if ( isCommandMsg(clientResponse) ) {
					god.process(socket , clientResponse) ;
				} else {
					if ( DEBUG )
						System.out.println("RECV MSG : [USERNAME : " + god.data.clients.get(socket) + "] => " + clientResponse) ;
					if ( god.data.publicChat ) {
						god.broadcastPublicMessage(socket , clientResponse) ;
					} else if ( god.data.mafiaChat ) {
						god.broadcastMafiaMessage(socket , clientResponse) ;
					} else {
						// Do Nothing
					}
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

