package jpass.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JPassInputStream}, {@link JPassOutputStream},
 * and {@link JPassStream.FileVersionType}.
 */
public class JPassStreamTest {

    // -------------------------------------------------------
    // FileVersionType enum tests
    // -------------------------------------------------------

    @Test
    public void version0ShouldHaveZeroSaltLength() {
        Assertions.assertEquals(0, JPassStream.FileVersionType.VERSION_0.getSaltLength());
    }

    @Test
    public void version1ShouldHaveSixteenByteSaltLength() {
        Assertions.assertEquals(16, JPassStream.FileVersionType.VERSION_1.getSaltLength());
    }

    @Test
    public void version0ShouldHaveCorrectVersionNumber() {
        Assertions.assertEquals(0, JPassStream.FileVersionType.VERSION_0.getVersion());
    }

    @Test
    public void version1ShouldHaveCorrectVersionNumber() {
        Assertions.assertEquals(1, JPassStream.FileVersionType.VERSION_1.getVersion());
    }

    @Test
    public void keyGeneratorsShouldNotBeNull() {
        Assertions.assertNotNull(JPassStream.FileVersionType.VERSION_0.getKeyGenerator());
        Assertions.assertNotNull(JPassStream.FileVersionType.VERSION_1.getKeyGenerator());
    }

    @Test
    public void supportedFileVersionsShouldContainBothVersions() {
        Assertions.assertTrue(JPassStream.SUPPORTED_FILE_VERSIONS.containsKey(0));
        Assertions.assertTrue(JPassStream.SUPPORTED_FILE_VERSIONS.containsKey(1));
    }

    // -------------------------------------------------------
    // JPassOutputStream + JPassInputStream round-trip tests
    // -------------------------------------------------------

    @Test
    public void shouldWriteAndReadBackWithSameKey() throws IOException {
        char[] key = "testPassword123".toCharArray();

        // Write
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        JPassOutputStream out = new JPassOutputStream(buffer, key);
        out.write("hello".getBytes());
        out.close();

        // Read
        JPassInputStream in = new JPassInputStream(
                new ByteArrayInputStream(buffer.toByteArray()), key);
        in.close();

        // Keys should match
        Assertions.assertNotNull(out.getKey());
        Assertions.assertNotNull(in.getKey());
        Assertions.assertArrayEquals(out.getKey(), in.getKey());
    }

    @Test
    public void differentPasswordsShouldProduceDifferentKeys() throws IOException {
        char[] key1 = "password1".toCharArray();
        char[] key2 = "password2".toCharArray();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        JPassOutputStream out = new JPassOutputStream(buffer, key1);
        out.close();

        JPassInputStream in = new JPassInputStream(
                new ByteArrayInputStream(buffer.toByteArray()), key2);
        in.close();

        // Keys should NOT match
        Assertions.assertFalse(Arrays.equals(out.getKey(), in.getKey()));
    }

    @Test
    public void generatedKeyShouldBe32Bytes() throws IOException {
        char[] key = "anyPassword".toCharArray();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        JPassOutputStream out = new JPassOutputStream(buffer, key);
        out.close();

        // AES-256 key = 32 bytes
        Assertions.assertEquals(32, out.getKey().length);
    }

    @Test
    public void shouldFallbackToVersion0OnUnknownIdentifier() throws IOException {
        // ByteArrayInputStream does not support mark/reset,
        // so JPassInputStream treats unknown identifier as version 0 (no salt).
        // It should still generate a key without throwing.
        byte[] garbage = "this is not jpass format".getBytes();
        char[] key = "pass".toCharArray();

        JPassInputStream in = new JPassInputStream(
                new ByteArrayInputStream(garbage), key);
        Assertions.assertNotNull(in.getKey());
        in.close();
    }

    @Test
    public void shouldThrowExceptionOnEmptyStream() {
        char[] key = "pass".toCharArray();

        Assertions.assertThrows(Exception.class, () -> {
            JPassInputStream in = new JPassInputStream(
                    new ByteArrayInputStream(new byte[0]), key);
            in.close();
        });
    }
}