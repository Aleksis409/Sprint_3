package Order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private static String[] color;
    private int orderTrack;

    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters (name = "Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8} ")
    public static Iterable<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"Имя", "Фамилия", "Адрес", "Сокольники", "+1234567890", 1, "2022-09-30", "Комментарий", new String[]{"BLACK"}},
                {"имя", "фамилия", "адрес", "5", "123456789012", 7, "2022-09-01", "", new String[]{"", "GREY"}},
                {"Firstname", "Lastname", "Address", "Dinamo", "+1 234 567 89 01", 2, "2022-10-15T21:00:00.000Z", "COMMENT", new String[]{"BLACK", "GREY"}},
                {"firstname", "lastname", "address", "100", "1 234 567 89 01", 5, "2022-10-31T13:21:30.067Z", "comment", new String[]{}},
        });
    }

    //Тест создания заказа
    @Test
    @DisplayName("Ok create order - /api/v1/order")
    public void createOrderOkTest() throws IOException {
        Order order = new Order(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        orderTrack = OrderSpec.getResponseCreateOrder(OrderSpec.serialisationOrder(order))
                .extract()
                .path("track");
        assertNotEquals(0, orderTrack);
    }
}