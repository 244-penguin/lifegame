package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NextButton implements ActionListener {
	BoardModel copy;
	BoardView vcopy;

	NextButton(BoardModel model, BoardView view){
		copy = model;
		vcopy = view;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copy.next();
		vcopy.repaint();
	}

}