package businesslogic;

import datarecords.FlightData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import persistence.FlightStorageService;
import persistence.GetInfoForCanceledFlights;
import persistence.GetInfoForFlights;
import persistence.GetInfoForRecurringFlights;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightManagerTest {

    private FlightManager flightManager;
    private  LocalDate dd = LocalDate.now();
    private LocalDate da = LocalDate.now();
    
    private GetInfoForFlights getInfoForFlights;

    @Mock
    private FlightStorageService flightStorageServiceMock;

    @BeforeEach
    void setUp() {
        flightStorageServiceMock = mock(FlightStorageService.class);
        flightManager= new FlightManager(flightStorageServiceMock);
    }

    @Test
    public void testAddFlight() {

        FlightData flightData = new FlightData("FL1","PL1","Amsterdam","Sofia","10:00","12:30", dd,da,false,true,120f);
        when(flightStorageServiceMock.add(flightData)).thenReturn(flightData);

        FlightData addedFlight = flightManager.add(flightData);

        assertEquals(flightData, addedFlight);
        verify(flightStorageServiceMock, times(1)).add(flightData);
    }
    @Test
    public void testGetHours() {
        ObservableList<String> expectedHours = FXCollections.observableArrayList("10, 23, 15");
        when(flightStorageServiceMock.getTimeOptionsForHour()).thenReturn(expectedHours);

        ObservableList<String> actualHours = flightManager.getHours();

        assertEquals(expectedHours, actualHours);
        verify(flightStorageServiceMock, times(1)).getTimeOptionsForHour();
    }

    @Test
    void getMinutes() {
        ObservableList<String> expectedMinutes = FXCollections.observableArrayList("05,30,20,25");
        when(flightStorageServiceMock.getTimeOptionsForMinutes()).thenReturn(expectedMinutes);

        ObservableList<String> actualMinutes = flightManager.getMinutes();

        assertEquals(expectedMinutes, actualMinutes);
        verify(flightStorageServiceMock, times(1)).getTimeOptionsForMinutes();
    }

    @Test
    void getAirports() {
        ObservableList<String> expectedAirports = FXCollections.observableArrayList("VNO/Vilnius,LHR/London,CDG/Paris,FRA/Frankfurt");

        when(flightStorageServiceMock.getAirports()).thenReturn(expectedAirports);


        ObservableList<String> actualAirports = flightManager.getAirports();

        assertEquals(expectedAirports,actualAirports);

        verify(flightStorageServiceMock,times(1)).getAirports();
    }

    @Test
    void getDataForAllFlights() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
        ObservableList<GetInfoForFlights> expectedData = createMockData();
        
        when(flightStorageServiceMock.getDataForFlights()).thenReturn(expectedData);

        // Act
        ObservableList<GetInfoForFlights> actualData = flightManager.getDataForAllFlights();



        // Assert
        assertEquals(expectedData, actualData);
        verify(flightStorageServiceMock, times(1)).getDataForFlights();
    }
    private ObservableList<GetInfoForFlights> createMockData() {
        // Create mock data for GetInfoForFlights objects
        GetInfoForFlights flight1 = new GetInfoForFlights("FL1","PL1","Amsterdam","Sofia","10:00","12:30", "2023-30-05","2023-30-05",false,true,120f);
        GetInfoForFlights flight2 = new GetInfoForFlights("FL2","PL2","Amsterdam","Sofia","10:00","12:30", "2023-01-06","2023-01-06",false,true,120f);
        // Add the flights to the list
        ObservableList<GetInfoForFlights> mockData = FXCollections.observableArrayList();
        mockData.add(flight1);
        mockData.add(flight2);

        return mockData;
    }


    @Test
    void getDataForAllPlanes() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
        ObservableList<String> expectedData = createMockDataForPlanes();
        when(flightStorageServiceMock.getPlaneIDs()).thenReturn(expectedData);

        // Act
        ObservableList<String> actualData = flightManager.getPlaneIDs();//null

        // Assert
        assertEquals(expectedData, actualData);
        verify(flightStorageServiceMock, times(1)).getPlaneIDs();
    }

    private ObservableList<String> createMockDataForPlanes() {

        ObservableList<String> mockData = FXCollections.observableArrayList();
        mockData.add("PL1");
        mockData.add("PL2");
        return mockData;
    }

    @Test
    void deleteSelectedFlight() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
       String flightID = "FL1";

        // Act
        flightManager.deleteSelectedFlight(flightID);

        // Assert
        verify(flightStorageServiceMock, times(1)).deleteFlight(flightID);
    }


    @Test
    void createRecurringFlights() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
        String duplicatedFlightId = "FL1";
        int dayInterval = 7;
        LocalDate dateOfDeparture = LocalDate.of(2023, 6, 1);
        LocalDate dateOfArrival = LocalDate.of(2023, 6, 1);
        LocalDate limitDate = LocalDate.of(2023, 6, 21);

        //TODO: test to fail
//        LocalDate dateOfDeparture = LocalDate.of(2023, 6, 1);
//        LocalDate dateOfArrival = LocalDate.of(2023, 6, 1);
//        LocalDate limitDate = LocalDate.of(2023, 5, 21);
//


        // Create GetInfoForRecurringFlights object
        GetInfoForRecurringFlights recurringFlightsData = new GetInfoForRecurringFlights(
                duplicatedFlightId, dayInterval, dateOfDeparture, dateOfArrival, limitDate);

        ObservableList<GetInfoForFlights> expectedData = createMockDataForRecurringFlights();

        when(flightStorageServiceMock.getDataForFlights()).thenReturn(expectedData);


        // Set up mock behavior
        doAnswer(new Answer<List<FlightData>>() {
            public List<FlightData> answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                GetInfoForRecurringFlights recurringFlightsData = (GetInfoForRecurringFlights) args[0];

                return generateRecurringFlights(recurringFlightsData);
            }
        }).when(flightStorageServiceMock).insertIntoRecurringFlights(any());



        // Act
        flightManager.createRecurringFlights(
                recurringFlightsData.getDuplicatedFlightId(),
                recurringFlightsData.getDayInterval(),
                recurringFlightsData.getDateOfDeparture(),
                recurringFlightsData.getDateOfArrival(),
                recurringFlightsData.getDayLimit()
        );

        ObservableList<GetInfoForFlights> actualData = flightManager.getDataForAllFlights();


        // Assert
       verify(flightStorageServiceMock, atLeastOnce()).insertIntoRecurringFlights(any());
       // verify(flightStorageServiceMock, times(1)).insertIntoRecurringFlights(any());

        for (GetInfoForFlights actial:
                actualData) {
            System.out.println(actial);

        }
        assertEquals(expectedData,actualData);

        // Verify that there are no interactions after this one
        //verifyNoMoreInteractions(flightStorageServiceMock);

    }

    private List<FlightData> generateRecurringFlights(GetInfoForRecurringFlights recurringFlightsData) {
        List<FlightData> generatedFlights = new ArrayList<>();
        LocalDate departureDate = recurringFlightsData.getDateOfDeparture();
        LocalDate arrivalDate = recurringFlightsData.getDateOfArrival();

        int numIterations = (int) (recurringFlightsData.getDayLimit().toEpochDay() - recurringFlightsData.getDateOfDeparture().toEpochDay()) / recurringFlightsData.getDayInterval();

        if (numIterations > 0) {
            for (int i = 1; i <= numIterations; i++) {
                departureDate = departureDate.plusDays(recurringFlightsData.getDayInterval());
                if (!departureDate.isAfter(recurringFlightsData.getDayLimit())) {
                    arrivalDate = arrivalDate.plusDays(recurringFlightsData.getDayInterval());

                    recurringFlightsData.setDateOfDeparture(departureDate);
                    recurringFlightsData.setDateOfArrival(arrivalDate);

                    // Generate the flight based on recurringFlightsData
                    FlightData generatedFlight = new FlightData(
                            recurringFlightsData.getDuplicatedFlightId(),
                            "PL1",
                            "Amsterdam",
                            "Sofia",
                            "10:30",
                            "12:30",
                            recurringFlightsData.getDateOfDeparture(),
                            recurringFlightsData.getDateOfArrival(),
                            false, // sdiscount
                            false, // ddiscount
                            120f // price
                    );

                    // Add additional logic to populate flight data as needed

                    generatedFlights.add(generatedFlight);

                } else {
                    break; // Exit the loop if the departureDate exceeds the limitDate
                }
            }
        }
        return generatedFlights;
    }

    private ObservableList<GetInfoForFlights> createMockDataForRecurringFlights() {

        // Create mock data for GetInfoForFlights objects
        GetInfoForFlights flight1 = new GetInfoForFlights("FL1","PL1","Amsterdam","Sofia","10:00","12:30", "2023-06-07","2023-06-07",false,true,120f);
        GetInfoForFlights flight2 = new GetInfoForFlights("FL2","PL2","Amsterdam","Sofia","10:00","12:30", "2023-06-14","2023-06-14",false,true,120f);
        // Add the flights to the list
        ObservableList<GetInfoForFlights> mockData = FXCollections.observableArrayList();
        mockData.add(flight1);
        mockData.add(flight2);
        return mockData;
    }



    @Test
    void insertFlightsIntoDeletedTable() {
        // Arrange
        flightManager = new FlightManager(flightStorageServiceMock);
        String flightId = "FL1";
        String reason = "Bad weather conditions!";



        // Set up mock behavior
        Mockito.doNothing().when(flightStorageServiceMock).insertIntoDeletedTable(flightId,reason);
        //test the persistence

        // Act
        flightManager.insertFlightsIntoDeletedTable(flightId,reason);

        // Assert
        Mockito.verify(flightStorageServiceMock, Mockito.times(1)).insertIntoDeletedTable(flightId,reason);

        // Add more assertions as needed
    }

    @Test
    void getDataForDeletedFlights() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
        ObservableList<GetInfoForCanceledFlights> expectedData = createExpectedData(); // Define your expected data here

        // Mock the FlightStorageService to return the expected data
        Mockito.when(flightStorageServiceMock.getDataForDeletedFlights()).thenReturn(expectedData);

        // Act
        ObservableList<GetInfoForCanceledFlights> actualData = flightManager.getDataForDeletedFlights();

        // Assert
        Mockito.verify(flightStorageServiceMock, Mockito.times(1)).getDataForDeletedFlights();
        // Add more assertions as needed

    }
    private ObservableList<GetInfoForCanceledFlights> createExpectedData() {
        // Create instances of GetInfoForCanceledFlights
        GetInfoForCanceledFlights info1 = new GetInfoForCanceledFlights(1, "FL1", "Reason1");
        GetInfoForCanceledFlights info2 = new GetInfoForCanceledFlights(2, "FL2", "Reason2");

        // Create an ObservableList and add the instances
        ObservableList<GetInfoForCanceledFlights> expectedData = FXCollections.observableArrayList();
        expectedData.add(info1);
        expectedData.add(info2);

        return expectedData;
    }


    @Test
    void storeFlightData() {
        // Arrange
        FlightManager flightManager = new FlightManager(flightStorageServiceMock);
        String flightId = "FL1";
        String planeId = "PL1";
        String departureAirport = "Amsterdam";
        String arrivalAirport = "Sofia";
        String departureTime = "10:30";
        String arrivalTime = "12:30";
        LocalDate departureDate = LocalDate.of(2023, 5, 1);
        LocalDate arrivalDate = LocalDate.of(2023, 5, 1);
        boolean specialDiscount = true;
        boolean groupDiscount = false;
        float price = 100.0f;

        // Act
        flightManager.storeFlightData(flightId, planeId, departureAirport, arrivalAirport, departureTime,arrivalTime,departureDate, arrivalDate, specialDiscount, groupDiscount, price);

        // Assert
        FlightData expectedFlightData = new FlightData(flightId, planeId, departureAirport, arrivalAirport, departureTime, arrivalTime, departureDate, arrivalDate, specialDiscount, groupDiscount, price);
        Mockito.verify(flightStorageServiceMock, Mockito.times(1)).insertFlightIntoFlightData(expectedFlightData);
        // Add more assertions as needed
    }
}
