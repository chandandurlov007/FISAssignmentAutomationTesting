import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ApiTesting {
	
	@Test
	public void getVerifyResponse() {
		Response response = given()
			.when()
				.get("https://api.coindesk.com/v1/bpi/currentprice.json");
			
		response.then()
		.statusCode(200);
		
		String ExpDescription = "British Pound Sterling";
		JsonPath jp = new JsonPath(response.asString());
		String ActDescription = jp.getString("bpi.GBP.description");
		Assert.assertEquals(ExpDescription, ActDescription);
		
		String allValues = jp.getString("bpi");
		Assert.assertTrue(allValues.contains("USD"));
		Assert.assertTrue(allValues.contains("GBP"));
		Assert.assertTrue(allValues.contains("EUR"));
	}
}
