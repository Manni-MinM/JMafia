// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class ServerData {
	// Private Fields
	private static ServerData data ;
	// Public Fields
	public int dayCount ;
	public long mafiaChatTimer ;
	public long publicChatTimer ;
	public boolean mafiaChat ;
	public boolean publicChat ;
	public String phase ;
	public String NULL_RESPONSE ;
	public ArrayList<String> roles ;
	public ArrayList<Socket> mafias ;
	public ArrayList<Socket> civilians ;
	public HashMap<String , Socket> roleMap ;
	public HashMap<Socket , String> clients ;
	// Constructor
	private ServerData() {
		dayCount = 0 ;
		mafiaChatTimer = (long)35000 ;
		publicChatTimer = (long)35000 ;
		mafiaChat = false ;
		publicChat = false ;
		phase = "NIGHT" ;
		NULL_RESPONSE = "NULL_RESPONSE" ;
		roles = new ArrayList<String>() ;
		mafias = new ArrayList<Socket>() ;
		civilians = new ArrayList<Socket>() ;
		roleMap = new HashMap<String , Socket>() ;
		clients = new HashMap<Socket , String>() ;
	}
	// Methods
	public static ServerData getInstance() {
		if ( data == null )
			data = new ServerData() ;
		return data ;
	}
}

