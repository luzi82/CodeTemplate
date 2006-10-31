package guri.codetemplate.standardmodule;

public class DataTableCoordinate implements Comparable<DataTableCoordinate> {

	final public int[] coordinate;

	public DataTableCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}

	public DataTableCoordinate(int size) {
		this.coordinate = new int[size];
		int i;
		for (i = 0; i < size; ++i) {
			coordinate[i] = -1;
		}
	}

	public DataTableCoordinate(DataTableCoordinate a, DataTableCoordinate b) {
		this(a.coordinate.length);
		if (a.coordinate.length != b.coordinate.length)
			throw new IllegalArgumentException();
		int i;
		for (i = 0; i < coordinate.length; ++i) {
			if (a.coordinate[i] == b.coordinate[i])
				coordinate[i] = a.coordinate[i];
			else if (a.coordinate[i] != -1 && b.coordinate[i] != -1)
				throw new IllegalArgumentException();
			else if (a.coordinate[i] == -1)
				coordinate[i] = b.coordinate[i];
			else
				coordinate[i] = a.coordinate[i];
		}
	}

	public int compareTo(DataTableCoordinate o) {
		int diff = 0;
		if (coordinate == null)
			diff -= 1;
		if (o.coordinate == null)
			diff += 1;
		if (diff != 0)
			return diff;
		diff = coordinate.length - o.coordinate.length;
		if (diff != 0)
			return diff;
		int i;
		for (i = 0; i < coordinate.length; ++i) {
			diff = coordinate[i] - o.coordinate[i];
			if (diff != 0)
				return diff;
		}
		return 0;
	}

	public boolean unique() {
		for (int i : coordinate) {
			if (i == -1)
				return false;
		}
		return true;
	}

	public int dimension() {
		return coordinate.length;
	}

}
