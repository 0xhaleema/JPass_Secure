package jpass.ui;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CopiablePasswordField} and {@link TextComponentFactory}.
 */
public class UiComponentTest {

    // -------------------------------------------------------
    // CopiablePasswordField tests
    // -------------------------------------------------------

    @Test
    public void copiablePasswordFieldShouldAllowCopyWhenEnabled() {
        CopiablePasswordField field = new CopiablePasswordField(true);
        Assertions.assertTrue(field.isCopyEnabled());
    }

    @Test
    public void copiablePasswordFieldShouldDisallowCopyWhenDisabled() {
        CopiablePasswordField field = new CopiablePasswordField(false);
        Assertions.assertFalse(field.isCopyEnabled());
    }

    @Test
    public void copiablePasswordFieldShouldExtendJPasswordField() {
        CopiablePasswordField field = new CopiablePasswordField(true);
        Assertions.assertInstanceOf(JPasswordField.class, field);
    }

    @Test
    public void copiablePasswordFieldShouldBeEditableByDefault() {
        CopiablePasswordField field = new CopiablePasswordField(true);
        Assertions.assertTrue(field.isEditable());
    }

    @Test
    public void copiablePasswordFieldShouldBeEnabledByDefault() {
        CopiablePasswordField field = new CopiablePasswordField(false);
        Assertions.assertTrue(field.isEnabled());
    }

    // -------------------------------------------------------
    // TextComponentFactory tests
    // -------------------------------------------------------

    @Test
    public void newTextFieldShouldReturnNonNull() {
        JTextField field = TextComponentFactory.newTextField();
        Assertions.assertNotNull(field);
    }

    @Test
    public void newTextFieldWithTextShouldContainText() {
        JTextField field = TextComponentFactory.newTextField("hello");
        Assertions.assertEquals("hello", field.getText());
    }

    @Test
    public void newTextFieldWithNullShouldReturnEmptyField() {
        JTextField field = TextComponentFactory.newTextField(null);
        Assertions.assertNotNull(field);
        Assertions.assertEquals("", field.getText());
    }

    @Test
    public void newPasswordFieldShouldReturnCopiablePasswordField() {
        JPasswordField field = TextComponentFactory.newPasswordField(true);
        Assertions.assertInstanceOf(CopiablePasswordField.class, field);
        Assertions.assertTrue(((CopiablePasswordField) field).isCopyEnabled());
    }

    @Test
    public void newPasswordFieldDefaultShouldHaveCopyDisabled() {
        JPasswordField field = TextComponentFactory.newPasswordField();
        Assertions.assertInstanceOf(CopiablePasswordField.class, field);
        Assertions.assertFalse(((CopiablePasswordField) field).isCopyEnabled());
    }

    @Test
    public void newTextAreaShouldReturnNonNull() {
        JTextArea area = TextComponentFactory.newTextArea();
        Assertions.assertNotNull(area);
    }

    @Test
    public void newTextAreaWithTextShouldContainText() {
        JTextArea area = TextComponentFactory.newTextArea("test content");
        Assertions.assertEquals("test content", area.getText());
    }
}