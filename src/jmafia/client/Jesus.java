// BWOTSHEWCHB

package jmafia.client ;

import java.util.* ;

import jmafia.util.* ;

public class Jesus {
	// Fields
	public ClientData data ;
	private Scanner scanner ;
	// Constructor
	public Jesus(ClientData data) {
		this.data = data ;
		this.scanner = new Scanner(System.in) ;
	}
	// Methods
	public String respond(String msg) {
		// $user@function:count:param1-param2-param3-...-paramCount
		Command command = new Command() ;
		command.parse(msg) ;
		// TODO : add other commands
		String function = command.getFunction() ;
		if ( function.equals("SHOW_MESSAGE") ) {
			return showMessageCommand(command) ;
		} else if ( function.equals("REQUEST_USERNAME") ) {
			return responseUsernameCommand(command) ;
		} else if ( function.equals("GET_ROLE") ) {
			return responseRoleCommand(command) ;
		} else if ( function.equals("OPEN_PUBLIC_CHATROOM") ) {
			return responseOpenChatroom(command) ;
		} else if ( function.equals("CLOSE_PUBLIC_CHATROOM") ) {
			return responseCloseChatroom(command) ;
		} else if ( function.equals("OPEN_MAFIA_CHATROOM") ) {
			return responseOpenChatroom(command) ;
		} else if ( function.equals("CLOSE_MAFIA_CHATROOM") ) {
			return responseCloseChatroom(command) ;
		} else if ( function.equals("REQUEST_KILL") ) {
			return responseKillCommand(command) ;
		} else if ( function.equals("REQUEST_MAFIA_HEAL") ) {
			return responseMafiaHealCommand(command) ;
		} else if ( function.equals("REQUEST_CIVILIAN_HEAL") ) {
			return responseCivilianHealCommand(command) ;
		} else if ( function.equals("REQUEST_DETECTIVE_GUESS") ) {
			return responseDetectiveGuessCommand(command) ;
		} else {
			return returnCommand(data.NULL_RESPONSE) ;
		}
	}
	public String returnCommand(String function , String... parameters) {
		Command serverCommand = new Command() ;
		serverCommand.setUsername(data.getUsername()) ;
		serverCommand.setFunction(function) ;
		serverCommand.setCount(parameters.length) ;
		for ( String parameter : parameters )
			serverCommand.addParameter(parameter) ;
		return serverCommand.toString() ;
	}
	// Commands
	// TODO : Add More Commands
	public String showMessageCommand(Command command) {
		String msg = "[" + command.getUsername() + "]: " + command.getParameters().get(0) ;
		System.out.println(msg) ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	public String responseUsernameCommand(Command command) {
		System.out.print("Username : ") ;
		String username = scanner.nextLine() ;
		data.setUsername(username) ;
		return returnCommand("RESPONSE_USERNAME" , username) ;
	}
	public String responseRoleCommand(Command command) {
		String roleName = command.getParameters().get(0) ;
		data.setRole(roleName) ;
		return showMessageCommand(command) ;
	}
	public String responseKillCommand(Command command) {
		System.out.print("Username of Player You Want to Kill : ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_KILL" , targetUsername) ;
	}
	public String responseMafiaHealCommand(Command command) {
		System.out.print("Username of Mafia You Want to Heal : ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_MAFIA_HEAL" , targetUsername) ;
	}
	public String responseCivilianHealCommand(Command command) {
		System.out.print("Username of Civilian You Want to Heal : ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_CIVILIAN_HEAL" , targetUsername) ;
	}
	public String responseDetectiveGuessCommand(Command command) {
		System.out.print("Username of Player You Suspect : ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_DETECTIVE_GUESS" , targetUsername) ;
	}
	public String responseOpenChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	public String responseCloseChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
}

