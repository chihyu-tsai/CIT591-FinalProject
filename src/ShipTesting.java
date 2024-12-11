import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ShipTesting {

    @Test
    void testGetLength() {
        Ship battleship = new Battleship();
        assertEquals(4, battleship.getLength());

        Ship cruiser = new Cruiser();
        assertEquals(3, cruiser.getLength());

        Ship destroyer = new Destroyer();
        assertEquals(2, destroyer.getLength());

        Ship submarine = new Submarine();
        assertEquals(1, submarine.getLength());

    }


    @Test
    void testGetBowRow() {
        Ship ship = new Battleship();  // or any ship type

        // Test initial state
        assertEquals(0, ship.getBowRow());

        // Test after placing ship
        Ocean ocean = new Ocean();
        ship.placeShipAt(1, 2, true, ocean);
        assertEquals(1, ship.getBowRow());
    }


    @Test
    void testGetBowColumn() {
        Ship ship = new Battleship();  // or any ship type

        // Test initial state
        assertEquals(0, ship.getBowColumn());

        // Test after placing ship
        Ocean ocean = new Ocean();
        ship.placeShipAt(2, 4, true, ocean);
        assertEquals(4, ship.getBowColumn());
    }

    @Test
    void testSetBowColumn() {
        Ship ship = new Battleship();

        ship.setBowColumn(1);
        assertEquals(1, ship.getBowColumn());

        ship.setBowColumn(3);
        assertEquals(3, ship.getBowColumn());

    }


    @Test
    void testIsHorizontal() {
        Ship ship = new Battleship();
        Ship ship2 = new Submarine();
        Ocean ocean = new Ocean();

        ship.placeShipAt(1, 3, true, ocean);
        assertTrue(ship.isHorizontal());

        ship2.placeShipAt(2,4, false, ocean);
        assertFalse(ship2.isHorizontal());


    }

    @Test
    void testSetBowRow() {
        Ship ship = new Battleship();

        ship.setBowRow(1);
        assertEquals(1, ship.getBowRow());

        ship.setBowRow(3);
        assertEquals(3, ship.getBowRow());
    }


    @Test
    void testGetShipType() {
        Ship battleship = new Battleship();
        assertEquals("Battleship", battleship.getShipType());

        Ship cruiser = new Cruiser();
        assertEquals("Cruiser", cruiser.getShipType());

        Ship destroyer = new Destroyer();
        assertEquals("Destroyer", destroyer.getShipType());

        Ship submarine = new Submarine();
        assertEquals("Submarine", submarine.getShipType());

        Ship emptySea = new EmptySea();
        assertEquals("empty", emptySea.getShipType());
    }


    @Test
    void testOkToPlaceShipAt() {
        Ocean ocean = new Ocean();
        Ship battleship = new Battleship();

        Ship[][] Before = new Ship[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Before[i][j] = ocean.getShipArray()[i][j];
            }
        }

        assertTrue(battleship.okToPlaceShipAt(0, 0, true, ocean));
        assertFalse(battleship.okToPlaceShipAt(0, 10, true, ocean));
        assertFalse(battleship.okToPlaceShipAt(11, 0, true, ocean));

        battleship.placeShipAt(0,0,true, ocean);
        assertFalse(battleship.okToPlaceShipAt(0, 10, true, ocean));


    }

    @Test
    void testIsSunk() {
        Ocean ocean = new Ocean();
        Ship battleship = new Battleship();

        battleship.placeShipAt(0, 0, true, ocean);
        assertFalse(battleship.isSunk());

        battleship.shootAt(0,0);
        assertFalse(battleship.isSunk());

        battleship.shootAt(0,1);
        battleship.shootAt(0,2);
        battleship.shootAt(0,3);
        assertTrue(battleship.isSunk());

    }


    @Test
    void testToString() {
        Ocean ocean = new Ocean();
        Ship battleship = new Battleship();

        battleship.placeShipAt(0, 0, true, ocean);
        assertEquals("S", battleship.toString());

        battleship.shootAt(0,0);
        assertEquals("S", battleship.toString());

        battleship.shootAt(0,1);
        battleship.shootAt(0,2);
        battleship.shootAt(0,3);
        assertEquals("x", battleship.toString());


    }


    @Test
    void testPlaceShipAt() {
        Ocean ocean = new Ocean();
        Ship battleship = new Battleship();

        battleship.placeShipAt(3, 2, true, ocean);
        assertEquals(3, battleship.getBowRow());
        assertEquals(2, battleship.getBowColumn());
        assertTrue(battleship.isHorizontal());

        Ship[][] ships = ocean.getShipArray();
        for (int i = 2; i < 6; i++) {
            assertEquals(battleship, ships[3][i]);
        }

        Ship cruiser = new Cruiser();
        cruiser.placeShipAt(1, 5, false, ocean);
        assertEquals(1, cruiser.getBowRow());
        assertEquals(5, cruiser.getBowColumn());
        assertFalse(cruiser.isHorizontal());

        for (int i = 1; i < 4; i++) {
            assertEquals(cruiser, ships[i][5]);
        }
    }

    @Test
    void testSetHorizontal() {
        Ship ship = new Battleship();
        Ocean ocean = new Ocean();

        ship.setHorizontal(true);
        assertTrue(ship.isHorizontal());

        ship.placeShipAt(3, 3, true, ocean);
        Ship[][] ships = ocean.getShipArray();

        assertEquals(ship, ships[3][3]);
        assertEquals(ship, ships[3][4]);
        assertEquals(ship, ships[3][5]);
        assertEquals(ship, ships[3][6]);


        Ship ship2 = new Battleship();
        ship2.setHorizontal(false);
        assertFalse(ship2.isHorizontal());

        ship2.placeShipAt(2, 2, false, ocean);
        ships = ocean.getShipArray();

        assertEquals(ship2, ships[2][2]);
        assertEquals(ship2, ships[3][2]);
        assertEquals(ship2, ships[4][2]);
        assertEquals(ship2, ships[5][2]);
    }



    @Test
    void testShipShootAt() {
        Ocean ocean = new Ocean();
        Ship battleship = new Battleship();

        battleship.placeShipAt(3, 3, true, ocean);

        assertTrue(ocean.shootAt(3, 3));
        assertTrue(ocean.shootAt(3, 4));
        assertTrue(ocean.shootAt(3, 5));

        assertFalse(ocean.shootAt(3, 2));
        assertFalse(ocean.shootAt(2, 3));

        ocean.shootAt(3, 6);
        assertFalse(ocean.shootAt(3, 3));

        Ship destroyer = new Destroyer();
        destroyer.placeShipAt(5, 5, false, ocean);

        assertTrue(ocean.shootAt(5, 5));
        assertTrue(ocean.shootAt(6, 5));
    }



}
