// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

import jmafia.roles.Role ;

public class ServerData {
	// Private Fields
	private static ServerData data ;
	// Public Fields
	public int dayCount ;
	public long mafiaChatTimer ;
	public long publicChatTimer ;
	public boolean mafiaChat ;
	public boolean publicChat ;

	public boolean lecterCuredSelf ;
	public boolean doctorCuredSelf ;
	public boolean mayorUsedAbility ;
	public boolean sniperKilledCivilian ;

	public String phase ;
	public String NULL_RESPONSE ;
	public ArrayList<String> alive ;
	public ArrayList<String> roles ;
	public ArrayList<Socket> mafias ;
	public ArrayList<Socket> civilians ;
	public HashMap<Socket , Role> socketRoleMap ;
	public HashMap<String , Socket> roleSocketMap ;
	public HashMap<Socket , String> clients ;
	public HashMap<String , Socket> usernames ;
	// Volatile Fields
	public String killed ;
	public String sniped ;
	public String silenced ;
	public String savedMafia ;
	public String savedCivilian ;
	public String titanGuessed;
	public String detectiveGuessed ;
	public ArrayList<String> ready ;
	// Constructor
	private ServerData() {
		dayCount = 0 ;
		mafiaChatTimer = /*(long)35000*/ (long)3000 ;
		publicChatTimer = /*(long)70000*/ (long)3000 ;
		mafiaChat = false ;
		publicChat = false ;

		lecterCuredSelf = false ;
		doctorCuredSelf = false ;
		mayorUsedAbility = false ;
		sniperKilledCivilian = false ;

		phase = "NIGHT" ;
		NULL_RESPONSE = "NULL_RESPONSE" ;
		alive = new ArrayList<String>() ;
		roles = new ArrayList<String>() ;
		mafias = new ArrayList<Socket>() ;
		civilians = new ArrayList<Socket>() ;
		socketRoleMap = new HashMap<Socket , Role>() ;
		roleSocketMap = new HashMap<String , Socket>() ;
		clients = new HashMap<Socket , String>() ;
		usernames = new HashMap<String , Socket>() ;

		killed = "NULL" ;
		sniped = "NULL" ;
		silenced = "NULL" ;
		savedMafia = "NULL" ;
		savedCivilian = "NULL" ;
		titanGuessed = "NULL" ;
		detectiveGuessed = "NULL" ;
		ready = new ArrayList<String>() ;
	}
	// Methods
	public static ServerData getInstance() {
		if ( data == null )
			data = new ServerData() ;
		return data ;
	}
	public void resetVolatile() {
		killed = "NULL" ;
		sniped = "NULL" ;
		silenced = "NULL" ;
		savedMafia = "NULL" ;
		savedCivilian = "NULL" ;
		titanGuessed = "NULL" ;
		detectiveGuessed = "NULL" ;
		ready = new ArrayList<String>() ;
	}
}

