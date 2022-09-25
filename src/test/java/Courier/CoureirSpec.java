package Courier;

import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import Config.Config;

public class CoureirSpec {
    private static final String ROOT = "/courier";
    private static final String COURIER = "/courier/{courierId}";
    private static final String LOGIN = ROOT + "/login";

    private static String jsonString;

    static ObjectMapper mapper = new ObjectMapper();

    //создание учетной записи курьера
    @Step("Get of the response when creating a courier of /api/v1/courier")
    public static ValidatableResponse getResponseCreateCourier(Courier courier, int statusCode) throws JsonProcessingException {
        jsonString = mapper.writeValueAsString(courier);
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(jsonString)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(statusCode);
    }

    //авторизация учетной записи курьера
    @Step("Get of the response when the courier is authorized of /api/v1/courier/login")
    public static ValidatableResponse getResponseCourierAuthorization (Courier courier, int statusCode) throws JsonProcessingException {
        jsonString = mapper.writeValueAsString(courier);
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(jsonString)
                .when()
                .post(LOGIN)
                .then().log().all()
                .statusCode(statusCode);
    }

    //удаление учетной записи курьера
    @Step("Deleted courier account of /api/v1/courier/:id")
    public static ValidatableResponse deleteCourier(int courierId) {
        jsonString = "{\"id\": \"" + courierId + "\"}";
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(jsonString)
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}







