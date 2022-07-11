import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyBoard {
	
	public static int[][] BOARD;
	public static int [][] PUZZLE;
	
	
	public MyBoard() {
		BOARD = new int[9][9];
		
		List<Integer> seed = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			seed.add(i);
		}
		Collections.shuffle(seed);
		System.out.println(seed);
		
		int n1=seed.get(0) ,n2=seed.get(1), n3=seed.get(2), n4=seed.get(3), n5=seed.get(4), n6=seed.get(5), n7=seed.get(6), n8=seed.get(7), n9=seed.get(8);
		int[][] board = {{n1,n2,n3,n4,n5,n6,n7,n8,n9}
						,{n4,n5,n6,n7,n8,n9,n1,n2,n3}
						,{n7,n8,n9,n1,n2,n3,n4,n5,n6}
						,{n2,n3,n1,n5,n6,n4,n8,n9,n7}
						,{n5,n6,n4,n8,n9,n7,n2,n3,n1}
						,{n8,n9,n7,n2,n3,n1,n5,n6,n4}
						,{n3,n1,n2,n6,n4,n5,n9,n7,n8}
						,{n6,n4,n5,n9,n7,n8,n3,n1,n2}
						,{n9,n7,n8,n3,n1,n2,n6,n4,n5}
						};

//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board.length; j++) {
//				System.out.print(board[i][j] +" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		//Çà ¼¯±â
		List<Integer> random = new ArrayList<>();
		for (int i=0; i<3;i++) {
			random.add(i);
		}
		
		
		for(int i=0; i<7; i+=3) {
			Collections.shuffle(random);
			int[] row_temp = board[i];			
			
			board[i] = board[random.get(0)+i];
			board[random.get(0)+i] = board[random.get(1)+i];
			board[random.get(1)+i] = row_temp;
		}
		
//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board.length; j++) {
//				System.out.print(board[i][j] +" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		//¿­ ¼¯±â

		int count = 0;
		int [][]copy = new int[9][9];
		while (count < 9) {
			for(int i=0; i<9; i++) {
				copy[count][i] = board[i][count];
			}
			count++;
		}
		
		
		for(int i=0; i<7; i+=3) {
			Collections.shuffle(random);
			
			
			
			
			int[] col_temp = copy[i];
			copy[i] = copy[random.get(0)+i];
			copy[random.get(0)+i] = copy[random.get(1)+i];
			copy[random.get(1)+i] = col_temp;
		}
		count = 0;
		
//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board.length; j++) {
//				System.out.print(copy[i][j] +" ");
//			}
//			System.out.println();
//		}
		
		
		while (count < 9) {
			for(int i=0; i<9; i++) {
				BOARD[i][count] = copy[count][i];
			}
			count++;
		}	
		
		//³­ÀÌµµ¿¡ µû¸¥ ºóÄ­¼ö
		int holes = MyLevel.GetLevel() * 20;
		System.out.println(holes);			
		
		PUZZLE = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				PUZZLE[i][j] = BOARD[i][j];				
			}			
		}
		
		int i,j,holes_num;
		holes_num = holes;
		
		while(holes_num > 0) {
			i = (int)(Math.random()*9);
			j = (int)(Math.random()*9);
			if (PUZZLE[i][j] ==0 ) {
				continue;
			}
			PUZZLE[i][j]=0;
			holes_num--;
		}
		
//		for (int k = 0; k < 9; k++) {
//			for (int p = 0; p < 9; p++) {
//				System.out.print(PUZZLE[k][p]);
//				System.out.print(" ");				
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		for (int k = 0; k < 9; k++) {
			for (int p = 0; p < 9; p++) {
				System.out.print(BOARD[k][p]);
				System.out.print(" ");
				
			}
			System.out.println();
		}
	}
	
	public static void createBoard() {
		MyBoard newPuzzle = new MyBoard();
	
	}

}
