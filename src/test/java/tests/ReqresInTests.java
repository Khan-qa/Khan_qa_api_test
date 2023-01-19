package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.requestSpec;
import static specs.Specs.responseSpec;


public class ReqresInTests {

    @Test
    @DisplayName("Проверка создания пользователя")
    void createTest() {
        CreateTestModel bodyModel = new CreateTestModel();
        bodyModel.setName("morpheus");
        bodyModel.setJob("leader");

        CreateTestModel responseModel = given(requestSpec)
                .body(bodyModel)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .spec(responseSpec)
                .extract().as(CreateTestModel.class);

        assertThat(responseModel.getName()).isEqualTo("morpheus");
        assertThat(responseModel.getJob()).isEqualTo("leader");
    }

    @Test
    @DisplayName("Проверка редактирования пользователя")
    void updateTest() {
        UpdateTestModel bodyModel = new UpdateTestModel();
        bodyModel.setName("morpheus");
        bodyModel.setJob("zion resident");

        UpdateTestModel responseModel = given(requestSpec)
                .body(bodyModel)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .spec(responseSpec)
                .extract().as(UpdateTestModel.class);

        assertThat(responseModel.getJob()).isEqualTo("zion resident");
    }

    @Test
    @DisplayName("Проверка пользователя в списке")
    void checkListUsers() {
        ListUsersModel responseModel = given(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .spec(responseSpec)
                .extract().as(ListUsersModel.class);

        assertThat(responseModel.getData().get(3).getEmail()).isEqualTo("byron.fields@reqres.in");
        assertThat(responseModel.getData().get(3).getFirstName()).isEqualTo("Byron");
        assertThat(responseModel.getData().get(3).getLastName()).isEqualTo("Fields");
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void deletedTest() {
        given(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .spec(responseSpec);
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации")
    void loginUnsuccessful() {
        LoginUnsuccessfulModel bodyModel = new LoginUnsuccessfulModel();
        bodyModel.setEmail("peter@klaven");

        LoginUnsuccessfulModel responseModel = given(requestSpec)
                .body(bodyModel)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .spec(responseSpec)
                .extract().as(LoginUnsuccessfulModel.class);

        assertThat(responseModel.getError()).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Проверка неуспешной регистрации")
    void registerUnsuccessful() {
        RegisterUnsuccessfulModel bodyModel = new RegisterUnsuccessfulModel();
        bodyModel.setEmail("sydney@fife");

        RegisterUnsuccessfulModel responseModel = given(requestSpec)
                .body(bodyModel)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .spec(responseSpec)
                .extract().as(RegisterUnsuccessfulModel.class);

        assertEquals("Missing password", responseModel.getError());
    }
}
