package ru.netology.banklogin.test;

import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.banklogin.data.SQLHelper.cleanDatabase;


public class BankLoginTest {

    @AfterAll
    static void teardown(){
        cleanDatabase();
}
    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin(){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase(){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }
    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIFLoginWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var vericationPage = loginPage.validLogin(authInfo);
        vericationPage.verifyVerificationPageVisiblity();
        var vericationCode = DataHelper.generateRandomVerificationCode();
        vericationPage.verify(vericationCode.getCode());
        vericationPage.verifyErrorNotificationVisiblity();
    }
}
