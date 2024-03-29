// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

import jmafia.util.* ;

/**
 * The Handler Class
 *
 * @author Manni Moghimi
 * @version v1.0
 */
public class Handler implements Runnable {
	// Fields
	private Socket socket ;
	private static God god = God.getInstance() ;
	private static boolean DEBUG = true ;
	/**
	 * Instantiates a new Handler.
	 *
	 * @param socket the socket
	 */
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
				if ( clientResponse == null ) {
					break ;
				} else if ( isCommandMsg(clientResponse) ) {
					god.process(socket , clientResponse) ;
				} else {
					if ( DEBUG )
						System.out.println("RECV MSG : [USERNAME : " + god.data.clients.get(socket) + "] => " + clientResponse) ;
					if ( clientResponse.contains("!EXIT") ) {
						break ;
					} else if ( god.data.publicChat ) {
						god.broadcastPublicMessage(socket , clientResponse) ;
					} else if ( god.data.mafiaChat ) {
						god.broadcastMafiaMessage(socket , clientResponse) ;
					} else {
						// Do Nothing
					}
				}
			}
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		} finally {
			god.data.usernames.remove(god.data.clients.get(socket)) ;
			god.data.clients.remove(socket) ;
			god.disconnect(socket) ;
		}
	}
	/**
	 * Is command msg boolean.
	 *
	 * @param msg the msg
	 * @return the boolean
	 */
	public boolean isCommandMsg(String msg) {
		return (msg.charAt(0) == '$') ;
	}
}

