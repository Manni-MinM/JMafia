// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server {
	// Fields
	private static int port = 6969 ;
	private static int userCount = 0 ;
	private static final int maxCapacity = 3 ;
	private static God god = God.getInstance() ;
	// Methods
	public static void main(String[] args) {
		try ( ServerSocket serverSocket = new ServerSocket(port) ) {
			System.out.println("[SERVER STARTED] : Listening on Port " + port) ;
			while ( userCount < maxCapacity ) {
				Socket socket = serverSocket.accept() ;
				Thread handler = new Thread(new Handler(socket)) ;
				handler.start() ;
				userCount ++ ;

				god.requestUsername(socket) ;
				god.sendWelcomeMessage(socket) ;
				god.sendRole(socket) ;
			}
			god.runFirstNight() ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

