// BWOTSHEWCHB

package jmafia.roles.mafia ;

import jmafia.roles.Role ;

public class Mafia extends Role {
	// Fields
	
	// Constructor
	public Mafia() {
		lives = 1 ;
		name = "The Mafia" ;
		isMafia = true ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Do Nothing
	}
}

