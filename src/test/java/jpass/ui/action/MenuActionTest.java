package jpass.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AbstractMenuAction} and {@link TextComponentActionType}.
 */
public class MenuActionTest {

    // -------------------------------------------------------
    // AbstractMenuAction tests
    // -------------------------------------------------------

    @Test
    public void abstractMenuActionShouldStoreText() {
        AbstractMenuAction action = new AbstractMenuAction("TestAction", null, null) {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        Assertions.assertEquals("TestAction", action.getValue(Action.NAME));
    }

    @Test
    public void abstractMenuActionShouldStoreShortDescription() {
        AbstractMenuAction action = new AbstractMenuAction("Save", null, null) {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        Assertions.assertEquals("Save", action.getValue(Action.SHORT_DESCRIPTION));
    }

    @Test
    public void abstractMenuActionShouldStoreAccelerator() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        AbstractMenuAction action = new AbstractMenuAction("Save", null, ks) {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        Assertions.assertEquals(ks, action.getValue(Action.ACCELERATOR_KEY));
    }

    @Test
    public void abstractMenuActionWithNullAcceleratorShouldNotStoreIt() {
        AbstractMenuAction action = new AbstractMenuAction("NoShortcut", null, null) {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        Assertions.assertNull(action.getValue(Action.ACCELERATOR_KEY));
    }

    // -------------------------------------------------------
    // TextComponentActionType tests
    // -------------------------------------------------------

    @Test
    public void textComponentActionTypeShouldHaveSixValues() {
        Assertions.assertEquals(6, TextComponentActionType.values().length);
    }

    @Test
    public void textComponentActionTypeNamesShouldFollowConvention() {
        for (TextComponentActionType type : TextComponentActionType.values()) {
            Assertions.assertTrue(type.getName().startsWith("jpass.text."),
                    "Should start with jpass.text.: " + type.getName());
            Assertions.assertTrue(type.getName().endsWith("_action"),
                    "Should end with _action: " + type.getName());
        }
    }

    @Test
    public void textComponentActionTypesShouldHaveNonNullActions() {
        for (TextComponentActionType type : TextComponentActionType.values()) {
            Assertions.assertNotNull(type.getAction());
        }
    }

    @Test
    public void cutShouldHaveCtrlXAccelerator() {
        KeyStroke expected = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
        Assertions.assertEquals(expected, TextComponentActionType.CUT.getAccelerator());
    }

    @Test
    public void copyShouldHaveCtrlCAccelerator() {
        KeyStroke expected = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
        Assertions.assertEquals(expected, TextComponentActionType.COPY.getAccelerator());
    }

    @Test
    public void clearAllShouldHaveNullAccelerator() {
        Assertions.assertNull(TextComponentActionType.CLEAR_ALL.getAccelerator());
    }
}