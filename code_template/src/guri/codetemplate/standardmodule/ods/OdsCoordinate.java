package guri.codetemplate.standardmodule.ods;

public class OdsCoordinate implements Comparable<OdsCoordinate> {

	final public int y, z;

	public OdsCoordinate(int y, int z) {
		this.y = y;
		this.z = z;
	}

	public int compareTo(OdsCoordinate o) {
		int ydiff = y - o.y;
		int zdiff = z - o.z;
		return (ydiff != 0) ? ydiff : zdiff;
	}

}
