// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Sniper extends Role {
	// Fields
	
	// Constructor
	public Sniper() {
		lives = 1 ;
		name = "The Sniper" ;
                isMafia = false ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Kill Mafia
	}
}

