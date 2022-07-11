import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InGameStatus extends JPanel{
	MyBoardGUI gui;
	public static int mm;
	public static int ss;
	public Timer t = new Timer();
	public TimerTask task;
	JLabel time;
	
	
	public InGameStatus(MyBoardGUI mb) {
		this.gui = mb;
		this.setBackground(Color.WHITE);		
		
		setLayout(new GridLayout(1, 2));
		
		JLabel uId= new JLabel(LoginPage.uId + "¥‘ π›∞©Ω¿¥œ¥Ÿ");
		 time = new JLabel(  String.format("%02d∫– : %02d√ ", mm , ss) );					
			
		add(uId);
		add(time);
	
	}

	public void stopSchedule() {	
		if (mm > 1 ||  ss > 1) {
			t.cancel();
			mm = 0;
			ss = 0;
		}	
	}
	
	
	public void startSchedule(Timer t2) {
		this.t=t2;
		task = new TimerTask() {
			@Override
			public void run() {
				ss++;
				time.setText(  String.format("%02d∫– : %02d√ ", mm , ss) );
				if (ss == 59) {
					mm++;
					ss = 0;					
				}				
			}			
		};	
		t.schedule(task, 0 , 1000);	
	}
}




	
	

