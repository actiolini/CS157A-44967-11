package moviebuddy.model;

public class Room {
    private int theatreId;
    private int roomNumber;
    private int numberOfRows;
    private int seatsPerRow;

    public Room(int theatreId, int roomNumber) {
        this.theatreId = theatreId;
        this.roomNumber = roomNumber;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }
}
