package jpass.xml.converter;

import jpass.xml.bind.Entries;
import jpass.xml.bind.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("XmlConverter Tests")
public class XmlConverterTest {

    private final XmlConverter<Entries> converter = new XmlConverter<>(Entries.class);

    @Test
    @DisplayName("write() produces non-empty XML output")
    void testWriteProducesOutput() throws IOException {
        Entries entries = new Entries();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        converter.write(entries, out);

        assertTrue(out.size() > 0, "Output should not be empty");
    }

    @Test
    @DisplayName("write() output contains XML declaration")
    void testWriteContainsXmlDeclaration() throws IOException {
        Entries entries = new Entries();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        converter.write(entries, out);
        String xml = out.toString();

        assertTrue(xml.startsWith("<?xml"), "Should start with XML declaration");
    }

    @Test
    @DisplayName("read() deserializes empty entries correctly")
    void testReadEmptyEntries() throws IOException {
        String xml = "<?xml version='1.0' encoding='UTF-8'?><entries></entries>";
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());

        Entries result = converter.read(in);

        assertNotNull(result);
        assertTrue(result.getEntry().isEmpty());
    }

    @Test
    @DisplayName("write() then read() round-trip preserves entry data")
    void testRoundTrip() throws IOException {
        Entries entries = new Entries();
        Entry entry = new Entry();
        entry.setTitle("GitHub");
        entry.setUser("haleema");
        entry.setPassword("pass123");
        entries.getEntry().add(entry);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        converter.write(entries, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Entries result = converter.read(in);

        assertEquals(1, result.getEntry().size());
        assertEquals("GitHub", result.getEntry().get(0).getTitle());
        assertEquals("haleema", result.getEntry().get(0).getUser());
    }

    @Test
    @DisplayName("write() then read() preserves multiple entries")
    void testRoundTripMultipleEntries() throws IOException {
        Entries entries = new Entries();
        for (int i = 1; i <= 3; i++) {
            Entry e = new Entry();
            e.setTitle("Entry" + i);
            entries.getEntry().add(e);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        converter.write(entries, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Entries result = converter.read(in);

        assertEquals(3, result.getEntry().size());
    }
}