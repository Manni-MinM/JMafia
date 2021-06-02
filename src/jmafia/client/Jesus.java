// BWOTSHEWCHB

package jmafia.client ;

import java.util.* ;

import jmafia.util.* ;

public class Jesus {
	// Fields
	private ClientData data ;
	// Constructor
	public Jesus(ClientData data) {
		this.data = data ;
	}
	// Methods
	public String respond(String msg) {
		// $user@function:count:param1-param2-param3-...-paramCount
		Command command = new Command() ;
		command.parse(msg) ;
		if ( command.getFunction().equals("showMessage") )
			return showMessageCommand(command) ;
		// TODO : add other commands
		return null ;
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
	public String showMessageCommand(Command command) {
		System.out.println(command.getParameters().get(0)) ;
		return null ;
	}
}

