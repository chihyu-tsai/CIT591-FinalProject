public class EmptySea extends Ship{

    public EmptySea() {
        this.length = 1;
        this.hit = new boolean[1];;
    }

    /**
     * Since an EmptySea is empty by definition, shooting at one will always be a miss.
     * @param row
     * @param column
     * @return false always, since nothing will be hit.
     */
    @Override
    public boolean shootAt(int row, int column) {
        return false;
    }


    /**
     * Since an EmptySea is empty by definition, it is not possible that one can be sunk.
     * @return false always, since nothing will be hit.
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    /**
     * Returns a single character String to use in the Ocean's print method.
     * This method can only be used to print out locations in the ocean that have been shot at;
     * it should not be used to print locations that have not been the target of a shot yet.
     * Since an EmptySea is empty by definition, targeting it will always result in a miss so return "-".
     * @return
     */
    @Override
    public String toString() {
        return "-";
    }

    public String getShipType() {
        return "empty";
    }
}
