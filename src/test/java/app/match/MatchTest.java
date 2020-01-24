package app.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MatchTest {

    private transient UUID matchid;
    private transient int playerOneId;
    private transient int playerTwoId;
    private transient Match match;

    @Mock
    private transient Match mockMatch;

    @BeforeEach
    void setUp() {
        //Might be flaky
        this.matchid = UUID.randomUUID();
        this.playerOneId = 0;
        this.playerTwoId = 1;
        match = new Match(matchid, playerOneId, playerTwoId);
        mockMatch = Mockito.mock(Match.class);
    }


    @Test
    void testInitialize() {
        assertEquals(matchid, match.getMatchid());
        assertFalse(match.ended);
    }

    @Test
    void testScoredPlayerOneNoEnd() {
        Session player1 = Mockito.mock(Session.class);
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(player1.getRemote()).thenReturn(endpoint);
        match.setPlayer(player1, 0);
        match.setPlayer(player1, 1);
        match.updateScore(1);
        assertEquals(1, match.getScore1());
        assertEquals(0, match.getScore2());
    }

    @Test
    void testScoredPlayerTwoNoEnd() {
        Session player1 = Mockito.mock(Session.class);
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(player1.getRemote()).thenReturn(endpoint);
        match.setPlayer(player1, 0);
        match.setPlayer(player1, 1);
        match.updateScore(2);
        assertEquals(0, match.getScore1());
        assertEquals(1, match.getScore2());
    }

    @Test
    void testScoredPlayerOneEnd() {
        Session player1 = Mockito.mock(Session.class);
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(player1.getRemote()).thenReturn(endpoint);
        match.setPlayer(player1, 0);
        match.setPlayer(player1, 1);
        for (int i = 0; i < 10; i++) {
            match.updateScore(1);
        }
        assertEquals(10, match.getScore1());
        assertEquals(0, match.getScore2());
        assertTrue(match.ended);
    }

    @Test
    void testScoredPlayerTwoEnd() {
        Session player1 = Mockito.mock(Session.class);
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(player1.getRemote()).thenReturn(endpoint);
        match.setPlayer(player1, 0);
        match.setPlayer(player1, 1);
        for (int i = 0; i < 10; i++) {
            match.updateScore(2);
        }
        assertEquals(0, match.getScore1());
        assertEquals(10, match.getScore2());
        assertTrue(match.ended);
    }

    @Test
    void testGetOpponentPlayerOne() {
        Session player1 = Mockito.mock(Session.class);
        Session player2 = Mockito.mock(Session.class);
        match.setPlayer(player1, 0);
        match.setPlayer(player2, 1);
        assertEquals(player2, match.getOpponent(player1));
    }

    @Test
    void testGetOpponentPlayerTwo() {
        Session player1 = Mockito.mock(Session.class);
        Session player2 = Mockito.mock(Session.class);
        match.setPlayer(player1, 0);
        match.setPlayer(player2, 1);
        assertEquals(player1, match.getOpponent(player2));
    }

    @Test
    void testGetOpponentNone() {
        Session player1 = Mockito.mock(Session.class);
        Session player2 = Mockito.mock(Session.class);
        match.setPlayer(player1, 0);
        match.setPlayer(player2, 1);
        assertEquals(player1, match.getOpponent(null));
    }

    @Test
    void testEndGameNoScore() {
        Session player1 = Mockito.mock(Session.class);
        RemoteEndpoint endpoint = Mockito.mock(RemoteEndpoint.class);
        when(player1.getRemote()).thenReturn(endpoint);
        match.setPlayer(player1, 0);
        match.setPlayer(player1, 1);
        match.endGame();
        assertEquals(0, match.getScore1());
        assertEquals(0, match.getScore2());
        assertTrue(match.ended);
    }
}
