import java.sql.Connection;
import java.sql.DriverManager;

public class Sudoku {

	public static void main(String[] args) {
		LoginPage loginPage = new LoginPage();
//		makeConnection();
	}

	public static Connection makeConnection() {

//		String url = "jdbc:oracle:thin:@10.30.3.95:1521:orcl";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String User = "c##1801214";
		String pw = "p1801214";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, User, pw);
			return conn;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
