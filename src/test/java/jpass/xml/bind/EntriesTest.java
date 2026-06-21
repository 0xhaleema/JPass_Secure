package jpass.xml.bind;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Entries Tests")
public class EntriesTest {

    private Entries entries;

    @BeforeEach
    void setUp() {
        entries = new Entries();
    }

    @Test
    @DisplayName("getEntry() returns empty list when null")
    void testGetEntryInitializesListWhenNull() {
        List<Entry> result = entries.getEntry();
        assertNotNull(result, "Entry list should not be null");
        assertTrue(result.isEmpty(), "Entry list should be empty initially");
    }

    @Test
    @DisplayName("getEntry() returns same list on repeated calls")
    void testGetEntryReturnsSameListInstance() {
        List<Entry> first = entries.getEntry();
        List<Entry> second = entries.getEntry();
        assertSame(first, second, "Should return the same list instance");
    }

    @Test
    @DisplayName("Adding entry to list persists correctly")
    void testAddEntryToList() {
        Entry entry = new Entry();
        entry.setTitle("TestTitle");
        entries.getEntry().add(entry);

        assertEquals(1, entries.getEntry().size());
        assertEquals("TestTitle", entries.getEntry().get(0).getTitle());
    }

    @Test
    @DisplayName("Multiple entries can be added")
    void testMultipleEntriesAdded() {
        entries.getEntry().add(new Entry());
        entries.getEntry().add(new Entry());
        entries.getEntry().add(new Entry());

        assertEquals(3, entries.getEntry().size());
    }
}