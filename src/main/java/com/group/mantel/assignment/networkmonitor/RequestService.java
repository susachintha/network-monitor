package com.group.mantel.assignment.networkmonitor;

import com.group.mantel.assignment.networkmonitor.model.AddressCount;
import com.group.mantel.assignment.networkmonitor.model.Request;
import com.group.mantel.assignment.networkmonitor.model.UrlCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestService {
    private static final Logger LOG = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    RequestRepository requestRepository;
    public record RequestRecord(String ipAddress, String url){}

    public RequestRecord extractRequest(String logLine) {
        return new RequestRecord(extractIPAddress(logLine), extractURL(logLine));
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

    public Request saveRequest(RequestRecord requestRecord) {
        Request request = new Request();
        request.setAddress(requestRecord.ipAddress);
        request.setUrl(requestRecord.url);
        return requestRepository.save(request);
    }

    public void saveRequestsFromLog(String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            while (line != null) {
                RequestService.RequestRecord requestRecord = extractRequest(line);
                saveRequest(requestRecord);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            LOG.error("IO Exception occurred");
        }
    }

    List<UrlCount> findMostVisitedUrls(Long numberOfTopUrls) {
        return requestRepository.findMostVisitedUrls(numberOfTopUrls);
    }

    List<AddressCount> findMostActiveIPAddresses(Long numberOfTopAddresses) {
        return requestRepository.findMostActiveIPAddresses(numberOfTopAddresses);
    }

}
