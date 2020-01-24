package app.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MatchControllerTest {

    private transient MatchController controller;

    @Mock
    private transient Session player1;

    @Mock
    private transient Session player2;

    @BeforeEach
    void setUp() {
        controller = new MatchController();
        player1 = Mockito.mock(Session.class);
        player2 = Mockito.mock(Session.class);
    }

    @Test
    void testNewPlayerNoneWaiting() {
        controller.handleNewPlayer(player1, 0);
        when(player1.isOpen()).thenReturn(true);
        assertEquals(player1, controller.getWaitingPlayer().getKey());
    }

    @Test
    void testNewPlayerOneWaiting() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        Mockito.when(player1.getRemote()).thenReturn(endpoint);
        Mockito.when(player2.getRemote()).thenReturn(endpoint);
        Mockito.when(player1.isOpen()).thenReturn(true);
        Mockito.when(player2.isOpen()).thenReturn(true);
        assertTrue(controller.matches.isEmpty());
        controller.addWaitingPlayer(player1, 0);
        controller.handleNewPlayer(player2, 1);
        assertFalse(controller.matches.isEmpty());
    }

    @Test
    void testCreateMatch() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        Mockito.when(player1.getRemote()).thenReturn(endpoint);
        Mockito.when(player2.getRemote()).thenReturn(endpoint);
        UUID uuid = controller.createNewMatch(player1, 0, player2, 1);
        assertNotNull(uuid);
    }

    @Test
    void testgetWaitingPlayerNotOpen() {
        Mockito.when(player1.isOpen()).thenReturn(false);
        controller.addWaitingPlayer(player1, 0);
        assertNull(controller.getWaitingPlayer());
    }
}
