package guri.codetemplate.standardmodule;

import java.util.TreeMap;

public class DataTableBase {

	private TreeMap<DataTableCoordinate, String> data = new TreeMap<DataTableCoordinate, String>();

	int dimension;

	public DataTableBase(int dimension) {
		this.dimension = dimension;
	}

	public void setData(DataTableCoordinate coordinate, String data) {
		if (coordinate == null)
			throw new NullPointerException();
		if (data == null)
			throw new NullPointerException();
		if (coordinate.coordinate.length != dimension)
			throw new IllegalArgumentException();
		if (this.data.containsKey(coordinate))
			throw new IllegalArgumentException();
		this.data.put(coordinate, data);
	}

	public String getData(DataTableCoordinate coordinate) {
		if (coordinate == null)
			throw new NullPointerException();
		String out = data.get(coordinate);
		return (out != null) ? out : "";
	}

}
