package com.algo.transact.home.smart_home.beans;

import java.util.ArrayList;

/**
 * Created by patilsp on 10/14/2017.
 */

public class House {
    int id;
    String name;
    int owner_id;
    String authentication_code;
    ArrayList<Room> rooms = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getAuthentication_code() {
        return authentication_code;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public static House getHouse(int owner_id) {
        /* TODO
         * Check whether house is already stored.
         * if stored in local memory retrieve from their
         *
         */
        House house = new House();
        house.name = "Sanjeevan House";
        house.id = 123;
        house.owner_id = owner_id;
        house.rooms = new ArrayList<>();

        Room room1 = new Room();
        room1.name = "Hall";


        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.BULB, "BULB 1", Peripheral.Status.ON, 100));
        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.FRIDGE, "Fridge", Peripheral.Status.ON, 0));
        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.FAN, "Fan", Peripheral.Status.ON, 100));
        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.BULB, "BULB 2", Peripheral.Status.ON, 100));
        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.BULB, "BULB 3", Peripheral.Status.ON, 100));
        room1.peripherals.add(new Peripheral(Peripheral.PERIPHERAL_TYPE.BULB, "BULB 4", Peripheral.Status.ON, 100));

        Room room2 = new Room();
        room2.name = "Kitchen";

        Room room3 = new Room();
        room3.name = "Sans Room";

        Room room4 = new Room();
        room4.name = "Govinds Room";

        Room room5 = new Room();
        room5.name = "Kapil & Vijen Room";


        house.rooms.add(room1);
        house.rooms.add(room2);
        house.rooms.add(room3);
        //    house.rooms.add(room4);
        //   house.rooms.add(room5);

        return house;


    }
}
