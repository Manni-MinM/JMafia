// BWOTSHEWCHB

package jmafia.client ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class ReadThread implements Runnable {
	// Fields
	private Jesus jesus ;
	private BufferedReader reader ;
	// Constructor
	public ReadThread(InputStream in) {
		this.reader = new BufferedReader(new InputStreamReader(in)) ;
	}
	// Methods
	@Override
	public void run() {
		try {
			while ( true ) {
				String msg = reader.readLine() ;
				if ( isCommandMsg(msg) ) {
					// TODO
					// jesus.respond(msg) ;
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

