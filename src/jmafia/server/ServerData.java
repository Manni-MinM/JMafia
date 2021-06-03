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
	public HashMap<Socket , String> clientMap ;
	// Constructor
	private ServerData() {
		clients = new ArrayList<Socket>() ;
		clientMap = new HashMap<Socket , String>() ;
	}
	// Methods
	public static ServerData getInstance() {
		if ( data == null )
			data = new ServerData() ;
		return data ;
	}
}

