// BWOTSHEWCHB

package jmafia.roles ;

public abstract class Role {
	// Fields
	protected int lives ;
	protected String name ;
	protected boolean isMafia ;
	protected boolean isAlive ;
	protected boolean canSpeak ;
	protected boolean detectedMafia ;
	// Constructor
	public Role() {
		isAlive = true ;
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
	public boolean canSpeak() {
		return canSpeak ;
	}
	public boolean detectedMafia() {
		return detectedMafia ;	
	}
	public void kill() {
		if ( !isAlive )
			return ;
		lives -- ;
		if ( lives == 0 )
			isAlive = false ;
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

