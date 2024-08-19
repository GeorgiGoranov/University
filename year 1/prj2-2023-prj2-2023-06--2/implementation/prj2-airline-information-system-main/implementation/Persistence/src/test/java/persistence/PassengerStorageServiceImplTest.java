//package persistence;
//
//import datarecords.PassengerData;
//import javafx.collections.ObservableList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import persistence.GetInfoForPassengers;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PassengerStorageServiceImplTest {
//    @Mock
//    private Statement statement;
//    @Mock
//    private ResultSet resultSet;
//
//    private PassengerStorageServiceImpl passengerStorageService;
//    private LocalDate DOB = LocalDate.of(1990, 1, 1);
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        passengerStorageService = new PassengerStorageServiceImpl();
//    }
//
//    @Test
//    void testAddPassenger() {
//        PassengerData passengerData = new PassengerData("John", "Doe", "1234567890", DOB);
//
//        PassengerData result = passengerStorageService.add(passengerData);
//
//        assertEquals(passengerData, result);
//    }
//
//    @Test
//    void testGetAll() {
//        assertThrows(UnsupportedOperationException.class, () -> passengerStorageService.getAll());
//    }
//
//    @Test
//    void testInsertPassengerData_Success() throws Exception {
//        ConnectionMock connectionMock = new ConnectionMock();
//        passengerStorageService.conn = connectionMock;
//        PassengerData passengerData = new PassengerData("John", "Doe", "1234567890", DOB);
//        String sql = "INSERT INTO Passenger (passengerFname, passengerIdNr, passengerBD, passengerLname) VALUES (?, ?, ?, ?)";
//
//        passengerStorageService.insertPassengerData(passengerData);
//
//        verify(connectionMock, times(2)).prepareStatement(sql);
//        verify(connectionMock.prepareStatement(sql), times(1)).executeUpdate();
//    }
//
//    @Test
//    void testInsertPassengerData_SQLException() throws Exception {
//        ConnectionMock connectionMock = new ConnectionMock();
//        passengerStorageService.conn = connectionMock;
//        PassengerData passengerData = new PassengerData("John", "Doe", "1234567890", DOB);
//        String errorMessage = "Failed to insert passenger data";
//        when(connectionMock.prepareStatement(anyString())).thenThrow(new SQLException(errorMessage));
//
//        passengerStorageService.insertPassengerData(passengerData);
//
//        verify(connectionMock, times(1)).prepareStatement(anyString());
//        verify(connectionMock, times(1)).close();
//    }
//
//    @Test
//    void testGetDataForPassengers() throws Exception {
//        ConnectionMock connectionMock = new ConnectionMock();
//        passengerStorageService.conn = connectionMock;
//        ResultSetMock resultSetMock = new ResultSetMock();
//        when(connectionMock.prepareStatement(anyString())).thenReturn(statement);
//        when(statement.executeQuery()).thenReturn(resultSetMock);
//
//        ObservableList<GetInfoForPassengers> result = passengerStorageService.getDataForPassengers();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(connectionMock, times(1)).prepareStatement(anyString());
//        verify(statement, times(1)).executeQuery();
//        verify(resultSet, times(2)).next();
//    }
//
//    @Test
//    void testRefreshDatabase_Success() throws Exception {
//        ConnectionMock connectionMock = new ConnectionMock();
//        passengerStorageService.conn = connectionMock;
//        String sql = "";
//        when(connectionMock.prepareStatement(sql)).thenReturn(statement);
//
//        passengerStorageService.refreshDatabase();
//
//        verify(connectionMock, times(1)).prepareStatement(sql);
//        verify(statement, times(1)).executeUpdate();
//    }
//
//    private static abstract class ConnectionMock implements Connection {
//        private PreparedStatementMock preparedStatementMock;
//
//        @Override
//        public Statement createStatement() {
//            return null;
//        }
//
//        @Override
//        public PreparedStatement prepareStatement(String sql) {
//            preparedStatementMock = new PreparedStatementMock();
//            return preparedStatementMock;
//        }
//
//        @Override
//        public void close() {
//            // Do nothing
//        }
//
//        // Implement other methods from the Connection interface
//
//        private static abstract class PreparedStatementMock implements PreparedStatement {
//            @Override
//            public void close() {
//                // Do nothing
//            }
//
//            @Override
//            public int executeUpdate() throws SQLException {
//                throw new SQLException("Failed to insert passenger data");
//            }
//
//            // Implement other methods from the PreparedStatement interface
//        }
//    }
//
//
//    private static class ResultSetMock implements ResultSet {
//        private int count = 0;
//
//        @Override
//        public boolean next() {
//            return count++ < 2;
//        }
//
//        @Override
//        public String getString(String columnLabel) {
//            if (columnLabel.equals("passengerID")) {
//                return "1";
//            } else if (columnLabel.equals("passengerFname")) {
//                return "John";
//            } else if (columnLabel.equals("passengerLname")) {
//                return "Doe";
//            } else if (columnLabel.equals("passengerIdNr")) {
//                return "1234567890";
//            }
//            return null;
//        }
//
//        @Override
//        public Date getDate(String columnLabel) {
//            return Date.valueOf("1990-01-01");
//        }
//
//        // Implement other methods from the ResultSet interface
//    }
//}
