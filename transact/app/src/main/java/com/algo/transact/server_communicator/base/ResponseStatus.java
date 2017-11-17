package com.algo.transact.server_communicator.base;

/**
 * Created by patilsp on 11/12/2017.
 */

public class ResponseStatus {
    private RESPONSE response;
    private String message;

    private Object responseObject;

    public static final String PERIPHERAL_UPDATE_INFO_SUCCESS = "Peripheral update information successful";

    public enum RESPONSE {
        e_PERIPHERALS_LIST_UPDATED_SUCCESSFULLY,
        e_ROOM_INFORMATION_UPDATED_SUCCESSFULLY,
        e_NEW_ROOM_CREATED,

    }

    public RESPONSE getResponse() {
        return response;
    }

    public Object getResponseObject() {
        return responseObject;
    }
}
