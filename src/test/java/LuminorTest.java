import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.screenshot;

public class LuminorTest {

    @Test
    public void testLuminor() throws InterruptedException, IOException {

        FileInputStream fis = new FileInputStream("LuminorTest.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Double val = sheet.getRow(1).getCell(1).getNumericCellValue();
        String s = Double.toString(val);

        String val2 = sheet.getRow(1).getCell(0).getStringCellValue();
        String xPath = "(//button[text()='" + val2 + "'])[1]";

        open("https://luminor.ee/currency-rates");
        Thread.sleep(20000);

        $(By.xpath("(//div[@class=\"currency-select-inner\"])[1]")).click();
        $(By.xpath(xPath)).click();
        Thread.sleep(5000);

        $("input").setValue(s).pressEnter();
        String buy= $(By.xpath("(//input)[2]")).getValue();

        screenshot("my_file_name");
        System.out.println(buy);
        Thread.sleep(5000);
    }
}
