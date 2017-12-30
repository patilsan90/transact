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
        e_DEVICE_CONFIGURED,
        e_DEVICE_MAC_STORE_SUCCESS,
        e_DEVICE_SET_IN_RECEPTION_MODE,
        e_HAS_SMART_HOME,
        e_DOES_NOT_HAVE_SMART_HOME

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
