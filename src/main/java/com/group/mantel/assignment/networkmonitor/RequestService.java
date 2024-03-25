package com.group.mantel.assignment.networkmonitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestService {
    record Request(String ipAddress, String url){}

    public Request extractRequest(String logLine) {
        return null;
    }

    public String extractIPAddress(String logLine) {
        String zeroTo255 = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        String ipAddressRegex = "\\b" + zeroTo255 + "\\."+ zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\b";
        Pattern regexPattern = Pattern.compile(ipAddressRegex);

        Matcher matcher = regexPattern.matcher(logLine);
        if (matcher.find())
            return matcher.group();
        return null;
    }

    public String extractURL(String logLine) {
        String[] parts = logLine.split("GET "); //Assumption is data contains only GET requests and in this format
        String[] urlAndWords = parts[1].split(" "); // reading the part next to GET
        return urlAndWords[0]; // return first word after GET
    }
}
