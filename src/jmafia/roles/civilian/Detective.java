// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

/**
 * The type Detective.
 */
public class Detective extends Role {
	/**
	 * Instantiates a new Detective.
	 */
	// Constructor
	public Detective() {
		lives = 1 ;
		name = "The Detective" ;
		isMafia = false ;
		detectedMafia = false ;
	}
}

