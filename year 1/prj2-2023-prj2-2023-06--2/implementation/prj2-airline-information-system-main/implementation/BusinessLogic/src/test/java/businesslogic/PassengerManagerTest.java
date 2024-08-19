package businesslogic;

import businesslogic.exceptions.InvalidDataBusinessLogicException;
import datarecords.PassengerData;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.GetInfoForPassengers;
import persistence.PassengerStorageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerManagerTest {
    @Mock
    private PassengerStorageService passengerStorageService;

    private PassengerManager passengerManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passengerManager = new PassengerManager(passengerStorageService);
    }


    @Test
    void testInsertPassengerData() {

        PassengerData passengerData = new PassengerData("John", "Doe", "1234567890", "01-02-1988");

        assertDoesNotThrow(() -> passengerManager.insertPassengerData(passengerData));

        verify(passengerStorageService, times(1)).insertPassengerData(passengerData);
    }

    @Test
    void testGetPassengerData() {
        ObservableList<GetInfoForPassengers> expectedResult = mock(ObservableList.class);

        when(passengerStorageService.getDataForPassengers()).thenReturn(expectedResult);

        ObservableList<GetInfoForPassengers> result = passengerManager.getPassengerData();


        assertEquals(expectedResult, result);
        verify(passengerStorageService, times(1)).getDataForPassengers();

    }

    @Test
    public void testValidPassengerData() {
        // Create a sample passengerData object with valid data
        PassengerData passengerData = new PassengerData("John", "Doe", "123456789", "01-01-1990");
        // Call the method and expect no exception
        assertDoesNotThrow(() -> passengerManager.isValid(passengerData));
    }

    @Test
    public void testNullPassengerData() {
        // Call the method with null passengerData and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(null));
    }

    @Test
    public void testEmptyFields() {
        // Create a sample passengerData object with empty fields
        PassengerData passengerData = new PassengerData("","","","");


        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }

    @Test
    public void testInvalidNameAndLastName() {
        // Create a sample passengerData object with invalid name and last name
        PassengerData passengerData = new PassengerData("John123", "Doe123", "123456789", "01-01-1990");
        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }

    @Test
    public void testInvalidPersonalNumber() {
        // Create a sample passengerData object with invalid personal number
        PassengerData passengerData = new PassengerData("John", "Doe", "123abc456", "01-01-1990");

        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }

    @Test
    public void testInvalidDateOfBirth() {
        // Create a sample passengerData object with invalid date of birth
        PassengerData passengerData = new PassengerData("John", "Doe", "123abc456", "2023-01-01");

        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }
    @Test
    public void testFutureDateOfBirth() {
        // Create a sample passengerData object with invalid date of birth
        PassengerData passengerData = new PassengerData("John", "Doe", "123abc456", "01-10-2024");

        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }

    @Test
    public void testInvalidDateFormat() {
        // Create a sample passengerData object with invalid date format
        PassengerData passengerData = new PassengerData("John", "Doe", "123abc456", "01/01/1990");

        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> passengerManager.isValid(passengerData));
    }
}
