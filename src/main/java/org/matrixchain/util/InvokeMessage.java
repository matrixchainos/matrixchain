package org.matrixchain.util;

public class InvokeMessage<T> {
    private String version = "MatrixC Api - 1.0";
    private int code;
    private T result;
    private String message;

    public InvokeMessage(){

    }

    public InvokeMessage(T result) {
        this.code = 200;
        this.result = result;
        this.message = "successful.";
    }

    public InvokeMessage(T result, String message) {
        this.code = 200;
        this.result = result;
        this.message = message;
    }

    public InvokeMessage(String message) {
        this.code = 404;
        this.result = null;
        this.message = message;
    }

    public InvokeMessage(int code, T result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InvokeMessage{" +
                "version='" + version + '\'' +
                ", code=" + code +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
