package com.app.account.resource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.account.model.ErrorResponse;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

/**
 * Integration test of account application.
 * 
 * @author Anitha Manoharan
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class AccountControllerIntegrationTest {


    @RegisterExtension
    static WireMockExtension EXTERNAL_SERVICE = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig()
            .port(8004))
        .build(); 
	@Autowired
	private MockMvc mockMvc;

	@Mock
	AccountController accountController;

	@DisplayName("Account not exists exception")
	@Test
	@Order(1)
	public void testRetrieveAccountNotExists() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(CommonConstants.GET_ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Create Account Success")
	@Test
	@Order(2)
	public void testCreateAccount() throws Exception {
		String response = CommonUtil.readJSONFile(CommonConstants.BASE_FILE_PATH + "transactionresponse.json");

		String inputRequest = CommonUtil.readJSONFile(
				CommonConstants.BASE_FILE_PATH + CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		EXTERNAL_SERVICE.stubFor(post(urlEqualTo(CommonConstants.TRANSACTION_URL)).willReturn(aResponse()
				.withHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON).withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.post(CommonConstants.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Create Account input whose customer id is not available")
	@Test
	@Order(3)
	public void testCreateAccountException() throws Exception {
		String inputRequest = CommonUtil
				.readJSONFile(CommonConstants.BASE_FILE_PATH + CommonConstants.INVALID_CUSTOMER_INPUT_FILE);
		RequestBuilder request = MockMvcRequestBuilders.post(CommonConstants.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Invalid Create Account Input")
	@Test
	@Order(4)
	public void testInvalidInputException() throws Exception {
		String inputRequest = CommonUtil
				.readJSONFile(CommonConstants.BASE_FILE_PATH + CommonConstants.INVALID_INPUT_FILE);
		RequestBuilder request = MockMvcRequestBuilders.post(CommonConstants.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}

	@DisplayName("Account Exists Exception")
	@Test
	@Order(5)
	public void testAccountExistsException() throws Exception {
		String response = CommonUtil
				.readJSONFile(CommonConstants.BASE_FILE_PATH + CommonConstants.TRANSACTION_RESPONSE_FILE);

		String inputRequest = CommonUtil.readJSONFile(
				CommonConstants.BASE_FILE_PATH + CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		EXTERNAL_SERVICE.stubFor(post(urlEqualTo(CommonConstants.TRANSACTION_URL)).willReturn(aResponse()
				.withHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON).withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.post(CommonConstants.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}

	@DisplayName("Get Customer Account detail")
	@Test
	@Order(6)
	public void testRetrieveAccountDetail() throws Exception {
		String response = CommonUtil
				.readJSONFile(CommonConstants.BASE_FILE_PATH + CommonConstants.RETRIEVE_TRANSACTION_FILE);

		String inputRequest = CommonUtil.readJSONFile(
				CommonConstants.BASE_FILE_PATH + CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		EXTERNAL_SERVICE.stubFor(get(urlEqualTo(CommonConstants.GET_TRANSACTION_URL)).willReturn(aResponse()
				.withHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON).withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.get(CommonConstants.GET_ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Internal Server Error")
	@Test
	@Order(7)
	public void testErrorInput() throws Exception {
		String inputRequest = CommonUtil.readJSONFile(CommonConstants.BASE_FILE_PATH + CommonConstants.ERROR_INPUT);
		RequestBuilder request = MockMvcRequestBuilders.post(CommonConstants.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
		String response  = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		ErrorResponse errorResponse = mapper.readValue(response, ErrorResponse.class);
		assertNotNull(errorResponse);
	}

	@DisplayName("Error connecting Transaction service")
	@Test
	@Order(8)
	public void testErrorTransaction() throws Exception {
		String inputRequest = CommonUtil.readJSONFile(
				CommonConstants.BASE_FILE_PATH + CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		
		RequestBuilder request = MockMvcRequestBuilders.get(CommonConstants.GET_ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
		assertNotNull(result.getResponse());
	}

}
