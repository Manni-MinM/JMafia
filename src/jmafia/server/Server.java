// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server {
	// Fields
	private static int port = 6969 ;
	private static int userCount = 0 ;
	private static final int maxCapacity = 6 ;
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
			}
			do {
				try {
					Thread.currentThread().sleep(100) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			} while ( god.data.clients.keySet().size() < maxCapacity ) ;
			try {
				Thread.currentThread().sleep(100) ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
			// Day Zero
			god.runFirstNight() ;
			god.runFirstDay() ;
			god.nextDay() ;
			// Run Game
			god.runNight() ;
//			god.runDay() ;
//			god.runVoting() ;
			god.nextDay() ;
			// End Game
			
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

