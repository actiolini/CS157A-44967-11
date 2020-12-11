package moviebuddy.model;

public class Ticket {
    private int scheduleId;
    private String seatNumber;
    private Boolean occupied;

    public int getScheduleId() {
        return scheduleId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
