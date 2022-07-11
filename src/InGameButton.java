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
			rst.setText("�����Դϴ� ����� ����Ǿ����ϴ�.");
			jbt.setText("����ȭ��");
			gui.dispose();
		} else {
			rst.setText("�����Դϴ� �ٽ� Ȯ�����ּ���.");
			jbt.setText("Ȯ���ϱ�");
			
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
			if (button.getText() == "����ȭ��") {
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

		JButton check = new JButton("����Ȯ��");
		JButton main = new JButton("���θ޴�");
		JButton save = new JButton("�����ϱ�");
		JButton conti = new JButton("�ҷ�����");
		JButton newGame = new JButton("�����ϱ�");

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
		case "����Ȯ��":
			if (pn.isStarted()) {
				if (Arrays.deepEquals(MyBoard.BOARD, pn.playerSolve)) {
					System.out.println("����!");
					PResult result = new PResult(gui, 1);
					SaveData newScore = new SaveData();

				} else {
					System.out.println("����!");
					PResult result = new PResult(gui, 0);
				}
			}else {
				System.out.println("isStarted false");
			}

			break;
		case "���θ޴�":
			System.out.println("���θ޴�");
			pn.setStarted(false);
			gui.setVisible(false);
			gui.mp.setVisible(true);

			igs.stopSchedule();
			
			System.out.println(InGameStatus.ss);
			
			break;
		case "�����ϱ�":
			
			if(pn.isStarted()) {
				System.out.println("�����ϱ�");
				new SaveData(pn.playerSolve, pn.playerInput);
			}
					
			break;
		case "�ҷ�����":
			System.out.println("�ҷ�����");
			
			if( SaveData.isSaveExist() ) {
				igs.stopSchedule();
				pn.loadPuzzle();
				pn.setStarted(true);								
				t = new Timer();				
				igs.startSchedule(t);
			}
			

			break;
		case "�����ϱ�":
			System.out.println("�����ϱ�");

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