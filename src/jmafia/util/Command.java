// BWOTSHEWCHB

package jmafia.util ;

import java.util.ArrayList ;

public class Command {
	// Fields
	private int count ;
	private String username ;
	private String function ;
	private ArrayList<String> parameters ;
	// Constructor
	public Command() {
		parameters = new ArrayList<String>() ;	
	}
	// Methods
	public String getUsername() {
		return username ;
	}
	public String getFunction() {
		return function ;
	}
	public ArrayList<String> getParameters() {
		return parameters ;
	}
	public void setCount(int count) {
		this.count = count ;
	}
	public void setUsername(String username) {
		this.username = username ;
	}
	public void setFunction(String function) {
		this.function = function ;
	}
	public void addParameter(String parameter) {
		this.parameters.add(parameter) ;
	}
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
		parametersStringed = parametersStringed.substring(0 , parametersStringed.length() - 1) ;
		return "$" + username + "@" + function + ":" + count + ":" + parametersStringed ;
	}
}

