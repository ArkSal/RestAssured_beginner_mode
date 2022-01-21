import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class TestBase {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    final String URI = "https://samples.openweathermap.org/data/2.5/weather";

    @BeforeClass
    void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(URI)
                .addHeader("Type", "GetRequestTest")
                .addFormParam("q", "London,uk")
                .addFormParam("appid", "b1b15e88fa797225412429c1c50c122a1")
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(2000L))
                .expectHeader("Connection", "keep-alive")
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody("name", is("London"))
                .expectBody("sys.country", is("GB"))
                .log(LogDetail.ALL)
                .build();

        requestSpecification = requestSpec;
        responseSpecification = responseSpec;
    }
}
