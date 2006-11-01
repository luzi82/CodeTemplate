package guri.codetemplate.standardmodule.datatable;

public class DataTableCoordinate implements Comparable<DataTableCoordinate> {

	final private String[] coordinate;

	public DataTableCoordinate(String[] coordinate) {
		this.coordinate = new String[coordinate.length];
		for (int i = 0; i < coordinate.length; ++i) {
			this.coordinate[i] = coordinate[i];
		}
	}

	public DataTableCoordinate(int size) {
		this.coordinate = new String[size];
	}

	public DataTableCoordinate(DataTableCoordinate a, DataTableCoordinate b) {
		this(a.coordinate.length);
		if (a.coordinate.length != b.coordinate.length)
			throw new IllegalArgumentException();
		int i;
		for (i = 0; i < coordinate.length; ++i) {
			if (a.coordinate[i] == null && b.coordinate[i] == null)
				coordinate[i] = null;
			else if (a.coordinate[i] == null)
				coordinate[i] = b.coordinate[i];
			else if (b.coordinate[i] == null)
				coordinate[i] = a.coordinate[i];
			else if (a.coordinate[i].equals(b.coordinate[i]))
				coordinate[i] = a.coordinate[i];
			else
				throw new IllegalArgumentException();
		}
	}

	public int compareTo(DataTableCoordinate o) {
		int diff = coordinate.length - o.coordinate.length;
		if (diff != 0)
			return diff;
		int i;
		for (i = 0; i < coordinate.length; ++i) {
			if (coordinate[i] == null && o.coordinate[i] == null)
				continue;
			else if (coordinate[i] == null)
				return -1;
			else if (o.coordinate[i] == null)
				return 1;
			diff = coordinate[i].compareTo(o.coordinate[i]);
			if (diff != 0)
				return diff;
		}
		return 0;
	}

	public boolean unique() {
		for (String i : coordinate) {
			if (i == null)
				return false;
		}
		return true;
	}

	public int dimension() {
		return coordinate.length;
	}

	public boolean contains(DataTableCoordinate coor) {
		if (dimension() != coor.dimension())
			return false;
		for (int i = 0; i < dimension(); ++i) {
			if (coordinate[i] == null)
				continue;
			if (!coor.coordinate[i].equals(coordinate[i])) {
				return false;
			}
		}
		return true;
	}

	public String coordinate(int id) {
		return coordinate[id];
	}

	public String[] coordinate() {
		String[] ret = new String[dimension()];
		for (int i = 0; i < dimension(); ++i) {
			ret[i] = coordinate[i];
		}
		return ret;
	}

}
