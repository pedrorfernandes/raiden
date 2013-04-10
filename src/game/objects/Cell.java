package game.objects;

class Cell {
	public int i;
	public int j;

	public Cell(){
		i = 0;
		j = 0;
	}

	public Cell(int row, int col){
		i = row;
		j = col;
	}
}