import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PResult extends JFrame {
	MyBoardGUI gui;

	public PResult(MyBoardGUI gui, int i) {
		this.gui = gui;
		gui.setVisible(false);
		this.setSize(200, 100);
		this.setLayout(new FlowLayout());
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - dimension.width / 2, screenSize.height / 2 - dimension.height);

		JLabel rst = new JLabel();
		JButton jbt = new JButton();
		jbt.addActionListener(new RListener());
		if (i == 1) {
			rst.setText("정답입니다 결과가 저장되었습니다.");
			jbt.setText("메인화면");
			gui.dispose();
		} else {
			rst.setText("오답입니다 다시 확인해주세요.");
			jbt.setText("확인하기");
			
//			gui.setVisible(true);			
		}
		this.add(rst);
		this.add(jbt);

		this.setVisible(true);
	}

	private class RListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			System.out.println(button.getText());
			if (button.getText() == "메인화면") {
				gui.mp.setVisible(true);
				gui.borderPanel.setStarted(false);
				System.out.println("1");
				dispose();
			} else {
				gui.setVisible(true);
				dispose();
			}
		}
	}
}

class InGameButton extends JPanel implements ActionListener {
	MyBoardGUI gui;
	BoardPanel pn;
	InGameStatus igs;
	Timer t = null;
	public InGameButton(MyBoardGUI myBoardGUI) {
		this.gui = myBoardGUI;
		this.pn = gui.borderPanel;
		this.igs = gui.igs;
		
		setLayout(new GridLayout(1, 4));

		JButton check = new JButton("정답확인");
		JButton main = new JButton("메인메뉴");
		JButton save = new JButton("저장하기");
		JButton conti = new JButton("불러오기");
		JButton newGame = new JButton("새로하기");

		check.setBackground(Color.white);
		main.setBackground(Color.white);
		save.setBackground(Color.white);
		conti.setBackground(Color.white);
		newGame.setBackground(Color.white);

		add(check);
		add(main);
		add(save);
		add(conti);
		add(newGame);

		check.addActionListener(this);
		main.addActionListener(this);
		save.addActionListener(this);
		conti.addActionListener(this);
		newGame.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		switch (button.getText()) {
		case "정답확인":
			if (pn.isStarted()) {
				if (Arrays.deepEquals(MyBoard.BOARD, pn.playerSolve)) {
					System.out.println("정답!");
					PResult result = new PResult(gui, 1);
					SaveData newScore = new SaveData();

				} else {
					System.out.println("오답!");
					PResult result = new PResult(gui, 0);
				}
			}else {
				System.out.println("isStarted false");
			}

			break;
		case "메인메뉴":
			System.out.println("메인메뉴");
			pn.setStarted(false);
			gui.setVisible(false);
			gui.mp.setVisible(true);

			igs.stopSchedule();
			
			System.out.println(InGameStatus.ss);
			
			break;
		case "저장하기":
			
			if(pn.isStarted()) {
				System.out.println("저장하기");
				new SaveData(pn.playerSolve, pn.playerInput);
			}
					
			break;
		case "불러오기":
			System.out.println("불러오기");
			
			if( SaveData.isSaveExist() ) {
				igs.stopSchedule();
				pn.loadPuzzle();
				pn.setStarted(true);								
				t = new Timer();				
				igs.startSchedule(t);
			}
			

			break;
		case "새로하기":
			System.out.println("새로하기");

			pn.setPuzzle();
			pn.setStarted(true);
			igs.stopSchedule();
			t = new Timer();			
			igs.startSchedule(t);
			
			break;

		default:
			break;
		}
		pn.requestFocus(true);

	}
}