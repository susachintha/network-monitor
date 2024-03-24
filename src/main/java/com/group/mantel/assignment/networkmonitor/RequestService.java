package com.group.mantel.assignment.networkmonitor;

public class RequestService {
    record Request(String ipAddress, String url){}

    public Request extractRequest(String logLine) {
        return null;
    }
}
