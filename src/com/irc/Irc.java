package com.irc;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by jordan on 03/11/15.
 */
public class Irc {
    private final Socket socket;
    private final IrcConfig config;
    private IrcReader ircReader;
    private BufferedWriter ircWriter;

    public Irc(IrcConfig config) {
        this.config = config;
        this.socket = new Socket();
    }

    public boolean connect() {
        try {
            socket.connect(new InetSocketAddress(config.serverAddress, config.serverPort));
            return socket.isConnected() && init();
        }catch (Exception ex) {

        }
        return false;
    }

    private boolean init() {
        try {
            if (ircReader != null) {
                ircReader.interrupt();
            }
            ircWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            if(config.password != null)
                sendLine("PASS " + config.password);
            if(config.nick != null)
                sendLine("NICK " + config.nick);
            if(config.username != null)
                sendLine("USER " + config.username + " 0 0 0");

            ircWriter.flush();

            ircReader = new IrcReader(this);
            ircReader.start();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void sendLine(String line) {
        try {
            ircWriter.write(line + "\r\n");
            ircWriter.flush();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void joinChannel(String channel) {
        sendLine("JOIN #" + channel);
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isConnected() {
        return socket != null ? socket.isConnected() : false;
    }

    public IrcConfig getConfig() {
        return config;
    }
}
