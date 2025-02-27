import dto.ProductsDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static dto.AuthDto.authRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static support.ResponseSpec.OKAY;

public class ProductsTest {

    String token;

    @BeforeClass
    public void login() {
        RestAssured.baseURI = "https://dummyjson.com";
        token = given()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(authRequest)
                .when()
                .post("auth/login")
                .then()
                .assertThat()
                .spec(OKAY)
                .and()
                .body("id", equalTo(1))
                .body("username", equalTo("emilys"))
                .body("email", equalTo("emily.johnson@x.dummyjson.com"))
                .extract().path("accessToken");
    }

    @AfterClass
    public void logout() {
        given()
                .header("Content-Type", ContentType.JSON)
                .when()
                .post("/auth/logout")
                .then()
                .spec(OKAY)
                .assertThat()
                .body("message", equalTo("Logged out successfully"));
    }

    @Test
    public void getProducts() {
        ProductsDto response = given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/products")
                .then()
                .spec(OKAY)
                .extract().response().as(ProductsDto.class);

        assertThat(response.getProducts(), hasItems(
                allOf(
                hasProperty("id", equalTo(7)),
                hasProperty("title", equalTo("Chanel Coco Noir Eau De")),
                hasProperty("price",closeTo(129.99, 0.01)),
                hasProperty("tags", hasItems("fragrances", "perfumes")),
                hasProperty("dimensions", allOf(
                        hasProperty("width", closeTo(21.27, 0.01)),
                        hasProperty("height", closeTo(28, 0.00)),
                        hasProperty("depth", closeTo(11.89, 0.01))
                )),
                hasProperty("reviews", hasItem(allOf(
                        hasProperty("rating", equalTo(4)),
                        hasProperty("comment", equalTo("Excellent quality!")),
                        hasProperty("date", equalTo("2024-05-23T08:56:21.619Z")),
                        hasProperty("reviewerName", equalTo("Lucas Allen")),
                        hasProperty("reviewerEmail", equalTo("lucas.allen@x.dummyjson.com"))
                )))
        ),
                allOf(
                        hasProperty("id", equalTo(23)),
                        hasProperty("title", equalTo("Eggs")),
                        hasProperty("price",closeTo(2.99, 0.01)),
                        hasProperty("tags", hasItems("dairy")),
                        hasProperty("dimensions", allOf(
                                hasProperty("width", closeTo(12.3, 0.01)),
                                hasProperty("height", closeTo(10.99, 0.00)),
                                hasProperty("depth", closeTo(15.53, 0.01))
                        )),
                        hasProperty("reviews", hasItem(allOf(
                                hasProperty("rating", equalTo(4)),
                                hasProperty("comment", equalTo("Very happy with my purchase!")),
                                hasProperty("date", equalTo("2024-05-23T08:56:21.620Z")),
                                hasProperty("reviewerName", equalTo("Cameron Perez")),
                                hasProperty("reviewerEmail", equalTo("cameron.perez@x.dummyjson.com"))
                        )))
                )
        ));
    }
}
