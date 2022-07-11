import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SaveData {
	Statement stmt;

	SaveData() {
		Connection con = Sudoku.makeConnection();
		String id = LoginPage.uId;
		int lv = MyLevel.GetLevel();
		int mm = InGameStatus.mm;
		int ss = InGameStatus.ss;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat tss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			stmt = con.createStatement();

			String sql = "insert into U_SCORE (CLEAR_L,CLEAR_T_MM,CLEAR_T_SS,USERID,CLEAR_DATE) "
						+ "VALUES(" + lv + "," + mm + "," + ss + "," +"'"+id+"'"+ ","+"'"+tss.format(ts)+"'"+")";
			System.out.println(sql);
			stmt.executeQuery(sql);
			
			stmt.close();
			con.close();			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SaveData(int[][] pSolve, PlayerInput[][] pInput) {
		Connection con = Sudoku.makeConnection();
						
		try {			
			stmt = con.createStatement();	
			
			String sql = "delete from U_SAVE where USERID = "+"'"+LoginPage.uId+"'";
			System.out.println(sql);
			stmt.executeQuery(sql);
			
			sql = "update SUDOKU_USER set P_mm="+InGameStatus.mm+","+" P_ss="+InGameStatus.ss +"where USERID ='"+LoginPage.uId+"'";
			System.out.println(sql);
			stmt.executeQuery(sql);
			
			
			int temp = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {		
			sql = "insert into U_SAVE(USERID, P_BOARD, P_PUZZLE, P_INPUT, P_SOLVE, P_SEQUENCE ) values ("
					+"'"+LoginPage.uId+"',"+ MyBoard.BOARD[i][j] +","+ MyBoard.PUZZLE[i][j] +","+ pInput[i][j].number +","+ pSolve[i][j] +","+ temp+")";
			
			System.out.println(sql);
			stmt.executeQuery(sql);
			temp++;
				}				
			}					
				stmt.close();
				con.close();		
		} catch (SQLException e) {					
			e.printStackTrace();
		}	
	}

	public static boolean isSaveExist() {
		Connection con = Sudoku.makeConnection();
		
		try {			
			Statement stmt = con.createStatement();
			
			String sql = "select * from U_SAVE where USERID="+"'"+LoginPage.uId+"'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				System.out.println("¼º°øÇÔ");
				return true;
			} else {
				return false;
			}	
		} catch (SQLException e) {					
			e.printStackTrace();
			return false;
		}			
	}
	
	
}
