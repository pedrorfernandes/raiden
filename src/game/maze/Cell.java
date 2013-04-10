package game.maze;

/**
 * Contains the row and column of a given maze cell.
 */
public class Cell {
	
	/** The row. */
	public int i;
	
	/** The column. */
	public int j;

	/**
	 * Instantiates a new cell with row and column as 0.
	 */
	public Cell(){
		i = 0;
		j = 0;
	}

	/**
	 * Instantiates a new cell with given row and column.
	 *
	 * @param row the cell row
	 * @param col the cell column
	 */
	public Cell(int row, int col){
		i = row;
		j = col;
	}
}