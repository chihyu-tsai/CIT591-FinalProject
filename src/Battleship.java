public class Battleship extends Ship {

    /**
     * Sets the inherited length variable and initializes the hit array, based on the size of this ship (4 tiles).
     */
    public Battleship() {
        this.length = 4;
        this.hit = new boolean[4];
    }

    /**
     * @return "Battleship", indicating that this is a Battleship.
     */
    @Override
    public String getShipType() {
        return "Battleship";
    }
}
