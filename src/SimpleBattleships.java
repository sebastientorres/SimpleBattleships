import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//import java.util.Arrays;
//import java.io.IOException;

public class SimpleBattleships {
	

/******************** BEGIN MAIN ********************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		// set the world \ playing board
		//int[][] playingBoard = new int[15][15];
		
		List<String> listHit = new ArrayList<String>();
		List<String> listMiss = new ArrayList<String>();
		//List<String> coordsFireRecording = new ArrayList<String>();
		List<String> coordsFired = new ArrayList<String>();
		
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
			
			//String fireCoords = readInCoords(listHit, listMiss, coordsFireRecording);
			
			String fireCoords = inputCoords(coordsFired);
			
			//validateCoords(fireCoords);

			if(checkShipHit(shipCoords, fireCoords, shipOrientation, shipLength)){
				listHit.add(fireCoords);
                System.out.println("HIT!!!");
			} else {
				listMiss.add(fireCoords);
                System.out.println("Missed, try again...");
            }

            gameOver = checkShipSunk(shipLength, listHit);
		}
		
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

	// As the name suggests, check if the ship has been sunk
	public static boolean checkShipSunk(int shipLength, List<String> listHit){
		boolean shipSunk = false;
		
		if(listHit.size() == shipLength){
			shipSunk = true;
            System.out.println("Ship sunk, you won.");
		} else {
			shipSunk = false;
		}
		
		return shipSunk;
	}
	
	
	// As we're initially playing the computer, let the length of the ship vary
	public static int randChooseLength(){
		
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
	// Need to be able to record coords that have already been hit
	public static boolean checkShipHit(String shipCoords, String coordsFire, boolean shipOrientation, int shipLength){
		
		boolean shipHit = false;
		
		int xfire = Integer.parseInt(splitCoordsString(coordsFire, 'x'));
		int yfire = Integer.parseInt(splitCoordsString(coordsFire, 'y'));
		
		int xship = Integer.parseInt(splitCoordsString(shipCoords, 'x'));
		int yship = Integer.parseInt(splitCoordsString(shipCoords, 'y'));
		
		// Orientation is vertical
		if (shipOrientation == true){
			for(int i = yship; i < yship + shipLength; i++) {
				if (xfire == xship && yfire == i){
					shipHit = true;
					break;
				} else {
					shipHit = false;
				}
			}
			// Orientation is horizontal
		} else {
			for(int i = xship; i < xship + shipLength; i++) {
				if (xfire == i && yfire == yship){
					shipHit = true;
					break;
				} else {
					shipHit = false;
				}
			}
		}

		return shipHit;
		
	}
	
	// Split the String based on a comma, convert that String position to an int
	public static String splitCoordsString(String coords, char xORy){
		
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

	public static String inputCoords(List<String> coordsFired){
		
		
		Scanner sc = new Scanner(System.in);

		String coordsEntered;

        do{
			System.out.println("Enter coordinates as 'x, y': ");
            coordsEntered = sc.nextLine();
            if(coordsEntered.contentEquals("q") || coordsEntered.contentEquals("Q")){
                System.out.println("Thanks for playing, bye.");
                System.exit(0);
            }
        }while(!validateCoords(coordsEntered) || coordsFired.contains(coordsEntered));
		
		coordsFired.add(coordsEntered);

		return coordsEntered;
	}
	
	public static boolean validateCoords(String coordsEntered){

        boolean results;

		int x, y;
		
		String strx = splitCoordsString(coordsEntered, 'x');
		String stry = splitCoordsString(coordsEntered, 'y');

		if (numericCheckCoordsFire(strx) && numericCheckCoordsFire(stry) ){
			x = Integer.parseInt(strx);
			y = Integer.parseInt(stry);
		
			if (x > 25 || y > 25){
				results = false;
				System.out.println("The dimensions of the board are 25 x 25, 'x,y' entered must be less than this.  You entered '" + strx + "' for x and '" + stry + "' for y.");
			} else {
				results = true;
        	}
		
		} else {
			results = false;
			System.out.println("Coords are supposed to be numbers...  You entered '" + strx + "' for x and '" + stry + "' for y.");
		}

		return results;
	}

 }