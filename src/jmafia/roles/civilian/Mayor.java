// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Mayor extends Role {
	// Fields
	
	// Constructor
	public Mayor() {
		lives = 1 ;
		name = "The Mayor" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// (target : self) Stop Voting
	}
}

