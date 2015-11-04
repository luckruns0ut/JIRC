package com.irc.event;

/**
 * Created by jordan on 03/11/15.
 */
public class IrcJoinServerEvent extends IrcEvent {
    private final String serverAddress;
    private final int serverPort;

    public IrcJoinServerEvent(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }
}
