import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimpleBattleships {
	

/******************** BEGIN MAIN ********************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		// set the world \ playing board
		//int[][] playingBoard = new int[15][15];

        /* Set some Lists.
        Record what has been hit, missed and fired, respectively.
        */
		List<String> listHit = new ArrayList<String>();
		List<String> listMiss = new ArrayList<String>();
		List<String> coordsFired = new ArrayList<String>();
		
		// Setting the ship up
		int shipLength = randChooseLength();
		boolean shipOrientation = randChooseOrientation();
		String shipCoords = shipChooseCoords(shipLength);

        /*
		System.out.println("Ship coordinates are " + shipCoords);
		System.out.println("Ship length is " + shipLength);
		if(shipOrientation == true){
			System.out.println("Ship orientaiton is vertical");
		} else{
			System.out.println("Ship orientaiton is horizontal");
		}*/

        System.out.println("Battleships!!!");
        System.out.println("A pretty simple game, choose coordinates x and y, where the values are between 1 and 25 inclusive,\n" +
                "with a comma ',' in between, for example: 23,2\n" +
                "Enter 'q' to quit the game.\n" +
                "Begin...");
        System.out.println("Ship length is " + shipLength);
		
		boolean gameOver = false;
		// This while loop is the actual game play
        while(!gameOver){
			String fireCoords = inputCoords(coordsFired);

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
	
	/* As the name suggests, check if the ship has been sunk.
    Compare the length of the ship against the number of elements in the List, the method returns true if the values
    are the same.
    */
	public static boolean checkShipSunk(int shipLength, List<String> listHit){
		boolean shipSunk;
		
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
		String shipCoords = x + "," + y;
		
		return shipCoords;
	}

	/* As we're initially playing the computer, let the orientation of the ship vary
	About as self explanatory as it's going to get?
	When boolean vertical is false, then the ship is horizontal.
	 */
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

	/* Check if the fired coordinates score a hit
	When shipOrientation is true x is extended/ranged by the shipLength, otherwise, y is.
	Return boolean as true when the coordinates fired match coordinates of the ship.
	*/
	public static boolean checkShipHit(String shipCoords, String coordsFire, boolean shipOrientation, int shipLength){
		
		boolean shipHit = false;
		
		int xfire = Integer.parseInt(splitCoordsString(coordsFire, 'x'));
		int yfire = Integer.parseInt(splitCoordsString(coordsFire, 'y'));
		
		int xship = Integer.parseInt(splitCoordsString(shipCoords, 'x'));
		int yship = Integer.parseInt(splitCoordsString(shipCoords, 'y'));
		
		// Orientation is vertical
		if (shipOrientation){
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
	
	/* Split the String based on a comma and convert that String position to an integer.
	Return the integer.
	 */
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

    /*
    Perform a numeric check on the entered coordinates.
    Return boolean.  Numbers are expected to match, thus boolean is true.
     */
	public static boolean numericCheckCoordsFire(String strCoords){

		return strCoords.matches("-?\\d+(\\.\\d+)?");
		
	}

    /*
    Read in the coordinates that the player gives, return validated coordinates as a String with the format of 'x,y'.
    Run withing a do-while loop so that the input occurs a least once.
    The while() section performs input validation.

    This method fails if <RETURN> is pressed without entering any data, same if the data is only half formed, for example
    '3,' fails.
     */
	public static String inputCoords(List<String> coordsFired){

		Scanner sc = new Scanner(System.in);

		String coordsEntered;

        do{
			System.out.println("Enter coordinates as 'x,y': ");
            coordsEntered = sc.nextLine();
            coordsEntered = coordsEntered.trim();
            if(coordsEntered.contentEquals("q") || coordsEntered.contentEquals("Q")){
                System.out.println("Thanks for playing, bye.");
                System.exit(0);
            }
        }while(!validateCoords(coordsEntered) || coordsFired.contains(coordsEntered));
		
		coordsFired.add(coordsEntered);

		return coordsEntered;
	}

	/*
	Validate the coordinates are numbers withing the range of the board and that they are in fact numbers.
	Return boolean.  When coordinates pass the checks, boolean is true.
	 */
	public static boolean validateCoords(String coordsEntered){

        boolean results;

		int x, y;
		
		String strx = splitCoordsString(coordsEntered, 'x');
		String stry = splitCoordsString(coordsEntered, 'y');

		if (numericCheckCoordsFire(strx) && numericCheckCoordsFire(stry) ){
			x = Integer.parseInt(strx);
			y = Integer.parseInt(stry);
		
			if (x > 25 || y > 25 || y < 1 || x < 1){
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