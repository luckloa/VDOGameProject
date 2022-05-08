package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class TC_VDOGameAPI {
	
	public int port=8080;
	public String URI ="http://localhot:"+port+"/app";
	
	@Test(priority=1)
	public void test_getAllvdoGames() {
		
		given()
		
		.when()
			.get(URI = "/videogames")
			
		.then()
			.statusCode(200);
			
	}
	@Test(priority=2)
	public void test_addNewvdoGames() {
		
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2019-09-20T08:55:58.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res =
		
			given()
				.contentType("application/json")
				.body(data)
				
			.when()
				.post(URI + "/videogames")
			
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
		
	}
	@Test(priority=3)
	public void test_getvdoGames() {
		given()
		
		.when()
			.get(URI + "/videogames" + "/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.name", equalTo("Spider-Man"));
			
	}
	@Test(priority=4)
	public void test_getVideoGame() {
		
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Paceman");
		data.put("releaseDate", "2019-09-20T08:57:58.510Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put(URI + "/videogames" + "/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.name", equalTo("Paceman"));	
	}
	@Test(priority=5)
	public void test_DeletevdoGame() {
		
		Response res =
		given()
		.when()
			.delete(URI + "/videogames" + "/100")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString = res.asString();
		
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
	}
	
}
