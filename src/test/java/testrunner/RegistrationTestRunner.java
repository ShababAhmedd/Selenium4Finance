package testrunner;

import config.Setup;
import controller.GmailController;
import org.testng.annotations.BeforeClass;
import utils.Utils;
import com.github.javafaker.Faker;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.UserRegistrationPage;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.time.Duration;

public class RegistrationTestRunner extends Setup {

    private GmailController gmailController;

    @BeforeClass
    public void initGmailController() {
        gmailController = new GmailController(prop);
    }

    @Test(priority = 1, description = "User Registration", enabled = true)
    public void userRegistration() throws InterruptedException, IOException, ParseException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        driver.findElement(By.partialLinkText("Register")).click();

        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String mail_name = "shabab.ahmed2000sa+";
        String email = (mail_name + Utils.generateRandomNumber(1000, 9999) + "@gmail.com");
        String pass = "1234";
        String phn = "0123" + Utils.generateRandomNumber(1000000, 9999999);
        String addrs = faker.address().fullAddress();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPass(pass);
        userModel.setPhn(phn);
        userModel.setAddrs(addrs);

        UserRegistrationPage user = new UserRegistrationPage(driver);
        user.userRegistration(userModel);

        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));     // explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));
        String actualSuccessfulMsg = driver.findElement(By.className("Toastify__toast")).getText();
        String expectedSuccessfulMsg = "registered successfully!";
        Assert.assertTrue(actualSuccessfulMsg.contains(expectedSuccessfulMsg));

        JSONObject userObj = new JSONObject();
        userObj.put("firstName", firstName);
        userObj.put("lastName", lastName);
        userObj.put("email", email);
        userObj.put("pass", pass);
        userObj.put("phn", phn);
        userObj.put("addrs", addrs);

        Utils.saveUserData("./src/test/resources/users.json", userObj);
    }

//    @Test(priority = 2, description = "Getting and saving mail ID", enabled = true)
//    public void mailId() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
//        Response res = gmailController.gmailInboxList();
//        JsonPath jsonPathId = res.jsonPath();
//        String id = jsonPathId.get("messages[0].id");
//        System.out.println("Last Mail ID: " + id);
//        Utils.setEnvVar("email_id", id);
//    }
//
//    @Test(priority = 3, description = "Getting the mail snippet", enabled = true)
//    public void mailSnippet() {
//        Response res = gmailController.readMessage();
//        JsonPath jsonPathSnippet = res.jsonPath();
//        String actualMsg = jsonPathSnippet.get("snippet");
//        System.out.println("Mail_Snippet: " + actualMsg);
//        String expectedMsg = "Welcome to our platform! We&#39;re excited to have you onboard. Best regards, Road to Career";
//        Assert.assertTrue(actualMsg.contains(expectedMsg));
//    }
    @Test(priority = 2, description = "Getting mail id and then mail snippet")
    public void mail() throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        Gmail gmail = new Gmail();
        gmail.mailId();
        String actualMsg = gmail.mailSnippet();
        System.out.println(actualMsg);
        String expectedMsg = "Welcome to our platform! We&#39;re excited to have you onboard. Best regards, Road to Career";
        Assert.assertTrue(actualMsg.contains(expectedMsg));
    }

}

