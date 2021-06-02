// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class WriteThread implements Runnable {
	// Fields
	private String username ;
	private PrintWriter writer ;
	// Constructor
	public WriteThread(OutputStream out) {
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
			msg = scanner.nextLine() ;
			sendMessage(msg) ;
		} while ( true ) ;
	} 
	public boolean isCommandMsg(String msg) {
		// $user@function:count:param1-param2-param3-...-paramCount
		return (msg.charAt(0) == '$') ;
	}
	public void sendCommand(String command) {
		writer.println("$" + username + "@" + command) ;
	}
	public void sendMessage(String msg) {
		writer.println("[" + username + "]: " + msg) ;
	}
}

