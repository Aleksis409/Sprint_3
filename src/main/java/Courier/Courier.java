package Courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier() {
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //создание "случайного" курьера
    public static Courier getRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                "123456",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    //создание "случайного" курьера без пароля
    public static Courier getWithoutPassword() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                "",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    //создание "случайного" курьера без логина
    public static Courier getWithoutLogin() {
        return new Courier(
                "",
                "123456",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
