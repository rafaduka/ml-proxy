package com.mercadolivre.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitConstants.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProxyApplicationTests {

	private static final String SITES = "/sites";
	private static final String CURRENCIES = "/currencies";
	private static final String CATEGORIES = "/categories/MLA5725";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void whenSendRequestToSitesResource_thenOK() {
		ResponseEntity<String> response = restTemplate.getForEntity(SITES, String.class);
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void whenRequestNotExceedingCapacity_thenReturnOkResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity(CATEGORIES, String.class);
		assertEquals(OK, response.getStatusCode());

		HttpHeaders headers = response.getHeaders();
		String key = "proxy_categories_127.0.0.1";

		assertEquals("5", headers.getFirst(HEADER_LIMIT + key));
		assertEquals("4", headers.getFirst(HEADER_REMAINING + key));
	}

	@Test
	public void whenRequestExceedingCapacity_thenReturnTooManyRequestsResponse() throws InterruptedException {
		ResponseEntity<String> response = this.restTemplate.getForEntity(CURRENCIES, String.class);
		assertEquals(OK, response.getStatusCode());

		for (int i = 0; i < 2; i++) {
			response = this.restTemplate.getForEntity(CURRENCIES, String.class);
		}

		assertEquals(TOO_MANY_REQUESTS, response.getStatusCode());

		HttpHeaders headers = response.getHeaders();
		String key = "proxy_currencies_127.0.0.1";

		assertEquals("1", headers.getFirst(HEADER_LIMIT + key));
		assertEquals("0", headers.getFirst(HEADER_REMAINING + key));

		TimeUnit.SECONDS.sleep(2);

		response = this.restTemplate.getForEntity(CURRENCIES, String.class);
		assertEquals(OK, response.getStatusCode());
	}
}
