package org.gwtproject.samples.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class MainW3cPage extends BasePage {

    public final String URL = "https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all";

    WebDriverWait wait = new WebDriverWait(driver, 10);

    @FindBy(xpath = "//*[contains(@class,'CodeMirror ')]")
    public WebElement sqlInput;

    @FindBy(xpath = "//button[contains(text(), 'Run')]")
    WebElement runSQLButton;

    @FindBy(xpath = "//div[@id='divResultSQL']/div/div")
    WebElement recordsNumberText;

    public MainW3cPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open(String url) {
        driver.get(url);
        try {
            driver.findElement(By.id("accept-choices")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Step("Execute Current SQL Script")
    public void executeCurrentScript() {
        wait.until(ExpectedConditions.elementToBeClickable(runSQLButton)).click();
    }

    @Step("Find a row by name and check address")
    public void findARowByNameAndCheckAddress(String name, String address) {
        WebElement cellWithName = driver.findElement(By.xpath(String.format("//td[text()='%s']", name)));
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", cellWithName);
        Assert.assertEquals("Address is not as expected", cellWithName.findElement(By.xpath(".//../td[4]")).getText(), address);
    }

    @Step("Executing the script {scriptRow}")
    public void executeCustomScript(String scriptRow) {
        wait.until(ExpectedConditions.elementToBeClickable(sqlInput)).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].CodeMirror.setValue(\"" + scriptRow + "\");", sqlInput);
        wait.until(ExpectedConditions.elementToBeClickable(runSQLButton)).click();
    }

    @Step("Check number of rows {rowsNumber}")
    public void checkRowsNumber(int rowsNumber) {
        wait.until(ExpectedConditions.visibilityOf(recordsNumberText));
        Assert.assertEquals("Row number is not as expected", recordsNumberText.getText(), "Number of Records: " + rowsNumber);
    }


    @Step("Get a random ID")
    public int getARandomID() {
        executeCurrentScript();
        wait.until(ExpectedConditions.visibilityOf(recordsNumberText));
        Random random = new Random();
        return ThreadLocalRandom.current().nextInt(1,
                (Integer.parseInt(recordsNumberText.getText().replaceAll("\\D+", ""))));
    }

    @Step("Get record number")
    public int getRecordsNumber() {
        executeCurrentScript();
        wait.until(ExpectedConditions.visibilityOf(recordsNumberText));
        return Integer.parseInt(recordsNumberText.getText().replaceAll("\\D+", ""));
    }

}