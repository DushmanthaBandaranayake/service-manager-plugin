package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dushmantha Bandaranayake
 */
public class ReloadButtonActionListener implements ActionListener {

    private MasterConfigInfoPanel masterConfigInfoPanel;

    public ReloadButtonActionListener(MasterConfigInfoPanel masterConfigInfoPanel) {

        this.masterConfigInfoPanel = masterConfigInfoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel<InfoPanel> defaultListModel = (DefaultListModel<InfoPanel>) masterConfigInfoPanel.getMainPanel().getJListServices().getModel();
        defaultListModel.removeAllElements();

        for (InfoPanel infoPanel : Utils.createRestServicePanels()) {
            defaultListModel.addElement(infoPanel);
        }
    }
}
