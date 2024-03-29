// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

/**
 * The Server Class
 *
 * @author Manni Moghimi
 * @version v1.0
 */
public class Server {
	// Fields
	private static int port = 6969 ;
	private static int userCount = 0 ;
	private static int playerCapacity ;
	private static God god = God.getInstance() ;
	// Methods
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in) ;
		// Ask Port
		System.out.print("[PORT] : ") ;
		port = scanner.nextInt() ;
		// Ask Player Count
		while ( playerCapacity > 10 || playerCapacity < 6 ) {
			System.out.print("[PLAYER COUNT] : ") ;
			playerCapacity = scanner.nextInt() ;
			god.data.playerCount = playerCapacity ;
		}
		// Run the Server
		god.init() ;
		try ( ServerSocket serverSocket = new ServerSocket(port) ) {
			System.out.println("[SERVER STARTED] : Listening on Port " + port) ;
			while ( userCount < playerCapacity ) {
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
			} while ( god.data.clients.keySet().size() < playerCapacity ) ;
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

