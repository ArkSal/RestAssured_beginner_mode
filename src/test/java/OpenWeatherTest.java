import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OpenWeatherTest extends TestBase {

    @Test(description = "Simple get request")
    void getRequest() {
        given()
                .get()
                .then();
    }
}
