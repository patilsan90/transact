package com.algo.transact.server_communicator.base;

/**
 * Created by patilsp on 11/12/2017.
 */

public class ResponseStatus {
    private RESPONSE response;
    private String message;


    public static final String PERIPHERAL_UPDATE_INFO_SUCCESS = "Peripheral update information successful";

    public enum RESPONSE {
        e_PERIPHERALS_LIST_UPDATED_SUCCESSFULLY,
        e_ROOM_INFORMATION_UPDATED_SUCCESSFULLY,
        e_NEW_ROOM_CREATED,
        e_DEVICE_CONFIGURED

    }

    public RESPONSE getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
