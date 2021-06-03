// BWOTSHEWCHB

package jmafia.roles ;

public abstract class Role {
	// Fields
	protected int lives ;
	protected String name ;
	protected boolean isMafia ;
	protected boolean isAlive ;
	protected boolean isSaved ;
	protected boolean isKilled ;
	protected boolean canSpeak ;
	// Constructor
	public Role() {
		isAlive = true ;
		isSaved = false ;
		isKilled = false ;
		canSpeak = true ;
	}
	// Methods
	public String getName() {
		return name ;
	}
	public boolean isMafia() {
		return isMafia ;
	}
	public boolean isAlive() {
		return isAlive ;
	}
	public boolean isSaved() {
		return isSaved ;
	}
	public boolean isKilled() {
		return isKilled ;
	}
	public boolean canSpeak() {
		return canSpeak ;
	}
	public void kill() {
		if ( !isAlive )
			return ;
		isKilled = true ;
	}
	public void save() {
		if ( !isAlive )
			return ;
		isSaved = true ;
	}
	public void aftermath() {
		if ( isKilled ) {
			if ( isSaved ) {
				// Pass
			} else {	
				lives -- ;
			}
		}
		if ( lives == 0 )
			isAlive = false ;
		else {
			isSaved = false ;
			isKilled = false ;
		}
	}
	public abstract void playRole(Role targetRole) ;
	@Override
	public String toString() {
		return name + " {\n" +
			"    lives = " + lives +
			"\n    isMafia = " + isMafia +
			"\n    isAlive = " + isAlive +
			"\n    isSaved = " + isSaved +
			"\n}" ;
	}
}

