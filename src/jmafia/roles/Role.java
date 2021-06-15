// BWOTSHEWCHB

package jmafia.roles ;

/**
 * The type Role.
 */
public abstract class Role {
	// Fields
	protected int lives ;
	protected String name ;
	protected boolean isMafia ;
	protected boolean isAlive ;
	protected boolean canSpeak ;
	protected boolean detectedMafia ;
	// Constructor
	/**
	 * Instantiates a new Role.
	 */
	public Role() {
		isAlive = true ;
		canSpeak = true ;
	}
	// Methods
	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name ;
	}
	/**
	 * Is mafia boolean.
	 *
	 * @return the boolean
	 */
	public boolean isMafia() {
		return isMafia ;
	}
	/**
	 * Is alive boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAlive() {
		return isAlive ;
	}
	/**
	 * Can speak boolean.
	 *
	 * @return the boolean
	 */
	public boolean canSpeak() {
		return canSpeak ;
	}
	/**
	 * Detected mafia boolean.
	 *
	 * @return the boolean
	 */
	public boolean detectedMafia() {
		return detectedMafia ;	
	}
	/**
	 * Kills the role.
	 */
	public void kill() {
		if ( !isAlive )
			return ;
		lives -- ;
		if ( lives == 0 )
			isAlive = false ;
	}
	/**
	 * Change can speak state.
	 */
	public void changeCanSpeakState() {
		canSpeak = !canSpeak ;
	}
	@Override
	public String toString() {
		return name + " {\n" +
			"    lives = " + lives +
			"\n    isMafia = " + isMafia +
			"\n    isAlive = " + isAlive +
			"\n}" ;
	}
}

