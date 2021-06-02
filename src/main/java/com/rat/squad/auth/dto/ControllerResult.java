package com.rat.squad.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Data class, that represents standard response
 */
@ToString
@Getter
@Setter
public class ControllerResult {

    private boolean success = true;
    private String message;
    private String result;

    public ControllerResult() {

    }

    public ControllerResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public ControllerResult(boolean success, String message, String result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    /**
     * method to create success ControllerResult with message
     * @param message message for client
     * @return new ControllerResult with success = true and message
     */
    public static ControllerResult successResult(String message) {
        ControllerResult result = new ControllerResult();
        result.success = true;
        result.message = message;
        return result;
    }


    /**
     * method to create fail ControllerResult with message
     * @param message message for client
     * @return new ControllerResult with success = false and message
     */
    public static ControllerResult failResult(String message) {
        ControllerResult result = new ControllerResult();
        result.success = false;
        result.message = message;
        return result;
    }


}