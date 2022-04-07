package com.example.mymovieapp_v1.domain.response;

public class GeneralResponse {
    private boolean success;
    private int status_code;
    private String status_message;

    public GeneralResponse(boolean success, int status_code, String status_message) {
        this.success = success;
        this.status_code = status_code;
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
