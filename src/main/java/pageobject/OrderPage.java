package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ==================== ЭЛЕМЕНТЫ СТРАНИЦЫ ЗАКАЗА ====================

    // -------------------- Первый экран: "Для кого самокат" --------------------

    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // -------------------- Второй экран: "Про аренду" --------------------

    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.xpath("//div[@class='Dropdown-placeholder']");
    private final By colorCheckboxBlack = By.id("black");
    private final By colorCheckboxGrey = By.id("grey");
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//div[contains(@class, 'Order')]//button[text()='Заказать']");

    // -------------------- Модальные окна --------------------

    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    private final By successMessage = By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");

    // ==================== КОНСТРУКТОР ====================

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        // Selenium 3: используем int для секунд
        this.wait = new WebDriverWait(driver, 15);
    }

    // ==================== ШАГИ ПЕРВОЙ ФОРМЫ ====================

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
    }

    public void enterSurname(String surname) {
        driver.findElement(surnameInput).sendKeys(surname);
    }

    public void enterAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void selectMetroStation(String metro) {
        driver.findElement(metroInput).click();
        driver.findElement(metroInput).sendKeys(metro);
        WebElement metroOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='select-search__select']//div[contains(text(), '" + metro + "')]")
        ));
        metroOption.click();
    }

    public void enterPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    // ==================== ШАГИ ВТОРОЙ ФОРМЫ ====================

    public void selectDeliveryDate(String date) {
        WebElement dateElement = driver.findElement(dateInput);
        dateElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'react-datepicker')]")
        ));
        WebElement day = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + date + "']")
        ));
        day.click();
    }

    public void selectRentalPeriod(String period) {
        driver.findElement(rentalPeriodDropdown).click();
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='Dropdown-option' and text()='" + period + "']")
        ));
        periodOption.click();
    }

    public void selectScooterColor() {
        driver.findElement(colorCheckboxBlack).click();
    }

    public void enterComment(String comment) {
        if (!comment.isEmpty()) {
            driver.findElement(commentInput).sendKeys(comment);
        }
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    // ==================== СОБРАННЫЕ МЕТОДЫ ====================

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        enterName(name);
        enterSurname(surname);
        enterAddress(address);
        selectMetroStation(metro);
        enterPhone(phone);
        clickNextButton();
    }

    public void fillSecondFormWithOptionalFields(String date, String period, boolean selectColor, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Про аренду')]")
        ));

        selectDeliveryDate(date);
        selectRentalPeriod(period);

        if (selectColor) {
            selectScooterColor();
        }
        enterComment(comment);

        clickOrderButton();
    }

    // ==================== МОДАЛЬНЫЕ ОКНА ====================

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    public boolean isOrderSuccessful() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }
}