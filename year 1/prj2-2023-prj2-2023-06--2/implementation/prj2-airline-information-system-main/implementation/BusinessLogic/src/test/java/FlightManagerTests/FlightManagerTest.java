package FlightManagerTests;

import businesslogic.FlightManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import persistence.FlightStorageService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FlightManagerTest {


    private FlightManager flightManager;

    @Mock
    private FlightStorageService flightStorageServiceMock;

    @BeforeEach
    void setUp() {
        flightStorageServiceMock = mock(FlightStorageService.class);
        flightManager= new FlightManager(flightStorageServiceMock);
    }

//    FlightDataDao daoMOCK = Mockito.mock(FlightDataDao.class);
    @Mock
    FlightStorageService dao;
    @ParameterizedTest
    @CsvSource({
            "10, 100, 90.0",
            "25, 200, 150.0",
            "50, 50, 25.0",
            "0, 80, 80.0"
    })
    public void testDiscountedPrice(double discount, double currentPrice, double expectedPrice) {
        double result = flightManager.discountedprice(discount, currentPrice);
        assertEquals(expectedPrice, result);
    }
    @ParameterizedTest
    @CsvSource({
            "10, 100, 111.11",
            "25, 80, 106.67",
            "50, 200, 400.0",
            "0, 150, 150.0",
            "30, 70, 100.0",

    })
    public void testUndiscountedPrice(double discount, double currentPrice, double expectedUndiscountedPrice) {
        double undiscountedPrice = flightManager.undiscountedprice(discount, currentPrice);
        assertEquals(expectedUndiscountedPrice, undiscountedPrice, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
            "06:30, 18:30, 50",
            "15:00, 21:00, 25",
            "21:30, 21:30, 0",
            "21:00, 21:30, 2",
            "20:30, 21:30, 4",
            "20:00, 21:30, 6",
            "19:30, 21:30, 8",
            "19:00, 21:30, 10",

    })
    public void testCalculateSunsetPercentage(String arrivalTimeStr, String sunsetTimeStr, int expectedPercentage) {

        LocalTime arrivalTime = LocalTime.parse(arrivalTimeStr);
        LocalTime sunsetTime = LocalTime.parse(sunsetTimeStr);

        int percentage = flightManager.calculateSunsetPercentage(arrivalTime, sunsetTime);

        assertEquals(expectedPercentage, percentage);
    }
    @ParameterizedTest
    @CsvSource({
            "1, 2023, 10, 1, false",
            "2, 2023, 5, 3, false",
            "3, 2023, 20, 3, false",
            "4, 2023, 15, 4, false"
    })
    public void testCheckDate(int month, int year, int day, String selectedMonth, boolean expectedResult) {
        LocalDate date = LocalDate.of(year, month, day);
        boolean result = flightManager.checkDate(date);
        assertEquals(expectedResult, result);
    }
    @ParameterizedTest
    @CsvSource({
            "Hello, true",
            "123, true",
            ", false",
            "3.8, true"
    })
    public void testCheckIfNotNull(String value, boolean expectedResult) {
        boolean result = flightManager.checkifnotNull(value);
        assertEquals(expectedResult, result);
    }
    @ParameterizedTest
    @CsvSource({
            "10.5, 10.5",
            "-5, -1",
            "150, -2",
            "abc, -3",
            "123213, -2"
    })
    public void testConvertToDouble(String value, double expectedValue) {
        double result = flightManager.convertToDouble(value);
        assertEquals(expectedValue, result);
    }
}
