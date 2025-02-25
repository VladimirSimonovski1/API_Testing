import dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import support.ListUserDataProvider;

import static dto.LoginDto.loginPayload;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static support.ResponseSpec.OK;

public class LoginTest {

    private String token = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        token = given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(loginPayload)
                .when()
                .post("/login")
                .then()
                .assertThat()
                .spec(OK)
                .and()
                .body("token", notNullValue())
                .extract().response().asString();
    }

    @AfterClass
    public void tearDown() {
        given()
                .when()
                .header("Content-Type", ContentType.JSON)
                .post("/logout")
                .then()
                .assertThat()
                .spec(OK)
                .and()
                .body("$", anEmptyMap());
    }

    @Test(dataProvider = "ListUserDataProvider", dataProviderClass = ListUserDataProvider.class)
    public void listUser(int userId, String email, String firstName, String lastName) {
        UserDto response = given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .header("Authorization", token)
                .pathParam("userId", userId)
                .when()
                .get("/users/{userId}")
                .then()
                .assertThat()
                .spec(OK)
                .and()
                .extract().response().as(UserDto.class);

assertThat(response, allOf(
        hasProperty("data", allOf(
                hasProperty("id", equalTo(userId)),
                hasProperty("email", equalTo(email)),
                hasProperty("first_name", equalTo(firstName)),
                hasProperty("last_name", equalTo(lastName)),
                hasProperty("avatar", notNullValue())
        )),
        hasProperty("support", allOf(
                hasProperty("url",  notNullValue()),
                hasProperty("text", notNullValue())
        ))
));
        assertEquals(response.getData().getId(), userId);
        assertEquals(response.getData().getEmail(), email);
        assertEquals(response.getData().getFirst_name(), firstName);
        assertEquals(response.getData().getLast_name(), lastName);
        assertNotNull(response.getData().getAvatar());
        assertNotNull(response.getSupport().getText());
        assertNotNull(response.getSupport().getUrl());
    }
}
