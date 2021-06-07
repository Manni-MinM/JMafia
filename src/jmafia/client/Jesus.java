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
		} else if ( function.equals("REQUEST_SNIPER_KILL") ) {
			return responseSniperKillCommand(command) ;
		} else if ( function.equals("REQUEST_SILENCED") ) {
			return responseSilencedCommand(command) ;
		} else if ( function.equals("REQUEST_TITAN_GUESS") ) {
			return responseTitanGuessCommand(command) ;
		} else if ( function.equals("REQUEST_DISCONNECT") ) {
			return responseDisconnectCommand(command) ;
		} else if ( function.equals("REQUEST_VOTE") ) {
			return responseVoteCommand(command) ;
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
		System.out.print("[The Holy One]: Username of Player You Want to Kill ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_KILL" , targetUsername) ;
	}
	public String responseMafiaHealCommand(Command command) {
		System.out.print("[The Holy One]: Username of Mafia You Want to Heal ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_MAFIA_HEAL" , targetUsername) ;
	}
	public String responseCivilianHealCommand(Command command) {
		System.out.print("[The Holy One]: Username of Civilian You Want to Heal ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_CIVILIAN_HEAL" , targetUsername) ;
	}
	public String responseDetectiveGuessCommand(Command command) {
		System.out.print("[The Holy One]: Username of Player You Suspect ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_DETECTIVE_GUESS" , targetUsername) ;
	}
	public String responseSniperKillCommand(Command command) {
		System.out.print("[The Holy One]: Username of Player You Want to Kill or \"PASS\" if You Dont Want to Kill Tonight ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_SNIPER_KILL" , targetUsername) ;
	}
	public String responseSilencedCommand(Command command) {
		System.out.print("[The Holy One]: Username of Player You Want to Silence or \"PASS\" if You Dont Want to Silence Anyone Tonight ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_SILENCED" , targetUsername) ;
	}
	public String responseTitanGuessCommand(Command command) {
		System.out.print("[The Holy One]: Username of Player You Suspect or \"PASS\" if You Dont Suspect Anyone Tonight ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_TITAN_GUESS" , targetUsername) ;
	}
	public String responseOpenChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	public String responseCloseChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	public String responseDisconnectCommand(Command command) {
		return returnCommand(data.NULL_RESPONSE) ;
	}
	public String responseVoteCommand(Command command) {
		System.out.print("[The Holy One]: Who Do You Wish to Eject ? ") ;
		String targetUsername = scanner.nextLine() ;
		return returnCommand("RESPONSE_VOTE" , targetUsername) ;
	}
}

