// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class ServerData {
	// Private Fields
	private static ServerData data ;
	// Public Fields
	public ArrayList<String> roles ;
	public ArrayList<Socket> mafias ;
	public ArrayList<Socket> civilians ;
	public HashMap<String , Socket> roleMap ;
	public HashMap<Socket , String> clients ;
	public String phase ;
	public String NULL_RESPONSE ;
	// Constructor
	private ServerData() {
		roles = new ArrayList<String>() ;
		mafias = new ArrayList<Socket>() ;
		civilians = new ArrayList<Socket>() ;
		roleMap = new HashMap<String , Socket>() ;
		clients = new HashMap<Socket , String>() ;
		phase = "NIGHT" ;
		NULL_RESPONSE = "NULL_RESPONSE" ;
	}
	// Methods
	public static ServerData getInstance() {
		if ( data == null )
			data = new ServerData() ;
		return data ;
	}
}

