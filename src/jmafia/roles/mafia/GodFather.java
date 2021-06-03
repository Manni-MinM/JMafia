// BWOTSHEWCHB

package jmafia.roles.mafia ;

import jmafia.roles.Role ;

public class GodFather extends Role {
	// Fields
	
	// Constructor
	public GodFather() {
		lives = 1 ;
		name = "The GodFather" ;
		isMafia = true ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Kill Civilian
//		if ( !targetRole.isMafia() )
//			targetRole.kill() ;
	}
}

