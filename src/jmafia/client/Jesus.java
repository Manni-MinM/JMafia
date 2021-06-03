// BWOTSHEWCHB

package jmafia.client ;

import java.util.* ;

import jmafia.util.* ;

public class Jesus {
	// Fields
	public ClientData data ;
	private Scanner scanner ;
	private String NULL_RESPONSE = "NULL_RESPONSE" ;
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
		} else {
			return returnCommand(NULL_RESPONSE) ;
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
		return returnCommand(NULL_RESPONSE) ;
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
}

