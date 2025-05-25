package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    //Dataprovider
    @DataProvider(name = "logindata")
    public String[][] getData() throws IOException {
        String path = "testData/Login_Credentials.xlsx";

        ExcelUtility xlutil = new ExcelUtility(path);

        int totalRows = xlutil.getRowCount("Sheet1");
        int totalCols = xlutil.getCellCount("Sheet1", 1);

        String[][] logindata = new String[totalRows][totalCols];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        return logindata;
    }
}