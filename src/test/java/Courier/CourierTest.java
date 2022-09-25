package Courier;

import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest extends CoureirSpec{
    private int courierId;
    private boolean createCourierIsOk = false; //признак создания учетной записи курьера

    Courier courier;

    @After
    public void tearDown() throws Exception {
        if (createCourierIsOk) {
            Courier сreatedСourier = new Courier(courier.getLogin(), courier.getPassword());
            //получение id созданной учетной записи курьера
            courierId = CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 200)
                    .extract()
                    .path("id");
            //удаление учетной записи курьера
            deleteCourier(courierId);
        }
    }

    //Тест успешного создания учетной записи курьера
    @Test
    @DisplayName("Ok create courier of /api/v1/courier")
    public void createCourierOkTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier,201)
                    .extract()
                    .path("ok");
        assertTrue(createCourierIsOk);
    }

    //Тест неуспешного создания учетной записи курьера без логина
    @Test
    @DisplayName("Fail create courier without login of /api/v1/courier")
    public void createCourierWithOutLoginFailTest() throws JsonProcessingException {
        courier = Courier.getWithoutLogin();
        CoureirSpec.getResponseCreateCourier(courier, 400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Тест неуспешного создания учетной записи курьера без пароля
    @Test
    @DisplayName("Fail create courier without password of /api/v1/courier")
    public void createCourierWithOutPasswordFailTest() throws JsonProcessingException {
        courier = Courier.getWithoutPassword();
        CoureirSpec.getResponseCreateCourier(courier, 400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Тест неуспешного создания учетной записи курьера с повторяющимся логином.
    //Тест падает, т.к. полученное сообщение не соответствует документации
    @Test
    @DisplayName("Fail create courier recurring login of /api/v1/courier")
    public void createCourierRecurringLoginFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk =  CoureirSpec.getResponseCreateCourier(courier, 201)
                .extract()
                .path("ok");
        CoureirSpec.getResponseCreateCourier(courier, 409)
                .body("message",equalTo("Этот логин уже используется"));
    }

    //Тест успешной авторизации курьера.
    @Test
    @DisplayName("Ok authorization courier of /api/v1/courier/login")
    public void authorizationCourierOkTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier, 201)
                .extract()
                .path("ok");
        Courier сreatedСourier = new Courier(courier.getLogin(), courier.getPassword());
        courierId = CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 200)
                .extract()
                .path("id");
        assertNotEquals(0, courierId);
    }

    //Тест неуспешной авторизации курьера без логина.
    @Test
    @DisplayName("Fail authorization courier without login of /api/v1/courier/login")
    public void authorizationCourierWithOutLoginFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier, 201)
                .extract()
                .path("ok");
        Courier сreatedСourier = new Courier("", courier.getPassword());
        CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 400)
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    //Тест неуспешной авторизации курьера без пароля.
    @Test
    @DisplayName("Fail authorization courier without password of /api/v1/courier/login")
    public void authorizationCourierWithOutPasswordFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier, 201)
                .extract()
                .path("ok");
        Courier сreatedСourier = new Courier(courier.getLogin(), "");
        CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 400)
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    //Тест неуспешной авторизации курьера c несуществующим логином.
    @Test
    @DisplayName("Fail authorization courier with wrong login of /api/v1/courier/login")
    public void authorizationCourierWithWrongLoginFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier,201)
                .extract()
                .path("ok");
        Courier сreatedСourier = new Courier((courier.getLogin() + "1"), courier.getPassword());
        CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 404)
                .body("message",equalTo("Учетная запись не найдена"));
    }

    //Тест неуспешной авторизации курьера c несуществующим паролем.
    @Test
    @DisplayName("Fail authorization courier with wrong password of /api/v1/courier/login")
    public void authorizationCourierWithWrongPasswordFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        createCourierIsOk = CoureirSpec.getResponseCreateCourier(courier, 201)
                .extract()
                .path("ok");
        Courier сreatedСourier = new Courier(courier.getLogin(), (courier.getPassword() + "1"));
        CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 404)
                .body("message",equalTo("Учетная запись не найдена"));
    }

    //Тест неуспешной авторизации несуществующего курьера.
    @Test
    @DisplayName("Fail authorization non-existent courier of /api/v1/courier/login")
    public void authorizationNonExistentCourierFailTest() throws JsonProcessingException {
        courier = Courier.getRandomCourier();
        Courier сreatedСourier = new Courier(courier.getLogin(), courier.getPassword());
        CoureirSpec.getResponseCourierAuthorization(сreatedСourier, 404)
                .body("message",equalTo("Учетная запись не найдена"));
    }
}

