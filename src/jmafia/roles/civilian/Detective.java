// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Detective extends Role {
	// Fields
	
	// Constructor
	public Detective() {
		lives = 1 ;
		name = "The Detective" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Check Mafia
	}
}

