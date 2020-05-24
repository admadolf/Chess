package ai.representation;

/**
 * Move vectors according to compass rose for little endian rank file mapping
 * @author adam adolf
 *
 */
public class Vector {

	//NORTH,NE,EAST,SE,SOUTH,SW,WEST,NW
	//ezek lehetnenek privat es getterekkel visszaadnak uj integer instancakat
	public static final int NORTH = 8;
	
	public static final int NE = 9;
	
	public static final int EAST = 1;
	
	public static final int SE = -7;
	
	public static final int SOUTH = -8;
	
	public static final int SW = -9;
	
	public static final int WEST = -1;
	
	public static final int NW = 7;
	
}
