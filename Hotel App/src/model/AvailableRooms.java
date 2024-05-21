package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class AvailableRooms {
    private final StringProperty roomType;
    private final IntegerProperty noOfRooms;

    public AvailableRooms(String roomType, int noOfRooms) {
        this.roomType = new SimpleStringProperty(roomType);
        this.noOfRooms = new SimpleIntegerProperty(noOfRooms);
    }

    public String getRoomType() {
        return roomType.get();
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public int getNoOfRooms() {
        return noOfRooms.get();
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms.set(noOfRooms);
    }

    public IntegerProperty noOfRoomsProperty() {
        return noOfRooms;
    }
}
