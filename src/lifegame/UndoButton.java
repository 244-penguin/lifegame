package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButton implements ActionListener {
	BoardModel copy;
	BoardView vcopy;

	UndoButton(BoardModel model, BoardView view){
		copy = model;
		vcopy = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copy.undo();
		vcopy.repaint();

	}

}