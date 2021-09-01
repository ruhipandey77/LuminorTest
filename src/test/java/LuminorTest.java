import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.screenshot;

public class LuminorTest {
    By currencyDropdown = By.xpath("(//div[@class=\"currency-select-inner\"])[1]");
    By convertedText = By.xpath("(//input)[2]");
    By inputText = By.cssSelector("input");

    @BeforeTest
    public String[] returnAmountCurrencyPath() throws IOException {
        String[] returnValues = new String[2];

        FileInputStream fis = new FileInputStream("LuminorTest.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Double amountDouble = sheet.getRow(1).getCell(1).getNumericCellValue();
        String amount = Double.toString(amountDouble);

        String currency = sheet.getRow(1).getCell(0).getStringCellValue();
        String currencyPath = "(//button[text()='" + currency + "'])[1]";

        returnValues[0] = amount;
        returnValues[1] = currencyPath;

        return returnValues;
    }

    @Test
    public void testLuminor() throws IOException {

        String[] returnValues = returnAmountCurrencyPath();

        open("https://luminor.ee/currency-rates");
        $(inputText).waitUntil(visible, 20000);

        $(currencyDropdown).click();
        $(By.xpath(returnValues[1])).click();

        $(inputText).setValue(returnValues[0]).pressEnter();
        String convertedAmount= $(convertedText).getValue();

        screenshot("my_file_name");
        System.out.println(convertedAmount);
    }
}
