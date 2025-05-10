package testrunner;

import config.Setup;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.ItemPage;
import page.LoginLogoutPage;
import page.UserDashboardPage;
import page.UserProfilePage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;

public class ItemTestRunner extends Setup {

    private LoginLogoutPage loginLogoutPage;
    private UserProfilePage userProfilePage;
    private ItemPage itemPage;

    public ItemTestRunner() throws IOException, ParseException, InterruptedException {
    }

    @BeforeClass
    public void init(){
        loginLogoutPage = new LoginLogoutPage(driver);
        userProfilePage = new UserProfilePage(driver);
        itemPage = new ItemPage(driver);
    }


    @Test(priority = 1, description = "user login after password reset")
    public void userLogin() throws IOException, ParseException, InterruptedException {
        JSONObject userObj = Utils.gettinglastUserObj();
//        LoginLogoutPage loginLogoutPage = new LoginLogoutPage(driver);
        String email = userObj.get("email").toString();
        String pass = userObj.get("pass").toString();
        loginLogoutPage.doLogin(email, pass);
    }

    @Test(priority = 2, description = "adding item - all fields")
    public void addAllItem() throws InterruptedException, IOException, ParseException {
//        ItemPage itemPage = new ItemPage(driver);
        itemPage.addingItem("all_items", "1000", "all item");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    @Test(priority = 3, description = "adding item - mandatory fields")
    public void addMandatoryItem() {
//        ItemPage itemPage = new ItemPage(driver);
        itemPage.addingItem("mandatory_items", "1000");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    @Test(priority = 4, description = "checks if all items and mandatory item list exists in the User Daily Costs table")
    public void itemExists() {
        UserDashboardPage dashboard = new UserDashboardPage(driver);
        Assert.assertTrue(dashboard.itemExists("all_items"));
        Assert.assertTrue(dashboard.itemExists("mandatory_items"));
    }


    String prevEmail;
    @Test(priority = 6, description = "updating user gmail")
    public void updateGmail() throws IOException, ParseException, InterruptedException {
        prevEmail = Utils.gettinglastUserObj().get("email").toString();
        System.out.println(prevEmail);
        userProfilePage.gotoProfile();
        String gmail = "shabab.ahmed2000sa+" + Utils.generateRandomNumber(1000, 9999) + "@gmail.com";
        userProfilePage.updateGmail(gmail);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        Utils.updateMail(gmail);   // updates gmail account in the json file
        loginLogoutPage.doLogout();
    }

    @Test(priority = 7, description = "loggin in with prev email address", enabled = true)
    public void prevEmailLogin() throws InterruptedException, IOException, ParseException {
        JSONObject userObj = Utils.gettinglastUserObj();
        String pass = userObj.get("pass").toString();
        loginLogoutPage.doLogin(prevEmail, pass);
        String actualMsg = driver.findElement(By.xpath("//p[text()='Invalid email or password']")).getText();
        String expectedMeg = "Invalid email or password";
        Assert.assertEquals(actualMsg, expectedMeg);
    }

    public void clearEmail() {
        loginLogoutPage.txtMail.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
    }

    public void clearPass() {
        loginLogoutPage.txtPass.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
    }

    @Test(priority = 8, description = "now logging in with the updated email", enabled = true)
    public void newEmailLogin() throws IOException, ParseException, InterruptedException {
        JSONObject userObj = Utils.gettinglastUserObj();
        String newEmail = userObj.get("email").toString();
        String pass = userObj.get("pass").toString();
        clearEmail();
        clearPass();
        loginLogoutPage.doLogin(newEmail, pass);
        Thread.sleep(2000);
        loginLogoutPage.doLogout();
    }

}
