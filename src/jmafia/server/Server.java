// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server {
	// Fields
	private static int port ;
	private static int userCount = 0 ;
	private static final int maxCapacity = 10 ;
	private static God god = God.getInstance() ;
	// Methods
	public static void main(String[] args) {
		// Ask Port
		Scanner scanner = new Scanner(System.in) ;
		System.out.print("Port ? ") ;
		port = scanner.nextInt() ;
		// Run the Server
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
			while ( !god.endgame() ) {
				god.runNight() ;
				if ( god.endgame() )
					break ;
				god.runDay() ;
				god.runVoting() ;
				god.nextDay() ;
			}
			
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

