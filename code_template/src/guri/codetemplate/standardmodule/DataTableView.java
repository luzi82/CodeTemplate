package guri.codetemplate.standardmodule;

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

}
