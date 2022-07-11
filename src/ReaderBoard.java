import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReaderBoard extends JFrame implements ActionListener{
	Connection con = Sudoku.makeConnection();
	Statement stmt;		
	JFrame mp;
	
	ReaderBoard(){}
	
	ReaderBoard(JFrame mp){
		this.mp=mp;
		setSize(500, 350);
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - dimension.width / 2, screenSize.height / 2 - dimension.height / 2);
		this.setResizable(false);
				
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setVisible(true);
		
		Score score = new Score();		
			
		JPanel goMain = new JPanel();
		goMain.setLayout(new FlowLayout());
		goMain.setMaximumSize(screenSize);
		JButton goM = new JButton("메인화면");	
		goMain.add(goM);

		this.add(score);
		this.add(goMain);	
		
		goM.addActionListener(this);
		
		
	
	}
	
	class Score extends JPanel{	
		ResultSet rs;
		JLabel[] preRs = new JLabel[10];		
		Score(){
//			this.setLayout(new FlowLayout());
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			try {
				stmt = con.createStatement();
				
				String sql = "select * from (select * from U_SCORE order by CLEAR_DATE deSC)where ROWNUM <= 10" ;
				System.out.println(sql);
				rs =  stmt.executeQuery(sql);
								
				while (rs.next()) {
					int i = 0;
					int clearL = rs.getInt("CLEAR_L");
					int clearTmm = rs.getInt("CLEAR_T_mm");
					int clearTss = rs.getInt("CLEAR_T_ss");
					String userId = rs.getString("USERID");
															
					preRs[i] = new JLabel();
					preRs[i].setText( String.format("ID: %s,  Clear Time: %s분 %s초,   Level: %d   ", userId,clearTmm,clearTss,clearL ) );
					preRs[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
					preRs[i].setHorizontalAlignment(JLabel.LEFT);
					
					System.out.println(preRs[0].getText());
						
					add(preRs[i]);
					
					i++;
				}
				
				stmt.close();
				con.close();
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("메인화면");
		mp.setVisible(true);
		this.dispose();
		
	}
}



