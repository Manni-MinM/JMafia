// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

import jmafia.util.* ;
import jmafia.roles.Role ;
import jmafia.roles.mafia.* ;
import jmafia.roles.civilian.* ;

public class God {
	// Fields
	public ServerData data ;
	private static God god ;
	private String serverName ;
	private static boolean DEBUG = true ;
	// Constructor
	public God() {
		// Config
		this.data = ServerData.getInstance() ;
		this.serverName = "The Holy One" ;
	}
	// Methods
	public static God getInstance() {
		if ( god == null )
			god = new God() ;
		return god ;
	}
	public void init() {
		initRoles() ;
	}
	public void initRoles() {
		if ( data.playerCount >= 6 ) {
			data.roles.add("The GodFather") ;
			data.roles.add("The Mafia") ;
			data.roles.add("The Doctor") ;
			data.roles.add("The Detective") ;
			data.roles.add("The Psychologist") ;
			data.roles.add("The Civilian") ;
		}
		if ( data.playerCount >= 7 ) {
			data.roles.add("The Titan") ;
		}
		if ( data.playerCount >= 8 ) {
			data.roles.add("Doctor Lecter") ;
		}
		if ( data.playerCount >= 9 ) {
			data.roles.add("The Sniper") ;
		}
		if ( data.playerCount >= 10 ) {
			data.roles.add("The Mayor") ;	
		}
		for ( int it = 0 ; it < data.roles.size() ; it ++ )
			Collections.shuffle(data.roles) ;
	}
	public void runFirstNight() {
		// Greet User
		for ( Socket client : data.clients.keySet() )
			god.sendWelcomeMessage(client) ;
		// Send Role to User
		for ( Socket client : data.clients.keySet() )
			god.sendRole(client) ;
		// Init Lists
		for ( String roleName : data.roleSocketMap.keySet() ) {
			if ( roleName.equals("The GodFather") ) {
				data.mafias.add(data.roleSocketMap.get(roleName)) ;
			} else if ( roleName.equals("Doctor Lecter") ) {
				data.mafias.add(data.roleSocketMap.get(roleName)) ;
			} else if ( roleName.equals("The Mafia") ) {
				data.mafias.add(data.roleSocketMap.get(roleName)) ;
			} else {
				data.civilians.add(data.roleSocketMap.get(roleName)) ;
			}
		}
		for ( String Username : data.allUsernames.keySet() )
			data.voteCounter.put(Username , 0) ;

		String msg = "Introduction Night Has Started !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;

		String introductionMsgMafia = "The Mafias => " ;
		for ( Socket mafia : data.mafias )
			if ( data.clients.containsKey(mafia) && data.clients.get(mafia) != null )
				introductionMsgMafia += data.clients.get(mafia) + " " ;
		for ( Socket mafia : data.mafias )
			if ( data.clients.containsKey(mafia) && data.clients.get(mafia) != null )
				sendMessage(mafia , introductionMsgMafia) ;

		if ( data.roleSocketMap.containsKey("The Doctor") && data.roleSocketMap.containsKey("The Mayor") ) {
			String introductionMsgDoctor = "The Doctor => " + data.clients.get(data.roleSocketMap.get("The Doctor")) ;
			sendMessage(data.roleSocketMap.get("The Mayor") , introductionMsgDoctor) ;
			String introductionMsgMayor = "The Mayor => " + data.clients.get(data.roleSocketMap.get("The Mayor")) ;
			sendMessage(data.roleSocketMap.get("The Doctor") , introductionMsgMayor) ;
		}

		msg = "Introduction Night Has Ended !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
	}
	public void runFirstDay() {
		String msg = "Introduction Day Has Started !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
		// Show All Players
		msg = "Players Alive => " ;
		for ( String username : data.usernames.keySet() )
			msg += username + " " ;
		broadcastMessage(msg) ;
		// Open Chatroom for all clients
		for ( Socket client : data.clients.keySet() )
			openPublicChatroom(client) ;
		// Keep Chatroom open for about a minute
		long timeChatroomOpened = System.currentTimeMillis() ;
		long timeChatroomClosed = System.currentTimeMillis() ;
		while ( timeChatroomClosed - timeChatroomOpened < data.publicChatTimer )
			timeChatroomClosed = System.currentTimeMillis() ;
		// Close Chatroom for all clients
		for ( Socket client : data.clients.keySet() )
			closePublicChatroom(client) ;
		// Display Final Message of the Day
		msg = "Introduction Day Has Ended !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
	}
	public void runNight() {
		String introductionMsgMafia = "Mafias => " ;
		for ( Socket mafia : data.mafias )
			introductionMsgMafia += data.clients.get(mafia) + " " ;
		for ( Socket mafia : data.mafias )
			sendMessage(mafia , introductionMsgMafia) ;

		String msg = "Night " + data.dayCount + " Has Started" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
		// Open Chat for Mafia
		for ( Socket client : data.mafias )
			openMafiaChatroom(client) ;
		// Keep Chatroom open for about half a minute
		long timeChatroomOpened = System.currentTimeMillis() ;
		long timeChatroomClosed = System.currentTimeMillis() ;
		while ( timeChatroomClosed - timeChatroomOpened < data.mafiaChatTimer )
			timeChatroomClosed = System.currentTimeMillis() ;
		// Close Chatroom for all clients
		for ( Socket client : data.mafias )
			closeMafiaChatroom(client) ;
		// Role : GodFather
		String theGodFather = "The GodFather" ;
		if ( isAlive(theGodFather) ) {
			askGodFather(data.roleSocketMap.get(theGodFather)) ;
			while ( true ) {
				if ( !data.killed.equals("NULL") ) 
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			data.ready.add(theGodFather) ;
		}
		// Role : Doctor Lecter 
		String doctorLecter = "Doctor Lecter" ;
		if ( isAlive(doctorLecter) ) {
			askDoctorLecter(data.roleSocketMap.get(doctorLecter)) ;
			while ( true ) {
				if ( !data.savedMafia.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			data.ready.add(doctorLecter) ;
		}
		// Role : The Doctor
		String theDoctor = "The Doctor" ;
		if ( isAlive(theDoctor) ) {
			askTheDoctor(data.roleSocketMap.get(theDoctor)) ;
			while ( true ) {
				if ( !data.savedCivilian.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			data.ready.add(theDoctor) ;
		}
		// Role : The Detective
		String theDetective = "The Detective" ;
		if ( isAlive(theDetective) ) {
			askDetective(data.roleSocketMap.get(theDetective)) ;
			while ( true ) {
				if ( !data.detectiveGuessed.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			Socket targetSocket = data.usernames.get(data.detectiveGuessed) ;
			Role targetRole = data.socketRoleMap.get(targetSocket) ;
			if ( targetRole.detectedMafia() ) {
				sendMessage(data.roleSocketMap.get(theDetective) , "YES") ;
			} else {
				sendMessage(data.roleSocketMap.get(theDetective) , "NO") ;
			}
			data.ready.add(theDetective) ;
		}
		// Role : The Sniper
		String theSniper = "The Sniper" ;
		if ( isAlive(theSniper) ) {
			askSniper(data.roleSocketMap.get(theSniper)) ;
			while ( true ) {
				if ( !data.sniped.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			data.ready.add(theSniper) ;
		}
		// Role : The Psychologist
		String thePsychologist = "The Psychologist" ;
		if ( isAlive(thePsychologist) ) {
			askPsychologist(data.roleSocketMap.get(thePsychologist)) ;
			while ( true ) {
				if ( !data.silenced.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			data.ready.add(thePsychologist) ;
		}
		// Role : The Titan
		String theTitan = "The Titan" ;
		if ( isAlive(theTitan) ) {
			askTitan(data.roleSocketMap.get(theTitan)) ;
			while ( true ) {
				if ( !data.titanGuessed.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			if ( data.titanGuessed.equals("PASS") ) {
				// Do Nothing
			} else {
				Socket targetSocket = data.allUsernames.get(data.titanGuessed) ;
				Role targetRole = data.socketRoleMap.get(targetSocket) ;
				if ( targetRole.detectedMafia() ) {
					sendMessage(data.roleSocketMap.get(theTitan) , "YES") ;
				} else {
					sendMessage(data.roleSocketMap.get(theTitan) , "NO") ;
				}
			}
			data.ready.add(theTitan) ;
		}
		// Role : The Mafia
		String theMafia = "The Mafia" ;
		if ( isAlive(theMafia) )
			data.ready.add(theMafia) ;
		// Role : The Civilian
		String theCivilian = "The Civilian" ;
		if ( isAlive(theCivilian) )
			data.ready.add(theCivilian) ;
		// Role : The Mayor
		String theMayor = "The Mayor" ;
		if ( isAlive(theMayor) )
			data.ready.add(theMayor) ;
		// Check if Everyone has Done their Job
		do {
			try {
				Thread.currentThread().sleep(100) ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
		} while ( data.ready.size() < data.alive.size() ) ;
		try {
			Thread.currentThread().sleep(100) ;
		} catch ( InterruptedException exception ) {
			exception.printStackTrace() ;
		}
		// Night Aftermath :
		String killed = data.killed ;
		Socket killedSocket = null ;
		Role killedRole = null ;
		if ( !killed.equals("NULL") ) {
			killedSocket = data.usernames.get(killed) ;
			killedRole = data.socketRoleMap.get(killedSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Killed : " + killed + " | Role : " + killedRole.getName()) ;
		} else {
			killed = null ;
		}

		String savedMafia = data.savedMafia ;
		Socket savedMafiaSocket = null ;
		Role savedMafiaRole = null ;
		if ( !savedMafia.equals("NULL") ) {
			savedMafiaSocket = data.usernames.get(savedMafia) ;
			savedMafiaRole = data.socketRoleMap.get(savedMafiaSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Saved By Lecter : " + savedMafia + " | Role : " + savedMafiaRole.getName()) ;
		} else {
			savedMafia = null ;
		}

		String savedCivilian = data.savedCivilian ;
		Socket savedCivilianSocket = null ;
		Role savedCivilianRole = null ;
		if ( !savedCivilian.equals("NULL") ) {
			savedCivilianSocket = data.usernames.get(savedCivilian) ;
			savedCivilianRole = data.socketRoleMap.get(savedCivilianSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Saved By Doctor : " + savedCivilian + " | Role : " + savedCivilianRole.getName()) ;
		} else {
			savedCivilian = null ;
		}

		String detectiveGuessed = data.detectiveGuessed ;
		Socket detectiveGuessedSocket = null ;
		Role detectiveGuessedRole = null ;
		if ( !detectiveGuessed.equals("NULL") ) {
			detectiveGuessedSocket = data.usernames.get(detectiveGuessed) ;
			detectiveGuessedRole = data.socketRoleMap.get(detectiveGuessedSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Guessed By Detective : " + detectiveGuessed + " | Role : " + detectiveGuessedRole.getName()) ;
		} else {
			detectiveGuessed = null ;
		}

		String sniped = data.sniped ;
		Socket snipedSocket = null ;
		Role snipedRole = null ;
		if ( !(sniped.equals("NULL") || sniped.equals("PASS")) ) {
			snipedSocket = data.usernames.get(sniped) ;
			snipedRole = data.socketRoleMap.get(snipedSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Sniped : " + sniped + " | Role : " + snipedRole.getName()) ;
		} else {
			sniped = null ;
		}

		String silenced = data.silenced ;
		Socket silencedSocket = null ;
		Role silencedRole = null ;
		if ( !(silenced.equals("NULL") || silenced.equals("PASS")) ) {
			silencedSocket = data.usernames.get(silenced) ;
			silencedRole = data.socketRoleMap.get(silencedSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Silenced : " + silenced + " | Role : " + silencedRole.getName()) ;
		} else {
			silenced = null ;
		}

		String titanGuessed = data.titanGuessed ;
		Socket titanGuessedSocket = null ;
		Role titanGuessedRole = null ;
		if ( !(titanGuessed.equals("NULL") || titanGuessed.equals("PASS")) ) {
			titanGuessedSocket = data.allUsernames.get(titanGuessed) ;
			titanGuessedRole = data.socketRoleMap.get(titanGuessedSocket) ;
			if ( DEBUG )
				System.out.println("GAME DETAILS => Guessed By Titan : " + titanGuessed + " | Role : " + titanGuessedRole.getName()) ;
		} else {
			titanGuessed = null ;
		}

		if ( killed != null ) {
			if ( savedCivilian != null && killed.equals(savedCivilian) ) {
				// Do Nothing
			} else {
				killedRole.kill() ;
			}
		}
		if ( sniped != null ) {
			if ( savedMafia != null && sniped.equals(savedMafia) ) {
				// Do Nothing
			} else {
				if ( !snipedRole.isMafia() ) {
					Socket sniperSocket = data.roleSocketMap.get(theSniper) ;
					Role sniperRole = data.socketRoleMap.get(sniperSocket) ;
					sniperRole.kill() ;
				}
				snipedRole.kill() ;
			}
		}

		if ( silenced != null )
			silencedRole.changeCanSpeakState() ;

		ArrayList<Socket> listDisconnect = new ArrayList<Socket>() ;
		for ( Socket client : data.clients.keySet() ) {
			Role role = data.socketRoleMap.get(client) ;
			if ( !role.isAlive() ) {
				disconnect(client) ;
				listDisconnect.add(client) ;
			}
		}
		for ( Socket client : listDisconnect ) {
			data.usernames.remove(data.clients.get(client)) ;
			data.clients.remove(client) ;
		}
		// Display Final Message of the Night
		msg = "Night " + data.dayCount + " Has Ended !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
	}
	public void runDay() {
		String msg = "Day " + data.dayCount + " Has Started !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
		// Show Alive Players 
		msg = "Players Alive => " ;
		for ( String username : data.usernames.keySet() ) {
			Socket socket = data.usernames.get(username) ;
			if ( data.clients.containsKey(socket) )
				msg += username + " " ;
		}
		broadcastMessage(msg) ;
		// Open Chatroom for all clients
		for ( Socket client : data.clients.keySet() ) {
			Role role = data.socketRoleMap.get(client) ;
			if ( role.canSpeak() )
				openPublicChatroom(client) ;
		}
		// Keep Chatroom open for about a minute
		long timeChatroomOpened = System.currentTimeMillis() ;
		long timeChatroomClosed = System.currentTimeMillis() ;
		while ( timeChatroomClosed - timeChatroomOpened < data.publicChatTimer )
			timeChatroomClosed = System.currentTimeMillis() ;
		// Close Chatroom for all clients
		for ( Socket client : data.clients.keySet() ) {
			Role role = data.socketRoleMap.get(client) ;
			if ( role.canSpeak() )
				closePublicChatroom(client) ;
		}
		// Display Final Message of the Day
		msg = "Day " + data.dayCount + " Has Ended !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
	}
	public void runVoting() {
		String msg = "Voting " + data.dayCount + " Has Started !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
		// Ask for Votes
		data.votes.put("PASS" , 0) ;
		for ( Socket client : data.clients.keySet() )
			data.votes.put(data.clients.get(client) , 0) ;
		data.ready = new ArrayList<String>() ;

		for ( String username : data.usernames.keySet() ) {
			askVote(data.usernames.get(username)) ;
			while ( true ) {
				if ( data.voteMap.containsKey(username) )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
			if ( data.voteMap.get(username).equals("PASS") ) {
				int prevValue = data.voteCounter.get(username) ;
				data.voteCounter.put(username , prevValue + 1) ;
			} else {
				data.voteCounter.put(username , 0) ;
			}
			data.ready.add(username) ;
		}
		// Check if Everyone has Done their Job
		do {
			try {
				Thread.currentThread().sleep(100) ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
		} while ( data.ready.size() < data.alive.size() ) ;
		try {
			Thread.currentThread().sleep(100) ;
		} catch ( InterruptedException exception ) {
			exception.printStackTrace() ;
		}

		broadcastMessage("\u001B[36m" + "Voting Results : " + "\u001B[0m") ;
		for ( String username : data.usernames.keySet() ) {
			msg = username + " Voted For => " + data.voteMap.get(username) ;
			broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
		}
		// Ask Mayor if he wants to cancel
		String theMayor = "The Mayor" ;
		if ( isAlive(theMayor) && !data.mayorUsedAbility ) {
			askMayor(data.roleSocketMap.get(theMayor)) ;
			while ( true ) {
				if ( !data.mayorDecision.equals("NULL") )
					break ;
				try {
					Thread.currentThread().sleep(50) ;
				} catch ( InterruptedException exception ) {
					exception.printStackTrace() ;
				}
			}
		}
		if ( data.mayorDecision.equals("YES") ) {
			data.mayorUsedAbility = true ;
			msg = data.clients.get(data.roleSocketMap.get(theMayor)) + " (The Mayor) Canceled The Voting !" ;
			broadcastMessage(msg) ;
			msg = "Nobody Was Ejected !" ;
			broadcastMessage("\u001B[31m" + msg + "\u001B[0m") ;
		} else {
			// Find user with max votes
			String maxVotesUsername = "PASS" ;
			String secondMaxVotesUsername = "PASS" ;
			for ( String username : data.usernames.keySet() )
				if ( data.votes.get(username) > data.votes.get(maxVotesUsername) )
					maxVotesUsername = username ;
			for ( String username : data.usernames.keySet() )
				if ( !username.equals(maxVotesUsername) )
					if ( data.votes.get(username) > data.votes.get(secondMaxVotesUsername) )
						secondMaxVotesUsername = username ;
			if ( maxVotesUsername.equals("PASS") ) {
				msg = "Nobody Was Ejected !" ;
				broadcastMessage("\u001B[31m" + msg + "\u001B[0m") ;
			} else if ( data.votes.get(maxVotesUsername) == data.votes.get(secondMaxVotesUsername) ) {
				msg = "Nobody Was Ejected !" ;
				broadcastMessage("\u001B[31m" + msg + "\u001B[0m") ;
			} else {
				msg = maxVotesUsername + " (" + data.socketRoleMap.get(data.usernames.get(maxVotesUsername)).getName() + ") Was Ejected !" ;
				broadcastMessage("\u001B[31m" + msg + "\u001B[0m") ;
				Socket ejectedUserSocket = data.usernames.get(maxVotesUsername) ;
				data.usernames.remove(maxVotesUsername) ;
				data.clients.remove(ejectedUserSocket) ;
				disconnect(ejectedUserSocket) ;
			}
		}

		ArrayList<String> temp = new ArrayList<String>() ;
		for ( String username : data.usernames.keySet() )
			if ( data.usernames.get(username) != null && data.voteCounter.get(username) >= 3 ) {
				msg = username + " (" + data.socketRoleMap.get(data.usernames.get(username)).getName() + ") Was Ejected For 3 Passes !" ;
				broadcastMessage("\u001B[31m" + msg + "\u001B[0m") ;
				temp.add(username) ;
			}
		for ( String username : temp ) {
			Socket ejectedUserSocket = data.usernames.get(username) ;
			data.usernames.remove(username) ;
			data.clients.remove(ejectedUserSocket) ;
			disconnect(ejectedUserSocket) ;
		}

		msg = "Voting " + data.dayCount + " Has Ended !" ;
		broadcastMessage("\u001B[36m" + msg + "\u001B[0m") ;
	}
	public void nextDay() {
		if ( !(data.silenced == null || data.silenced.equals("NULL")) ) {
			Socket silencedSocket = data.allUsernames.get(data.silenced) ;
			if ( data.clients.containsKey(silencedSocket) ) {
				Role silencedRole = data.socketRoleMap.get(silencedSocket) ;
				silencedRole.changeCanSpeakState() ;
			}
		}
		data.resetVolatile() ;
		data.dayCount ++ ;
	}
	public void mafiaWin() {
		broadcastMessage("\u001B[36m" + "Game Finished => The Mafias Won" + "\u001B[0m") ;
		System.out.println("Game Finished => The Mafias Won") ;
	}
	public void civilianWin() {
		broadcastMessage("\u001B[36m" + "Game Finished => The Civilians Won" + "\u001B[0m") ;
		System.out.println("Game Finished => The Civilians Won") ;
	}
	public boolean endgame() {
		Socket godFatherSocket = data.roleSocketMap.get("The GodFather") ;
		if ( !(data.clients.containsKey(godFatherSocket) && data.clients.get(godFatherSocket) != null) ) {
			civilianWin() ;
			return true ;
		} else if ( data.mafias.size() >= data.civilians.size() ) {
			mafiaWin() ;
			return true ;
		}
		return false ;
	}
	public void sendCommand(Socket socket , String function , String... parameters) {
		Command serverCommand = new Command() ;
		serverCommand.setUsername(serverName) ;
		serverCommand.setFunction(function) ;
		serverCommand.setCount(parameters.length) ;
		for ( String parameter : parameters )
			serverCommand.addParameter(parameter) ;
		if ( DEBUG )
			System.out.println("SENT CMD : [USERNAME : " + data.clients.get(socket) + "] => " + serverCommand.toString()) ;
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream() , true) ;
			writer.println(serverCommand.toString()) ;
		} catch ( IOException exception ) {
			exception.printStackTrace() ;
		}
		try {
			Thread.currentThread().sleep(50) ;
		} catch ( InterruptedException exception ) {
			exception.printStackTrace() ;
		}
	}
	public void process(Socket socket , String msg) {
		Command clientCommand = new Command() ;
		clientCommand.parse(msg) ;
		String username = clientCommand.getUsername() ;
		if ( DEBUG )
			System.out.println("RECV CMD : [USERNAME : " + username + "] => " + clientCommand.toString()) ;
		String function = clientCommand.getFunction() ;
		ArrayList<String> parameters = clientCommand.getParameters() ;
		if ( clientCommand.getFunction().equals("NULL_RESPONSE") ) {
			// Pass
		} else if ( clientCommand.getFunction().equals("RESPONSE_USERNAME") ) {
			String targetUsername = parameters.get(0) ;
			if ( isUserValid(targetUsername) ) {
				data.clients.put(socket , username) ;
				data.usernames.put(username , socket) ;
				data.allUsernames.put(username , socket) ;
			} else {
				requestUsername(socket) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_KILL") ) {
			String targetUsername = parameters.get(0) ;
			if ( isGodFatherValid(targetUsername) ) {
				data.killed = targetUsername ;
			} else {
				askGodFather(data.roleSocketMap.get("The GodFather")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_MAFIA_HEAL") ) {
			String targetUsername = parameters.get(0) ;
			if ( isDoctorLecterValid(targetUsername) ) {
				data.savedMafia = targetUsername ;
			} else {
				askDoctorLecter(data.roleSocketMap.get("Doctor Lecter")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_CIVILIAN_HEAL") ) {
			String targetUsername = parameters.get(0) ;
			if ( isTheDoctorValid(targetUsername) ) {
				data.savedCivilian = targetUsername ;
			} else {
				askTheDoctor(data.roleSocketMap.get("The Doctor")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_DETECTIVE_GUESS") ) {
			String targetUsername = parameters.get(0) ;
			if ( isDetectiveValid(targetUsername) ) {
				data.detectiveGuessed = targetUsername ;
			} else {
				askDetective(data.roleSocketMap.get("The Detective")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_SNIPER_KILL") ) {
			String targetUsername = parameters.get(0) ;
			if ( isSniperValid(targetUsername) ) {
				data.sniped = targetUsername ;
			} else {
				askSniper(data.roleSocketMap.get("The Sniper")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_SILENCED") ) {
			String targetUsername = parameters.get(0) ;
			if ( isPsychologistValid(targetUsername) ) {
				data.silenced = targetUsername ;
			} else {
				askPsychologist(data.roleSocketMap.get("The Psychologist")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_TITAN_GUESS") ) {
			String targetUsername = parameters.get(0) ;
			if ( isTitanValid(targetUsername) ) {
				data.titanGuessed = targetUsername ;
			} else {
				askTitan(data.roleSocketMap.get("The Titan")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_MAYOR_DECISION") ) {
			String decision = parameters.get(0) ;
			if ( isMayorValid(decision) ) {
				data.mayorDecision = decision ;
			} else {
				askMayor(data.roleSocketMap.get("The Mayor")) ;
			}
		} else if ( clientCommand.getFunction().equals("RESPONSE_VOTE") ) {
			String targetUsername = parameters.get(0) ;
			if ( isVoteValid(targetUsername) ) {
				int targetUserVotes = data.votes.get(targetUsername) ;
				data.votes.put(targetUsername , targetUserVotes + 1) ;
				data.voteMap.put(username , targetUsername) ;
			} else {
				askVote(data.usernames.get(username)) ;
			}
		} else {
			// Do Nothing
		}
	}
	// Role Methods
	// TODO : Add Role Methods Here
	public boolean isUserValid(String targetUsername) {
		return !data.allUsernames.containsKey(targetUsername) ;
	}
	public boolean isAlive(String roleName) {
		String targetUsername = data.clients.get(data.roleSocketMap.get(roleName)) ;
		return data.usernames.containsKey(targetUsername) ;
	}
	public boolean isGodFatherValid(String targetUsername) {
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername) ;
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.isAlive() && !targetRole.isMafia() ) {
			return true ;
		} else {
			return false ;
		}
	}
	public boolean isDoctorLecterValid(String targetUsername) {
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername); 
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.getName().equals("Doctor Lecter") ) {
			if ( data.lecterCuredSelf == false ) {
				data.lecterCuredSelf = true ;
				return true ;
			} else {
				return false ;
			}
		} else {
			if ( targetRole.isAlive() && targetRole.isMafia() )
				return true ;
			else
				return false ;
		}
	}
	public boolean isTheDoctorValid(String targetUsername) {
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername); 
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.getName().equals("The Doctor") ) {
			if ( data.doctorCuredSelf == false ) {
				data.doctorCuredSelf = true ;
				return true ;
			} else {
				return false ;
			}
		} else {
			if ( targetRole.isAlive() )
				return true ;
			else
				return false ;
		}
	}
	public boolean isDetectiveValid(String targetUsername) {
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername) ;
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.isAlive() ) {
			return true ;
		} else {
			return false ;
		}
	}
	public boolean isSniperValid(String targetUsername) {
		if ( targetUsername.equals("PASS") )
			return true ;
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername) ;
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.isAlive() ) {
			return true ;
		} else {
			return false ;
		}
	}
	public boolean isPsychologistValid(String targetUsername) {
		if ( targetUsername.equals("PASS") )
			return true ;
		if ( !data.usernames.containsKey(targetUsername) )
			return false ;
		Socket targetSocket = data.usernames.get(targetUsername) ;
		Role targetRole = data.socketRoleMap.get(targetSocket) ;
		if ( targetRole.isAlive() ) {
			return true ;
		} else {
			return false ;
		}
	}
	public boolean isTitanValid(String targetUsername) {
		if ( targetUsername.equals("PASS") )
			return true ;
		if ( data.allUsernames.containsKey(targetUsername) && !data.usernames.containsKey(targetUsername) ) {
			return true ;
		} else {
			return false ;
		}
	}
	public boolean isMayorValid(String decision) {
		return (decision.equals("YES") || decision.equals("NO")) ;
	}
	public boolean isVoteValid(String targetUsername) {
		if ( targetUsername.equals("PASS") )
			return true ;
		if ( !data.usernames.containsKey(targetUsername) ) {
			return false ;
		} else {
			return true ;
		}
	}
	// Commands
	// TODO : Add Commands Here
	public void sendWelcomeMessage(Socket socket) {
		String msg = "Welcome " + data.clients.get(socket) + " !" ;
		sendMessage(socket , msg) ;
	}
	public void broadcastMessage(String msg) {
		for ( String username : data.allUsernames.keySet() )
			sendMessage(data.allUsernames.get(username) , msg) ;
	}
	public void broadcastPublicMessage(Socket socket , String msg) {
		for ( Socket client : data.clients.keySet() )
			if ( client != socket ) {
				try {
					PrintWriter writer = new PrintWriter(client.getOutputStream() , true) ;
					writer.println("\u001B[32m" + msg + "\u001B[0m") ;
				} catch ( IOException exception ) {
					exception.printStackTrace() ;
				}
			}
	}
	public void broadcastMafiaMessage(Socket socket , String msg) {
		for ( Socket client : data.mafias )
			if ( client != socket ) {
				try {
					PrintWriter writer = new PrintWriter(client.getOutputStream() , true) ;
					writer.println("\u001B[32m" + msg + "\u001B[0m") ;
				} catch ( IOException exception ) {
					exception.printStackTrace() ;
				}
			}
	}
	public void sendMessage(Socket socket , String msg) {
		sendCommand(socket , "SHOW_MESSAGE" , msg) ;
	}
	public void requestUsername(Socket socket) {
		sendCommand(socket , "REQUEST_USERNAME") ;
	}
	public void sendRole(Socket socket) {
		String roleName = data.roles.get(data.roles.size() - 1) ;
		data.roles.remove(roleName) ;
		data.roleSocketMap.put(roleName , socket) ;
		if ( roleName.equals("Doctor Lecter") ) {
			data.socketRoleMap.put(socket , new DoctorLecter()) ;
		} else if ( roleName.equals("The GodFather") ) {
			data.socketRoleMap.put(socket , new GodFather()) ;
		} else if ( roleName.equals("The Mafia") ) {
			data.socketRoleMap.put(socket , new Mafia()) ;
		} else if ( roleName.equals("The Civilian") ) {
			data.socketRoleMap.put(socket , new Civilian()) ;
		} else if ( roleName.equals("The Detective") ) {
			data.socketRoleMap.put(socket , new Detective()) ;
		} else if ( roleName.equals("The Doctor") ) {
			data.socketRoleMap.put(socket , new Doctor()) ;
		} else if ( roleName.equals("The Mayor") ) {
			data.socketRoleMap.put(socket , new Mayor()) ;
		} else if ( roleName.equals("The Psychologist") ) {
			data.socketRoleMap.put(socket , new Psychologist()) ;
		} else if ( roleName.equals("The Sniper") ) {
			data.socketRoleMap.put(socket , new Sniper()) ;
		} else if ( roleName.equals("The Titan") ) {
			data.socketRoleMap.put(socket , new Titan()) ;
		} else {
			// Do Nothing
		}
		data.alive.add(data.socketRoleMap.get(socket).getName()) ;
		sendCommand(socket , "GET_ROLE" , roleName) ;
	}
	public void openPublicChatroom(Socket socket) {
		data.publicChat = true ;
		sendCommand(socket , "OPEN_PUBLIC_CHATROOM") ;
	}
	public void closePublicChatroom(Socket socket) {
		data.publicChat = false ;
		sendCommand(socket , "CLOSE_PUBLIC_CHATROOM") ;
	}
	public void openMafiaChatroom(Socket socket) {
		data.mafiaChat = true ;
		sendCommand(socket , "OPEN_MAFIA_CHATROOM") ;
	}
	public void closeMafiaChatroom(Socket socket) {
		data.mafiaChat = false ;
		sendCommand(socket , "CLOSE_MAFIA_CHATROOM") ;
	}
	public void askGodFather(Socket socket) {
		sendCommand(socket , "REQUEST_KILL") ;
	}
	public void askDoctorLecter(Socket socket) {
		sendCommand(socket , "REQUEST_MAFIA_HEAL") ;
	}
	public void askTheDoctor(Socket socket) {
		sendCommand(socket , "REQUEST_CIVILIAN_HEAL") ;
	}
	public void askDetective(Socket socket) {
		sendCommand(socket , "REQUEST_DETECTIVE_GUESS") ;
	}
	public void askSniper(Socket socket) {
		sendCommand(socket , "REQUEST_SNIPER_KILL") ;
	}
	public void askPsychologist(Socket socket) {
		sendCommand(socket , "REQUEST_SILENCED") ;
	}
	public void askTitan(Socket socket) {
		sendCommand(socket , "REQUEST_TITAN_GUESS") ;
	}
	public void askMayor(Socket socket) {
		sendCommand(socket , "REQUEST_MAYOR_DECISION") ;
	}
	public void askVote(Socket socket) {
		sendCommand(socket , "REQUEST_VOTE") ;
	}
	public void disconnect(Socket socket) {
		data.alive.remove(data.socketRoleMap.get(socket).getName()) ;
		if ( data.mafias.contains(socket) )
			data.mafias.remove(socket) ;
		else
			data.civilians.remove(socket) ;
		sendMessage(socket , "\u001B[31m" + "YOU DIED !" + "\u001B[0m") ;
		sendCommand(socket , "REQUEST_DISCONNECT") ;
	}
	
}

