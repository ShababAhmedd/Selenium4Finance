package testrunner;

import config.Setup;
import controller.GmailController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.ResetPage;
import page.ResetPasswordPage;
import utils.Utils;

import javax.naming.ConfigurationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ResetPassTestRunner extends Setup {

    private ResetPage resetPage;
    private GmailController gmailController;

    public ResetPassTestRunner() throws IOException, ParseException {
    }

    @BeforeClass
    public void initResetTestRunner() throws IOException, ParseException {
        resetPage = new ResetPage(driver);
        gmailController = new GmailController(prop);
    }



    public void clearData() {
        resetPage.txtEmail.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
    }

    @Test(priority = 1, description = "negative test case 1", enabled = true)
    public void resetPass() {
        driver.findElement(By.partialLinkText("Reset it here")).click();
        resetPage.ResetPass("fake@user.com");

        String actualMsg = driver.findElement(By.tagName("p")).getText();
        String expectedMsg = "Your email is not registered";
        Assert.assertEquals(actualMsg, expectedMsg);

        clearData();
    }

    @Test(priority = 2, description = "negative test case 2", enabled = true)
    public void resetPassW() {
        resetPage.ResetPass("fake@");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String actualMsg = (String) js.executeScript("return arguments[0].validationMessage;", resetPage.txtEmail);
//        System.out.println(actualMsg);
        String expectedMsg = "Please enter a part following '@'.";
        Assert.assertTrue(actualMsg.contains(expectedMsg));

        clearData();
    }

    @Test(priority = 3, description = "negative test case 3", enabled = true)
    public void resetPassWo() {
        resetPage.ResetPass("abc123");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String actualMsg = (String) js.executeScript("return arguments[0].validationMessage;", resetPage.txtEmail);
//        System.out.println(actualMsg);
        String expectedMsg = "Please include an '@' in the email address.";
        Assert.assertTrue(actualMsg.contains(expectedMsg));

        clearData();
    }

    @Test (priority = 4, description = "entering valid gmail in the reset page", enabled = true)
    public void enteringValidMail() throws IOException, ParseException, InterruptedException {
        JSONObject userObj = Utils.gettinglastUserObj();
        String email = userObj.get("email").toString();
        resetPage.ResetPass(email);

        Thread.sleep(3000);
        String actualMsg = driver.findElement(By.tagName("p")).getText();
        System.out.println(actualMsg);
        String expectedMsg = "Password reset link sent to your email";
        Assert.assertEquals(actualMsg, expectedMsg);
    }


    @Test(priority = 5, description = "Getting the reset link and resetting password", enabled = true)
    public void resettingPassword() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException, InterruptedException, ParseException {
        Thread.sleep(5000);
        Gmail gmail = new Gmail();
        gmail.mailId();
        String mailBody = gmail.mailSnippet();
//        System.out.println(mailBody);
        String link = Utils.extractResetLink(mailBody);
//        System.out.println(link);

        driver.navigate().to(link);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
        resetPasswordPage.enterPassword("admin");

        String actualMsg = driver.findElement(By.tagName("p")).getText();
        String expectedMsg = "Password reset successfully";
        Assert.assertEquals(actualMsg, expectedMsg);

        JSONParser parser = new JSONParser();
        JSONArray userArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) userArray.get(userArray.size() - 1);
        userObj.put("pass", "admin"); // puts password in the last json object
        FileWriter fileWriter = new FileWriter("./src/test/resources/users.json");
        fileWriter.write(userArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

}
