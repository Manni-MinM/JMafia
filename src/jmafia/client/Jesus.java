// BWOTSHEWCHB

package jmafia.client ;

import java.util.* ;

import jmafia.util.* ;

/**
 * The Jesus Class (Client Controller)
 *
 * @author Manni Moghimi
 * @version v1.0
 */
public class Jesus {
	// Fields
	public ClientData data ;
	private Scanner scanner ;
	// Constructor
	/**
	 * Instantiates a new Jesus.
	 *
	 * @param data the data
	 */
	public Jesus(ClientData data) {
		this.data = data ;
		this.scanner = new Scanner(System.in) ;
	}
	// Methods
	/**
	 * Respond string.
	 *
	 * @param msg the msg
	 * @return the string
	 */
	public String respond(String msg) {
		// $user@function:count:param1-param2-param3-...-paramCount
		Command command = new Command() ;
		command.parse(msg) ;
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
		} else if ( function.equals("REQUEST_MAYOR_DECISION") ) {
			return responseMayorDecisionCommand(command) ;
		} else if ( function.equals("REQUEST_DISCONNECT") ) {
			return responseDisconnectCommand(command) ;
		} else if ( function.equals("REQUEST_VOTE") ) {
			return responseVoteCommand(command) ;
		} else {
			return returnCommand(data.NULL_RESPONSE) ;
		}
	}
	/**
	 * Return command string.
	 *
	 * @param function   the function
	 * @param parameters the parameters
	 * @return the string
	 */
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
	/**
	 * Show message command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String showMessageCommand(Command command) {
		String msg = "[" + command.getUsername() + "]: " + command.getParameters().get(0) ;
		System.out.println(msg) ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	/**
	 * Response username command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseUsernameCommand(Command command) {
		String username = "" ;
		while ( username.equals("") ) {
			System.out.print("Username : ") ;
			username = scanner.nextLine() ;
		}
		data.setUsername(username) ;
		return returnCommand("RESPONSE_USERNAME" , username) ;
	}
	/**
	 * Response role command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseRoleCommand(Command command) {
		String roleName = command.getParameters().get(0) ;
		data.setRole(roleName) ;
		return showMessageCommand(command) ;
	}
	/**
	 * Response kill command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseKillCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Player You Want to Kill ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_KILL" , targetUsername) ;
	}
	/**
	 * Response mafia heal command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseMafiaHealCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Mafia You Want to Heal ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_MAFIA_HEAL" , targetUsername) ;
	}
	/**
	 * Response civilian heal command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseCivilianHealCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Civilian You Want to Heal ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_CIVILIAN_HEAL" , targetUsername) ;
	}
	/**
	 * Response detective guess command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseDetectiveGuessCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Player You Suspect ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_DETECTIVE_GUESS" , targetUsername) ;
	}
	/**
	 * Response sniper kill command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseSniperKillCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Player You Want to Kill or \"PASS\" if You Dont Want to Kill Tonight ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_SNIPER_KILL" , targetUsername) ;
	}
	/**
	 * Response silenced command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseSilencedCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Player You Want to Silence or \"PASS\" if You Dont Want to Silence Anyone Tonight ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_SILENCED" , targetUsername) ;
	}
	/**
	 * Response titan guess command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseTitanGuessCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Username of Player You Suspect or \"PASS\" if You Dont Suspect Anyone Tonight ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_TITAN_GUESS" , targetUsername) ;
	}
	/**
	 * Response mayor decision command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseMayorDecisionCommand(Command command) {
		String decision = "" ;
		while ( decision.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[33m" + "Do You Wish to Cancel The Voting \"YES\" or \"NO\" (You Can Only Do This Once) ? " + "\u001B[0m") ;
			decision = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_MAYOR_DECISION" , decision) ;
	}
	/**
	 * Response open chatroom string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseOpenChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	/**
	 * Response close chatroom string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseCloseChatroom(Command command) {
		data.changeChatState() ;
		return returnCommand(data.NULL_RESPONSE) ;
	}
	/**
	 * Response disconnect command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseDisconnectCommand(Command command) {
		return returnCommand(data.NULL_RESPONSE) ;
	}
	/**
	 * Response vote command string.
	 *
	 * @param command the command
	 * @return the string
	 */
	public String responseVoteCommand(Command command) {
		String targetUsername = "" ;
		while ( targetUsername.equals("") ) {
			System.out.print("[The Holy One]: " + "\u001B[31m" + "Who Do You Wish to Eject (\"PASS\" if You Do Not Wish to Vote) ? " + "\u001B[0m") ;
			targetUsername = scanner.nextLine() ;
		}
		return returnCommand("RESPONSE_VOTE" , targetUsername) ;
	}
}

