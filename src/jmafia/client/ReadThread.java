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
					// TODO
					try {
						String response = jesus.respond(msg) ;
						if ( response != null )
							queue.put(jesus.respond(msg)) ;
					} catch ( InterruptedException exception ) {
						exception.printStackTrace() ;
					}
				} else {
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

