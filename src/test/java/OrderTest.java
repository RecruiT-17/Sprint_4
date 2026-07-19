import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.OrderPage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private OrderPage orderPage;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final boolean selectColor;
    private final String comment;
    private final boolean useTopButton;

    public OrderTest(String name, String surname, String address, String metro,
                     String phone, String date, String rentalPeriod,
                     boolean selectColor, String comment, boolean useTopButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.selectColor = selectColor;
        this.comment = comment;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters(name = "Заказ {0} {1}, кнопка {9}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Мария", "Котова", "г. Москва, ул. Красная", "Чистые пруды",
                        "84444333221", "17", "сутки", true, "Очень нужен чёрный самокат!", true},
                {"Иван", "Петров", "г. Москва, ул. Ленина", "Сокольники",
                        "89998887766", "29", "шестеро суток", false, "", false}
        });
    }

    @Before
    public void setUpOrderTest() {
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testOrderScooter() {
        if (useTopButton) {
            homePage.clickOrderButtonTop();
        } else {
            homePage.clickOrderButtonBottom();
        }

        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.fillSecondFormWithOptionalFields(date, rentalPeriod, selectColor, comment);
        orderPage.confirmOrder();

        Assert.assertTrue("Заказ должен быть оформлен успешно", orderPage.isOrderSuccessful());
    }
}