
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MyCell {
	int x, y;
	int width, height;
	Color color;
	boolean selected = false;

	MyCell(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	void draw(Graphics g) {
		g.setColor(this.color);
		g.fill3DRect(x, y, width, height, !selected);
	}

	public void setColor(Color a) {
		// TODO Auto-generated method stub
		this.color = a;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Rectangle getCellRect() {
		return new Rectangle(x, y, width, height);
	}

}

class CellNumber {
	int x, y;
	Color color;
	int number;

	CellNumber(int x, int y, Color color, int number) {
		this.x = x;
		this.y = y;
		this.color = color;

		this.number = number;
	}

	void draw(Graphics g) {
		g.setColor(this.color);
		Font f = new Font("SansSerif", Font.BOLD, 30);
		g.setFont(f);

		if (this.number != 0) {
			g.drawString("" + number, x, y);
		} 

	}

	public void setColor(Color a) {
		// TODO Auto-generated method stub
		this.color = a;
	}
}

class PlayerInput {
	int x, y;
	Color color;
	int number;

	PlayerInput(int col, int row, Color color) {
		this.x = col;
		this.y = row;
		this.color = color;
	}

	void draw(Graphics g) {

		if (this.number != 0) {
			g.setColor(this.color);
			Font f = new Font("SansSerif", Font.BOLD, 30);
			g.setFont(f);

			g.drawString("" + number, x, y);
		}
	}

	public void setColor(Color a) {
		// TODO Auto-generated method stub
		this.color = a;
	}
}

class BoardPanel extends JPanel {
	private static boolean STARTED = false;
	public static int ROW = 9;
	public static int COL = 9;
	public MyCell[][] cells;
	public CellNumber[][] cellNumbers;
	public PlayerInput[][] playerInput;
	public int[][] playerSolve =new int[ROW][COL];

	private SudokuPos curPos = new SudokuPos(4, 4);
	
	public BoardPanel() {		
		setFocusable(true);
		cells = new MyCell[ROW][COL];
		cellNumbers = new CellNumber[ROW][COL];		
		playerInput = new PlayerInput[ROW][COL];
	
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {																				
				if (i < 3 && j < 3 || i > 5 && j > 5 || i < 3 && j > 5 || i > 5 && j < 3
						|| i > 2 && i < 6 && j > 2 && j < 6) {
					cells[i][j] = new MyCell(60 * i + 0, 60 * j + 0, 60, 60, Color.LIGHT_GRAY);
				} else {
					cells[i][j] = new MyCell(60 * i + 0, 60 * j + 0, 60, 60, Color.white);
				}				
			}			
		}
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {				
				if (isStarted()) {
					onKeyPressed(e);
				}				
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (isStarted()) {
					onMousePressed(e);	
				}				
			}
		});
	}

	public boolean isStarted() {		
		return STARTED;
	}
	
	public void setStarted(boolean started) {
		repaint();
		this.STARTED = started;
	}
	
	public void setPuzzle( ) {
		MyBoard.createBoard();
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {		
					cellNumbers[i][j] = new CellNumber(60 * i + 23, 60 * j + 42,  Color.BLACK, MyBoard.PUZZLE[j][i]);					
					this.playerInput[i][j] = new PlayerInput(60 * i + 23, 60 * j + 42, Color.BLUE);
					this.playerSolve[i][j] = MyBoard.PUZZLE[i][j];	
			}		
		}				
	}
	
	public void loadPuzzle() {
		Statement stmt;
		int[][] input = new int [9][9];		
		int[][] solve = new int [9][9];
		int[][] board = new int [9][9];
		int[][] puzzle = new int [9][9];
		MyBoard.BOARD = new int[9][9];
		MyBoard.PUZZLE = new int[9][9];
				 
		Connection con = Sudoku.makeConnection();
		try {
			stmt = con.createStatement();
			int temp = 0;
			
			String sql = "select p_mm,p_ss from SUDOKU_USER where userId ="+"'"+ LoginPage.uId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			InGameStatus.mm = rs.getInt("p_mm");
			InGameStatus.ss = rs.getInt("p_ss");
						
			sql = "select P_PUZZLE,P_INPUT,P_BOARD,P_SOLVE from U_SAVE where userId="+"'"+LoginPage.uId+"'" +" order by P_SEQUENCE";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					rs.next();
					board[i][j] = rs.getInt("P_BOARD");
					puzzle[i][j] = rs.getInt("P_PUZZLE");
					input[i][j] = rs.getInt("P_INPUT");
					solve[i][j] = rs.getInt("P_SOLVE");										
				}
			}
			
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {	
					MyBoard.BOARD[i][j] = board[i][j];
					MyBoard.PUZZLE[i][j] = puzzle[i][j];
					
					cellNumbers[i][j] = new CellNumber(60 * i + 23, 60 * j + 42,  Color.BLACK, puzzle[j][i]);					
					this.playerInput[i][j] = new PlayerInput(60 * i + 23, 60 * j + 42, Color.BLUE);
					this.playerInput[i][j].number = input[i][j];
					this.playerSolve[i][j] = solve[i][j];							
				}		
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void onKeyPressed(KeyEvent e) {							
		int x =curPos.getX();
		int y =curPos.getY();
		int temp = 0;
		
		switch (e.getKeyCode()) {
		case 49: case 97: temp = 1;
			break;
		case 50: case 98: temp = 2;
			break;
		case 51:case 99: temp = 3;
			break;
		case 52: case 100: temp = 4;
			break;
		case 53: case 101: temp = 5;
			break;
		case 54: case 102: temp = 6;
			break;
		case 55: case 103: temp = 7;
			break;
		case 56: case 104: temp = 8;
			break;
		case 57: case 105: temp = 9;
			break;
		default:			
			break;
		}
		if (MyBoard.PUZZLE[y][x] == 0) {
			playerInput[x][y].number= temp;
			playerSolve[y][x] = temp;
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(playerSolve[i][j]+" ");
				}
				System.out.println();
			}
		}				
//		System.out.println(myPuzzle[x][y]);
		repaint();
	}

	protected void onMousePressed(MouseEvent e) {
//		System.out.println(e.getPoint());
//		System.out.print((int)e.getPoint().getX()/60 + " ");
//		System.out.println((int)e.getPoint().getY()/60);
		SudokuPos pos = getCellPosForPoint(e.getPoint());
		setCurCell(pos.getX(), pos.getY());
	}

	private void setCurCell(int x, int y) {
//		curPos.setPos(x,y);						
		if (curPos.getY() != y || curPos.getX() != x) {
			(getCell(curPos)).setSelected(false);
			curPos.setPos(x, y);
			(getCell(curPos)).setSelected(true);
			repaint();
		}
	}

	private MyCell getCell(SudokuPos pos) {
		return getCell(pos.getX(), pos.getY());
	}

	private MyCell getCell(int x, int y) {
		return cells[x][y];

	}

	private SudokuPos getCellPosForPoint(Point pt) {
		return new SudokuPos((int) pt.getX() / 60, (int) pt.getY() / 60);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				cells[i][j].draw(g);			
			}
		}
		if (isStarted()) {
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {			
						cellNumbers[i][j].draw(g);
						playerInput[i][j].draw(g);									
				}
			}
		}
	}
}


public class MyBoardGUI extends JFrame {
	BoardPanel borderPanel;
	InGameButton igb;
	InGameStatus igs;
	JFrame mp;
	MyBoardGUI(JFrame mp) {
		this.mp = mp;
		setSize(556, 640);
		Dimension dimension = this.getSize();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - dimension.width / 2, screenSize.height / 2 - dimension.height / 2);
		this.setResizable(false);							
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SUDOKU");
		
		borderPanel = new BoardPanel();
		
		igs = new InGameStatus(this);
		igs.setMaximumSize(dimension);
		
		igb = new InGameButton(this);		
		igb.setMaximumSize(dimension);		
		
		add(borderPanel);
		add(igs);
		add(igb);
		setVisible(true);
	}
}
