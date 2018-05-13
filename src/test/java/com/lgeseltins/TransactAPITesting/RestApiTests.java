package com.lgeseltins.TransactAPITesting;

import com.lgeseltins.backend.model.AddressDto;
import com.lgeseltins.backend.model.AuthorizationDto;
import com.lgeseltins.backend.model.RegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.FileAssert.fail;

public class RestApiTests {
    private static final Logger LOGGER = LogManager.getLogger(RestApiTests.class);
    private static Random rand = new Random();

    private RestTemplate restTemplate;

    // Specified URL
    private static final String APPLICATION_PORT = "8888";
    private static final String RESOURCE_URL = "http://207.154.242.0:" + APPLICATION_PORT;

    //  Completed registration form fields
    private static final String EMAIL = "name" + rand.nextInt(50) + 1 + "@email.com";
    private static final String PHONE = "+3712877840";
    private static String PWD = "111bbb";
    private static final String BIRTHDATE_OVER_21 = "1991-08-01T00:00:00.000Z";
    private static final String DESCRIPTION = "Hello world";
    private static final String COUNTRY = "Latvia";
    private static final String CITY = "Riga";
    private static final String STATE = "LV";
    private static final String ZIP = "LV-1019";
    private static final String STREET = "Slavu 18";

    // Incorrect registration form fields
    private static final String EMAIL_NULL = null; // * obligate field
    private static final String EMAIL_INCORRECT = "not@registered.com";
    private static final String PWD_INCORRECT = "@incorrect";
    private static final String BIRTHDATE_UNDER_21 = "2012-08-01T00:00:00.000Z";
    private static final String BIRTHDATE_NULL = null; // * obligate field
    private static final String DESCRIPTION_NULL = null; // * obligate field
    private static final String COUNTRY_NULL = null;
    private static final String CITY_NULL = null;
    private static final String ZIP_NULL = null;
    private static final String STREET_NULL = null;

    @BeforeClass
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test(priority = 1)
    public void shouldHandlePostCorrectRegistrationData() {
        LOGGER.info("Sending registration data....");

        // Since server does not return error on incorrect password format
        // We might want to check password format implicitly
        if (PWD.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
            PWD = "correctPassword123";
        }

        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET));
        LOGGER.info(registrationData);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        LOGGER.info("Response for POST registration data: " + responseEntity.getBody());
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutAllObligatedFields() {
        LOGGER.info("Sending incorrect registration data....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL_NULL, PHONE, PWD, BIRTHDATE_NULL, DESCRIPTION_NULL,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutEmailObligatedField() {
        LOGGER.info("Sending incorrect registration data (without email)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL_NULL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithIncorrectBirthDateUnder21() {
        LOGGER.info("Sending incorrect registration data (under 21 age)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_UNDER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithIncorrectEmail() {
        LOGGER.info("Sending incorrect registration data (incorrect email address)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL_NULL, PHONE, PWD, BIRTHDATE_UNDER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutCountryField() {
        LOGGER.info("Sending incorrect registration data (without country filed)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY_NULL, CITY, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutCityField() {
        LOGGER.info("Sending incorrect registration data (without city filed)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY_NULL, STATE, ZIP, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutZipField() {
        LOGGER.info("Sending incorrect registration data (without zip filed)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP_NULL, STREET));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void shouldHandlePostRegistrationDataWithoutStreetField() {
        LOGGER.info("Sending incorrect registration data (without street filed)....");
        RegistrationDto registrationData = new RegistrationDto(EMAIL, PHONE, PWD, BIRTHDATE_OVER_21, DESCRIPTION,
            new AddressDto(COUNTRY, CITY, STATE, ZIP, STREET_NULL));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<RegistrationDto> entity = new HttpEntity<>(registrationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/register", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 400 Bad Request");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }


    @Test(dependsOnMethods = {"shouldHandlePostCorrectRegistrationData"})
    public void shouldHandlePostAuthorizationData() {
        LOGGER.info("Sending authorization data....");
        AuthorizationDto authorizationData = new AuthorizationDto(EMAIL, PWD);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<AuthorizationDto> entity = new HttpEntity<>(authorizationData, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(RESOURCE_URL + "v1/authorize", HttpMethod.POST, entity, String.class);
        LOGGER.info("Response for POST registration data: " + responseEntity.getBody());
    }

    @Test
    public void shouldHandlePostIncorrectLoginAuthorizationData() {
        LOGGER.info("Sending authorization data....");
        AuthorizationDto authorizationData = new AuthorizationDto(EMAIL_INCORRECT, PWD);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<AuthorizationDto> entity = new HttpEntity<>(authorizationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/authorize", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 401 Unauthorized");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    public void shouldHandlePostIncorrectPasswordAuthorizationData() {
        LOGGER.info("Sending authorization data....");
        AuthorizationDto authorizationData = new AuthorizationDto(EMAIL, PWD_INCORRECT);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<AuthorizationDto> entity = new HttpEntity<>(authorizationData, headers);

        try {
            restTemplate.exchange(RESOURCE_URL + "v1/authorize", HttpMethod.POST, entity, String.class);
            fail("Expecting HttpClientErrorException: 401 Unauthorized");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.UNAUTHORIZED);
        }
    }
}
