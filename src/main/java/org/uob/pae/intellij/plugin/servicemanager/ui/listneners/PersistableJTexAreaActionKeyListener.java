package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.ide.util.PropertiesComponent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PersistableJTexAreaActionKeyListener implements KeyListener {
    private final JTextArea jTextArea;
    private final String key;

    public PersistableJTexAreaActionKeyListener(JTextArea jTextArea, String persistenceKeyServiceInfo) {
        this.jTextArea = jTextArea;
        this.key = persistenceKeyServiceInfo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        PropertiesComponent.getInstance().setValue(key, jTextArea.getText());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        PropertiesComponent.getInstance().setValue(key, jTextArea.getText());
    }

}
