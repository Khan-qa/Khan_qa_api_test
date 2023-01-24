package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specs {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification responseSpec201 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification responseSpec204 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification responseSpec400 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .build();
}
