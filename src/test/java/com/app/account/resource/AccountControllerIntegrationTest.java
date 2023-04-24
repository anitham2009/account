package com.app.account.resource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.account.model.CreateAccountRequest;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@AutoConfigureWireMock(port = 8004)
@TestMethodOrder(OrderAnnotation.class)
public class AccountControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE = "createaccountrequestinitalcredit.json";
	public static final String INVALID_CUSTOMER_INPUT_FILE = "invalidcustomerrequest.json";
	public static final String INVALID_INPUT_FILE = "invalidinput.json";
	public static final String TRANSACTION_RESPONSE_FILE = "retrievetransaction.json";
	public static final String ERROR_INPUT = "errorinput.json";
	public static final String ERROR_TRANSACTION_RESPONSE_FILE = "errortransactonresponse.json";

	@Test
	@Order(1)
	public void testRetrieveAccountNotExists() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse());
	}

	
	
	@Test
	@Order(2)
	public void testCreateAccount() throws Exception {
		String response = readJSONFile(BASE_FILE_PATH + "transactionresponse.json");

		String inputRequest = readJSONFile(BASE_FILE_PATH + CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		stubFor(post(urlEqualTo("/transaction"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(result.getResponse());
	}

	@Test
	@Order(3)
	public void testCreateAccountException() throws Exception {
		String inputRequest = readJSONFile(BASE_FILE_PATH + INVALID_CUSTOMER_INPUT_FILE);
		RequestBuilder request = MockMvcRequestBuilders.post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse());
	}

	@Test
	@Order(4)
	public void testInvalidInputException() throws Exception {
		String inputRequest = readJSONFile(BASE_FILE_PATH + INVALID_INPUT_FILE);
		RequestBuilder request = MockMvcRequestBuilders.post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	@Order(5)
	public void testAccountExistsException() throws Exception {
		String response = readJSONFile(BASE_FILE_PATH + "transactionresponse.json");

		String inputRequest = readJSONFile(BASE_FILE_PATH + CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		stubFor(post(urlEqualTo("/transaction"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	@Order(6)
	public void testRetrieveAccountDetail() throws Exception {
		String response = readJSONFile(BASE_FILE_PATH + TRANSACTION_RESPONSE_FILE);

		String inputRequest = readJSONFile(BASE_FILE_PATH + CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		stubFor(get(urlEqualTo("/transaction/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.get("/account/1").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(result.getResponse());
	}

	@Test
	@Order(7)
	public void testErrorInput() throws Exception {
		String inputRequest = readJSONFile(BASE_FILE_PATH + ERROR_INPUT);
		RequestBuilder request = MockMvcRequestBuilders.post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
		assertNotNull(result.getResponse());
	}
	
	@Test
	@Order(8)
	public void testErrorTransaction() throws Exception {
		String response = readJSONFile(BASE_FILE_PATH + ERROR_TRANSACTION_RESPONSE_FILE);

		String inputRequest = readJSONFile(BASE_FILE_PATH + CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE);
		stubFor(get(urlEqualTo("/transaction/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response)));
		Thread.sleep(1000);
		RequestBuilder request = MockMvcRequestBuilders.get("/account/1").contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
		String responseMsg = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		//mapper.readValue(responseMsg, Erro)
		assertNotNull(result.getResponse());
	}
	/**
	 * Read json file.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	String readJSONFile(String filePath) throws IOException {
		File file = new File(filePath);
		String content = FileUtils.readFileToString(file, "UTF-8");
		return content;
	}

	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}
}
