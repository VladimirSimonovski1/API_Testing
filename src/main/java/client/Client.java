package client;

import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class Client {

    public static <T> T getRequest(String param, int value, String url, ResponseSpecification spec, Type type) {
        return given()
                .header("Content-Type", ContentType.JSON)
                .pathParam(param, value)
                .when()
                .get(url + "/{"+ param + "}")
                .then()
                .assertThat()
                .spec(spec)
                .log().all()
                .extract().response().as(type);
    }

}
