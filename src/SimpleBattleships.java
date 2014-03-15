import java.util.Random;
import java.io.Console;
import java.util.Arrays;
import java.io.IOException;

public class SimpleBattleships {
	

/******************** BEGIN MAIN ********************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Set up a console object
		Console c = System.console();
				
		boolean gameOver = false;
		boolean coordsFireValid = false;
		
		// set the world \ playing board
		int[][] playingBoard = new int[25][25];
		
		// setting the ship up
		int shipLength = randChooseLength();
		boolean shipOrientation = randChooseOrientation();
		String shipCoords = shipChooseCoords(shipLength);
		
		System.out.println("Ship coordinates are " + shipCoords);
		System.out.println("Ship orientaiton is " + shipOrientation);
		
		while(!gameOver){
			 
			//Read in coords and validate them
			
			String coordsFire = c.readLine("Enter coordinates as 'x,y': ");
						
			while(!coordsFireValid){
				
				coordsFireValid = validateCoordsFire(coordsFire);

			}
			
			// You've fixed this already, what did you do?....
			int x = Integer.parseInt(splitCoordsString(coordsFire, 'x'));
			int y = Integer.parseInt(splitCoordsString(coordsFire, 'y'));
			
			System.out.println("Firing coordinates are x=" + x + " and y=" + y);
			
			//check coords / hit
			//checkShipHit(shipCoords, coordsFire, shipOrientation, playingBoard);

		}
		System.out.println("gameOver became false");
		System.exit(0);
			
	}

/******************** END OF MAIN ********************/
	
	
	// Is the game over? See if the Ship has been sunk
	public static boolean checkGameOver(boolean shipSunk){
		boolean gameOver = false;
		
		if(shipSunk){
			gameOver = true;
		} else {
			gameOver = false;
		}
		
		return gameOver;
	}
	
	// Validate the entered coordinates
	public static boolean validateCoordsFire(String coordsFire){
		boolean coordsFireValid = false;
		int x, y;
		
		String strx = splitCoordsString(coordsFire, 'x');
		String stry = splitCoordsString(coordsFire, 'y');
		
		
		if (numericCheckCoordsFire(strx) && numericCheckCoordsFire(stry) ){
			x = Integer.parseInt(strx);
			y = Integer.parseInt(stry);
			
			if (x >= 26 || y >= 26){
				coordsFireValid = false;
				System.out.println("The dimensions of the board are 25 x 25, 'x,y' entered must be less than this.");
			} else {
				coordsFireValid = true;
			}
			
		} else {
			coordsFireValid = false;
			System.out.println("Coords are supposed to be numbers...  You entered '" + strx + "' for x and '" + stry + "' for y.");
		}

		return coordsFireValid;
		
	}
	
	// As the name suggests, check if the ship has been sunk
	public static boolean checkShipSunk(int numberOfHits, int length){
		boolean shipSunk = false;
		
		if(numberOfHits == length){
			shipSunk = true;
		} else {
			shipSunk = false;
		}
		
		return shipSunk;
	}
	
	
	// As we're initially playing the computer, let the length of the ship vary
	private static int randChooseLength(){
		
		Random rand = new Random();
		
		int length = rand.nextInt(5) +1;
		
		return length;

	}
	
	// Choose coords of the ship - return a String with the contents being "x,y"
	public static String shipChooseCoords(int shipLength){
		
		Random rand = new Random();
		int x = rand.nextInt(25) +1;
		
		// Ensure that we don't exceed the realms of the board
		if(x + shipLength > 25){
			int excess = x + shipLength;
			int offsetCorrection = excess - 25;
			
			x = offsetCorrection;
			
		}
		
		int y = shipLength;
		
		// This isn't correct, I don't think.
		// Think that this should be a List, or comma separated String, or something else. 
		String shipCoords = x + "," + y;
		
		return shipCoords;
	}

	// As we're initially playing the computer, let the orientation of the ship vary
	public static boolean randChooseOrientation(){
		
		boolean vertical;
		
		Random rand = new Random();
		
		int value = rand.nextInt(2) +1;
		
		if (value == 1 ){
			vertical = true;
		} else {
			vertical = false;
		}
		
		return vertical;
		
	}

	// Check if the fired coords score a hit
	public static void checkShipHit(String shipCoords, String coordsFire, boolean shipOrientation, int[][] playingBoard){
		
		
		
	}
	
	// Split the String based on a comma, convert that String position to an int
	private static String splitCoordsString(String coords, char xORy){
		System.out.println("in splitCoordsString " + xORy);
		String[] line = coords.split(",");
		
		int pos;
		
		switch (xORy){
			case 'x':
				pos = 0;
				break;
			case 'y':
				pos = 1;
				break;
			default:
				pos = 10;
				break;
		}


		String coordsValue = line[pos];
		
		System.out.println("End of splitCoordsString " + xORy);
		
		return coordsValue;		
	}

	public static boolean numericCheckCoordsFire(String strCoords){
		System.out.println("numericCheckCoordsFire");	
		return strCoords.matches("-?\\d+(\\.\\d+)?");
		
	}
}
