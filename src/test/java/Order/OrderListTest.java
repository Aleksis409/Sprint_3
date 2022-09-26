package Order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class OrderListTest {

    //Тест: При отправке запроса на список заказов возвращается ответ со списком заказов
    @Test
    @DisplayName("Ok getting a list of orders - /api/v1/order")
    public void getResponseListOfOrdersOkTest() {
        OrderSpec.getResponseListOfOrders()
                .and()
                .body("orders.findAll {it.id > 0}.track", hasItems(notNullValue()));
        // все заказы с "id" > 0 имеют "track" !=0
    }
}
