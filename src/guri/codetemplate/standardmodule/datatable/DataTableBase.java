package guri.codetemplate.standardmodule.datatable;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataTableBase {

	private final TreeMap<DataTableCoordinate, String> data = new TreeMap<DataTableCoordinate, String>();

	final public int dimension;

	public DataTableBase(int dimension) {
		this.dimension = dimension;
	}

	public void setData(DataTableCoordinate coordinate, String data) {
		if (coordinate == null)
			throw new NullPointerException();
		if (data == null)
			throw new NullPointerException();
		if (coordinate.dimension() != dimension)
			throw new IllegalArgumentException();
		if (!coordinate.unique())
			throw new IllegalArgumentException();
		if (this.data.containsKey(coordinate)){
//			System.err.println(this.data.size());
//			for(String i:coordinate.coordinate()){
//				System.err.println(i);
//			}
			throw new IllegalArgumentException();
		}
		this.data.put(coordinate, data);
	}

	public String getData(DataTableCoordinate coordinate) {
		if (coordinate == null)
			throw new NullPointerException();
		if (coordinate.dimension() != dimension)
			throw new IllegalArgumentException();
		if (!coordinate.unique())
			throw new IllegalArgumentException();
		String out = data.get(coordinate);
		return (out != null) ? out : "";
	}

	public Set<DataTableCoordinate> keySet() {
		return new TreeSet<DataTableCoordinate>(data.keySet());
	}

}
