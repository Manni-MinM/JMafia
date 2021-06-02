// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Handler implements Runnable {
	// Fields
	private Socket socket ;
	private static God god = God.getInstance() ;
	private static ServerData data = ServerData.getInstance() ;
	// Constructor
	public Handler(Socket socket) {
		this.socket = socket ;
	}
	// Methods
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
			String msg = "" ;
			do {
				msg = reader.readLine() ;
				if ( msg == null )
					continue ;
				if ( isCommandMsg(msg) ) {
					System.out.println("Command Msg : " + msg) ;
					broadcastCommand(socket , god.respond(msg)) ;
				} else {
					System.out.println("Chat Msg : " + msg) ;
					broadcastMessage(socket , msg) ;
				}
			} while ( true ) ;
		} catch ( SocketException exception ) {
			exception.printStackTrace() ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		} finally {
			data.clients.remove(socket) ;
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
	public void broadcastCommand(Socket self , String msg) {
		try {
			PrintWriter writer = new PrintWriter(self.getOutputStream() , true) ;
			writer.println(msg) ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
	}
	public void broadcastMessage(Socket self , String msg) {
		for ( Socket receiver : data.clients )
			try {
				if ( receiver != self ) {
					PrintWriter writer = new PrintWriter(receiver.getOutputStream() , true) ;
					writer.println(msg) ;
				}
			} catch ( IOException exception ) {
				exception.printStackTrace() ;
			}
	}
}

