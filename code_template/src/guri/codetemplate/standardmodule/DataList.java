package guri.codetemplate.standardmodule;

import java.util.LinkedList;

public class DataList {

	public String data = null;

	public LinkedList<DataList> array = null;

	public int dimension = 0;

	public DataList(LinkedList<DataList> array, int dimension) {
		this.array = array;
		this.dimension = dimension;
	}

	public DataList(String data) {
		this.data = data;
	}

}
