import java.util.Random;
/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.
 *
 * @author harry
 *
 */
public class Ocean implements OceanInterface {

	/**
	 * A 10x10 2D array of Ships, which can be used to quickly determine which ship
	 * is in any given location.
	 */
	protected Ship[][] ships;

	/**
	 * The total number of shots fired by the user
	 */
	protected int shotsFired;

	/**
	 * The number of times a shot hit a ship. If the user shoots the same part of a
	 * ship more than once, every hit is counted, even though the additional "hits"
	 * don't do the user any good.
	 */
	protected int hitCount;

	/**
	 * The number of ships totally sunk.
	 */
	protected int shipsSunk;

	/**
	 * Keep track of if the spot is being hit or not
	 */
	protected boolean[][] shootBoard = new boolean[10][10];


	/**
	 * Creates an "empty" ocean, filling every space in the <code>ships</code> array
	 * with EmptySea objects. Should also initialize the other instance variables
	 * appropriately.
	 */


	public Ocean() {
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
		ships = new Ship[10][10];
		EmptySea emptyGrid = new EmptySea();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ships[i][j] = emptyGrid;
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				shootBoard[i][j] = false;
			}
		}

	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean. Larger ships
	 * must be placed before smaller ones to avoid cases where it may be impossible
	 * to place the larger ships.
	 *
	 * @see java.util.Random
	 */
	public void placeAllShipsRandomly() {
		Ship battleship = new Battleship();
		Ship cruiser1 = new Cruiser();
		Ship cruiser2 = new Cruiser();
		Ship destroyer1 = new Destroyer();
		Ship destroyer2 = new Destroyer();
		Ship destroyer3 = new Destroyer();
		Ship submarine1 = new Submarine();
		Ship submarine2 = new Submarine();
		Ship submarine3 = new Submarine();
		Ship submarine4 = new Submarine();

		boolean battleship_placed = false;

		while (!battleship_placed) {
			randomGenerator(battleship);
			if (battleship.okToPlaceShipAt(battleship.bowRow, battleship.bowColumn, battleship.horizontal, this)) {
				battleship.placeShipAt(battleship.bowRow, battleship.bowColumn, battleship.horizontal, this);
				battleship_placed = true;
			}
		}

		boolean cruiser1_placed = false;
		boolean cruiser2_placed = false;

		while (!cruiser1_placed) {
			randomGenerator(cruiser1);
			if (cruiser1.okToPlaceShipAt(cruiser1.bowRow, cruiser1.bowColumn, cruiser1.horizontal, this)) {
				cruiser1.placeShipAt(cruiser1.bowRow, cruiser1.bowColumn, cruiser1.horizontal, this);
				cruiser1_placed = true;
			}
		}

		while (!cruiser2_placed) {
			randomGenerator(cruiser2);
			if (cruiser2.okToPlaceShipAt(cruiser2.bowRow, cruiser2.bowColumn, cruiser2.horizontal, this)) {
				cruiser2.placeShipAt(cruiser2.bowRow, cruiser2.bowColumn, cruiser2.horizontal, this);
				cruiser2_placed = true;
			}
		}

		boolean destroyer1_placed = false;
		boolean destroyer2_placed = false;
		boolean destroyer3_placed = false;

		while (!destroyer1_placed) {
			randomGenerator(destroyer1);
			if (destroyer1.okToPlaceShipAt(destroyer1.bowRow, destroyer1.bowColumn, destroyer1.horizontal, this)) {
				destroyer1.placeShipAt(destroyer1.bowRow, destroyer1.bowColumn, destroyer1.horizontal, this);
				destroyer1_placed = true;
			}
		}
		while (!destroyer2_placed) {
			randomGenerator(destroyer2);
			if (destroyer2.okToPlaceShipAt(destroyer2.bowRow, destroyer2.bowColumn, destroyer2.horizontal, this)) {
				destroyer2.placeShipAt(destroyer2.bowRow, destroyer2.bowColumn, destroyer2.horizontal, this);
				destroyer2_placed = true;
			}
		}
		while (!destroyer3_placed) {
			randomGenerator(destroyer3);
			if (destroyer3.okToPlaceShipAt(destroyer3.bowRow, destroyer3.bowColumn, destroyer3.horizontal, this)) {
				destroyer3.placeShipAt(destroyer3.bowRow, destroyer3.bowColumn, destroyer3.horizontal, this);
				destroyer3_placed = true;
			}
		}

		boolean submarine1_placed = false;
		boolean submarine2_placed = false;
		boolean submarine3_placed = false;
		boolean submarine4_placed = false;

		while (!submarine1_placed) {
			randomGenerator(submarine1);
			if (submarine1.okToPlaceShipAt(submarine1.bowRow, submarine1.bowColumn, submarine1.horizontal, this)) {
				submarine1.placeShipAt(submarine1.bowRow, submarine1.bowColumn, submarine1.horizontal, this);
				submarine1_placed = true;
			}
		}

		while (!submarine2_placed) {
			randomGenerator(submarine2);
			if (submarine2.okToPlaceShipAt(submarine2.bowRow, submarine2.bowColumn, submarine2.horizontal, this)) {
				submarine2.placeShipAt(submarine2.bowRow, submarine2.bowColumn, submarine2.horizontal, this);
				submarine2_placed = true;
			}
		}

		while (!submarine3_placed) {
			randomGenerator(submarine3);
			if (submarine3.okToPlaceShipAt(submarine3.bowRow, submarine3.bowColumn, submarine3.horizontal, this)) {
				submarine3.placeShipAt(submarine3.bowRow, submarine3.bowColumn, submarine3.horizontal, this);
				submarine3_placed = true;
			}
		}

		while (!submarine4_placed) {
			randomGenerator(submarine4);
			if (submarine4.okToPlaceShipAt(submarine4.bowRow, submarine4.bowColumn, submarine4.horizontal, this)) {
				submarine4.placeShipAt(submarine4.bowRow, submarine4.bowColumn, submarine4.horizontal, this);
				submarine4_placed = true;
			}
		}
	}


	/**
	 * Checks if this coordinate is not empty; that is, if this coordinate does not
	 * contain an EmptySea reference.
	 *
	 * @param row    the row (0 to 9) in which to check for a floating ship
	 * @param column the column (0 to 9) in which to check for a floating ship
	 * @return {@literal true} if the given location contains a ship, and
	 *         {@literal false} otherwise.
	 */
	public boolean isOccupied(int row, int column) {
		if (ships[row][column] instanceof EmptySea) {
			return false;
		}
		return true;
	}

	/**
	 * Fires a shot at this coordinate. This will update the number of shots that
	 * have been fired (and potentially the number of hits, as well). If a location
	 * contains a real, not sunk ship, this method should return {@literal true}
	 * every time the user shoots at that location. If the ship has b een sunk,
	 * additional shots at this location should return {@literal false}.
	 *
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return {@literal true} if the given location contains an afloat ship (not an
	 *         EmptySea), {@literal false} if it does not.
	 */
	public boolean shootAt(int row, int column) {
		shotsFired += 1;

		if (ships[row][column] instanceof EmptySea) {
			shootBoard[row][column] = true;
			return false;
		} else if (ships[row][column].isSunk()) {
			return false;
		} else {
			this.ships[row][column].shootAt(row, column);
			shootBoard[row][column] = true;
			hitCount += 1;
			if (ships[row][column].isSunk()) {
				shipsSunk += 1;
			}
			return true;
		}

	}


	/**
	 * @return the number of shots fired in this game.
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * @return the number of hits recorded in this game.
	 */
	public int getHitCount() {
		return this.hitCount;
	}

	/**
	 * @return the number of ships sunk in this game.
	 */
	public int getShipsSunk() {
		return this.shipsSunk;
	}

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 *         {@literal false}.
	 */
	public boolean isGameOver() {
		if (shipsSunk == 10) {
			return true;
		}
		return false;
	}

	/**
	 * Provides access to the grid of ships in this Ocean. The methods in the Ship
	 * class that take an Ocean parameter must be able to read and even modify the
	 * contents of this array. While it is generally undesirable to allow methods in
	 * one class to directly access instancce variables in another class, in this
	 * case there is no clear and elegant alternatives.
	 *
	 * @return the 10x10 array of ships.
	 */
	public Ship[][] getShipArray() {
		return this.ships;
		//return null;
	}

	/**
	 * Prints the ocean. To aid the user, row numbers should be displayed along the
	 * left edge of the array, and column numbers should be displayed along the top.
	 * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
	 * 0, 0.
	 * <ul>
	 * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
	 * ship</li>
	 * <li>'-' to indicate a location that you have fired upon and found nothing
	 * there</li>
	 * <li>'x' to indicate a location containing a sunken ship</li>
	 * <li>'.' (a period) to indicate a location that you have never fired
	 * upon.</li>
	 * </ul>
	 *
	 * This is the only method in Ocean that has any printing capability, and it
	 * should never be called from within the Ocean class except for the purposes of
	 * debugging.
	 *
	 */

	public void print() {
		// Print column numbers (0-9)
		System.out.print("\t");
		for (int col = 0; col < 10; col++) {
			System.out.print(col + "\t");
		}
		System.out.println("\t");
		for (int row = 0; row < 10; row++) {
			// Print row number (0-9)
			System.out.print(row + "\t");
			for (int col = 0; col < 10; col++) {
				Ship ship = ships[row][col];
				// if being shot at
				if (shootBoard[row][col]) {
					System.out.print(ship.toString() + "\t");
				} else {
					System.out.print(".\t");
				}
			}
			System.out.println();
		}
	}





	private void randomGenerator(Ship ship) {

		Random rand = new Random();
		int row = rand.nextInt(10);
		int column = rand.nextInt(10);
		int horizontal = rand.nextInt(2);
		boolean booleanHorizontal = horizontal != 0;
		ship.setBowColumn(column);
		ship.setBowRow(row);
		ship.setHorizontal(booleanHorizontal);

	}

}
