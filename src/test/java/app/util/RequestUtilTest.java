package app.util;

import static app.util.RequestUtil.removeSessionAttrLoggedOut;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Request;
import spark.Session;

public class RequestUtilTest {

    @Test
    void testRemoveSessionAttrLoggedOutTrue() {
        Request mockReq = Mockito.mock(Request.class);
        Mockito.when(mockReq.session()).thenReturn(Mockito.mock(Session.class));
        Mockito.when(mockReq.session().attribute("loggedOut"))
            .thenReturn(Mockito.mock(Object.class));
        assertTrue(removeSessionAttrLoggedOut(mockReq));
    }

    @Test
    void testRemoveSessionAttrLoggedOutFalse() {
        Request mockReq = Mockito.mock(Request.class);
        Mockito.when(mockReq.session()).thenReturn(Mockito.mock(Session.class));
        Mockito.when(mockReq.session().attribute("loggedOut"))
            .thenReturn(null);
        assertFalse(removeSessionAttrLoggedOut(mockReq));
    }

    @Test
    void testSetterTest() {
        assertNotNull(new RequestUtil());
    }
}
