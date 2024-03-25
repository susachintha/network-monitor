package com.group.mantel.assignment.networkmonitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
