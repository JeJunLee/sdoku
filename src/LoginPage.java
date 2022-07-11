import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginPage extends JFrame {
	public static String uId;
	public JLabel lStatus;
	public int lStCount;
	LoginPage lp = this;

	public LoginPage() {
		this.setSize(200, 190);
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - dimension.width / 2, screenSize.height / 2 - dimension.height / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		LoginInput loginInput = new LoginInput();
		this.add(loginInput);

		setVisible(true);
	}

	class LoginInput extends JPanel implements ActionListener {
		JTextField lInPutId;
		JPasswordField lInputPw;
		
		public LoginInput() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel panel0 = new JPanel();

			JPanel panel1 = new JPanel();
			JLabel id = new JLabel("ID   ");
			lInPutId = new JTextField(10);
			panel1.add(id);
			panel1.add(lInPutId);

			JPanel panel2 = new JPanel();
			JLabel pw = new JLabel("PW");			
			lInputPw = new JPasswordField(10);	
			panel2.add(pw);
			panel2.add(lInputPw);
			
			JPanel panel3 = new JPanel();
			JButton loginB = new JButton("로그인");
			JButton signUpB = new JButton("회원가입");
			panel3.add(loginB);
			panel3.add(signUpB);
			
			JPanel panel4 = new JPanel();
			lStatus = new JLabel("");
			panel4.add(lStatus);
			
			loginB.addActionListener(this);
			signUpB.addActionListener(this);

			panel0.add(panel1);
			panel0.add(panel2);
			panel0.add(panel3);
			panel0.add(panel4);

			this.add(panel0);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			switch (button.getText()) {
			case "로그인":
				System.out.println(button.getText());
				boolean succ = false;						
				succ = login();															
				if (succ) {
					System.out.println("로그인 성공 메인페이지 이동");
					MainPage mainPage = new MainPage();
					dispose();
					
				} else {
					System.out.println("로그인 실패");
					lStatus.setText("로그인 실패("+lStCount+")");
					lStCount++;
					lInPutId.setText("");
					lInputPw.setText("");
				}
			
				break;
			case "회원가입":
				System.out.println(button.getText());
				lp.setVisible(false);
				SignUpPage signUpPage = new SignUpPage(lp);
				break;
			default:
				break;
			}
		}

		private boolean login(){						
			String id = lInPutId.getText();						
			String pw = String.valueOf( lInputPw.getPassword() );			
			
			if ( id.length() != 0 && pw.length() !=0 ) {
				Connection con = Sudoku.makeConnection();
				Statement stmt;				
				try {
					stmt = con.createStatement();									
					String sql = "select * from SUDOKU_USER where userId="+"'"+id+"'" +" AND userPw="+"'"+pw+"'";
					System.out.println(sql);
					ResultSet rs = stmt.executeQuery(sql);							
					if ( rs.next() ) {
						System.out.println("로그인 성공");					
						con.close();
						stmt.close();
						LoginPage.uId = id;
						return true;
					} else {
						System.out.println("로그인 실패");																
						con.close();
						stmt.close();
						return false;
					}				
				} catch (SQLException e) {					
					e.printStackTrace();
					return false;
				}
			}else {
				return false;
			}											
		}
	}
}

class SignUpPage extends JFrame {
	LoginPage lp;

	public SignUpPage(LoginPage lp) {
		this.lp = lp;
		this.setSize(200, 170);
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - dimension.width / 2, screenSize.height / 2 - dimension.height / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		SignUpInput signUpInput = new SignUpInput();
		this.add(signUpInput);

		setVisible(true);
	}

	class SignUpInput extends JPanel implements ActionListener {
		JTextField sInputId;
		JPasswordField sInputPw;
				
		public SignUpInput() {

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel panel0 = new JPanel();

			JPanel panel1 = new JPanel();
			JLabel id = new JLabel("ID   ");
			sInputId = new JTextField(10);
			panel1.add(id);
			panel1.add(sInputId);

			JPanel panel2 = new JPanel();
			JLabel pw = new JLabel("PW");
			sInputPw = new JPasswordField(10);
			panel2.add(pw);
			panel2.add(sInputPw);

			JPanel panel3 = new JPanel();
			JButton signUp = new JButton("가입하기");
			JButton back = new JButton("돌아가기");

			panel3.add(signUp);
			panel3.add(back);

			signUp.addActionListener(this);
			back.addActionListener(this);

			panel0.add(panel1);
			panel0.add(panel2);
			panel0.add(panel3);

			this.add(panel0);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			switch (button.getText()) {
			case "가입하기":
				System.out.println(button.getText());				
				boolean succ = signUp();
				
				if (succ) {
					System.out.println("가입성공");
					lp.lStatus.setText("가입 성공");
				} else {
					System.out.println("가입실패");
					lp.lStatus.setText("가입 실패");
				}
				lp.setVisible(true);
				dispose();
				
				break;
			case "돌아가기":
				System.out.println(button.getText());
				lp.setVisible(true);
				dispose();
				break;
			default:
				break;
			}
		}

		private boolean signUp() {
			Connection con = Sudoku.makeConnection();
			Statement stmt;
			String id = sInputId.getText();						
			String pw = String.valueOf( sInputPw.getPassword() );
			
			if (id.length() != 0 && pw.length() !=0) {
				try {
					stmt = con.createStatement();
					
					String sql = "select * from SUDOKU_USER where userId="+"'"+id+"'" ;
					System.out.println(sql);
					ResultSet rs = stmt.executeQuery(sql);
					
					if ( !rs.next() ) {
						sql = "insert into SUDOKU_USER (userId, userPw) VALUES("+"'"+id+"'"+","+"'"+pw+"'"+")";					
						System.out.println(sql);
						stmt.executeQuery(sql);
						System.out.println("Insert 성공");
						
						con.close();
						stmt.close();
						return true;
					} else {
						System.out.println("가입실패");
						return false;
					}					
				} catch (SQLException e) {					
					e.printStackTrace();
					return false;
				}
			}
			return false;
		}
	}
}
