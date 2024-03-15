import java.util.Arrays;
import java.util.Scanner;

//Stack class for BackTracing
class StackMaze {
	int top = -1;
	String[] data;
	int maxSize = 0;
	
	public StackMaze(int maxSize){
		this.maxSize = maxSize;
		data = new String[maxSize];
	}
	
	public String ShowTop() {
		if(IsEmpty()) 
			return null;
		
		return data[top];
	}
	
	public boolean IsFull() {
		if(top == (maxSize - 1))
			return true;
		return false;
	}
	
	public boolean IsEmpty() {
		if(top == - 1)
			return true;
		return false;
	}
	
	public boolean push(String value) {
		if(IsFull())
			return false;
		data[++top] = value;
		return true;
	}
	
	public String pop() {
		if(IsEmpty())
			return null;
		String prev_Top = data[top];
		data[top] = null;
		top--;
		return prev_Top;
	}
	
	//Simple Method for Printing what is in stack 
	public void PrintStack() {
		System.out.println("=============");
		for(int i=0;i<=top;i++)
			System.out.println(data[i]);
	}
}


public class Maze {
	
	//matrix size
	static int numberPath;
	static Scanner input;
	static StackMaze spp;
	//check Block ways Number
	static int HowManyBlock = 0;
	
	public static void main(String[] arg) {
		
		spp = new StackMaze(30);
		input = new Scanner(System.in);
		
		
		System.out.println("Please Enter the Number of MazeRunner Area");
		numberPath = input.nextInt();
		
		int[][] MazeArray = new int[numberPath][numberPath]; 
		
		//Getting Maze Path We get only 1 or 0
		System.out.println("Please just Enter 0 or 1 in MazeRunner Area"+
				"\n"+"(0=>OpenWay 1=>BlockWay)");
		//Getting Inputs for Array
		for(int i = 0;i<numberPath;i++) {
			for(int j = 0;j<numberPath;j++) {
				System.out.print("Space ["+i+"]["+j+"]  ");
				int vorodi = input.nextInt();
				if(vorodi == 0 || vorodi == 1)
					MazeArray[i][j] = vorodi;
				else {
					System.out.println("Wrong Input !!!!!!!!");
					System.exit(0);
				}
			}
			System.out.println("");
		}
		
		int[][] MazeArrayClone = MazeArray.clone();
		
		//Print Path
		for(int i = 0;i<numberPath;i++) {
			for(int j = 0;j<numberPath;j++) {
				System.out.print("["+MazeArray[i][j]+"]  ");
			}
			System.out.println("");
		}
		
		//Checking some special cases that the path is not Entered correctly
		if(MazeArray[0][0]==1) {
			System.out.println("Error!!!! " + "\n"
					+ "mouse was Block from the firstPlace");
			System.exit(0);
		}
		if(MazeArray[numberPath-1][numberPath-1] == 1)	{
			System.out.println("Error!!!! " + "\n"
					+ "There is a wall instead of cheese");
			System.exit(0);
		}
		
		//Start Going into the maze and start the run
		GoTo8Direction(1, 0, 0, MazeArray);
	
		//Print the i and j of way
		spp.PrintStack();
		
		//if the way is not founded
		if(spp.IsEmpty()) {
			System.out.println("No way Found !!!");
			System.exit(0);
		}
		
		//Show Last Index (The Cheese)  
		int lastplace = numberPath - 1;
		
		System.out.println(lastplace+" "+lastplace);
		
		//show the way with Number 2
		SetUpSolveArray(MazeArrayClone);

		//set Up the Graphical way for Maze
		char[] finalPath = ShowGraphic(MazeArrayClone);
		new DrawGraphics(numberPath,finalPath); 
	}
	
	private static void SetUpSolveArray(int[][] MazeArrayClone) {
		
		System.out.println("=========================");
		System.out.println("2 is the way that mouse went");
		
		
		MazeArrayClone[numberPath-1][numberPath-1] = 2;
		
		for(int i = 0;i<numberPath;i++) {
			for(int j = 0;j<numberPath;j++) {
				if(MazeArrayClone[i][j] == -1) {
					MazeArrayClone[i][j] = 2;
					System.out.print("["+MazeArrayClone[i][j]+"]  ");
					continue;
				}
				System.out.print("["+MazeArrayClone[i][j]+"]  ");
			}
			System.out.println("");
		}
		
	
	}

	//Check the winState
	public static boolean WinState(int i,int j,int[][] arr) {
		if(i == (numberPath-1) && j == (numberPath-1)) {
			
			System.out.println("Congratulation You Helped mouse for finding cheese");
			return true;
		}
		return false;	
	}
	
	public static boolean AreYouMovingBack(int i,int j,int[][] arr) {
		if(arr[i][j] == spp.top) {
			return true;
		}
		return false;
	}
	
	//Block a specific index
	public static void Block(int i,int j,int[][] arr) {
		HowManyBlock++;
		arr[i][j] = -1;
	}
	//UnBlock a specific index
	public static void UnBlock(int i,int j,int[][] arr) {
		arr[i][j] = 0;
	}
	
	//Starting Maze
	public static void GoTo8Direction(int cases,int i,int j,int[][] arr) {
		if(WinState(i, j, arr)) {
			return ;
		}
		switch (cases) {
			case 1: {
				//Going East
				int jOld = j;
				j++;
				
					if(IsSafe(numberPath, i, j, arr)) {
							spp.push(i+" "+jOld);
							Block(i, jOld, arr);
							GoTo8Direction(1, i, j, arr);
							
					}else {
						GoTo8Direction(2,i,jOld,arr);
					}
				
				break;
			}case 2:{
				//Going SouthEast
				int jOld = j;
				int iOld = i;
				j++;
				i++;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+jOld);
						Block(iOld, jOld, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(3,iOld,jOld,arr);
					}
				
				break;
			}case 3:{
				//Going South
				int iOld = i;
				i++;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+j);
						Block(iOld, j, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(4,iOld,j,arr);
					}
				
				break;
			}case 4:{
				//Going SouthWest
				int jOld = j;
				int iOld = i;
				j--;
				i++;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+jOld);
						Block(iOld, jOld, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(5,iOld,jOld,arr);
					}
				
				break;
			}case 5:{
				//Going West
				int jOld = j;
				j--;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(i+" "+jOld);
						Block(i, jOld, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(6,i,jOld,arr);
					}
				
				break;
			}case 6:{
				//Going NorthWest
				int jOld = j;
				int iOld = i;
				j--;
				i--;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+jOld);
						Block(iOld, jOld, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(7,iOld,jOld,arr);
					}
				
				break;
			}case 7:{
				//Going North
				int iOld = i;
				i--;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+j);
						Block(iOld, j, arr);
						GoTo8Direction(1, i, j, arr);
					}else {
						GoTo8Direction(8,iOld,j,arr);
					}
				
				break;
			}case 8:{
				//Going NorthEast
				int jOld = j;
				int iOld = i;
				j++;
				i--;
					if(IsSafe(numberPath, i, j, arr)) {
						spp.push(iOld+" "+jOld);
						Block(iOld, jOld, arr);
						GoTo8Direction(1, i, j, arr);
					}else{
						//if you could not go to any path you will Back to the top index of Stack
						if(HowManyBlock>=1) {
							NeverCheckAgian(iOld, jOld, arr);
							MoveBack(arr);
						}
					}
					break;
			}	
		}
	}
	
	//if you go back you never go to that index again with this method
	private static void NeverCheckAgian(int i, int j, int[][] arr) {
		arr[i][j] = -3;
	}

	//get the top index stack and pop it
	public static void MoveBack(int[][] arr) {
		if(spp.IsEmpty())
			return;
		
		//Block(i, j, arr);
		String[] BackStep = spp.pop().split(" ");
		int iBack = Integer.valueOf(BackStep[0]);
		int jBack = Integer.valueOf(BackStep[1]);
		
		arr[iBack][jBack] = 0;
		GoTo8Direction(2,iBack,jBack,arr);
	}

	//check if i and j are good enough to be go 
	public static boolean IsSafe(int n,int i,int j,int[][] arr) {
		if(i<n && j<n && i>=0 && j>=0 && arr[i][j] == 0 && arr[i][j]!=-1 && arr[i][j]!=-3)
			return true;
		return false;
	}

	//with this methode we will create an array of char that show the way to cheese 
	//and pass this array to our graphical class to draw this path there
	//with some nice Animations
	public static char[] ShowGraphic(int[][] arr) {
		char[] FinalPath = new char[20];
		
		int iOld = 0;
		int jOld = 0;
		int hPath = 0;
		
		for(int i = 0;i<numberPath;i++) {
			for(int j = 0;j<numberPath;j++) {
				if(arr[i][j] == 2) {
					if(((j-jOld) == 1) && ((i-iOld) == 0)) {
						FinalPath[hPath] = 'E';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
						
					
					if(((j-jOld) == 1) && ((i-iOld) == 1)) {
						FinalPath[hPath] = '7';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == 0) && ((i-iOld) == 1)) {
						FinalPath[hPath] = 'S';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == -1) && ((i-iOld) == 1)) {
						FinalPath[hPath] = '8';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == -1) && ((i-iOld) == 0)) {
						FinalPath[hPath] = 'W';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == -1) && ((i-iOld) == -1)) {
						FinalPath[hPath] = '6';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == 0) && ((i-iOld) == -1)) {
						FinalPath[hPath] = 'N';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
					
					if(((j-jOld) == 1) && ((i-iOld) == -1)) {
						FinalPath[hPath] = '5';
						hPath++;
						jOld = j;
						iOld = i;
						continue;
					}
				}
			}
		}
		return FinalPath;
	}
}
