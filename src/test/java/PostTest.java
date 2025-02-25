import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import support.PostsDataProvider;

import static dto.PostDto.postRequest;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Reporter.log;
import static support.ResponseSpec.CREATION;
import static support.ResponseSpec.OKAY;

public class PostTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        log(format("Getting ready to test the %s API to test", RestAssured.baseURI), true);
    }

    @AfterClass
    public void tearDown() {
        log("All tests in this class are executed!", true);
    }

    @Test
    public void getPost() {
        given()
                .header("Content-Type", ContentType.JSON)
                .pathParam("postId", 2)
                .when()
                .get("/posts/{postId}")
                .then()
                .statusCode(SC_OK)
                .assertThat()
                .body("userId", equalTo(1))
                .body("id", equalTo(2))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .extract().response().prettyPrint();
    }

    @Test
    public void createPost() {
        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(postRequest)
                .when()
                .post("/posts")
                .then()
                .assertThat()
                .spec(CREATION)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.jsonPath().getString("title"), postRequest.getTitle());
        softAssert.assertEquals(response.jsonPath().getString("body"), postRequest.getBody());
        softAssert.assertEquals(response.jsonPath().getInt("userId"), postRequest.getUserId());
        softAssert.assertAll();
    }

    @Test
    public void deletePost() {
        given()
                .header("Content-Type", ContentType.JSON)
                .when()
                .delete("/posts/3")
                .then()
                .assertThat()
                .spec(OKAY)
                .and()
                .body("$", anEmptyMap())
                .extract().response().prettyPrint();
    }

    @Test(dataProvider = "DataProviderPosts", dataProviderClass = PostsDataProvider.class)
    public void createPosts(String title, String body, int userId) {
        postRequest.setTitle(title);
        postRequest.setBody(body);
        postRequest.setUserId(userId);
        given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(postRequest)
                .when()
                .post("/posts")
                .then()
                .statusCode(SC_CREATED)
                .and()
                .body("title", equalTo(postRequest.getTitle()))
                .body("body", equalTo(postRequest.getBody()))
                .body("userId", equalTo(postRequest.getUserId()))
                .extract().response().prettyPrint();
    }
}
