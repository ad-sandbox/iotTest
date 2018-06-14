package de.appdynamics.ace.test.iot.rmi.impl;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.Map;

public class RMIMessageObject implements Serializable{
    public String getMsg() {
        return _msg;
    }

    public void setMsg(String msg) {
        _msg = msg;
    }

    public Map<String, String> getHeaders() {
        return _headers;
    }

    public void setHeaders(Map<String, String> headers) {
        _headers = headers;
    }

    private String _msg;
    private Map<String, String> _headers;

    public RMIMessageObject() {}
    public RMIMessageObject(String msg, Map<String,String> headers){
        _msg = msg;

        _headers = headers;
    }

    public RMIMessageObject(String msg){
        _msg = msg;
    }

}
