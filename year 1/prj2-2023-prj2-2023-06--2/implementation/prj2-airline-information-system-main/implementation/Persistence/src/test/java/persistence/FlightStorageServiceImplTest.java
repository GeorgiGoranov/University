package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class FlightStorageServiceImplTest {
    @Mock
    private Connection connection = mock(Connection.class);

    @Mock
    private PreparedStatement selectDateOfDepartureAndArrival = mock(PreparedStatement.class);

    @Mock
    private PreparedStatement selectPs = mock(PreparedStatement.class);

    @Mock
    private ResultSet rss = mock(ResultSet.class);

    @Mock
    private ResultSet rs = mock(ResultSet.class);;

    private GetInfoForRecurringFlights getInfoForRecurringFlights;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(selectDateOfDepartureAndArrival, selectPs);
        when(selectDateOfDepartureAndArrival.executeQuery()).thenReturn(rss);
        when(selectPs.executeQuery()).thenReturn(rs);

        String flightid = "FL1";
        LocalDate dateOfDeparture = LocalDate.of(2023, 6, 1);
        LocalDate dateOfArrival = LocalDate.of(2023, 6, 2);
        LocalDate dayLimit = LocalDate.of(2023, 6, 3);
        int dayInterval = 7;

        getInfoForRecurringFlights = new GetInfoForRecurringFlights(flightid, dayInterval, dateOfDeparture, dateOfArrival, dayLimit);
    }

    @Test
    void insertIntoRecurringFlightsFromFlightData_shouldInsertRecordsIntoFlightData() throws Exception {
        // Mocking the database results
        when(rss.next()).thenReturn(true);
        when(rss.getString("dateOfDeparture")).thenReturn("2023-06-01");
        when(rss.getString("dateOfArrival")).thenReturn("2023-06-02");
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("planeid")).thenReturn("planeId");
        when(rs.getString("departureairport")).thenReturn("departureAirport");
        when(rs.getString("arrivalairport")).thenReturn("arrivalAirport");
        when(rs.getString("actualdeparturetime")).thenReturn("actualDepartureTime");
        when(rs.getString("actualarrivaltime")).thenReturn("actualArrivalTime");
        when(rs.getBoolean("sdiscount")).thenReturn(true);
        when(rs.getBoolean("ddiscount")).thenReturn(false);
        when(rs.getInt("price")).thenReturn(100);

        // Call the method under test
       // FlightStorageServiceImpl.insertIntoRecurringFlights(getInfoForRecurringFlights);

        // Verify that the appropriate methods were called with the expected parameters
        verify(connection).prepareStatement("SELECT dateOfDeparture, dateOfArrival FROM flightdata WHERE flightid = ?");
        verify(selectDateOfDepartureAndArrival).setString(1, "flightId");
        verify(selectDateOfDepartureAndArrival).executeQuery();

        verify(connection).prepareStatement("SELECT * FROM flightdata WHERE flightid = ? AND dateOfDeparture = ? AND dateOfArrival = ?");
        verify(selectPs).setString(1, "flightId");
        verify(selectPs).setDate(2, java.sql.Date.valueOf("2023-06-01"));
        verify(selectPs).setDate(3, java.sql.Date.valueOf("2023-06-02"));
        verify(selectPs).executeQuery();

        verify(connection).prepareStatement("INSERT INTO FlightData(flightid,planeid,departureAirport, arrivalAirport, actualDepartureTime, actualArrivalTime, dateOfDeparture, dateOfArrival, sdiscount, ddiscount, price)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        verify(selectPs, times(2)).setString(1, "flightId");
        verify(selectPs).setString(2, "planeId");
        verify(selectPs).setString(3, "departureAirport");
        verify(selectPs).setString(4, "arrivalAirport");
        verify(selectPs).setString(5, "actualDepartureTime");
        verify(selectPs).setString(6, "actualArrivalTime");
        verify(selectPs, times(2)).setDate(7, java.sql.Date.valueOf("2023-06-01"));
        verify(selectPs, times(2)).setDate(8, java.sql.Date.valueOf("2023-06-02"));
        verify(selectPs).setBoolean(9, true);
        verify(selectPs).setBoolean(10, false);
        verify(selectPs).setInt(11, 100);
        verify(selectPs, times(2)).executeUpdate();
    }
}