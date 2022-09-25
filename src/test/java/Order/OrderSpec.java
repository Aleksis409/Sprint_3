package Order;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import Config.Config;
import java.io.IOException;

public class OrderSpec {
    private static final String ROOT = "/orders";
    private static String jsonString;

    //сереализация объекта Order в json
    @Step("Order object serialization in json")
    public static String serialisationOrder(Order order) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(order);
        return jsonString;
    }

    //создание заказа
    @Step("Get of the response when creating of order - /api/v1/order")
    public static ValidatableResponse getResponseCreateOrder(String jsonString) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(jsonString)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(201);
    }

    //получение списка заказов
    @Step("Getting a list of orders - /api/v1/order")
    public static ValidatableResponse getResponseListOfOrders() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .when()
                .get(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
