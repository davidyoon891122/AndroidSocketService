package com.example.library;

public class MessageQuery extends NetoolQuery{

    private String Message;
    public MessageQuery() {
        set_process(000);
        set_service(001);
    }

    public void set_Message(String value) {
        this.Message = value;
    }

    public void willBuildDataBytes() {
        putStringData(Message);
    }
}
