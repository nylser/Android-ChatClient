package net.mineguild.android.chatclient.connection;

public enum ConnectionResult {

    SUCCESS("Connection success!"),
    IO_ERROR("IO Error!"),
    NAME_USED("Name already used!");

    private final String message;
    ConnectionResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
