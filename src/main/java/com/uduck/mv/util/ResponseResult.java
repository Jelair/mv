package com.uduck.mv.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseResult {
    private int code;
    private String message;
    private Map<String, Object> map = new HashMap<>();

    public ResponseResult() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getMsg();
    }

    public static ResponseResult success(){
        ResponseResult rr = new ResponseResult();
        return rr;
    }

    public static ResponseResult fail(Status status){
        ResponseResult rr = new ResponseResult();
        rr.setCode(status.getCode());
        rr.setMessage(status.getMsg());
        return rr;
    }

    public ResponseResult addData(String key, Object value){
        this.map.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public enum Status{
        SUCCESS(200, "success"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found"),
        SERVER_ERROR(500, "Server Error");
        private int code;
        private String msg;

        Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
