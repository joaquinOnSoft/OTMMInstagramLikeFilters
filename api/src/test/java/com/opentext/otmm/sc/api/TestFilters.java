package com.opentext.otmm.sc.api;

import org.junit.Test;
import io.restassured.RestAssured;

public class TestFilters {
	
	//@Test
	public void getFilters() {
		RestAssured
        	.given()        		
        	.when()
        		.get("/v6/filters")
        	.then()
        		.log().all();
        		//.and().assertThat().body("lotto.lottoId", hasItems("", ""));
	}
}
