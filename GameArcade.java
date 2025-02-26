import java.util.*;
//class for all type of input validations (most methods of this class uses concept of recursion)
class Validations
{
	Scanner sc = new Scanner(System.in);
	//method to validate int input ranging between 0-9
	int validateChoice(String s, int from, int to)
	{
		char c = s.charAt(0);
		int n = c-48;
		if(s.length()!=1 || (n>to || n<from))
		{
			System.out.print("Please enter valid input from given options (" + from + "-" + to + ") : ");
			return validateChoice(sc.next().trim(), from, to); //Concept used: Recursion
		}
		else
			return n;
	}
	
	//method to validate char input from a char array
	char validateCharInput(String given, char[] expected)
	{
		boolean valid = false;
		while(!valid)
		{
			if(given.length()==1)
				valid = true;
			else
			{
				System.out.print("Please enter only 1 character at a time\nEnter your input : ");
				return validateCharInput(sc.next(), expected);
			}
			for(int i=0; i<expected.length; i++)
			{
				if(given.charAt(0)==expected[i])
				{				
					valid = true;
					break;
				}
				else
					valid = false;
			}
			if(!valid)
			{
				System.out.print("Invalid choice\nPlease enter valid input : ");
				return validateCharInput(sc.next(), expected);
			}
		}
		return given.charAt(0);
	}
	
	//method to validate mobile number input
	long validateMobileNumber(String input)
	{
		if(input.length()!=10 || input.charAt(0)!='9')
		{
			System.out.print("Please enter a 10 digit mobile number starting with digit '9': ");
			return validateMobileNumber(sc.next());
		}
		for(char x : input.toCharArray())
		{
			if(!Character.isDigit(x))
			{
				System.out.print("Enter a valid mobile number with 10 digits only: ");
				validateMobileNumber(sc.next());
			}
		}
		return Long.parseLong(input);
	}
	
	//method to validate the entered email
	String validateEmailID(String input)
	{
		if(!input.endsWith("@gmail.com"))
		{
			System.out.print("Mail id must end with '@gmail.com' \nEnter valid input:");
			return validateEmailID(sc.next());
		}
		int countAt = 0;
		for(char x : input.toCharArray())
		{
			if(x=='@')
				countAt++;
		}
		if(countAt>1)
		{
			System.out.println("Invalid mailID entered\nPlease enter valid input : ");
			return validateEmailID(sc.next());
		}
		for(char x : input.toCharArray())
		{
			if(x<'a' || x>'z')
				if(x<'A' || x>'Z')
					if(x<'0' || x>'9')
						if(!(x=='.' || x=='@'))
						{
							System.out.print("You have entered input in invalid format\nAn emailID allows(A-Z, a-z, 0-9, ., @)\nPlease try again with a valid mail id : ");
							return validateEmailID(sc.next());
						}
		}
		return input;
	}
	
	//method to validate whether entered OTP matches with generated one.
	boolean validateOTP(int expected, String input)
	{
		if(input.length()!=4)
			return false;
		for(char x : input.toCharArray())
			if(!Character.isDigit(x))
				return false;
		if(Integer.parseInt(input)!=expected)
			return false;
		return true;
	}
	
	//method to validate username input give by user
	String validateUserName(String input)
	{
		if(input.length()<4 || input.length()>16)
		{	
			System.out.print("Enter username of valid length : ");
			return validateUserName(sc.nextLine());
		}
		for(char x : input.toCharArray())
		{
			if(x==' ')
			{
				System.out.print("A user-name must not have any blankspaces\nEnter valid input :");
				return validateUserName(sc.nextLine());
			}
			if(x<'a' || x>'z')
				if(x<'A' || x>'Z')
					if(x<'0' || x>'9')
					{
						System.out.print("Invalid input\nPlease enter a valid username according to rules : ");
						return validateUserName(sc.nextLine());
					}
		}
		return input;
	}
	
	//method to check if password is strong enough or not
	String validatePassword(String input)
	{
		if(input.length()<6 || input.length()>16)
		{
			System.out.print("Password length must be between 6-16 characters : ");
			return validatePassword(sc.nextLine());
		}
		boolean lower = false, upper = false, number = false, special = false;
		for(char x : input.toCharArray())	
		{
			if(x>='a' && x<='z')
				lower = true;
			else if(x>='A' && x<='Z')
				upper = true;
			else if(x>='0' && x<='9')
				number = true;
			else if(x=='!' || x=='@' || x=='#' || x=='$' || x=='%' || x=='^' || x=='&' || x=='*' || x=='(' || x==')' || x=='-' || x=='_' || x=='=' || x=='+')
				special = true;
			else if(x==' ')
			{
				System.out.println("No blank spaces are allowed in password");
				System.out.print("Enter password : ");
				return validatePassword(sc.nextLine());
			}
		}
		if(!(lower && upper && number && special))
		{
			System.out.println("A password must contain atleast 1 lowercase, 1 uppercase, 1 numerical and 1 special character");
			System.out.print("Create a strong password : ");
			return validatePassword(sc.nextLine());
		}
		return input;
	}
}//end of validations class

//class to let user register and login into existing account
class RegistrationAndLogin extends Validations //Concept used: Inheritance
{
	//method that completes registration process f user and returns a new user object
	User registration()
	{
		boolean isMobileRegistration = false; //to check whether user used mobile number or email id to register
		long mobileNumber = 0;
		String emailID = null;
		System.out.println("\nPress 1 to register with moblie number.");
		System.out.println("Press 2 to register with email-id.");
		System.out.print("Enter your choice : ");
		int regiChoice = validateChoice(sc.next(), 1, 2);
		switch(regiChoice)
		{
			case 1 : //registration using mobile number
				System.out.print("\nEnter mobile to register : ");
				mobileNumber = validateMobileNumber(sc.next());
				isMobileRegistration = true;
			break;
			case 2 : // registration using email id
				System.out.print("Enter email to register : ");
				emailID = validateEmailID(sc.next());
			break;
		}
		
		//attempts for valid OTP inputs
		int attempts = 3;
		boolean validOTP = false;
		
		while(attempts>0)
		{
			int otp = (int)(Math.random()*10000); //OTP generation
			
			if(otp<1000)
				continue;
				
			System.out.println("\nYour verification OTP is " + otp);
			System.out.print("Enter OTP : ");
			if(validateOTP(otp, sc.next())) //valid otp input
			{
				System.out.println("\n\t--OTP verificaiton completed.--");
				validOTP = true;
				break;
			}
			else if(attempts==1) // all attempts used and last input also incorrect
			{
				validOTP = false;
				System.out.println("\nToo many invalid inputs!\nPlease try again later.");
				break;
			}
			else // incorrect input asking user to enter again and deceasing remaining attempts
			{
				validOTP = false;
				System.out.println("\nInvalid otp entered\n" + (attempts-1) + " attempts left for correct input");
			}
				attempts--;//ensures attempts decrease after every incorrect input
		}
		
		//if user did not enter correct otp for more than allowed inputs then ask to register again
		if(!validOTP)
			return registration();
	
		sc.nextLine();
		System.out.println("\nNow, set a user-name for yourself");
		System.out.println("UserName can have Alphabets(A-Z, a-z), numerical characters only(0-9), must not have any blank spaces and it must be between length of 4-16 characters");
		System.out.print("Create username of your choice : ");
		String userName = validateUserName(sc.nextLine()); //setting user-name that will be user's name for the entire code from now on
		
		System.out.println("\nPlease a create a STRONG password for safety of your account");
		System.out.println("The password must include atleast one UpperCase character, a LowerCase character, a digit and a special character");
		System.out.print("Create your password : ");
		String password = validatePassword(sc.nextLine()); // strong password for account security
		
		System.out.println("\nWelcome " + userName + ",");
		System.out.println("Your account has been sucessfully created!");
		
		//object created according to registration choices
		if(isMobileRegistration)
			return new User(mobileNumber, userName, password);
		else 
			return new User(emailID, userName, password);
	}
	
	//method to let user regiter into exixsting account and returns index of that object from users array
	int login(User[] users)
	{
		System.out.print("\nEnter username : "); //asking username for login
		String name = validateUserName(sc.nextLine());
		
		boolean found = false;
		int i=0;
		
		for(i=0; i<(User.userCount); i++) // searching if an object with given username exists or not
		{
			if(users[i].userName.equals(name))
			{
				System.out.print("\nEnter password : "); // if found ask password for that object
				
				if(users[i].password.equals(sc.nextLine())) // if correct password then login successful
				{
					System.out.println("\n\n\t:::::: Login successful ::::::");
					found = true;
					return i;
				}
				else 
				{
					System.out.println("-->Incorrect password entered<--"); // if not then ask to login again or return to main menu
					return -1;
				}
			}
		}
		if(!found) //if username not found display massege
		{
			System.out.println("\nNo user found with given user-name.");
			System.out.println("\nPress 1 to try again");
			System.out.println("Press 2 to go to main menu");

			System.out.println("Enter your choice : ");
			int option = validateChoice(sc.next(), 1, 2);

			if(option==1)
			{
				System.out.println("\nPlease try again with valid username");
				return login(users);
			}
			else
				return -1;
		}
		else
			return i;
	}
}//end of RegistrationAndLogin class

//User class to give user object its attributes and constructors to set its values
class User
{
	//instance variables that are attributes of every user created
	long mobileNumber;
	String emailID, userName, password;
	boolean isMobileRegistration;
	static int userCount=0; //static variable that keeps count of total users created
	
	User()// no argument constructor to make sure no errors occur if no arguments constructor is called
	{
		
	}
	
	// constructor to set user that has registered using mobile number
	User(long mobileNumber, String userName, String password)
	{
		this.mobileNumber = mobileNumber;
		this.userName = userName;
		this.password = password;
		this.emailID = null;
		this.isMobileRegistration = true;
	}
	
	// constructor to set user that has registered using email id
	User(String emailID, String userName, String password)
	{
		this.emailID = emailID;
		this.userName = userName;
		this.password = password;
		this.mobileNumber = 0;
		this.isMobileRegistration = false;
	}
	
	//method to display profile diplay of user
	void displayProfile(User[] users, int i)
	{
		if(users[i].isMobileRegistration)
			System.out.println("\nMobile number : " + users[i].mobileNumber);
		else
			System.out.println("\nEmail id : " + users[i].emailID);
		System.out.println("Username : " + users[i].userName);
	}
}//end of User class

//TicTacToe is the first game of the game arcade this class consists everything related to tictactoe game
class TicTacToe
{
	Scanner sc = new Scanner(System.in);
	Validations obj = new Validations();
	
	char[][] board = new char[3][3]; // default board set to 3*3 size
	String player1, player2, player, winner;
	char sign1, sign2, sign, winnerSign;
	int position;
	boolean isCompPlaying = false;
	
	// method to initialize board with '-'
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
	
	//method to print board after every move made
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
	
	//method to complete user move
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
		if(board[i][j]=='-') //if square is available then the move is made
		{
			board[i][j] = (char)uSign;
			printBoard();
		}
		else // else asks user to choose another square
		{
			System.out.print("\nSquare is occupied.\ntry another square : ");
			user_move(sc.next().trim(), uSign);
		}
	}
	
	//method make computer move
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
		if(board[i][j]=='-')// if move is valid then it is made
		{
			System.out.println("\nComputer's move : " + temp + "\n");
			board[i][j] = (char)uSign;
			printBoard();
		}
		else
			computer_move(uSign);
	}
	
	//method to check if anu of the user have won the game
	boolean checkWin(char uSign)
	{
		for (int i = 0; i < 3; i++) 
		{ 
			if (board[i][0] == uSign && board[i][1] == uSign && board[i][2] == uSign) //checks all rows
				return true; 
			if (board[0][i] == uSign && board[1][i] == uSign && board[2][i] == uSign) //checks all rows
				return true;
		} 
		//checks all diagonals
		if (board[0][0] == uSign && board[1][1] == uSign && board[2][2] == uSign)
			return true;
		if (board[0][2] == uSign && board[1][1] == uSign && board[2][0] == uSign)
			return true;
		return false;
	}
	
	//method to check if game is made a draw or not
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
	
	//method to play the game using all methods of this class
	void playGame(String userName)
	{
		System.out.println("\nWelcome to the game of Tic-Tac-Toe");
		System.out.println("RULES : ");
		System.out.println("1. There are 2 choices of signs (O or X) ");
		System.out.println("2. whoever chooses O will move first");
		System.out.println("3. whoever first succeeds to stack their sign in 3 boxes in a row or a column or a diagonal wins...\n");
		
		initializeBoard();
		
		System.out.print("\nPress 1 to play with your friend\nPress 2 to play with computer : "); // selecting whether to play with computer or friend
		int play = obj.validateChoice(sc.next().trim(), 1, 2);
		
		player1 = userName;
		
		System.out.print("\n\nSelect a sign (player with sign 'O' will be making the first move)\nPress 1 for 'O'\nPress 2 for 'X' : ");
		sign1 = obj.validateChoice(sc.next().trim(), 1,2)==1?'O':'X'; // selection of sign
		sign2 = sign1=='O'?'X':'O';
		
		if(play==2) // making computer a second player
		{
			isCompPlaying = true;
			player2 = "Computer";
		}
		else // taking input of second player
		{
			System.out.print("\nEnter second player name : ");
			player2 = sc.next().trim();
		}
		
		//setting signs
		player = sign1=='O'?player1:player2;
		sign = sign1 == 'O' ? sign1 : sign2;
		
		int gameNo = 0;
		while(gameNo<3)
		{
			System.out.println("\nPlease enter number as shown below to place your sign in the corresponding box."); // input of placing sign
			int show=1;
			System.out.println("\n+---------------+---------------+---------------+");
			for(int i=0; i<board.length; i++) // printing board for the first time
			{
				for(int j=0; j<board[i].length; j++)
				{
					System.out.print("|\t" + show++ + "\t");
				}
				System.out.println("|\n+---------------+---------------+---------------+");
			}
			
			while(!(isDraw()||checkWin(sign=='O'?'X':'O'))) // main loop for playing game.
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
			gameNo++;
		}
	}
}//end of TicTacToe class

class MineSweeper
{
	Scanner sc = new Scanner(System.in);
	Validations obj = new Validations();
	
	char[][] board;
	boolean[][] mines;
	boolean[][] squareRevealed;
	
	int rows;
	int cols;
	int totalMines;
	
	boolean gameWon = false;
	boolean gameOver = false;
	
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
			System.out.println("\n \nBOOMMMMMMMMMMMMM !!!!!!!\n You hit the mine.");
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
		gameWon = (count+totalMines)==(rows*cols);
		if (gameWon)
		{
			System.out.println("Congratulations !!\nYou have sucessfully cleared the mines.");
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
	
	void playGame (String userName) 
	{
		System.out.println("\nWelcome to the game of Mine-Sweeper.");
		System.out.println("This game has different modes on the basis of difficulty");
		System.out.println("Game Guide :");
		System.out.println("1. The game has a board of a certain size chosen according to the mode of difficulty you will chose.");
		System.out.println("2. Some of the squares on the board will have mines underneath it!");
		System.out.println("3. You need to clear the board without hitting a single mine in order to win the game.");
		System.out.println("4. The number shown on the revealed square will indicate number of nearby mines(in the 3*3 area)");
		System.out.println("\nBest of Luck!\n");
		
		String name1 = userName;
		
		System.out.println("\nPress 1 to play as single-player");
		System.out.println("Press 2 to play against a friend");
		System.out.print("Enter your choice: ");
		int players = obj.validateChoice(sc.next().trim(), 1, 2);
		
		if(players == 2)
			System.out.print("\nEnter player 2 name : ");
		String name2 = players==2?(sc.next().trim()) : name1;
		
		System.out.println("\nEnter difficulty mode");
		System.out.println("1. Press 1 for easy mode(6*6 board and 4 mines)");
		System.out.println("2. Press 2 for moderate mode(9*9 board and 9 mines)");
		System.out.println("3. Press 3 for hard mode(9*9 board and 14 mines)");
		System.out.print("\nEnter your choice : ");
		int difficulty = obj.validateChoice(sc.next().trim(), 1, 3);
		
		switch(difficulty)
		{
			case 1 : 
			rows = 6;
			cols = 6;
			totalMines = 4;
			break;
			
			case 2 :
			rows = 9;
			cols = 9;
			totalMines = 9;
			break;
			
			case 3 :
			rows = 9;
			cols = 9;
			totalMines = 14;
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
					System.out.println("\nSquare already revealed\nEnter valid input\n");
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
	Validations obj = new Validations();
	
	int[][] board = new int[4][4];
	
	int userScore = 0;
	
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
			int r = (int)(Math.random()*4);
			int c = (int)(Math.random()*4);
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
		int r = (int)(Math.random()*4);
		int c = (int)(Math.random()*4);
		while(flag)
		{
			r = (int)(Math.random()*4);
			c = (int)(Math.random()*4);
			if(board[r][c]==0)
			{
				board[r][c] = 2;
				flag = false;
			}
		}
	}
	
	void printBoard()
	{
		System.out.println("+---------------+---------------+---------------+---------------+");
		for(int i=0; i<board.length; i++)
		{
			System.out.print("|");
			for(int j=0; j<board[i].length; j++)
			{
				System.out.print("\t" + (board[i][j]==0?" ":board[i][j]) + "\t|");
			}
			System.out.println("\n+---------------+---------------+---------------+---------------+");
		}
	}
	
	int boardMovement(char input, int repeat)
	{
		int score = 0;
		if(input=='W' || input=='w' || input=='A' || input=='a')
		{
			for(int i=0; i<board.length; i++)
			{
				int count = 0;
				for(int j=0; j<board.length; j++)
				{
					if(input=='W' || input=='w')
					{
						if(board[j][i]!=0)
						{
							int temp = board[j][i];
							board[j][i] = 0;
							board[count++][i] = temp;
						}
					}
					if(input=='A' || input=='a')
					{
						if(board[i][j]!=0)
						{
							int temp = board[i][j];
							board[i][j] = 0;
							board[i][count++] = temp;
						}
					}
				}
				if(repeat==0)
				{
					for(int j=0; j<board.length-1; j++)
					{
						if(input=='W' || input=='w')
						{
							if(board[j][i]==board[j+1][i])
							{
								board[j][i]*=2;
								board[j+1][i]=0;
								j++;
								score+=(board[i][j]*10);
							}
						}
						if(input=='A' || input=='a')
						{
							if(board[i][j]==board[i][j+1])
							{
								board[i][j]*=2;
								board[i][j+1]=0;
								j++;
								score+=(board[i][j]*10);
							}
						}
					}
					boardMovement(input, repeat+1);
				}					
			}
		}
		if(input=='S' || input=='s' || input=='D' || input=='d')
		{
			for(int i=0; i<board.length; i++)
			{
				int count = board.length-1;
				for(int j=board.length-1; j>=0; j--)
				{
					if(input=='S' || input=='s')
					{
						if(board[j][i]!=0)
						{
							int temp = board[j][i];
							board[j][i] = 0;
							board[count--][i] = temp;
						}
					}
					if(input=='D' || input=='d')
					{
						if(board[i][j]!=0)
						{
							int temp = board[i][j];
							board[i][j] = 0;
							board[i][count--] = temp;
						}
					}
				}
				if(repeat==0)
				{
					for(int j=board.length-1; j>=1; j--)
					{
						if(input=='S' || input=='s')
						{
							if(board[j][i]==board[j-1][i])
							{
								board[j][i]*=2;
								board[j-1][i]=0;
								j--;
								score+=(board[i][j]*10);
							}
						}
						if(input=='D' || input=='d')
						{
							if(board[i][j]==board[i][j-1])
							{
								board[i][j]*=2;
								board[i][j-1]=0;
								j--;
								score+=(board[i][j]*10);
							}
						}
					}
					boardMovement(input, repeat+1);
				}				
			}
		}
		return score;
	}
	
	boolean gameOver()
	{
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board.length; j++)
			{
				if(board[i][j]==0)
					return false;
				if(i>0 && (board[i][j]==board[i-1][j]))
					return false;
				if(j>0 && (board[i][j]==board[i][j-1]))
					return false;
			}
		}
		return true;
	}
	
	void playGame(String userName)
	{
		System.out.println("Welcome to 2048 Game!");
		System.out.println("This game is a mathematical fun game in which joining 2 same numbered boxes will make a new double valued box");
		System.out.println("Score will increase whenever ");
		System.out.println("\nTo play the game : ");
		System.out.println("Press W/w to move the squares upwards");
		System.out.println("Press A/a to move the squares to the left");
		System.out.println("Press S/s to move the squares downwards");
		System.out.println("Press D/d to move the squares to the right");
		System.out.println("Enjoy the game!\n");
		
		initializeBoard();

		printBoard();

		System.out.println("\n\nPress W/w, A/a, S/s or D/d to move numbers on the board in desired direction");

		int score = 0;

		while(!gameOver())
		{
			System.out.print("\nPress W/A/S/D to move: ");
			score+=boardMovement(obj.validateCharInput(sc.next(), new char[]{'w', 'a', 's', 'd', 'W', 'A', 'S', 'D'}), 0);
			placeNumbers();
			printBoard();
			System.out.println("\nScore : " + score);
		}
		System.out.println("Game Over!\nfinal score: " + score);
	}
}

class GameArcade
{
	static
	{
		System.out.println("\n\n::::::::::::::::::Welcome to the virtual Gaming-arcade::::::::::::::::::\n");
	}
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		RegistrationAndLogin obj = new RegistrationAndLogin();
		User[] users = new User[10];
		TicTacToe tictactoe = new TicTacToe();
		MineSweeper minesweeper = new MineSweeper();
		Game2048 game2048 = new Game2048();
		
		int userIndex = 0;
		
		boolean exit = false;
		boolean again = false;
		int choice = 0;
		while(choice!=3)
		{
			System.out.println("\nPress 1 to register (new user)");
			System.out.println("Press 2 to login (existing user)");
			System.out.println("Press 3 to exit arcade");
			System.out.print("Enter your choice : ");
			choice = obj.validateChoice(sc.next(), 1, 3);
			
			switch(choice)
			{
				case 1 : users[User.userCount++] = obj.registration();
				break;
				
				case 2 : 
				userIndex = obj.login(users);
				if(userIndex==-1)
				{
					again = true;
					System.out.println("\nLog-in failed\ntry again later...");
				}
				break;
				
				case 3 : exit = true;
				break;
				
				default : 
				break;
			}
			
			if(exit)
				break;
			
			if(again)
				continue;
			
			int gameChoice = 0;
			
			while(gameChoice!=4)
			{
				System.out.println("\nPress 1 to play Tic-Tac-Toe");
				System.out.println("Press 2 to play the game of Mine-Sweeper");
				System.out.println("Press 3 to play 2048");
				System.out.println("Press 4 to log out");
				System.out.print("Enter your choice : ");
				
				gameChoice = obj.validateChoice(sc.next(), 1, 4);
				
				switch(gameChoice)
				{
					case 1 : tictactoe.playGame(users[userIndex].userName);
					break;
					case 2 : minesweeper.playGame(users[userIndex].userName);
					break;
					case 3 : game2048.playGame(users[userIndex].userName);
					break;
					case 4 : //exiting the loop...
					break;
				}
			}
		}
	}
}