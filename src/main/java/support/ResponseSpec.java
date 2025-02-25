package support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class ResponseSpec {

    public static ResponseSpecification OK = new ResponseSpecBuilder()
            .expectStatusCode(SC_OK)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification CREATED = new ResponseSpecBuilder()
            .expectStatusCode(SC_CREATED)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
}
