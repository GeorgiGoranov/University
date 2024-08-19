package businesslogic;

import businesslogic.exceptions.InvalidDataBusinessLogicException;
import datarecords.BookingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.BookingStorageService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Mock
    private BookingStorageService mockBookingStorageService;

    private BookingManager bookingManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookingManager = new BookingManager(mockBookingStorageService);
    }

    @Test
    public void testAddBookingData() {
        //Arrange
        LocalDate date =  LocalDate.of(2023, 5, 31);
        List<String> fList = new ArrayList<>();
        List<String> pList = new ArrayList<>();
        fList.add("F1");
        fList.add("F2");
        pList.add("P1");
        pList.add("P2");

        //LocalDate bookingDate, String customerID, List<String> flightIDs, List<String> passengerIDs)
        BookingData bookingData = new BookingData(date, "111222333", fList, pList);
        BookingData expectedBookingData = new BookingData(date, "111222333", fList, pList);
        when(mockBookingStorageService.add(bookingData)).thenReturn(expectedBookingData);

        // Act
        BookingData result = bookingManager.add(bookingData);

        // Assert
        assertEquals(expectedBookingData, result);
        verify(mockBookingStorageService, times(1)).add(bookingData);
    }

    @Test
    public void testInsertBookingData() {
        // Arrange
        LocalDate date =  LocalDate.of(2023, 5, 31);
        List<String> fList = new ArrayList<>();
        List<String> pList = new ArrayList<>();
        fList.add("F1");
        fList.add("F2");
        pList.add("P1");
        pList.add("P2");

        //LocalDate bookingDate, String customerID, List<String> flightIDs, List<String> passengerIDs)
        BookingData bookingData = new BookingData(date, "111222333", fList, pList);
        String expectedBookingId = "123";
        when(mockBookingStorageService.insertBookingData(bookingData)).thenReturn(expectedBookingId);

        // Act
        String result = bookingManager.insertBookingData(bookingData);

        // Assert
        assertEquals(expectedBookingId, result);
        verify(mockBookingStorageService, times(1)).insertBookingData(bookingData);
    }

    @Test
    public void testGetBookingPrice() {
        // Arrange
        String bookingId = "123";
        float expectedPrice = 100.0f;
        when(mockBookingStorageService.getBookingPrice(bookingId)).thenReturn(expectedPrice);

        // Act
        Float result = bookingManager.getBookingPrice(bookingId);

        // Assert
        assertEquals(expectedPrice, result);
        verify(mockBookingStorageService, times(1)).getBookingPrice(bookingId);
    }
    @Test
    public void testValidData() {
        // Create a sample bookingData object

        List<String> passengerIDs = new ArrayList<>();
        passengerIDs.add("P1");
        passengerIDs.add("P2");
        List<String> flightIDs = new ArrayList<>();
        flightIDs.add("F1");
        LocalDate date = LocalDate.now();
        BookingData bookingData = new BookingData(date, "C1", flightIDs, passengerIDs );

        // Call the method and expect no exception
        assertDoesNotThrow(() -> bookingManager.validData(bookingData));
    }

    @Test
    public void testNullBookingData() {
        // Call the method with null bookingData and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> bookingManager.validData(null));
    }

    @Test
    public void testEmptyPassengerIDs() {
        // Create a sample bookingData object with empty passengerIDs
        List<String> passengerIDs = new ArrayList<>();
        List<String> flightIDs = new ArrayList<>();
        flightIDs.add("F1");
        LocalDate date = LocalDate.now();
        BookingData bookingData = new BookingData(date, "C1", flightIDs, passengerIDs );

        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> bookingManager.validData(bookingData));
    }

    @Test
    public void testEmptyFlightIDs() {
        // Create a sample bookingData object with empty flightIDs
        List<String> passengerIDs = new ArrayList<>();
        passengerIDs.add("P1");
        passengerIDs.add("P2");
        List<String> flightIDs = new ArrayList<>();
        LocalDate date = LocalDate.now();
        BookingData bookingData = new BookingData(date, "C1", flightIDs, passengerIDs );


        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> bookingManager.validData(bookingData));
    }

    @Test
    public void testFutureBookingDate() {
        // Create a sample bookingData object with a future booking date
        List<String> passengerIDs = new ArrayList<>();
        passengerIDs.add("P1");
        passengerIDs.add("P2");
        List<String> flightIDs = new ArrayList<>();
        flightIDs.add("F1");
        LocalDate date = LocalDate.now().plusDays(2);
        BookingData bookingData = new BookingData(date, "C1", flightIDs, passengerIDs );


        // Call the method and expect InvalidDataBusinessLogicException
        assertThrows(InvalidDataBusinessLogicException.class, () -> bookingManager.validData(bookingData));
    }
}
