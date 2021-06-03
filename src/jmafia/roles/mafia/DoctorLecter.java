// BWOTSHEWCHB

package jmafia.roles.mafia ;

import jmafia.roles.Role ;

public class DoctorLecter extends Role {
	// Fields
	
	// Constructor
	public DoctorLecter() {
		lives = 1 ;
		name = "Doctor Lecter" ;
		isMafia = true ;
	}
	// Methods
	@Override
	public void playRole(Role targetRole) {
		// Save Mafia
//		if ( targetRole.isMafia() )
//			targetRole.save() ;
	}
}

