package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.ide.util.PropertiesComponent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PersistableJTexFielsActionKeyListener implements KeyListener {

    private final JTextField jTextField;
    private final String key;

    public PersistableJTexFielsActionKeyListener(JTextField jTextField, String persistenceKeyServiceInfo) {
        this.jTextField = jTextField;
        this.key = persistenceKeyServiceInfo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        PropertiesComponent.getInstance().setValue(key, jTextField.getText());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
