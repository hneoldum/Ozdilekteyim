import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class BasePage extends BaseTest {
    Logger logger = LogManager.getLogger(BasePage.class);
    WebDriver driver;
    FluentWait<WebDriver> wait;
    JavascriptExecutor jsdriver;



    public BasePage(){
        driver = BaseTest.appiumDriver;
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(30)).
                pollingEvery(Duration.ofMillis(300)).
                ignoring(NoSuchElementException.class);
        jsdriver = (JavascriptExecutor) driver;
    }


    public WebElement findElement(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void LogInfo(String text) {
        System.out.println(text);
        logger.info(text);
    }

    @Step("<int> saniye kadar bekle")
    public void waitForsecond(int s) throws InterruptedException {
        Thread.sleep(1000*s);
    }

    @Step("<id> elemetini kontrol et ve tıkla")
    public void findByelementEndclick(String id){
        MobileElement element = appiumDriver.findElement(By.id(id));
        if (element.isDisplayed()){
            element.click();
        }else{
            System.out.println("görünür değil");
        }
    }

    @Step("Sayfayı aşağı doğru kaydır")
    public void swipeUp(){
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = appiumDriver.manage().window().getSize();
        System.out.println("Telefon Ekran Boyutu " + dims);
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        System.out.println("Başlangıç noktası " + pointOptionStart);
        pointOptionEnd = PointOption.point(dims.width / 2, dims.height / 4);
        System.out.println("Bitiş noktası " + pointOptionEnd);
        try {
            new TouchAction(appiumDriver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {

        }
    }


    @Step("Xpath <xpath> li elementi bul ve tıkla")
    public void clickByxpath(String xpath){
        appiumDriver.findElement(By.xpath(xpath)).click();
    }


    @Step("Id <id> li elementi bul ve tıkla")
    public void clickByid(String id){
        appiumDriver.findElement(By.id(id)).click();
    }


    @Step("<xpath> elementini kontrol et")
    public void controlApp(String xpath) throws InterruptedException {
        if (appiumDriver.findElement(By.xpath(xpath)).isDisplayed()){
            waitForsecond(3);
        } else{
            waitForsecond(3);
        }
    }


    public void sendKeys(By by, String text){
        findElement(by).sendKeys(text);
    }

    @Step ("<id> e-mail adresini gir")
    public void Login (String id){
        if (id != null){
            sendKeys(By.id(id),"text");

            LogInfo(id+"E-mail yazıldı");
        }else{
            LogInfo(id+"E-mail yazma hatası");
        }
    }


    @Step ("<id> şifre gir")
    public void LoginSifre(String id2){
        if (id2 != null){
            sendKeys(By.id(id2),"aaaa");

            LogInfo(id2+"sifre girildi");
        }else{
            LogInfo(id2+"sifre girme hatası");
        }
    }

}

