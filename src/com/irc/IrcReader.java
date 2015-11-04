package com.irc;

import com.irc.event.IrcJoinServerEvent;
import com.irc.event.IrcPrivateMessageEvent;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by jordan on 03/11/15.
 */
public class IrcReader extends Thread {
    private final Irc irc;
    private Scanner scanner;

    public IrcReader(Irc irc) throws IOException {
        this.irc = irc;
        this.scanner = new Scanner(irc.getSocket().getInputStream());
    }

    @Override
    public void run() {
        System.out.println("Starting irc reader...");

        while(isAlive() && irc.isConnected()) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // the server should always prefix its messages with a :
                if(line.startsWith(":")) {
                    IrcMessage message = new IrcMessage(line);
                    System.out.println(message.toString());
                    System.out.println(line);

                    if(message.getCommand().equals("004")) {
                        Application.application.onIrcEvent(new IrcJoinServerEvent(irc.getConfig().serverAddress, irc.getConfig().serverPort));
                    }

                    if(message.getCommand().equals("PRIVMSG")) {
                        Application.application.onIrcEvent(new IrcPrivateMessageEvent(message));
                    }
                }
            }
        }

        System.out.println("Irc reader done.");
    }
}
