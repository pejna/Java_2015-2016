package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

public class RCPosition {

	private int column;

	private int row;


	public RCPosition(int row, int column) {
		assignValues(row, column);
	}


	public RCPosition(String position) {
		Objects.requireNonNull(position);

		String[] args = position.trim().split(",");
		if (args.length != 2) {
			throw new IllegalArgumentException(
					"Warning- Given string has illegal argument count, "
							+ "expected 2 but was given: " + args.length + "!");
		}

		assignValues(Integer.parseInt(args[0].trim()),
				Integer.parseInt(args[1].trim()));
	}


	private void assignValues(int row, int column) {
		this.column = column;
		this.row = row;
	}


	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}


	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "(" + column + "," + row + ")";
	}

}
