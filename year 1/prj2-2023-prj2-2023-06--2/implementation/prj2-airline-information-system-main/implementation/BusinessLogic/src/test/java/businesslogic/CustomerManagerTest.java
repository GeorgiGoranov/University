package businesslogic;

import datarecords.CustomerData;
import businesslogic.exceptions.InvalidDataBusinessLogicException;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.CustomerStorageService;
import persistence.GetInfoForCustomers;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.engine.TestTag.isValid;
import static org.mockito.Mockito.*;

    class CustomerManagerTest {
        @Mock
        private CustomerStorageService customerStorageService;

        private CustomerManager customerManager;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            customerManager = new CustomerManager(customerStorageService);
        }

        @Test
        void testAddCustomer() {
            CustomerData customerData = new CustomerData("John", "Doe", "123456789");
            CustomerData expectedResult = new CustomerData("John", "Doe", "123456789");
            when(customerStorageService.add(customerData)).thenReturn(expectedResult);

            CustomerData result = customerManager.add(customerData);

            assertEquals(expectedResult, result);
            verify(customerStorageService, times(1)).add(customerData);
        }

        @Test
        void testInsertCustomer() {
            CustomerData customerData = new CustomerData("John", "Doe", "123456789");

            assertDoesNotThrow(() -> customerManager.insertCustomerData(customerData));

            verify(customerStorageService, times(1)).insertCustomerData(customerData);
        }

        @Test
        void testGetCustomerData() {
            ObservableList<GetInfoForCustomers> expectedResult = mock(ObservableList.class);
            when(customerStorageService.getDataForCustomers()).thenReturn(expectedResult);

            ObservableList<GetInfoForCustomers> result = customerManager.getCustomerData();

            assertEquals(expectedResult, result);
            verify(customerStorageService, times(1)).getDataForCustomers();
        }
        @Test
        public void testValidCustomerData() {
            // Create a sample customerData object with valid data
            CustomerData customerData = new CustomerData("John", "Doe", "123456789");
            // Call the method and expect no exception
            assertDoesNotThrow(() -> customerManager.isValid(customerData));
        }

        @Test
        public void testNullCustomerData() {
            // Call the method with null customerData and expect InvalidDataBusinessLogicException
            assertThrows(InvalidDataBusinessLogicException.class, () -> customerManager.isValid(null));
        }

        @Test
        public void testEmptyFields() {
            // Create a sample customerData object with empty fields
            CustomerData customerData = new CustomerData("","","");

            // Call the method and expect InvalidDataBusinessLogicException
            assertThrows(InvalidDataBusinessLogicException.class, () -> customerManager.isValid(customerData));
        }

        @Test
        public void testInvalidNameAndLastName() {
            // Create a sample customerData object with invalid name and last name
            CustomerData customerData = new CustomerData("John123", "Doe123","123456789" );
            // Call the method and expect InvalidDataBusinessLogicException
            assertThrows(InvalidDataBusinessLogicException.class, () -> customerManager.isValid(customerData));
        }

        @Test
        public void testInvalidPersonalNumber() {
            // Create a sample customerData object with invalid personal number
            CustomerData customerData = new CustomerData("John", "Doe", "123456BTDE");
            // Call the method and expect InvalidDataBusinessLogicException
            assertThrows(InvalidDataBusinessLogicException.class, () -> customerManager.isValid(customerData));
        }
   }
