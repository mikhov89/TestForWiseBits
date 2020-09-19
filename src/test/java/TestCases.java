import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.gwtproject.samples.pages.MainW3cPage;
import org.junit.Test;

public class TestCases extends BaseTest {

    private MainW3cPage mainW3cPage = new MainW3cPage(driver);

    private final String CUSTOMER_NAME = "Test Customer";

    @DisplayName("Check address by name")
    @Description("FInd a row with the specified name and check the address is euqal to expected")
    @Test
    public void checkAddressTest() {
        mainW3cPage.open(mainW3cPage.URL);
        mainW3cPage.executeCurrentScript();
        mainW3cPage.findARowByNameAndCheckAddress("Giovanni Rovelli", "Via Ludovico il Moro 22");
    }

    @DisplayName("Check number of customers in London")
    @Description("Running a custom script with city='London' and check the customers number in there")
    @Test
    public void checkNumberOfCustomersInLondon() {
        mainW3cPage.open(mainW3cPage.URL);
        mainW3cPage.executeCustomScript("SELECT * FROM Customers where city='London';");
        mainW3cPage.checkRowsNumber(6);
    }

    @DisplayName("Add a new customer and check his record exists")
    @Description("Inserting a new row in DB, than making a SELECT for his record")
    @Test
    public void createAndCheckANewCustomer() {
        mainW3cPage.open(mainW3cPage.URL);
        mainW3cPage.executeCustomScript("insert into Customers" +
                " (CustomerName, ContactName, Address, City, PostalCode, Country)" +
                " values" +
                " ('" + CUSTOMER_NAME + "', 'Test Contact', 'Baket Str. 7', 'London', 'WA1 1DP', 'UK');");
        mainW3cPage.executeCustomScript("SELECT * FROM Customers where CustomerName='" + CUSTOMER_NAME + "';");
        mainW3cPage.checkRowsNumber(1);
    }

    @DisplayName("Update a random row in DB")
    @Description("Running a custom script with city='London' and check the customers number in there")
    @Test
    public void updateCustomer() {
        mainW3cPage.open(mainW3cPage.URL);
        int ID = mainW3cPage.getARandomID();
        mainW3cPage.executeCustomScript("UPDATE Customers " +
                "SET CustomerName = 'Updated Customer',  ContactName = 'Updated Contact', Address = 'Test Address', City = 'Limassol', PostalCode= '1515', Country = 'Cyprus' " +
                "WHERE CustomerID = " + ID + ";");
        mainW3cPage.executeCustomScript("SELECT * FROM Customers WHERE CustomerID = " + ID + " and CustomerName = 'Updated Customer';");
        mainW3cPage.checkRowsNumber(1);
    }

    @DisplayName("Delete a customer")
    @Description("Geting number of rows than deleting a cutomer")
    @Test
    public void deleteCustomerTest() {
        mainW3cPage.open(mainW3cPage.URL);
        int ID = mainW3cPage.getARandomID();
        int currentRecordsNumber = mainW3cPage.getRecordsNumber();
        mainW3cPage.executeCustomScript("DELETE FROM Customers WHERE CustomerID = " + ID + ";");
        mainW3cPage.executeCustomScript("SELECT * FROM Customers;");
        mainW3cPage.checkRowsNumber(currentRecordsNumber - 1);
    }

}
