// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

public class Doctor extends Role {
	// Fields
	
	// Constructor
	public Doctor() {
		lives = 1 ;
		name = "The Doctor" ;
                isMafia = false ;
	}
	// Methods	
	@Override
	public void playRole(Role targetRole) {
		// Save Civilian
	}
}

