// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.* ;

public class WriteThread implements Runnable {
	// Fields
	private Jesus jesus ;
	private PrintWriter writer ;
	private SynchronousQueue<String> queue ;
	// Constructor
	public WriteThread(OutputStream out , SynchronousQueue<String> queue , Jesus jesus) {
		this.jesus = jesus ;
		this.queue = queue ;
		this.writer = new PrintWriter(out , true) ;
	}
	// Methods
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in) ;

		String msg = "" ;
		do {
			String command = null ;
			// TODO : clean up
			try {
				command = queue.poll() ;
				sendCommand(command) ;
			} catch ( Exception exception ) {
				exception.printStackTrace() ;
			}
//			msg = scanner.nextLine() ;
//			sendMessage(msg) ;
		} while ( true ) ;
	} 
	public void sendCommand(String command) {
		// $user@function:count:param1-param2-param3-...-paramCount
		if ( command == null )
			return ;
		writer.println(command) ;
	}
	public void sendMessage(String msg) {
		if ( msg == null )
			return ;
		writer.println("[" + jesus.data.getUsername() + "]: " + msg) ;
	}
}

