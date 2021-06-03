// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Civilian extends Role {
	// Fields
	
	// Constructor
	public Civilian() {
		lives = 1 ;
		name = "The Civilian" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// (target : self) Do Nothing
	}
}

