import dto.UsersDto;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import support.ListUsersDataProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static support.ResponseSpec.OK;

public class UserTest {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI ="https://reqres.in/api";
    }

    @Test(dataProvider = "ListUsersDataProvider", dataProviderClass = ListUsersDataProvider.class)
    public void getUsers(int pageId, int userId, String email, String firstName, String lastName) {
        UsersDto response = given()
                .header("Content-Type", "application/json")
                .queryParam("page", pageId)
                .when()
                .get("/users")
                .then()
                .assertThat()
                .spec(OK)
                .extract().response().as(UsersDto.class);

        assertThat(response, allOf(
                hasProperty("page", equalTo(pageId)),
                hasProperty("per_page", equalTo(6)),
                hasProperty("total", equalTo(12)),
                hasProperty("total_pages", equalTo(2)),
                hasProperty("data", hasItem(allOf(
                        hasProperty("id", equalTo(userId)),
                        hasProperty("email", equalTo(email)),
                        hasProperty("first_name", equalTo(firstName)),
                        hasProperty("last_name", equalTo(lastName)),
                        hasProperty("avatar", notNullValue())
                ))
        )));

        assertThat(response, allOf(
                hasProperty("support", allOf(
                        hasProperty("url", notNullValue()),
                        hasProperty("text", notNullValue())
                ))
        ));
    }

    @Test
    public static boolean checkPalindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    @Test
    public static int maxNumber(int a, int b, int c) {
        return Math.max(a, Math.max(b,c));
    }

    @Test
    public static boolean evenNumber(int number) {
        return number % 2 == 0;
    }
}
