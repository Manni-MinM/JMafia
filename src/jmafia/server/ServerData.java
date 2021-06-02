// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class ServerData {
	// Private Fields
	private static ServerData data ;
	// Public Fields
	public ArrayList<Socket> clients ;
	// Constructor
	private ServerData() {
		clients = new ArrayList<Socket>() ;
	}
	// Methods
	public static ServerData getInstance() {
		if ( data == null )
			data = new ServerData() ;
		return data ;
	}
}

