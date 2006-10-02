package guri.codetemplate.standardmodule;

public class DataTableCoordinate implements Comparable<DataTableCoordinate> {
	public int[] coordinate;

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

}
