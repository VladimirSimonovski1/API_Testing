import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import support.PostsDataProvider;

import static dto.PostDto.postBody;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Reporter.log;
import static support.ResponseSpec.CREATED;
import static support.ResponseSpec.OK;

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
                .body(postBody)
                .when()
                .post("/posts")
                .then()
                .assertThat()
                .spec(CREATED)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.jsonPath().getString("title"), postBody.getTitle());
        softAssert.assertEquals(response.jsonPath().getString("body"), postBody.getBody());
        softAssert.assertEquals(response.jsonPath().getInt("userId"), postBody.getUserId());
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
                .spec(OK)
                .and()
                .body("$", anEmptyMap())
                .extract().response().prettyPrint();
    }

    @Test(dataProvider = "DataProviderPosts", dataProviderClass = PostsDataProvider.class)
    public void createPosts(String title, String body, int userId) {
        postBody.setTitle(title);
        postBody.setBody(body);
        postBody.setUserId(userId);
        given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(postBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(SC_CREATED)
                .and()
                .body("title", equalTo(postBody.getTitle()))
                .body("body", equalTo(postBody.getBody()))
                .body("userId", equalTo(postBody.getUserId()))
                .extract().response().prettyPrint();
    }
}
