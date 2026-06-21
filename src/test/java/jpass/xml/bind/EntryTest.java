package jpass.xml.bind;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Entry Tests")
public class EntryTest {

    private Entry entry;

    @BeforeEach
    void setUp() {
        entry = new Entry();
    }

    @Test
    @DisplayName("Default constructor sets creationDate")
    void testConstructorSetsCreationDate() {
        assertNotNull(entry.getCreationDate(), "Creation date should be set");
    }

    @Test
    @DisplayName("Default constructor sets lastModification")
    void testConstructorSetsLastModification() {
        assertNotNull(entry.getLastModification(), "Last modification should be set");
    }

    @Test
    @DisplayName("creationDate and lastModification are equal on new Entry")
    void testCreationAndModificationDatesEqual() {
        assertEquals(entry.getCreationDate(), entry.getLastModification());
    }

    @Test
    @DisplayName("setTitle and getTitle work correctly")
    void testTitleGetterSetter() {
        entry.setTitle("Gmail");
        assertEquals("Gmail", entry.getTitle());
    }

    @Test
    @DisplayName("setUrl and getUrl work correctly")
    void testUrlGetterSetter() {
        entry.setUrl("https://gmail.com");
        assertEquals("https://gmail.com", entry.getUrl());
    }

    @Test
    @DisplayName("setUser and getUser work correctly")
    void testUserGetterSetter() {
        entry.setUser("haleema@gmail.com");
        assertEquals("haleema@gmail.com", entry.getUser());
    }

    @Test
    @DisplayName("setPassword and getPassword work correctly")
    void testPasswordGetterSetter() {
        entry.setPassword("securePass123");
        assertEquals("securePass123", entry.getPassword());
    }

    @Test
    @DisplayName("setNotes and getNotes work correctly")
    void testNotesGetterSetter() {
        entry.setNotes("Personal account");
        assertEquals("Personal account", entry.getNotes());
    }

    @Test
    @DisplayName("setCreationDate overrides default date")
    void testSetCreationDate() {
        entry.setCreationDate("2024-01-01T00:00:00");
        assertEquals("2024-01-01T00:00:00", entry.getCreationDate());
    }

    @Test
    @DisplayName("All fields are null by default except dates")
    void testDefaultFieldsAreNull() {
        assertNull(entry.getTitle());
        assertNull(entry.getUrl());
        assertNull(entry.getUser());
        assertNull(entry.getPassword());
        assertNull(entry.getNotes());
    }
}