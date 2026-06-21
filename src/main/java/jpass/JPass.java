package jpass;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jpass.ui.JPassFrame;
import jpass.util.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Entry point of JPass.
 *
 * @author Gabor_Bata
 *
 */
public final class JPass {

    private static final Logger LOG = Logger.getLogger(JPass.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private JPass() {
        // not intended to be instantiated
    }

    public static void main(final String[] args) {
        try {
            UIManager.put("Button.arc", 4);
            FlatLaf lookAndFeel;
            if (Configuration.getInstance().is("ui.theme.dark.mode.enabled", false)) {
                lookAndFeel = new FlatDarkLaf();
            } else {
                lookAndFeel = new FlatLightLaf();
            }
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            LOG.log(Level.CONFIG, "Could not set look and feel for the application", e);
        }

        SwingUtilities.invokeLater(() -> JPassFrame.getInstance((args.length > 0) ? args[0] : null));
    }
}
