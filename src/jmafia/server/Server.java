// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server {
	// Fields
	private static int port = 6969 ;
	private static ServerData data = ServerData.getInstance() ;
	// Methods
	public static void main(String[] args) {
		try ( ServerSocket serverSocket = new ServerSocket(port) ) {
			System.out.println("[SERVER STARTED] : Listening on Port " + port) ;
			while ( true ) {
				Socket socket = serverSocket.accept() ;
				data.clients.add(socket) ;
				Thread handler = new Thread(new Handler(socket)) ;
				handler.start() ;
			}
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
}

