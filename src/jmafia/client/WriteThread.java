// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.* ;

public class WriteThread implements Runnable {
	// Fields
	private String username ;
	private PrintWriter writer ;
	private SynchronousQueue<String> queue ;
	// Constructor
	public WriteThread(OutputStream out , SynchronousQueue<String> queue) {
		this.queue = queue ;
		this.writer = new PrintWriter(out , true) ;
	}
	// Methods
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in) ;
		
		System.out.print("Username : ") ;
		username = scanner.nextLine() ;
		sendCommand("register:0:") ;

		String msg = "" ;
		do {
			String command = null ;
			while ( queue.size() > 0 ) {
				try {
					command = queue.take() ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
				sendCommand(command) ;
			}
			msg = scanner.nextLine() ;
			sendMessage(msg) ;
		} while ( true ) ;
	} 
	public void sendCommand(String command) {
		// $user@function:count:param1-param2-param3-...-paramCount
		if ( command == null )
			return ;
		writer.println("$" + username + "@" + command) ;
	}
	public void sendMessage(String msg) {
		if ( msg == null )
			return ;
		writer.println("[" + username + "]: " + msg) ;
	}
}

