import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import api.StellarBurgersApi;
import model.UserRequest;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;

public class ProfilePageTest {

    @Rule
    public DriverRule driverRule = new DriverRule();
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private StellarBurgersApi api;
    private UserRequest userRequest;
    private String accessToken;

    @Before
    public void setUp() {
        WebDriver webDriver = driverRule.getWebDriver();
        loginPage = new LoginPage(webDriver);
        mainPage = new MainPage(webDriver);
        profilePage = new ProfilePage(webDriver);
        api = new StellarBurgersApi();
        userRequest = Utils.createRandomUser();
        accessToken = api.createUser(userRequest);
    }

    @Test
    @DisplayName("Проверка кнопки - лк")
    public void testProfileButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();

        Assert.assertTrue("Profile link is not working", loginPage.isEnterHeaderVisible());
    }

    @Test
    @DisplayName("")
    public void testLogoButton() throws InterruptedException {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();
        Thread.sleep(10000);
        loginPage.clickLogoButton();
        mainPage.waitForLoad();
        Assert.assertTrue("Logo button is not working", mainPage.isCreateOrderHeaderVisible());
    }

    @Test
    @DisplayName("Проверка кнопки - Конструктор")
    public void testConstructorButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();

        loginPage.clickConstructorButton();
        mainPage.waitForLoad();
        Assert.assertTrue("Constructor button is not working", mainPage.isCreateOrderHeaderVisible());
    }

    @Test
    @DisplayName("Проверка кнопки - Выход")
    public void testLogoutButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userRequest.getEmail(), userRequest.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();
        mainPage.isCreateOrderHeaderVisible();
        mainPage.clickEnterProfileLink();
        profilePage.waitForLoad();
        profilePage.clickLogoutButton();

        loginPage.waitForLoad();
        Assert.assertTrue("Logout button is not working", loginPage.isEnterHeaderVisible());
    }

    @After
    public void tearDown() {
        api.deleteUser(accessToken);
    }
}
