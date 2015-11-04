package com.irc;

import com.irc.event.IrcEvent;
import com.irc.event.IrcEventListener;
import com.irc.event.IrcJoinServerEvent;
import com.irc.event.IrcPrivateMessageEvent;

/**
 * Created by jordan on 03/11/15.
 */
public class Application implements IrcEventListener {
    public static Application application;

    public static void main(String[] args) {
        Application.application = new Application();
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Irc irc;

    public Application() {
        try {
            IrcConfig config = new IrcConfig();
            config.username = "me";
            config.nick = "hello";

            irc = new Irc(config);
            if(irc.connect()) {
                System.out.println("Connected.");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onIrcEvent(IrcEvent ircEvent) {
        if(ircEvent instanceof IrcPrivateMessageEvent) {
            IrcPrivateMessageEvent ev = (IrcPrivateMessageEvent)ircEvent;
            String sender = ev.getSender();
            String channel = ev.getChannel();
            String message = ev.getMessage();

            if(!sender.equals(irc.getConfig().nick)) {
                System.out.println("[" + channel + "] " + sender + ": " + message);
                irc.sendLine("PRIVMSG " + channel + " :" + sender + ", Here's your message repeated because I'm bored: " + message);
            }
        }

        if(ircEvent instanceof IrcJoinServerEvent) {
            IrcJoinServerEvent ev = (IrcJoinServerEvent)ircEvent;
            System.out.println("Joined server [" + ev.getServerAddress() + "]:" + ev.getServerPort());
            irc.joinChannel("somecoolchannel");
        }
    }
}
