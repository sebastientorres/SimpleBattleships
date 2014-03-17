import java.util.Random;
import java.io.Console;
//import java.util.Arrays;
//import java.io.IOException;

public class SimpleBattleships {
	

/******************** BEGIN MAIN ********************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// set the world \ playing board
		int[][] playingBoard = new int[15][15];
		
		// setting the ship up
		int shipLength = randChooseLength();
		boolean shipOrientation = randChooseOrientation();
		String shipCoords = shipChooseCoords(shipLength);
		
		System.out.println("Ship coordinates are " + shipCoords);
		System.out.println("Ship length is " + shipLength);
		if(shipOrientation == true){
			System.out.println("Ship orientaiton is vertical");
		} else{
			System.out.println("Ship orientaiton is horizontal");
		}
		
		
		boolean gameOver = false;
		while(!gameOver){
			
			String fireCoords = readInCoords();
			
			boolean shipHit = checkShipHit(shipCoords, fireCoords, shipOrientation, shipLength);
			
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
		boolean coordsFireValid;
		int x, y;
		
		String strx = splitCoordsString(coordsFire, 'x');
		String stry = splitCoordsString(coordsFire, 'y');
		
		
		if (numericCheckCoordsFire(strx) && numericCheckCoordsFire(stry) ){
			x = Integer.parseInt(strx);
			y = Integer.parseInt(stry);
			
			if (x >= 26 || y >= 26){
				coordsFireValid = false;
				System.out.println("The dimensions of the board are 25 x 25, 'x,y' entered must be less than this.  You entered '" + strx + "' for x and '" + stry + "' for y.");
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
	// when shipOrientation is true x is extended/ranged by the shipLength, otherwise, y is 
	public static boolean checkShipHit(String shipCoords, String coordsFire, boolean shipOrientation, int shipLength){
		
		boolean shipHit;
		
		int xfire = Integer.parseInt(splitCoordsString(coordsFire, 'x'));
		int yfire = Integer.parseInt(splitCoordsString(coordsFire, 'y'));
		
		int xship = Integer.parseInt(splitCoordsString(shipCoords, 'x'));
		int yship = Integer.parseInt(splitCoordsString(shipCoords, 'y'));
		
		if (shipOrientation == true){
			for(int i = xship; i < xship + shipLength; i++) {
				if (xfire == i && yfire == yship){
					shipHit = true;
				} else {
					shipHit = false;
				}
			}
		} else {
			for(int i = yship; i < yship + shipLength; i++) {
				if (xfire == xship && yfire == i){
					shipHit = true;
				} else {
					shipHit = false;
				}
			}
		}

		// Why is this not getting the assignment that it should - according to the logic above
		return shipHit;
		
	}
	

	
	// Split the String based on a comma, convert that String position to an int
	private static String splitCoordsString(String coords, char xORy){
		
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
		
		return coordsValue;		
	}

	public static boolean numericCheckCoordsFire(String strCoords){

		return strCoords.matches("-?\\d+(\\.\\d+)?");
		
	}

	public static String readInCoords(){
		
		// Set up a console object
		Console c = System.console();
				
		//boolean coordsFireValid = false;
			
		String coordsFire = c.readLine("Enter coordinates as 'x,y':");
		
		validateCoordsFire(coordsFire);

		int x = Integer.parseInt(splitCoordsString(coordsFire, 'x'));
		int y = Integer.parseInt(splitCoordsString(coordsFire, 'y'));
		
		System.out.println("Firing coordinates are x = " + x + " and y = " + y);
				
		return coordsFire;
	}

}
