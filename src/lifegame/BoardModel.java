package lifegame;

import java.util.ArrayList;

public class BoardModel{
	private int cols;	//行数
	private int rows;	//列数
	private int numofbackup;	//さかのぼれる盤の数
	private boolean[][] cells;
	private boolean[][][] prevcells;	//以前の盤の状態を保存した配列
	private ArrayList<BoardListener> listeners;

	public BoardModel(int c, int r){
		cols = c;
		rows = r;
		numofbackup = 0;
		cells = new boolean[rows][cols];
		prevcells = new boolean[1000][rows][cols];
		listeners = new ArrayList<BoardListener>();
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public void printForDebug() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (cells[i][j]) {
					System.out.print("*");
				}else {
					System.out.print(".");
				}

				if(j == (cols - 1)) {
					System.out.println();
				}
			}
		}
		System.out.println();
	}

	public void changeCellState(int x, int y) {
		backup(cells, prevcells[numofbackup]);
		++numofbackup;

		if (cells[y][x] == false) {
			cells[y][x] = true;
		}else {
			cells[y][x] = false;
		}
		this.fireUpdate();
	}

	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}

	public void fireUpdate() {
		for (BoardListener listener: listeners) {
			listener.updated(this);
		}
	}

	public void next() {	//盤面を一つ進める
		backup(cells, prevcells[numofbackup]);
		++numofbackup;
		boolean[][] tmp = new boolean[rows][cols];
		backup(cells,tmp);

		for(int k = 0; k < rows; k++) {
			System.arraycopy(cells[k], 0, tmp[k], 0, cols);
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (cells[i][j]) {
					if (search(i, j) == 2 || search(i, j) == 3) {
					}else {
						tmp[i][j] = false;
					}
				}else {
					if (search(i, j) == 3) {
						tmp[i][j] = true;
					}
				}
			}
		}

		backup(tmp, cells);
		this.fireUpdate();
	}

	public void undo(){	//盤面の一つ巻き戻し
		if(isUndoable()) {
			--numofbackup;
			backup(prevcells[numofbackup], cells);

			this.fireUpdate();
		}
	}

	public int search(int i, int j) {	//あるセルの周囲8マスに生きているセルがいくつあるのか探索
		int num = 0;

		if(0 <= (i-1) && 0 <= (j-1)) {
			if(cells[i-1][j-1]) {
				num++;
			}
		}

		if(0 <= (i-1)) {
			if(cells[i-1][j]) {
				num++;
			}
		}

		if(0 <= (i-1) && (j+1) < cols) {
			if(cells[i-1][j+1]) {
				num++;
			}
		}

		if(0 <= (j-1)) {
			if(cells[i][j-1]) {
				num++;
			}
		}

		if((j+1) < cols) {
			if(cells[i][j+1]) {
				num++;
			}
		}

		if((i+1) < rows && 0 <= (j-1)) {
			if(cells[i+1][j-1]) {
				num++;
			}
		}

		if((i+1) < rows) {
			if(cells[i+1][j]) {
				num++;
			}
		}

		if((i+1) < rows && (j+1) < cols) {
			if(cells[i+1][j+1]) {
				num++;
			}
		}

		return num;
	}

	public void backup(boolean[][] src, boolean[][] dist) {	//二次元配列のコピー
		for(int k = 0; k < rows; k++) {
			System.arraycopy(src[k], 0, dist[k], 0, cols);
		}
	}

	public boolean isUndoable() {
		if(0 < numofbackup) {
			return true;
		}else {
			return false;
		}
	}

	public int numofLines(String lineName) {	//引数によって，行数，列数を返す
		if(lineName == "rows") {
			return rows;
		}else if(lineName == "cols") {
			return cols;
		}else {
			return -1;
		}
	}

	public boolean[][] returnCells(){
		return cells;
	}

	public boolean isAlive(int x, int y) {
		if(cells[x][y]) {
			return true;
		}
		return false;
	}

}