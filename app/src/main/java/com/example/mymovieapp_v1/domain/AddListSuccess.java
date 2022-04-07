package com.example.mymovieapp_v1.domain;

public class AddListSuccess {
    private int status_code;
    private String status_message;
    private boolean success;
    private int list_id;

    public AddListSuccess(int status_code, String status_message, boolean success, int list_id) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.success = success;
        this.list_id = list_id;
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

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
}
