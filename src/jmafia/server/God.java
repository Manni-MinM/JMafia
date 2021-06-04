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
	// Private Methods
	private void init() {
		// TODO : Add Inits Here
		initRoles() ;
	}
	private void initRoles() {
		data.roles.add("The Detective") ;
		data.roles.add("The Sniper") ;
		data.roles.add("Doctor Lecter") ;
		data.roles.add("The Civilian") ;
		data.roles.add("The Mayor") ;
		data.roles.add("The Psychologist") ;
		data.roles.add("The Titan") ;
		data.roles.add("The GodFather") ;
		data.roles.add("The Mafia") ;
		data.roles.add("The Doctor") ;
		// Shuffle it (i know its random but it doesnt shuffle well on one try ^_^)
		// TODO : Uncomment below
//		for ( int it = 0 ; it < 10 ; it ++ )
//			Collections.shuffle(data.roles) ;
	}
	// Public Methods
	public static God getInstance() {
		if ( god == null ) {
			god = new God() ;
			god.init() ;
		}
		return god ;
	}
	public void runFirstNight() {
		data.phase = "NIGHT" ;
		for ( String roleName : data.roleMap.keySet() ) {
			if ( roleName.equals("The GodFather") ) {
				data.mafias.add(data.roleMap.get(roleName)) ;
			} else if ( roleName.equals("Doctor Lecter") ) {
				data.mafias.add(data.roleMap.get(roleName)) ;
			} else if ( roleName.equals("The Mafia") ) {
				data.mafias.add(data.roleMap.get(roleName)) ;
			} else {
				data.civilians.add(data.roleMap.get(roleName)) ;
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
		System.err.println(data.publicChatTimer) ;
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
			openMafiaChatroom(client) ;
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
		String function = clientCommand.getFunction() ;
		ArrayList<String> parameters = clientCommand.getParameters() ;
		if ( clientCommand.getFunction().equals("NULL_RESPONSE") ) {
			// Pass
		} else if ( clientCommand.getFunction().equals("RESPONSE_USERNAME") ) {
			data.clients.put(socket , username) ;
		}
		if ( DEBUG )
			System.out.println("RECV CMD : [USERNAME : " + username + "] => " + clientCommand.toString()) ;
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
		data.roleMap.put(roleName , socket) ;
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

}

