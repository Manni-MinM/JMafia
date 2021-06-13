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
	public int playerCount ;
	public long mafiaChatTimer ;
	public long publicChatTimer ;
	public boolean mafiaChat ;
	public boolean publicChat ;

	public boolean lecterCuredSelf ;
	public boolean doctorCuredSelf ;
	public boolean mayorUsedAbility ;
	public boolean sniperKilledCivilian ;

	public String NULL_RESPONSE ;
	public ArrayList<String> alive ;
	public ArrayList<String> roles ;
	public ArrayList<Socket> mafias ;
	public ArrayList<Socket> civilians ;
	public HashMap<Socket , Role> socketRoleMap ;
	public HashMap<String , Socket> roleSocketMap ;
	public HashMap<Socket , String> clients ;
	public HashMap<String , Socket> usernames ;
	public HashMap<String , Socket> allUsernames ;
	public HashMap<String , Integer> voteCounter ;
	// Volatile Fields
	public String killed ;
	public String sniped ;
	public String silenced ;
	public String savedMafia ;
	public String savedCivilian ;
	public String mayorDecision ;
	public String titanGuessed;
	public String detectiveGuessed ;
	public ArrayList<String> ready ;
	public HashMap<String , Integer> votes ;
	public HashMap<String , String> voteMap ;
	// Constructor
	private ServerData() {
		dayCount = 0 ;
		playerCount = 0 ;
		mafiaChatTimer = /*(long)60000*/ (long)30000 ;
		publicChatTimer = /*(long)300000*/ (long)30000 ;
		mafiaChat = false ;
		publicChat = false ;

		lecterCuredSelf = false ;
		doctorCuredSelf = false ;
		mayorUsedAbility = false ;
		sniperKilledCivilian = false ;

		NULL_RESPONSE = "NULL_RESPONSE" ;
		alive = new ArrayList<String>() ;
		roles = new ArrayList<String>() ;
		mafias = new ArrayList<Socket>() ;
		civilians = new ArrayList<Socket>() ;
		socketRoleMap = new HashMap<Socket , Role>() ;
		roleSocketMap = new HashMap<String , Socket>() ;
		clients = new HashMap<Socket , String>() ;
		usernames = new HashMap<String , Socket>() ;
		allUsernames = new HashMap<String , Socket>() ;
		voteCounter = new HashMap<String , Integer>() ;

		killed = "NULL" ;
		sniped = "NULL" ;
		silenced = "NULL" ;
		savedMafia = "NULL" ;
		savedCivilian = "NULL" ;
		mayorDecision = "NULL" ;
		titanGuessed = "NULL" ;
		detectiveGuessed = "NULL" ;
		ready = new ArrayList<String>() ;
		votes = new HashMap<String , Integer>() ;
		voteMap = new HashMap<String , String>() ;
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
		mayorDecision = "NULL" ;
		titanGuessed = "NULL" ;
		detectiveGuessed = "NULL" ;
		ready = new ArrayList<String>() ;
		votes = new HashMap<String , Integer>() ;
		voteMap = new HashMap<String , String>() ;
	}
}

