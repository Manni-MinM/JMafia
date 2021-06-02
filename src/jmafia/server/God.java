// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class God {
	// Fields
	private Parser parser ;
	private static God god ;
	// Constructor
	public God() {
		// Pass
	}
	// Methods
	public static God getInstance() {
		if ( god == null )
			god = new God() ;
		return god ;
	}
	public String respond(String msg) {
		Command command = new Command() ;
		command.parse(msg) ;
		if ( command.getFunction().equals("register") )
			return registerCommand(command) ;
		// TODO : add other commands
		return null ;
	}
	public String returnMessage(String msg) {
		// $user@function:count:param1-param2-param3-...-paramCount
		Command serverCommand = new Command() ;
		serverCommand.setUsername("The Holy One") ;
		serverCommand.setFunction("showMessage") ;
		serverCommand.setCount(1) ;
		serverCommand.addParameter(msg) ;
		return serverCommand.toString() ;
	}
	public String returnCommand(String function , String... parameters) {
		Command serverCommand = new Command() ;
		serverCommand.setUsername("The Holy One") ;
		serverCommand.setFunction(function) ;
		serverCommand.setCount(parameters.length) ;
		for ( String parameter : parameters )
			serverCommand.addParameter(parameter) ;
		return serverCommand.toString() ;
	}
	public void printDebug(String msg) {
		Command command = new Command() ;
		command.parse(msg) ;
		System.out.println("username : " + command.getUsername()) ;
		System.out.println("function : " + command.getFunction()) ;
		System.out.print("parameters : ") ;
		for ( String parameter : command.getParameters() )
			System.out.print(parameter + " ") ;
		System.out.println() ;
	}
	// Commands
	public String registerCommand(Command command) {
		return returnMessage("[Welcome " + command.getUsername() + " !]") ;
	}
}

