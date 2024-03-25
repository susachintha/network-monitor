package com.group.mantel.assignment.networkmonitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = {RequestService.class})
class RequestServiceTests {

	@Autowired
	RequestService requestService;

	@Test
	void extractRequest() {
		String requestLog = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";
		RequestService.Request request = requestService.extractRequest(requestLog);
		Assertions.assertEquals("177.71.128.21", request.ipAddress());
		Assertions.assertEquals("/intranet-analytics/", request.url());
	}

	@Test
	void extractRequests() {
		BufferedReader expectedReader;
		BufferedReader reader;
		try {

			expectedReader = new BufferedReader((new FileReader("src/test/resources/ip-addresses-and-urls.txt")));
			reader = new BufferedReader(new FileReader("src/test/resources/programming-task-example-data.log"));
			String line = reader.readLine();
			String expectValues = expectedReader.readLine();

			while (line != null) {
				RequestService.Request request = requestService.extractRequest(line);
				String[] values = expectValues.split(" ");
				Assertions.assertEquals(values[0], request.ipAddress());
				Assertions.assertEquals(values[1], request.url());
				// read next line
				line = reader.readLine();
				expectValues = expectedReader.readLine();

			}

			reader.close();
			expectedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIPAddressRegex() {
		Assertions.assertEquals("177.71.128.21", requestService.extractIPAddress("177.71.128.21 - - [10/Jul/2018:22:21:28 +0200]"));
		Assertions.assertEquals("50.112.00.11", requestService.extractIPAddress("50.112.00.11 - admin [11/Jul/2018:17:33:01 +0200]"));

	}

	@Test
	void testExtractURL() {
		String url = requestService.extractURL("177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574");
		Assertions.assertEquals("/intranet-analytics/", url);
	}

}
