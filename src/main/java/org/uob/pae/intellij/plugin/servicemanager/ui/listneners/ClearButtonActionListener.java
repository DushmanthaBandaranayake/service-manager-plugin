package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dushmantha Bandaranayake
 */
public class ClearButtonActionListener implements ActionListener {
    RestServiceInfoPanel serviceInfoPanel;

    public ClearButtonActionListener(RestServiceInfoPanel serviceInfoPanel) {
        this.serviceInfoPanel = serviceInfoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serviceInfoPanel.getLogJTextArea().setText("");
    }
}
