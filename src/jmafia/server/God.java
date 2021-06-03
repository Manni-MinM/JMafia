// BWOTSHEWCHB

package jmafia.server ;

import java.io.* ;
import java.net.* ;
import java.util.* ;

import jmafia.util.* ;

public class God {
	// Fields
	private static God god ;
	private String serverName ;

	private String NULL_RESPONSE = "NULL_RESPONSE" ;
	private HashMap<Socket , String> clients ;
	// Constructor
	public God() {
		this.serverName = "The Holy One" ;

		this.clients = new HashMap<Socket , String>() ;
	}
	// Methods
	public static God getInstance() {
		if ( god == null )
			god = new God() ;
		return god ;
	}
	// Methods
	public void sendCommand(Socket socket , String function , String... parameters) {
		Command serverCommand = new Command() ;
		serverCommand.setUsername(serverName) ;
		serverCommand.setFunction(function) ;
		serverCommand.setCount(parameters.length) ;
		for ( String parameter : parameters )
			serverCommand.addParameter(parameter) ;
		System.out.println("SENT : [SOCKET : " + socket + " | USERNAME : " + clients.get(socket) + "] => " + serverCommand.toString()) ;
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
			System.out.println("USERNAME KIRI :" + username) ;
			clients.put(socket , username) ;
		}
		System.out.println("RECV : [SOCKET : " + socket + " | USERNAME : " + username + "] => " + clientCommand.toString()) ;
	}
	// Commands
	// TODO : Add More Commands
	public void sendMessage(Socket socket , String msg) {
		sendCommand(socket , "SHOW_MESSAGE" , msg) ;
	}
	public void sendWelcomeMessage(Socket socket) {
		synchronized ( socket ) {
			String msg = "Welcome " + clients.get(socket) + " !" ;
			sendMessage(socket , msg) ;
			try {
				socket.wait() ;
			} catch ( InterruptedException exception ) {
				exception.printStackTrace() ;
			}
		}
	}
	// TODO : Add Send Role Command
	public  void requestUsername(Socket socket) {
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

