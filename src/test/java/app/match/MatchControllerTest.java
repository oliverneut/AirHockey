package app.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;
import javafx.util.Pair;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MatchControllerTest {

    private transient MatchController matchController;
    private transient Session opponent;
    private transient Session session;

    @Mock
    private transient MatchController mockController;

    @BeforeEach
    void setUp() {
        matchController = new MatchController();
        mockController = Mockito.mock(MatchController.class);
        session = Mockito.mock(Session.class);
        opponent = Mockito.mock(Session.class);
    }

    @Test
    void testNewPlayerTestNoOpponent() {
        Session session = Mockito.mock(Session.class);
        matchController.handleNewPlayer(session, 0);
        assertEquals(new Pair<>(session, 0), matchController.waitingPlayers.peek());
    }

    @Test
    void testNewPlayerTestOpponent() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(session.isOpen()).thenReturn(true);
        when(opponent.isOpen()).thenReturn(true);
        when(session.getRemote()).thenReturn(endpoint);
        when(opponent.getRemote()).thenReturn(endpoint);
        matchController.handleNewPlayer(session, 0);
        matchController.handleNewPlayer(opponent, 1);
        assertTrue(matchController.playerToMatch.containsKey(session.hashCode()));
        assertTrue(matchController.playerToMatch.containsKey(opponent.hashCode()));
    }

    @Test
    void testWaitingPlayerNotOpen() {
        when(session.isOpen()).thenReturn(false);
        matchController.addWaitingPlayer(session, 0);
        Pair<Session, Integer> result = matchController.getWaitingPlayer();
        assertNull(result);
    }

    @Test
    void testDeleteMatch() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);
        when(opponent.getRemote()).thenReturn(endpoint);
        UUID matchid = matchController.createNewMatch(session, 0, opponent, 1);
        Match match = matchController.matches.get(matchid);
        assertEquals(match, matchController.deleteMatch(matchid));
    }

    @Test
    void testGameReady() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);
        when(opponent.getRemote()).thenReturn(endpoint);
        when(session.isOpen()).thenReturn(true);
        when(opponent.isOpen()).thenReturn(true);
        UUID matchid = matchController.createNewMatch(session, 0, opponent, 1);
        assertTrue(matchController.isMatchReadyToStart(matchid));
    }

    @Test
    void testGameNotReady() {
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);
        when(opponent.getRemote()).thenReturn(endpoint);
        when(session.isOpen()).thenReturn(true);
        when(opponent.isOpen()).thenReturn(false);
        UUID matchid = matchController.createNewMatch(session, 0, opponent, 1);
        assertFalse(matchController.isMatchReadyToStart(matchid));
    }
}
