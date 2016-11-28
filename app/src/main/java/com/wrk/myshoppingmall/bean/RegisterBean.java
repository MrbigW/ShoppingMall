package com.wrk.myshoppingmall.bean;

/**
 * Created by MrbigW on 2016/11/28.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class RegisterBean {


    /**
     * status : 200
     * message : 请求成功
     * body : {"r":1}
     * timestamp : 1480321768824
     * exception : null
     */

    private int status;
    private String message;
    private BodyBean body;
    private long timestamp;
    private Object exception;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public static class BodyBean {
        /**
         * r : 1
         */

        private int r;

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }
    }
}
