package com.example.mymovieapp_v1.domain.response;

public class CreateListResponse {
    private String status_message;
    private boolean success;
    private int status_code;
    private int list_id;

    public CreateListResponse(String status_message, boolean success, int status_code, int list_id) {
        this.status_message = status_message;
        this.success = success;
        this.status_code = status_code;
        this.list_id = list_id;
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

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
}
