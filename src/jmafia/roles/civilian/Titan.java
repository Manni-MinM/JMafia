// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Titan extends Role {
	// Fields
	
	// Constructor
	public Titan() {
		lives = 2 ;
		name = "The Titan" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Check Dead
	}
}

