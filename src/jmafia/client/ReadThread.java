// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.* ;

public class ReadThread implements Runnable {
	// Fields
	private Jesus jesus ;
	private SynchronousQueue<String> queue ;
	private BufferedReader reader ;
	private static boolean DEBUG = false ;
	// Constructor
	public ReadThread(InputStream in , SynchronousQueue<String> queue , Jesus jesus) {
		this.queue = queue ;
		this.jesus = jesus ;
		this.reader = new BufferedReader(new InputStreamReader(in)) ;
	}
	// Methods
	@Override
	public void run() {
		try {
			while ( true ) {
				String msg = reader.readLine() ;
				if ( msg == null )
					continue ;
				if ( isCommandMsg(msg) ) {
					try {
						if ( DEBUG )
							System.out.println("RECV CMD : " + msg) ;
						String response = jesus.respond(msg) ;
						queue.put(response) ;
					} catch ( InterruptedException exception ) {
						exception.printStackTrace() ;
					}
				} else {
					if ( DEBUG )
						System.out.println("RECV MSG : " + msg) ;
					System.out.println(msg) ;
				}
			}
		} catch ( SocketException exception) {
			exception.printStackTrace() ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		} finally {
			try {
				reader.close() ;
			} catch ( IOException exception ) {
				exception.printStackTrace() ;
			}
		}
	}
	public boolean isCommandMsg(String msg) {
		return (msg.charAt(0) == '$') ;
	}
}

