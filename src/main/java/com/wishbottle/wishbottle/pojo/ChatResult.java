package com.wishbottle.wishbottle.pojo;

public class ChatResult {
    private Integer code;

    private String message;

    private Object data;

    public ChatResult(Integer code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
