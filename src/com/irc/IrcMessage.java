package com.irc;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jordan on 03/11/15.
 */
public class IrcMessage {
    private final String line;

    private String prefix;

    private String command;
    private String[] params;

    public IrcMessage(String line) {
        this.line = line;
        parse();
    }

    // message = [ ":" prefix SPACE ] command [ params ] crlf
    // aka :prefix command params crlf
    private void parse() {
        String[] split = line.split(" ");
        this.prefix = split[0].replace(":", ""); // prefix is before the first space, but remove the : in front of it
        this.command = split[1];

        // parse params
        ArrayList<String> p = new ArrayList<>();

        // chop off the first two split chunks to get the parameter string, then split it up
        String[] paramsSplit = line.replaceFirst(":" + this.prefix + " " + this.command + " ", "").split(" ");

        String parsedString = "";
        boolean foundColon = false;
        for(String param : paramsSplit) {
            if(param.startsWith(":"))
                foundColon = true;

            if(!foundColon)
                p.add(param);
            else
                parsedString += param + " ";
        }
        parsedString = parsedString.replaceFirst(":", "");
        p.add(parsedString.trim());
        this.params = p.toArray(new String[p.size()]);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public String[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        String out = "[IrcMessage] ";
        out += "prefix=[" + getPrefix() + "] ";
        out += "command=[" + getCommand() + "] ";
        out += "params=[" + Arrays.toString(params) + "]";
        return out;
    }
}