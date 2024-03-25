package com.group.mantel.assignment.networkmonitor;

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
		request.setIpAddress("192.168.1.1");
		Request savedRequest = requestRepository.save(request);
		Assertions.assertEquals(request.getIpAddress(), savedRequest.getIpAddress());
		Assertions.assertEquals(request.getUrl(), savedRequest.getUrl());
	}

	@Test
	void testFindDistinctUrl() {
		Request request1 = new Request();
		request1.setUrl("/");
		request1.setIpAddress("192.168.1.1");
		Request savedRequest1 = requestRepository.save(request1);

		Request request2 = new Request();
		request2.setUrl("/");
		request2.setIpAddress("192.168.1.2");
		Request savedRequest2 = requestRepository.save(request2);

		Request request3 = new Request();
		request3.setUrl("/home");
		request3.setIpAddress("192.168.1.3");
		Request savedRequest3 = requestRepository.save(request3);

		List<String> distinctUrls = requestRepository.findDistinctUrl();
		Assertions.assertEquals(2, distinctUrls.size());

	}


}
