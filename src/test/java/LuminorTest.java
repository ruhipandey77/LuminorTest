import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.screenshot;

public class LuminorTest {

    @Test
    public void testLuminor() throws InterruptedException, IOException {

        FileInputStream fis = new FileInputStream("LuminorTest.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Double amountDouble = sheet.getRow(1).getCell(1).getNumericCellValue();
        String amount = Double.toString(amountDouble);

        String currency = sheet.getRow(1).getCell(0).getStringCellValue();
        String xPath = "(//button[text()='" + currency + "'])[1]";

        open("https://luminor.ee/currency-rates");
        $("input").waitUntil(visible, 20000);

        $(By.xpath("(//div[@class=\"currency-select-inner\"])[1]")).click();
        $(By.xpath(xPath)).click();


        $("input").setValue(amount).pressEnter();
        String buy= $(By.xpath("(//input)[2]")).getValue();

        screenshot("my_file_name");
        System.out.println(buy);
    }
}
