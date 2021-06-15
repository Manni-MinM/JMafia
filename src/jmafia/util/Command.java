// BWOTSHEWCHB

package jmafia.util ;

import java.util.ArrayList ;

/**
 * The type Command.
 */
public class Command {
	// Fields
	private int count ;
	private String username ;
	private String function ;
	private ArrayList<String> parameters ;

	/**
	 * Instantiates a new Command.
	 */
// Constructor
	public Command() {
		parameters = new ArrayList<String>() ;	
	}

	/**
	 * Gets username.
	 *
	 * @return the username
	 */
// Methods
	public String getUsername() {
		return username ;
	}

	/**
	 * Gets function.
	 *
	 * @return the function
	 */
	public String getFunction() {
		return function ;
	}

	/**
	 * Gets parameters.
	 *
	 * @return the parameters
	 */
	public ArrayList<String> getParameters() {
		return parameters ;
	}

	/**
	 * Sets count.
	 *
	 * @param count the count
	 */
	public void setCount(int count) {
		this.count = count ;
	}

	/**
	 * Sets username.
	 *
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username ;
	}

	/**
	 * Sets function.
	 *
	 * @param function the function
	 */
	public void setFunction(String function) {
		this.function = function ;
	}

	/**
	 * Add parameter.
	 *
	 * @param parameter the parameter
	 */
	public void addParameter(String parameter) {
		this.parameters.add(parameter) ;
	}

	/**
	 * Parse.
	 *
	 * @param command the command
	 */
	public void parse(String command) {
		// $user@function:count:param1-param2-param3-...-paramCount
		command = command.substring(1) ;
		String[] splitAtsign = command.split("@") ;
		setUsername(splitAtsign[0]) ;
		String[] splitColon = splitAtsign[1].split(":") ;
		setFunction(splitColon[0]) ;
		setCount(Integer.parseInt(splitColon[1])) ;
		if ( count == 0 ) {
			// Pass
		} else if ( count == 1 ) {
			addParameter(splitColon[2]) ;
		} else {
			String[] splitDash = splitColon[2].split("-") ;
			for ( String parameter : splitDash )
				parameters.add(parameter) ;
		}
	}

	/**
	 * Print debug.
	 */
	public void printDebug() {
		System.out.println("username : " + username) ;
		System.out.println("function : " + function) ;
		System.out.print("parameters : ") ;
		for ( String parameter : parameters )
			System.out.print(parameter + " ") ;
		System.out.println() ;
	}
	@Override
	public String toString() {
		String parametersStringed = "" ;
		for ( String parameter : parameters )
			parametersStringed += parameter + "-" ;
		if ( parametersStringed.length() > 0 )
			parametersStringed = parametersStringed.substring(0 , parametersStringed.length() - 1) ;
		return "$" + username + "@" + function + ":" + count + ":" + parametersStringed ;
	}
}

