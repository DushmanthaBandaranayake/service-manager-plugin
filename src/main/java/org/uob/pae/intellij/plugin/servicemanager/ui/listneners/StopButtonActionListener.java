package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.JavaProcessHandler;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dushmantha Bandaranayake
 */
public class StopButtonActionListener implements ActionListener {

    private final RestServiceInfoPanel restServiceInfoPanel;

    public StopButtonActionListener(RestServiceInfoPanel restServiceInfoPanel) {
        this.restServiceInfoPanel = restServiceInfoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            restServiceInfoPanel.getStatusTextField().setText("");
            JavaProcessHandler.stopProcess(restServiceInfoPanel);
            restServiceInfoPanel.setRunning(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
