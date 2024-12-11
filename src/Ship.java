public abstract class Ship {

    protected int bowColumn;
    protected int bowRow;
    protected boolean[] hit;
    protected boolean horizontal;
    protected int length;


    /**
     *
     * @return the column of the bow (front) of the ship.
     */
    public int getBowColumn() {
        return bowColumn;
    };

    /**
     *
     * @return the row of the bow (front) of the ship.
     */
    public int getBowRow() {
        return bowRow;
    };

    /**
     *
     * @return the length of the ship.
     */
    public int getLength() {
        return length;
    };

    /**
     *
     * @return the String representing the type of this ship.
     */
    public abstract String getShipType();

    /**
     *
     * @return true if this boat is horizontal (facing left), false otherwise.
     */
    public boolean isHorizontal() {
        return horizontal;
    }


    /**
     * Returns true if this ship has been sunk and false otherwise.
     * @return true if every part of the ship has been hit, and false otherwise.
     */
    public boolean isSunk() {
        int totalHit = 0;
        for (int i = 0; i < length; i++) {
            if (hit[i]) {
                totalHit += 1;
            }
        }
        if (totalHit == length) {
            return true;
        }
        return false;
    }


    /**
     * Determines whether or not this is represents a valid placement configuration for this Ship in this Ocean.
     * Ship objects in an Ocean must not overlap other Ship objects or touch them vertically, horizontally, or diagonally.
     * Additionally, the placement cannot be such that the Ship would extend beyond the extents of the 2D array in which it is placed.
     * Calling this method should not actually change either the Ship or the provided Ocean.
     * @param row the candidate row to place the ship
     * @param column the candidate column to place the ship
     * @param horizontal whether or not to have the ship facing to the left
     * @param ocean the Ocean in which this ship might be placed
     * @return true if it is valid to place this ship of this length in this location with this orientation, and false otherwise.
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        if (!horizontal) {
            if (row >= 10 || row + length > 10 || row < 0) {
                return false;
            }
            if (column >= 10 || column + length > 10 || column < 0) {
                return false;
            }

            int startRow = Math.max(row - 1, 0) ;
            int endRow = Math.min(row + length, 9) ;
            int startCol = Math.max(column - 1, 0) ;
            int endCol = Math.min(column + 1, 9) ;

            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    // if (row == 0) {}
                    if (ocean.isOccupied(i,j)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            // this is horizontal case
            if (row >= 10 || row + length > 10 || row < 0) {
                return false;
            }
            if (column >= 10 || column + length > 10 || column < 0) {
                return false;
            }

            int startRow = Math.max(row - 1, 0) ;
            int endRow = Math.min(row + 1, 9) ;
            int startCol = Math.max(column - 1, 0) ;
            int endCol = Math.min(column + length, 9) ;

            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    if (ocean.isOccupied(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }


    /**
     * Puts the Ship in the Ocean. This will give values to the bowRow, bowColumn, and horizontal instance variables in the Ship.
     * This should also place a reference to this Ship in each of the one or more locations (up to four) in the corresponding
     * Ocean array this Ship is being placed in. Each of the references placed in the Ocean will be identical since it is not
     * possible to refer to a "part" of a ship, only the whole ship.
     * @param row
     * @param column
     * @param horizontal
     * @param ocean
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        setBowRow(row);
        setBowColumn(column);
        setHorizontal(horizontal);
        if (!horizontal) {
            for (int i = 0; i < length; i++) {
                ocean.ships[row + i][column] = this;
            }
            // horizontal case
        } else {
            for (int i = 0; i < length; i++) {
                ocean.ships[row][column + i] = this;
            }
        }
    }


    /**
     *
     * @param bowColumn the bowColumn to set
     */
    public void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    /**
     *
     * @param bowRow the bowRow to set
     */
    public void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    /**
     *
     * @param horizontal the horizontal to set
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }


    /**
     * If a part of this ship occupies this coordinate, and if the ship hasn't been sunk, mark the part of the ship
     * at that coordinate as "hit".
     * @param row
     * @param column
     * @return true if this ship hasn't been sunk and a part of this ship occupies the given row and column and false otherwise.
     */
    public boolean shootAt(int row, int column) {
        if (horizontal) {
            if (!this.isSunk()) {
                int location = column - bowColumn;
                if (location >= 0 && location < length) {
                    this.hit[location] = true;
                    return true;
                }
            }
        } else {
            if (!this.isSunk()) {
                int location = row - bowRow;
                if (location >= 0 && location < length) {
                    this.hit[location] = true;
                    return true;
                }

            }
        }
        return false;

    }

    /**
     *Returns a single character String to use in the Ocean's print method. This method should return "x" if the
     * ship has been sunk, and "S" if it has not yet been sunk. This method can only be used to print out locations
     * in the ocean that have been shot at; it should not be used to print locations that have not been the target of a shot yet.
     * @return "x" if this ship has been sunk, and "S" otherwise.
     */
    public String toString() {
        if (this.isSunk()) {
            return "x";
        }
        return "S";

    }

}
