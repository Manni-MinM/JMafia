// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Psychologist extends Role {
	// Fields
	
	// Constructor
	public Psychologist() {
		lives = 1 ;
		name = "The Psychologist" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Silence Role
	}
}

