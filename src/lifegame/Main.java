package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main implements Runnable{

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Main());
	}

	public void run() {
		test();
	}

	public static void test() {
		BoardModel model = new BoardModel(10,10);
		model.addListener(new ModelPrinter());

		//ウィンドウの作成
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//ウィンドウ内部を占有する「ベース」パネルの作成
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 300));	//希望サイズの指定
		frame.setMinimumSize(new Dimension(300, 200));	//最小サイズの指定

		frame.setTitle("Lifegame");

		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		base.add(view, BorderLayout.CENTER);

		//ボタンの作成
		JButton next = new JButton("Next");
		JButton undo = new JButton("Undo");
		JButton newgame = new JButton("New Game");
		JButton auto = new JButton("Auto");
		next.addActionListener(new NextButton(model, view));
		undo.addActionListener(new UndoButton(model, view));
		newgame.addActionListener(new NewGameButton());
		auto.addActionListener(new AutoButton());

		//ボタンを配置するパネルの作成
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());

		//ボタンをボタン用のパネルに配置
		buttonPanel.add(next);
		buttonPanel.add(undo);
		buttonPanel.add(newgame);
		buttonPanel.add(auto);

		frame.pack();
		frame.setVisible(true);
	}

}