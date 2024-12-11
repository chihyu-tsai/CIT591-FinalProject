import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;


public class OceanTesting {

    @Test
    void testPlaceAllShipsRandomly() {

        Ocean ocean = new Ocean();
        ocean.placeAllShipsRandomly();

        int battleship = 0;
        int cruiser = 0;
        int destroyer = 0;
        int submarine = 0;

        Ship[][] ships = ocean.getShipArray();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Ship ship = ships[row][col];
                if (ship instanceof Battleship) {
                    battleship ++;
                }
                if (ship instanceof Cruiser) {
                    cruiser ++;
                }
                if (ship instanceof Destroyer) {
                    destroyer ++;
                }
                if (ship instanceof Submarine) {
                    submarine ++;
                }
            }
        }


        int expectedBattleship = 4;
        int expectedCruiser = 6;
        int expectedDestroyer = 6;
        int expectedSubmarine = 4;


        assertEquals(expectedBattleship, battleship);
        assertEquals(expectedCruiser, cruiser);
        assertEquals(expectedDestroyer, destroyer);
        assertEquals(expectedSubmarine, submarine);

    }

    @Test
    void testIsOccupied() {

        Ocean ocean = new Ocean();
        ocean.placeAllShipsRandomly();

        boolean Occupied = false;
        boolean Empty = false;

        for (int i = 0; i < 10 && (!Occupied || !Empty); i++) {
            for (int j = 0; j < 10 && (!Occupied || !Empty); j++) {
                if (ocean.isOccupied(i, j)) Occupied = true;
                else Empty = true;
            }
        }

        assertTrue(Occupied);
        assertTrue(Empty);

    }
    //
    @Test
    void testGetShotsFired() {
        Ocean ocean = new Ocean();
        ocean.placeAllShipsRandomly();

        boolean shot1 = ocean.shootAt(1,2);
        boolean shot2 =ocean.shootAt(3,4);
        int shots = ocean.getShotsFired();
        assertEquals(2, shots);
    }

    @Test
    void testGetHitCount() {
        // test for initial state
        Ocean ocean = new Ocean();
        assertEquals(0, ocean.getHitCount());

        // test if hits are recorded correctly
        ocean.placeAllShipsRandomly();

        boolean shipSunk = false;
        for (int i = 0; i < 10 && !shipSunk; i++) {
            for (int j = 0; j < 10 && !shipSunk; j++) {
                if (ocean.isOccupied(i, j)) {
                    // should count the first hit
                    ocean.shootAt(i, j);
                    int hitCount = 1;
                    assertEquals(hitCount, ocean.getHitCount());

                    // should still count the second hit
                    ocean.shootAt(i, j);
                    hitCount ++;
                    assertEquals(hitCount, ocean.getHitCount());

                } else if (!ocean.isOccupied(i, j)) {
                    // should not count
                    ocean.shootAt(i, j);
                    int hitCount2 = 0;

                    // should still not count
                    ocean.shootAt(i, j);
                    assertEquals(hitCount2, ocean.getHitCount());

                }
            }
        }

    }




    @Test
    void testGetShipsSunk() {
        Ocean ocean = new Ocean();
        // check initial state
        assertEquals(0, ocean.getShipsSunk());

        ocean.placeAllShipsRandomly();

        // Count how many ships are actually sunk by checking the ship array
        int expectedsunkCount = 0;
        Ship[][] ships = ocean.getShipArray();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Ship ship = ships[row][col];
                if (!(ship instanceof EmptySea) && ship.isSunk() &&
                        ship.getBowRow() == row && ship.getBowColumn() == col) {
                    expectedsunkCount++;
                }
            }
        }
        assertEquals(expectedsunkCount, ocean.getShipsSunk());
    }



    @Test
    void testIsGameOver() {
        Ocean ocean = new Ocean();

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

        battleship.placeShipAt(6, 3, true, ocean);
        cruiser1.placeShipAt(0, 5, true, ocean);
        cruiser2.placeShipAt(3, 7, true, ocean);
        destroyer1.placeShipAt(3, 2, false, ocean);
        destroyer2.placeShipAt(3, 4, true, ocean);
        destroyer3.placeShipAt(7, 1, false, ocean);
        submarine1.placeShipAt(8, 4, false, ocean);
        submarine2.placeShipAt(9, 8, false, ocean);
        submarine3.placeShipAt(0, 9, true,ocean);
        submarine4.placeShipAt(3, 0, true, ocean);

        System.out.println("Initial ships sunk: " + ocean.getShipsSunk());

        // sink the battleship
        ocean.shootAt(6, 3);
        ocean.shootAt(6, 4);
        ocean.shootAt(6, 5);
        ocean.shootAt(6, 6);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        assertFalse (ocean.isGameOver());

        // Sink cruisers
        ocean.shootAt(0, 5);
        ocean.shootAt(0, 6);
        ocean.shootAt(0, 7);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        ocean.shootAt(3, 7);
        ocean.shootAt(3, 8);
        ocean.shootAt(3, 9);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        // Sink destroyers
        ocean.shootAt(3, 2);
        ocean.shootAt(4, 2);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        ocean.shootAt(3, 4);
        ocean.shootAt(3, 5);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        ocean.shootAt(7, 1);
        ocean.shootAt(8, 1);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        // Sink submarines
        ocean.shootAt(8, 4);
        ocean.shootAt(9, 8);
        ocean.shootAt(0, 9);
        ocean.shootAt(3, 0);
        System.out.println("ships sunk: " + ocean.getShipsSunk());

        assertTrue (ocean.isGameOver());

    }

    @Test
    void testGetShipArray() {
        Ocean ocean = new Ocean();
        Ship[][] ships = ocean.getShipArray();

        assertEquals(ships.length, 10);
        assertEquals(ships[0].length, 10);

        // check if is empty sea at initial state
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(ships[i][j] instanceof EmptySea);
            }
        }

        // place a battleship and check if reflects on the grid
        Ship submarines = new Submarine();
        submarines.placeShipAt(5, 5, true, ocean);
        ships = ocean.getShipArray();
        assertTrue(ships[5][5] instanceof Submarine);

    }

    @Test
    void testPrint() {



    }


    @Test
    void testOceanShootAt() {
        Ocean ocean = new Ocean();
        Ship sea = new EmptySea();
        Ship submarine = new Submarine();
        Ship cruiser = new Cruiser();
        sea.placeShipAt(1, 2, true, ocean);
        submarine.placeShipAt(3, 4, false, ocean);
        cruiser.placeShipAt(5, 6, true, ocean);

        //test if shotCount update works
        boolean shot1 = ocean.shootAt(1, 2);
        boolean shot2 = ocean.shootAt(3, 4);
        boolean shot3 = ocean.shootAt(5, 6);
        boolean shot4 = ocean.shootAt(5, 7);
        boolean shot5 = ocean.shootAt(5, 8);
        boolean shot6 = ocean.shootAt(5, 8);

        int shots = ocean.getShotsFired();
        assertEquals(6, shots);
        assertFalse(shot1);
        assertTrue(shot2);
        assertTrue(shot5);
        assertFalse(shot6);


    }

    @Test
    void testShootEverything() {
        Ocean o = new Ocean();
        o.placeAllShipsRandomly();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                o.shootAt(i, j);

            }
        }
        assertEquals(10, o.getShipsSunk());
    }



}

