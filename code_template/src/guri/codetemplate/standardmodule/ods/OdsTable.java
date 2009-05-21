package guri.codetemplate.standardmodule.ods;

import java.util.TreeMap;

public class OdsTable {

	private final TreeMap<OdsCoordinate, String> map = new TreeMap<OdsCoordinate, String>();

	private int yMax = 0;

	private int zMax = 0;

	public OdsTable() {
	}

	public void put(int y, int z, String value) {
		OdsCoordinate coor = new OdsCoordinate(y, z);
		map.put(coor, value);
		if (y + 1 > yMax)
			yMax = y + 1;
		if (z + 1 > zMax)
			zMax = z + 1;
	}

	public int yMax() {
		return yMax;
	}

	public int zMax() {
		return zMax;
	}

	public String get(int y, int z) {
		OdsCoordinate coor = new OdsCoordinate(y, z);
		return map.get(coor);
	}

}
