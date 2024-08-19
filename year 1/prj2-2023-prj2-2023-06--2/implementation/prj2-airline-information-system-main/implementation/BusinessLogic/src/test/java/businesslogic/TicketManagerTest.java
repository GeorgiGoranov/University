package businesslogic;

import datarecords.TicketData;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import persistence.TicketStorageService;

import static org.mockito.Mockito.*;

public class TicketManagerTest {
    private TicketManager ticketManager;
    @Mock
    private TicketStorageService ticketStorageServiceMock;

    @BeforeEach
    void setUp(){
        ticketStorageServiceMock = mock(TicketStorageService.class);
        ticketManager = new TicketManager(ticketStorageServiceMock);
    }

//    @Test
//    public void testSaveTicket(){
//        TicketData
//    }



}
