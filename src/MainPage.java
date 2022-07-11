import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.remote.SubjectDelegationPermission;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class MainPage extends JFrame {
	
	public MainPage() {		
		setSize(480,100);
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width/2 - dimension.width/2 , screenSize.height/2 - dimension.height );
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setTitle("SUDOKU");
		
		MainButton mbutton = new MainButton(this);
		
		add(mbutton);		
		setVisible(true);
		
	}	
}

class MainButton extends JPanel{
	public MainButton(JFrame mp) {
		setLayout(new GridLayout(1,4));
		
		JButton startG = new JButton("���ӽ���");
		JButton level = new JButton("���̵�");
		JButton preRes = new JButton("�������");
		JButton exit = new JButton("����");
		
		startG.setBackground(Color.white);
		level.setBackground(Color.white);
		preRes.setBackground(Color.white);
		exit.setBackground(Color.white);
		
		add(level);
		add(startG);
		add(preRes);
		add(exit);
		
		level.addActionListener(new MainListener(mp));		
		startG.addActionListener(new MainListener(mp));
		preRes.addActionListener(new MainListener(mp));
		exit.addActionListener(new MainListener(mp));	
				
	}
	
	
	
}

class MainListener implements ActionListener{
	JFrame mp;
	MyLevel myLevel;
	MyBoardGUI myBoardGUI;
	
	public MainListener(JFrame mp) {
		this.mp = mp;
	}
	public MainListener() {}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		
		JButton button = (JButton)e.getSource();
				
		switch (button.getText()) {
		case "���̵�":
			System.out.println(button.getText());
			mp.setVisible(false);
			myLevel = new MyLevel(mp);	
			break;
			
		case "���ӽ���":
			System.out.println(button.getText());
			mp.setVisible(false);
			myBoardGUI = new MyBoardGUI(mp);
			break;
			
		case "�������": 
			System.out.println(button.getText());
			mp.setVisible(false);
			ReaderBoard readerBoard = new ReaderBoard(mp);
			break;
			
		case "����": 
			System.out.println(button.getText());
			System.exit(0);	
			break;
			
		default:	
			break;
		}		
	}
}

class MyLevel extends JFrame implements ActionListener{
	private static int LEVEL = 1;
	JFrame lv= new JFrame();
	JFrame mp;
	
	public MyLevel(JFrame mp) {
		lv.setSize(350,100);
		Dimension dimension = lv.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		lv.setLocation(screenSize.width/2 - dimension.width/2 , screenSize.height/2 - dimension.height );
		lv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		lv.setLayout(new GridLayout(1,3));
		
		JButton low = new JButton("�ʱ�");
		JButton middle= new JButton("�߱�");
		JButton high = new JButton("���");
		lv.add(low);
		lv.add(middle);
		lv.add(high);
		
		low.addActionListener(this);
		middle.addActionListener(this);
		high.addActionListener(this);
		
		lv.setVisible(true);
		this.mp = mp;
		
	}
	
	public static int GetLevel() {
		return LEVEL;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton)e.getSource();
		
		switch (button.getText()) {
		case "�ʱ�":
			System.out.println(button.getText());
			LEVEL = 1;			
			break;
		case "�߱�": 
			LEVEL = 2;
			System.out.println(button.getText());		
			break;
		case "���": 
			LEVEL = 3;
			System.out.println(button.getText());	
			break;		
		default:	
			break;
		}	
		
		System.out.println(LEVEL);
		mp.setVisible(true);
		lv.dispose();
		
	}
}

