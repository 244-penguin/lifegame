package lifegame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class BoardView extends JPanel
	implements BoardListener, MouseListener, MouseMotionListener {
	BoardModel copy;
	boolean[][] tmp;
	int[] lastPoint = {0, 0};	//最後に押した・ドラッグした座標（行，列）を保存する

	BoardView(BoardModel model){
		copy = model;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		tmp = new boolean[model.numofLines("rows")][model.numofLines("cols")];
	}

	@Override
	public void paint(Graphics g) {
		int oneWid = (this.getWidth() -20) /copy.numofLines("cols");
		int oneHei = (this.getHeight() - 20) /copy.numofLines("rows");
		super.paint(g);

		//横線
		for(int i = 0; i <= copy.numofLines("rows"); ++i) {
		g.drawLine(10,10 + (i * oneHei), this.getWidth()-10, 10 + (i * oneHei));
		}

		//縦線
		for(int j = 0; j <= copy.numofLines("cols"); ++j) {
		g.drawLine(10 + (j * oneWid), 10, 10 + (j * oneWid), this.getHeight()-15);
		}

		//塗りつぶし
		for(int i = 0; i < copy.numofLines("rows"); ++i) {
			for(int j = 0; j < copy.numofLines("cols"); ++j) {
				if(copy.isAlive(j, i)) {
				g.fillRect(cols2x(i) + 1, rows2y(j) + 1, oneWid - 1, oneHei - 1);
				}
			}
		}
	}

	public int rows2y(int rows) {	//行数からy座標への変換
		int oneHei = (this.getHeight() - 20) /copy.numofLines("rows");
		return 10 + rows * oneHei;

	}
	public int cols2x(int cols) {	//列数からx座標への変換
 		int oneWid = (this.getWidth() -20) /copy.numofLines("cols");
		return 10 + cols * oneWid;
	}

	public int y2rows(int y) {	//y座標から行数への変換
		int oneHei = (this.getHeight() - 20) /copy.numofLines("rows");
		return (y - 10) / oneHei;
	}

	public int x2cols(int x) {	//x座標から列数への変換
		int oneWid = (this.getWidth() -20) /copy.numofLines("cols");
		return (x - 10) / oneWid;
	}

	@Override
	public void mouseDragged(MouseEvent e) {	//ドラッグして新たなセルに侵入したら，セルの状態を変える
		if((lastPoint[0] != x2cols(e.getX())) || (lastPoint[1] != y2rows(e.getY()))) {
			copy.changeCellState(x2cols(e.getX()), y2rows(e.getY()));
			this.repaint();
		}

		lastPoint[0] = x2cols(e.getX());
		lastPoint[1] = y2rows(e.getY());

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {	//マウスのボタンが押されたらその座標を含むセルの状態を変える
		copy.changeCellState(x2cols(e.getX()), y2rows(e.getY()));
		this.repaint();
		copy.backup(copy.returnCells(), tmp);
		lastPoint[0] = x2cols(e.getX());
		lastPoint[1] = y2rows(e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void updated(BoardModel m) {
		// TODO 自動生成されたメソッド・スタブ

	}

}