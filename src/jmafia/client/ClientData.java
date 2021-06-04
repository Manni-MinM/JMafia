// BWOTSHEWCHB

package jmafia.client ;

import jmafia.roles.Role ;
import jmafia.roles.mafia.* ;
import jmafia.roles.civilian.* ;

public class ClientData {
	// Fields
	public String NULL_RESPONSE ;
	private Role role ;
	private boolean chatIsOpen ;
	private String username ;
	// Constructor
	public ClientData() {
		chatIsOpen = false ;
		NULL_RESPONSE = "NULL_RESPONSE" ;
	}
	// Methods
	public String getUsername() {
		return username ;
	}
	public void setUsername(String username) {
		this.username = username ;
	}
	public Role getRole() {
		return role ;
	}
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
	public void changeChatState() {
		chatIsOpen = !chatIsOpen ;
	}
	public boolean chatIsOpen() {
		return chatIsOpen ;
	}
}

