package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ==================== ЭЛЕМЕНТЫ ГЛАВНОЙ СТРАНИЦЫ ====================

    // Кнопка «Заказать» в верхней части страницы (в шапке)
    private final By orderButtonTop = By.xpath("//button[@class='Button_Button__ra12g']");

    // Кнопка «Заказать» в нижней части страницы (в футере)
    private final By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Button__ra12g')]");

    // Кнопка согласия на cookie-баннер
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    // Заголовки вопросов в разделе «Вопросы о важном» (аккордеон)
    private final By accordionHeaders = By.cssSelector(".accordion__button");

    // Панели с ответами в разделе «Вопросы о важном»
    private final By accordionPanels = By.cssSelector(".accordion__panel");

    // ==================== КОНСТРУКТОР ====================

    public HomePage(WebDriver driver) {
        this.driver = driver;
        // Selenium 3: используем int для секунд
        this.wait = new WebDriverWait(driver, 10);
    }

    // ==================== МЕТОДЫ ДЛЯ ОТКРЫТИЯ СТРАНИЦЫ ====================

    // Открыть главную страницу
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Прокрутить к разделу «Вопросы о важном»
    public void scrollToAccordion() {
        WebElement accordion = driver.findElement(By.cssSelector(".accordion"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", accordion);
    }

    // ==================== МЕТОДЫ ДЛЯ РАБОТЫ С COOKIE ====================

    public void acceptCookies() {
        List<WebElement> cookies = driver.findElements(cookieAcceptButton);
        if (!cookies.isEmpty()) {
            cookies.get(0).click();
        }
    }

    // ==================== МЕТОДЫ ДЛЯ АККОРДЕОНА ====================

    // Кликнуть на заголовок вопроса по тексту вопроса
    public void clickAccordionHeaderByQuestionText(String questionText) {
        WebElement button = driver.findElement(
                By.xpath("//div[contains(@class, 'accordion__button') and contains(text(), '" + questionText + "')]")
        );
        button.click();
    }

    // Получить текст ответа по тексту вопроса
    public String getAccordionPanelTextByQuestionText(String questionText) {
        WebElement button = driver.findElement(
                By.xpath("//div[contains(@class, 'accordion__button') and contains(text(), '" + questionText + "')]")
        );
        String panelId = button.getAttribute("aria-controls");
        WebElement panel = driver.findElement(By.id(panelId));
        wait.until(ExpectedConditions.visibilityOf(panel));
        return panel.getText();
    }

    // ==================== КНОПКИ «ЗАКАЗАТЬ» ====================

    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    public void clickOrderButtonBottom() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom)).click();
    }
}