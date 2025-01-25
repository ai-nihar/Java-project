import java.util.*;
class General
{
	Scanner sc = new Scanner(System.in);
	int validateChoice(String s, int from, int to)
	{
		char c = s.charAt(0);
		int n = c-48;
		if(s.length()!=1 || (n>to || n<from))
		{
			System.out.print("Please enter valid input from give options (" + from + "-" + to + ") : ");
			return validateChoice(sc.next().trim(), from, to);
		}
		else
			return n;
	}
}
class TicTacToe
{
	Scanner sc = new Scanner(System.in);
	General obj = new General();
	
	char[][] board = new char[3][3];
	String player1;
	String player2;
	String player;
	String winner;
	char sign1;
	char sign2;
	char sign;
	char winnerSign;
	int position;
	boolean isCompPlaying = false;
	void initializeBoard()
	{
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				board[i][j] = '-';
			}
		}
	}
	void printBoard()
	{
		System.out.println("\n+---------------+---------------+---------------+");
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				System.out.print("|\t" + (char)board[i][j] + "\t");
			}
			System.out.println("|\n+---------------+---------------+---------------+");
		}
	}
	void user_move(String posi, char uSign)
	{
		int i=0, j=-1;
		int position = obj.validateChoice(posi, 1, 9);
		while(position>3)
		{
			position-=3;
			i++;
		}
		j+=position;
		if(board[i][j]=='-')
		{
			board[i][j] = (char)uSign;
			printBoard();
		}
		else
		{
			System.out.print("\nSquare is occupied.\ntry another square : ");
			user_move(sc.next().trim(), uSign);
		}
	}
	void computer_move(char uSign)
	{
		int i=0, j=-1;
		int position=0;
		while(position==0)
			position = (int)(Math.random()*10);
		int temp = position;
		while(position>3)
		{
			position-=3;
			i++;
		}
		j+=position;
		if(board[i][j]=='-')
		{
			System.out.println("\nComputer's move : " + temp + "\n");
			board[i][j] = (char)uSign;
			printBoard();
		}
		else
			computer_move(uSign);
	}
	boolean checkWin(char uSign)
	{
		for (int i = 0; i < 3; i++) 
		{ 
			if (board[i][0] == uSign && board[i][1] == uSign && board[i][2] == uSign) return true; 
			if (board[0][i] == uSign && board[1][i] == uSign && board[2][i] == uSign) return true;
		} 
		if (board[0][0] == uSign && board[1][1] == uSign && board[2][2] == uSign) return true;
		if (board[0][2] == uSign && board[1][1] == uSign && board[2][0] == uSign) return true;
		return false;
	}
	boolean isDraw()
	{ 
		for(int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
				if (board[i][j] == '-')
					return false;
		}
		return true; 
	}
	void playGame()
	{
		System.out.println("\nWelcome to the game of Tic-Tac-Toe\nRULES : \n1. There are 2 choices of signs (O or X) \n2. whoever chooses O will move first\n3. whoever first succeeds to stack their sign in 3 boxes in a row or a column or a diagonal wins...\n");
		
		initializeBoard();
		
		System.out.print("\nPress 1 to play with your friend\nPress 2 to play with computer : ");
		int play = obj.validateChoice(sc.next().trim(), 1, 2);
		
		System.out.print("\nEnter your name : ");
		player1 = sc.next().trim();
		
		System.out.print("\n\nEnter your sign \nPress 1 for 'O'\nPress 2 for 'X' : ");
		sign1 = obj.validateChoice(sc.next().trim(), 1,2)==1?'O':'X';
		sign2 = sign1=='O'?'X':'O';
		
		if(play==2)
		{
			isCompPlaying = true;
			player2 = "Computer";
		}
		else
		{
			System.out.print("\nEnter second player name : ");
			player2 = sc.next().trim();
		}
		
		player = sign1=='O'?player1:player2;
		sign = sign1 == 'O' ? sign1 : sign2;
		
		System.out.println("\nPlease enter number as shown below to place your sign in the corresponding box.");
		int show=1;
		System.out.println("\n+---------------+---------------+---------------+");
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				System.out.print("|\t" + show++ + "\t");
			}
			System.out.println("|\n+---------------+---------------+---------------+");
		}
		
		while(!(isDraw()||checkWin(sign=='O'?'X':'O')))
		{
			if(player==player2)
			{
				if(isCompPlaying)
					computer_move(sign);
				else
				{
					System.out.print("\n" + player + "'s turn : ");
					user_move(sc.next().trim(), sign);
				}
				player = player1;
				sign = sign=='O'?'X':'O';
			}
			else
			{
				System.out.print("\n" + player + "'s turn : ");
				user_move(sc.next().trim(), sign);
				player=player2;
				sign = sign=='O'?'X':'O';
			}
		}
		if(checkWin(sign=='O'?'X':'O'))
		{
			winner = player==player1?player2:player1;
			winnerSign = sign==sign1?sign2:sign1;
			
			System.out.println("\n" + winner + "(" + winnerSign + ") won the game");
		}
		else if(isDraw())
		{
			System.out.println("\nIts a tie");
		}
	}
}

class MineSweeper
{
	Scanner sc = new Scanner(System.in);
	General obj = new General();
	
	char[][] board;
	boolean[][] mines;
	boolean[][] squareRevealed;
	
	int rows;
	int cols;
	int totalMines;
	
	boolean gameWon = false;
	boolean gameOver = false;
	
	// int tileScore = 0;
	// int winscore = 0;
	// int score1 = 0;
	// int score2 = 0;
	// static int highScore = 0;
	
	void initializeBoard()
	{
		board = new char[rows][cols];
		mines = new boolean[rows][cols];
		squareRevealed = new boolean[rows][cols];
		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				board[i][j] = '-';
				mines[i][j] = false;
				squareRevealed[i][j] = false;
			}
		}
	}
	
	void placeMines()
	{
		int placedMines = 0; 
		while(placedMines<totalMines)
		{
			int row = (int)(Math.random()*rows);
			int col = (int)(Math.random()*cols);
			if(!mines[row][col])
			{
				mines[row][col] = true;
				placedMines++;
			}
		}
	}
	
	int checkSurrounding(int row, int col) 
	{
		if(mines[row][col])
		{
			return -1;
		}
		int surroundingMines = 0;
		for(int i=-1; i<=1; i++)
		{
			for(int j=-1; j<=1; j++)
			{
				if(row+i>=rows || row+i<0 || col+j>=cols || col+j<0)
					continue;
				if(mines[row+i][col+j])
					surroundingMines++;
			}
		}
		board[row][col] = (surroundingMines == 0 ? ' ' : ((char)(surroundingMines+48)));
		squareRevealed[row][col] = true;
		return surroundingMines;
	}
	
	void revealSurrounding(int row, int col)
	{
		if(checkSurrounding(row, col) == 0)
		{
			for(int i=-1; i<=1; i++)
			{
				for(int j=-1; j<=1; j++)
				{
					if(row+i>=rows || row+i<0 || col+j>=cols || col+j<0 || squareRevealed[row+i][col+j] == true)
						continue;
					int surroundingMines = checkSurrounding(row+i, col+j);
					board[row+i][col+j] = (surroundingMines == 0 ? ' ' : ((char)(surroundingMines+48)));
					if(surroundingMines==0)
						revealSurrounding(row+i, col+j);
					squareRevealed[row+i][col+j] = true;
				}
			}
		}
	}
	
	void printBoard()
	{
		System.out.print("   ");
		for (int i=0; i<rows; i++)
			System.out.print("  " + (i+1) + " ");
		System.out.print("\n   +");
		for(int i=0; i<rows; i++)
			System.out.print("---+");
		System.out.println();
		for (int i=0; i<rows; i++)
		{
			System.out.print(" " + (i+1) + " |");
			for (int j=0; j<cols; j++)
			{
				System.out.print(" " + board[i][j] + " |");
			}
			System.out.print("\n   +");
			for(int j=0; j<rows; j++)
				System.out.print("---+");
			System.out.println();
		}
	}
	
	boolean mineBlast(int row, int col)
	{
		if(mines[row][col])
		{
			gameOver=true;
			System.out.println("BOOM!\nYou hit the mine.");
			System.out.print("\n\n   ");
			for (int i=0; i<rows; i++)
				System.out.print("  " + (i+1) + " ");
			System.out.print("\n   +");
			for(int i=0; i<rows; i++)
				System.out.print("---+");
			System.out.println();
			for (int i=0; i<rows; i++)
			{
				System.out.print(" " + (i+1) + " |");
				for (int j=0; j<cols; j++)
				{
					if(checkSurrounding(i, j)==-1)
						System.out.print(" * |");
					else
						System.out.print(" " + board[i][j] + " |");
				}
				System.out.print("\n   +");
				for(int j=0; j<rows; j++)
					System.out.print("---+");
				System.out.println();
			}
		}
		return gameOver;
	}
	
	boolean checkWin()
	{
		int count = 0;
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<cols; j++)
			{
				if(mines[i][j]) 
					continue;
				if(squareRevealed[i][j])
					count++;
				else
					break;
			}
		}
		gameWon = count+totalMines==(rows*cols) ? true : false;
		if (gameWon)
		{
			System.out.println("Congratulations!!\nYou have sucessfully cleared the mines.");
			System.out.print("\n\n   ");
			for (int i=0; i<rows; i++)
				System.out.print("  " + (i+1) + " ");
			System.out.print("\n   +");
			for(int i=0; i<rows; i++)
				System.out.print("---+");
			System.out.println();
			for (int i=0; i<rows; i++)
			{
				System.out.print(" " + (i+1) + " |");
				for (int j=0; j<cols; j++)
				{
					if(checkSurrounding(i, j)==-1)
						System.out.print(" * |");
					else
						System.out.print(" " + board[i][j] + " |");
				}
				System.out.print("\n   +");
				for(int j=0; j<rows; j++)
					System.out.print("---+");
				System.out.println();
			}
		}
		return gameWon;
	}
	
	// int countRevealedTiles()
	// {
		// for(int i=0; i<board.length; i++)
		// {
			// for(int j=0; j<board[i].length; j++)
			// {
				// if(squareRevealed[i][j])
				// {
					
				// }
			// }
		// }
	// }
	
	void playGame () 
	{
		System.out.println("\nWelcome to the game of Mine-Sweeper.\nThis game has different modes on the basis of difficulty\nGame Guide :\n1. The game has a board of a certain size chosen according to the mode of difficulty you will chose.\n2. Some of the squares on the board will have mines underneath it!\n3. You need to clear the board without hitting a single mine in order to win the game.\n4. The number shown on the revealed square will indicate number of nearby mines(in the 3*3 area)\nBest of Luck!\n");
		
		System.out.print("Enter your name : ");
		String name1 = sc.next().trim();
		
		System.out.print("Press 1 to play as single-player\nPress 2 to play against a friend : ");
		int players = obj.validateChoice(sc.next().trim(), 1, 2);
		
		if(players == 2)
			System.out.print("Enter player 2 name : ");
		String name2 = players==2?(sc.next().trim()) : name1;
		
		System.out.println("Enter difficulty mode\n1. Press 1 for easy mode(6*6 board and 4 mines)\n2. Press 2 for moderate mode(9*9 board and 9 mines)\n3. Press 3 for hard mode(9*9 board and 14 mines)");
		System.out.print("Enter your choice : ");
		int difficulty = obj.validateChoice(sc.next().trim(), 1, 3);
		
		switch(difficulty)
		{
			case 1 : 
			rows = 6;
			cols = 6;
			totalMines = 4;
			// tileScore = 15;
			// winscore = 100;
			break;
			case 2 :
			rows = 9;
			cols = 9;
			totalMines = 9;
			// tileScore = 30;
			// winscore = 200;
			break;
			case 3 :
			rows = 9;
			cols = 9;
			totalMines = 14;
			// tileScore = 50;
			// winscore = 350;
			break;
		}
		
		initializeBoard();
		placeMines();
		
		String player = name2;
		
		while(!(gameOver || gameWon))
		{
			player = (player==name1) ? name2 : name1;
				
			printBoard();
			
			System.out.print("\n" + player + "'s turn.\nEnter your move(e.g. 2 3) : ");
			
			int r;
			int c;
			boolean flag = true;
			do{
				r = obj.validateChoice(sc.next().trim(), 1, rows) - 1;
				c = obj.validateChoice(sc.next().trim(), 1, cols) - 1;
				if(squareRevealed[r][c]) 
					System.out.println("Square already revealed\nEnter valid input");
				else
					flag = false;
			}while(flag);
			if(checkSurrounding(r, c)==-1)
				mineBlast(r, c);
			else if(checkSurrounding(r, c)==0)
				revealSurrounding(r, c);
			else
				checkWin();
		}
	}
}

class Game2048
{
	Scanner sc = new Scanner(System.in);
	General obj = new General();
	
	int[][] board = new int[6][6];
	void initializeBoard()
	{
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				board[i][j] = 0;
			}
		}
		int count = 0;
		while(count!=2)
		{
			int r = (int)(Math.random()*6);
			int c = (int)(Math.random()*6);
			if(board[r][c]==0)
			{
				board[r][c] = 2;
				count++;
			}
		}
	}
	
	void placeNumbers()
	{
		boolean flag = true;
		int r = (int)(Math.random()*6);
		int c = (int)(Math.random()*6);
		while(flag)
		{
			r = (int)(Math.random()*6);
			c = (int)(Math.random()*6);
			if(board[r][c]==0)
			{
				board[r][c] = 2;
				flag = false;
			}
		}
	}
	
	void printBoard()
	{
		System.out.println("+---+---+---+---+---+---+");
		for(int i=0; i<board.length; i++)
		{
			System.out.print("|");
			for(int j=0; j<board[i].length; j++)
			{
				System.out.print(" " + (board[i][j]==0?" ":board[i][j]) + " |");
			}
			System.out.println("\n+---+---+---+---+---+---+");
		}
	}
	
	void numbersMovement(char input, int repeat)
	{
		
		if(input=='W' || input=='w' || input=='S' || input=='s')
		{
			boolean flag = false;
			if(input=='w'||input=='W')
				flag = true;
			
			for(int i=0; i<board.length; i++)
			{
				int count = 0;
				for(int j=(flag?0:board.length); flag?(j<board.length):(j>0); flag?(j++):(j--))
				{
					if(board[j][i]!=0)
					{
						int temp = board[j][i];
						board[j][i] = 0;
						board[count++][i] = temp;
					}
				}
				if(repeat==0)
				{
					for(int j=(flag?0:board.length); flag?(j<board.length-1):(j>1); flag?(j++):(j--))
					{
						if(board[j][i]==board[j+(flag?1:-1)][i])
						{
							board[j][i]*=2;
							board[j+(flag?1:-1)][i]=0;
							flag?(j++):(j--);
						}
					}
				}					
			}
		}
		// if(input=='A' || input=='a' || input=='D' || input=='d')
		// {
			
		// }
		numbersMovement(input, repeat+1);
	}
}

class GameArcade
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		General obj = new General();
		TicTacToe tictactoe = new TicTacToe();
		MineSweeper minesweeper = new MineSweeper();
		Game2048 game2048 = new Game2048();
		
		// System.out.println("Welcome to the virtual Gaming-arcade\nYou can play variety of games with your friends");
		// System.out.println("Press 1 to play Tic-Tac-Toe\nPress 2 to play the game of Mine-Sweeper");
		// System.out.print("Enter your choice : ");
		
		// int choice = obj.validateChoice(sc.next(), 1, 3);
		
		// switch(choice)
		// {
			// case 1 : tictactoe.playGame();
			// break;
			// case 2 : minesweeper.playGame();
			// break;
			// case 3 : 
			// break;
		// }
		
		game2048.initializeBoard();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.placeNumbers();
		game2048.printBoard();
		game2048.numbersMovement(w, 0);
		game2048.printBoard();
	}
}