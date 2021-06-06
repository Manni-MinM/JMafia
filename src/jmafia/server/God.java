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
		if ( god == null ) {
			god = new God() ;
			god.init() ;
		}
		return god ;
	}
	public void init() {
		// TODO : Add Inits Here
		initRoles() ;
	}
	public void initRoles() {
		data.roles.add("The Sniper") ;
		data.roles.add("The Mafia") ;
		data.roles.add("The Civilian") ;
		data.roles.add("The Mayor") ;
		data.roles.add("The Psychologist") ;
		data.roles.add("The Titan") ;
		data.roles.add("The Detective") ;
		data.roles.add("The GodFather") ;
		data.roles.add("Doctor Lecter") ;
		data.roles.add("The Doctor") ;
		// Shuffle it (i know its random but it doesnt shuffle well on one try ^_^)
		// TODO : Uncomment below
		//		for ( int it = 0 ; it < 10 ; it ++ )
		//			Collections.shuffle(data.roles) ;
	}
	public void runFirstNight() {
		data.phase = "NIGHT" ;

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

		String msg = "Introduction Night Has Started !" ;
		broadcastMessage(msg) ;

		String introductionMsgMafia = "The Mafias Are => " ;
		for ( Socket mafia : data.mafias )
			introductionMsgMafia += data.clients.get(mafia) + " " ;
		for ( Socket mafia : data.mafias )
			sendMessage(mafia , introductionMsgMafia) ;

		msg = "Introduction Night Has Ended !" ;
		broadcastMessage(msg) ;
	}
	public void runFirstDay() {
		data.phase = "DAY" ;

		String msg = "Introduction Day Has Started !" ;
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
		broadcastMessage(msg) ;
	}
	public void runNight() {
		data.phase = "NIGHT" ;

		String msg = "Night " + data.dayCount + " Has Started" ;
		broadcastMessage(msg) ;
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
			System.err.println("killed : " + data.killed) ;
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
			System.err.println("mafia saved : " + data.savedMafia) ;
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
			System.err.println("civilian saved : " + data.savedCivilian) ;
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
				System.err.println("detective guessed : " + data.detectiveGuessed + " | guess : correct") ;
			} else {
				sendMessage(data.roleSocketMap.get(theDetective) , "NO") ;
				System.err.println("detective guessed : " + data.detectiveGuessed + " | guess : wrong") ;
			}
			data.ready.add(theDetective) ;
		}
		// Role : The Sniper
		String theSniper = "The Sniper" ;
		if ( isAlive(theSniper) ) {
			// TODO : Complete Code
			data.ready.add(theSniper) ;
		}
		// Role : The Psychologist
		String thePsychologist = "The Psychologist" ;
		if ( isAlive(thePsychologist) ) {
			// TODO : Complete Code
			data.ready.add(thePsychologist) ;
		}
		// Role : The Titan
		String theTitan = "The Titan" ;
		if ( isAlive(theTitan) ) {
			// TODO : Complete Code
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
		// Display Final Message of the Night
		msg = "Night " + data.dayCount + " Has Ended !" ;
		broadcastMessage(msg) ;
	}
	public void nextDay() {
		data.dayCount ++ ;
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
			data.clients.put(socket , username) ;
			data.usernames.put(username , socket) ;
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
		} else {
			// Do Nothing
		}
	}
	// Role Methods
	// TODO : Add Role Methods Here
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
		System.err.println("ENTERED CHECK METHOD") ;
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
	// Commands
	// TODO : Add Commands Here
	public void sendWelcomeMessage(Socket socket) {
		String msg = "Welcome " + data.clients.get(socket) + " !" ;
		sendMessage(socket , msg) ;
	}
	public void broadcastMessage(String msg) {
		for ( Socket client : data.clients.keySet() )
			sendMessage(client , msg) ;
	}
	public void broadcastPublicMessage(Socket socket , String msg) {
		for ( Socket client : data.clients.keySet() )
			if ( client != socket ) {
				try {
					PrintWriter writer = new PrintWriter(client.getOutputStream() , true) ;
					writer.println(msg) ;
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
					writer.println(msg) ;
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
	public void readyGodFather(Socket socket) {
		sendCommand(socket , "READY_KILL") ;
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
}

