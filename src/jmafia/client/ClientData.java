// BWOTSHEWCHB

package jmafia.client ;

import jmafia.roles.Role ;
import jmafia.roles.mafia.* ;
import jmafia.roles.civilian.* ;

/**
 * A Class to Contain Client Data
 *
 * @author Manni Moghimi
 * @version v1.0
 */
public class ClientData {
	// Fields
	public String NULL_RESPONSE ;
	private Role role ;
	private boolean chatIsOpen ;
	private String username ;
	// Constructor
	/**
	 * Instantiates a new Client data.
	 */
	public ClientData() {
		chatIsOpen = false ;
		NULL_RESPONSE = "NULL_RESPONSE" ;
	}
	// Methods
	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username ;
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
	 * Gets role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role ;
	}
	/**
	 * Sets role.
	 *
	 * @param roleName the role name
	 */
	public void setRole(String roleName) {
		if ( roleName.equals("Doctor Lecter") ) {
			this.role = new DoctorLecter() ;
		} else if ( roleName.equals("The GodFather") ) {
			this.role = new GodFather() ;
		} else if ( roleName.equals("Mafia") ) {
			this.role = new Mafia() ;
		} else if ( roleName.equals("Civilian") ) {
			this.role = new Civilian() ;
		} else if ( roleName.equals("The Detective") ) {
			this.role = new Detective() ;
		} else if ( roleName.equals("The Doctor") ) {
			this.role = new Doctor() ;
		} else if ( roleName.equals("The Mayor") ) {
			this.role = new Mayor() ;
		} else if ( roleName.equals("The Psychologist") ) {
			this.role = new Psychologist() ;
		} else if ( roleName.equals("The Sniper") ) {
			this.role = new Sniper() ;
		} else if ( roleName.equals("The Titan") ) {
			this.role = new Titan() ;
		} else {
			this.role = null ;
		}
	}
	/**
	 * Change chat state.
	 */
	public void changeChatState() {
		chatIsOpen = !chatIsOpen ;
	}
	/**
	 * Chat is open boolean.
	 *
	 * @return the boolean
	 */
	public boolean chatIsOpen() {
		return chatIsOpen ;
	}
}

