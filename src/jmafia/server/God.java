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
		// Team Mafia
		data.roles.add("The GodFather") ;
		data.roles.add("Doctor Lecter") ;
		data.roles.add("The Mafia") ;
		// Team Civilians
		data.roles.add("The Doctor") ;
		data.roles.add("The Detective") ;
		data.roles.add("The Sniper") ;
		data.roles.add("The Civilian") ;
		data.roles.add("The Mayor") ;
		data.roles.add("The Psychologist") ;
		data.roles.add("The Titan") ;
		// Shuffle it (i know its random but it doesnt shuffle well on one try ^_^)
		for ( int it = 0 ; it < 10 ; it ++ )
			Collections.shuffle(data.roles) ;
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
		String introductionMsgMafia = "The Mafias Are => " ;
		for ( Socket mafia : data.mafias )
			introductionMsgMafia += data.clients.get(mafia) + " " ;
		for ( Socket mafia : data.mafias )
			sendMessage(mafia , introductionMsgMafia) ;
	}
	public void sendCommand(Socket socket , String function , String... parameters) {
		Command serverCommand = new Command() ;
		serverCommand.setUsername(serverName) ;
		serverCommand.setFunction(function) ;
		serverCommand.setCount(parameters.length) ;
		for ( String parameter : parameters )
			serverCommand.addParameter(parameter) ;
		if ( DEBUG )
			System.out.println("SENT : [SOCKET : " + socket + " | USERNAME : " + data.clients.get(socket) + "] => " + serverCommand.toString()) ;
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream() , true) ;
			writer.println(serverCommand.toString()) ;
		} catch ( IOException exception ) {
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
			System.out.println("RECV : [SOCKET : " + socket + " | USERNAME : " + username + "] => " + clientCommand.toString()) ;
	}
	// Commands
	// TODO : Add Commands Here
	public void sendMessage(Socket socket , String msg) {
		sendCommand(socket , "SHOW_MESSAGE" , msg) ;
	}
	public void sendWelcomeMessage(Socket socket) {
		synchronized ( socket ) {
			String msg = "Welcome " + data.clients.get(socket) + " !" ;
			sendMessage(socket , msg) ;
			try {
				socket.wait() ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
		}
	}
	public void sendRole(Socket socket) {
		synchronized ( socket ) {
			String roleName = data.roles.get(data.roles.size() - 1) ;
			data.roles.remove(roleName) ;
			data.roleMap.put(roleName , socket) ;
			sendCommand(socket , "GET_ROLE" , roleName) ;
		}
	}
	public void requestUsername(Socket socket) {
		synchronized ( socket ) {
			sendCommand(socket , "REQUEST_USERNAME") ;
			try {
				socket.wait() ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
		}
	}
}

