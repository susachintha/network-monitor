package com.group.mantel.assignment.networkmonitor;

import com.group.mantel.assignment.networkmonitor.model.AddressCount;
import com.group.mantel.assignment.networkmonitor.model.UrlCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RequestRepositoryTests {

	@Autowired
	RequestRepository requestRepository;

	@Test
	void testSaveRequest() {
		Request request = new Request();
		request.setUrl("/");
		request.setAddress("192.168.1.1");
		Request savedRequest = requestRepository.save(request);
		Assertions.assertEquals(request.getAddress(), savedRequest.getAddress());
		Assertions.assertEquals(request.getUrl(), savedRequest.getUrl());
	}

	@Test
	void testFindDistinctUrl() {
		Request request1 = new Request();
		request1.setUrl("/");
		request1.setAddress("192.168.1.1");
		Request savedRequest1 = requestRepository.save(request1);

		Request request2 = new Request();
		request2.setUrl("/");
		request2.setAddress("192.168.1.2");
		Request savedRequest2 = requestRepository.save(request2);

		Request request3 = new Request();
		request3.setUrl("/home");
		request3.setAddress("192.168.1.3");
		Request savedRequest3 = requestRepository.save(request3);

		List<String> distinctUrls = requestRepository.findDistinctUrl();
		Assertions.assertEquals(2, distinctUrls.size());

	}

	@Test
	void testFindTopUrls() {

		String topUrl1 = "/";
		int url1Count = 4;

		for (int i = 0; i < url1Count; i++) {
			Request request = new Request();
			request.setUrl(topUrl1);
			request.setAddress("192.168.1.1");
			requestRepository.save(request);
		}

		Request randomRequest1 = new Request();
		randomRequest1.setUrl("/hosting/");
		randomRequest1.setAddress("192.168.1.5");
		requestRepository.save(randomRequest1);

		Request randomRequest2 = new Request();
		randomRequest2.setUrl("/location");
		randomRequest2.setAddress("192.168.1.5");
		requestRepository.save(randomRequest2);

		String topUrl2 = "/home";
		int url2Count = 3;
		for (int i = 0; i < url2Count; i++) {
			Request request = new Request();
			request.setUrl(topUrl2);
			request.setAddress("192.168.0.30"); //dynamically generating ip address for verification
			requestRepository.save(request);
		}

		String topUrl3 = "/contact";
		int url3Count = 2;
		for (int i = 0; i < url3Count; i++) {
			Request request = new Request();
			request.setUrl(topUrl3);
			request.setAddress("192.168.1.3");
			requestRepository.save(request);
		}

		List<UrlCount> topUrls = requestRepository.findMostVisitedUrls(3L);

		Assertions.assertEquals(topUrl1, topUrls.get(0).getUrl());
		Assertions.assertEquals(url1Count, topUrls.get(0).getCount());
		Assertions.assertEquals(topUrl2, topUrls.get(1).getUrl());
		Assertions.assertEquals(url2Count, topUrls.get(1).getCount());
		Assertions.assertEquals(topUrl3, topUrls.get(2).getUrl());
		Assertions.assertEquals(url3Count, topUrls.get(2).getCount());



	}

	@Test
	void testMostUsedIPAddresses() {
		String url1 = "/";
		int url1Count = 5;
		for (int i = 0; i < url1Count; i++) {
			Request request = new Request();
			request.setUrl(url1);
			request.setAddress("192.168.1.1");
			requestRepository.save(request);
		}

		String url2 = "/home";
		int url2Count = 4;
		for (int i = 0; i < url2Count; i++) {
			Request request = new Request();
			request.setUrl(url2);
			request.setAddress("192.168.1.2");
			requestRepository.save(request);
		}

		Request randomRequest = new Request();
		randomRequest.setUrl("/about");
		randomRequest.setAddress("192.168.1.3");
		requestRepository.save(randomRequest);

		String url4 = "/contact";
		int url4Count = 3;
		for (int i = 0; i < url4Count; i++) {
			Request request = new Request();
			request.setUrl(url4);
			request.setAddress("192.168.1.4");
			requestRepository.save(request);
		}

		List<AddressCount> mostUsedAddresses = requestRepository.findMostActiveIPAddresses(3L);
		Assertions.assertEquals("192.168.1.1", mostUsedAddresses.get(0).getAddress());
		Assertions.assertEquals(url1Count, mostUsedAddresses.get(0).getCount());

		Assertions.assertEquals("192.168.1.2", mostUsedAddresses.get(1).getAddress());
		Assertions.assertEquals(url2Count, mostUsedAddresses.get(1).getCount());

		Assertions.assertEquals("192.168.1.4", mostUsedAddresses.get(2).getAddress());
		Assertions.assertEquals(url4Count, mostUsedAddresses.get(2).getCount());

	}


}
