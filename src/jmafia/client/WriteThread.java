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
	private static boolean DEBUG = false ;
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
		boolean polled = false ;
		do {
			String command = null ;
			try {
				Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			try {
				command = queue.poll() ;
				if ( command != null ) {
					sendCommand(command) ;
					polled = true ;
					if ( DEBUG )
						System.out.println("SENT CMD : " + command) ;
				}
			} catch ( Exception exception ) {
				exception.printStackTrace() ;
			}
			if ( !polled && jesus.data.chatIsOpen() ) {
				int availableBytes = 0 ;
				try {
					availableBytes = System.in.available() ;
				} catch ( IOException exception ) {
					exception.printStackTrace() ;
				}
				if ( availableBytes > 0 ) {
					msg = scanner.nextLine() ;
					sendMessage(msg) ;
					if ( DEBUG )
						System.out.println("SENT MSG : " + msg) ;
				}
			}
			polled = false ;
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

