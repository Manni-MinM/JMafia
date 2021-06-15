// BWOTSHEWCHB

package jmafia.roles.civilian ;

import jmafia.roles.Role ;

/**
 * The type Civilian.
 */
public class Civilian extends Role {
	// Constructor
	/**
	 * Instantiates a new Civilian.
	 */
	public Civilian() {
		lives = 1 ;
		name = "The Civilian" ;
		isMafia = false ;
		detectedMafia = false ;
	}
}

