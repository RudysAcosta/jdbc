package dev.ncrousset.utils;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataConnectionTest {

    @Test
    void testGetInstanceReturnsConnection() {
        try {
            Connection connection = DataConnection.getInstance();
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("Should not have thrown SQLException: " + e.getMessage());
        }
    }
}
