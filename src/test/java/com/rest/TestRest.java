package com.rest;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.core.StringContains;

public class TestRest {
//	@Test
//	public void toValidateResponseCode() {
//		given()
//				.baseUri("https://restful-booker.herokuapp.com/")
//				.header("x-api-key","2f1eefa3fdcb13c").
//		when()
//				.get("booking").////booking/11
//		then()	
//				.log().all()
//				.assertThat()
//				.statusCode(200);
// 	}
	@Test
	public void toValidateResponseBody() {
		given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c").when()
				.get("booking/11").then()
				// .log().all()
				.assertThat().statusCode(200).body("bookingdates.checkin", equalTo("2018-01-01"));
	}

	@Test
	public void toExctract_Response() {
		Response res = given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c")
				.when().get("booking/11").then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println("Response:-" + res.asString());
	}

	@Test
	public void to_Exctract_Single_Value_Form_Response_method_1() {
		Response res = given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c")
				.when().get("booking/11").then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println("Checkout date:-" + res.path("bookingdates"));
	}

	@Test
	public void to_Exctract_Single_Value_Form_Response_method_2() {
		Response res = given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c")
				.when().get("booking/11").then().log().all().assertThat().statusCode(200).extract().response();
		JsonPath jp = new JsonPath(res.asString());
		System.out.println("Checkout date:-" + jp.getString("additionalneeds"));
	}

	@Test
	public void to_Exctract_Single_Value_Form_Response_method_3() {
		String res = given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c")
				.when().get("booking/11").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println("lastname:-" + JsonPath.from(res).getString("lastname"));
	}

	@Test
	public void to_Exctract_Single_Value_Form_Response_method_4() {
		boolean depositpaid = given().baseUri("https://restful-booker.herokuapp.com/")
				.header("x-api-key", "2f1eefa3fdcb13c").when().get("booking/11").then().log().all().assertThat()
				.statusCode(200).extract().response().path("depositpaid");

		System.out.println("depositpaid:-" + depositpaid);
	}

	@Test
	public void to_Log_request_Response() {
		given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c").log().headers()
		.when()
				.get("booking/11").then()
				.log().headers();

	
	}
	@Test
	public void to_Log_if_error() {
		given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c").log().all()
		.when()
				.get("booking/11")
				.then()
				.log().ifStatusCodeIsEqualTo(200);

	
	}
	@Test
	public void to_blacklist_multiple_header() {
		Set<String>headers=new HashSet<String>();
		headers.add("x-api-key");
		headers.add("Accept");
		given().baseUri("https://restful-booker.herokuapp.com/").header("x-api-key", "2f1eefa3fdcb13c")
		.config(config.logConfig
				(LogConfig.logConfig().blacklistHeaders(headers))).log().all()
		.when()
				.get("booking/11")
				.then()
				.log().ifStatusCodeIsEqualTo(200);

	
	}
	@Test
	public void to_multiple_header_By_HeadersObject() {
		Header header=new Header("Content-Type", "application/json");
		Header authorization=new Header("Authorization", "Bearer your_token_here");
		Header header1 =new Header("Custom-Header1", "CustomValue1");
		Header header2=new Header("Custom-Header2", "CustomValue2");
		
		Headers headers=new Headers(header,authorization,header1,header2);
		given().baseUri("https://jsonplaceholder.typicode.com/posts")
				.headers(headers).log().all()
		.when()
				.get()
		.then()
				.log().all().assertThat().statusCode(200);
	}
}
