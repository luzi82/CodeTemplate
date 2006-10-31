package guri.codetemplate.standardmodule.datatable;

import java.util.TreeSet;

public class DataTableView {

	final private DataTableBase base;

	final private DataTableCoordinate index;

	public DataTableView(DataTableBase base) {
		this.base = base;
		this.index = new DataTableCoordinate(base.dimension);
	}

	public DataTableView(DataTableView view, DataTableCoordinate coor) {
		this.base = view.base;
		this.index = new DataTableCoordinate(view.index, coor);
	}

	public String getData(DataTableCoordinate coor) {
		if (coor == null)
			throw new NullPointerException();
		if (coor.dimension() != base.dimension)
			throw new IllegalArgumentException();
		DataTableCoordinate c = new DataTableCoordinate(index, coor);
		if (!c.unique())
			throw new IllegalArgumentException();
		return base.getData(c);
	}

	public TreeSet<DataTableCoordinate> getSubViewCoorSet(int coor) {
		TreeSet<DataTableCoordinate> ret = new TreeSet<DataTableCoordinate>();
		if (coor >= index.dimension())
			throw new IllegalArgumentException();
		if (index.coordinate(coor) != null) {
			ret.add(index);
			return ret;
		}
		String[] oriCoor = index.coordinate();
		for (DataTableCoordinate i : base.keySet()) {
			if (!index.contains(i))
				continue;
			oriCoor[coor] = i.coordinate(coor);
			DataTableCoordinate j = new DataTableCoordinate(oriCoor);
			ret.add(j);
		}
		return ret;
	}

	public int dimension() {
		return base.dimension;
	}

	public DataTableCoordinate coordinate() {
		return index;
	}

	public String value() {
		if (!index.unique())
			throw new IllegalArgumentException();
		return base.getData(index);
	}

}
