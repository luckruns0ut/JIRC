package com.irc.event;

import com.irc.IrcMessage;

/**
 * Created by jordan on 03/11/15.
 */
public class IrcPrivateMessageEvent extends IrcEvent {
    private final String sender;
    private final String channel;
    private final String message;

    public IrcPrivateMessageEvent(String sender, String channel, String message) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
    }

    public IrcPrivateMessageEvent(IrcMessage pm) {
        this(pm.getPrefix().split("!")[0], pm.getParams()[0], pm.getParams()[1].replaceFirst(":", ""));
    }

    public String getSender() {
        return sender;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }
}
