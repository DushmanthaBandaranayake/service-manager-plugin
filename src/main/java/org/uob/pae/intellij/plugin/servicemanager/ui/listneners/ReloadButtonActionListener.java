package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MainPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ReloadButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel<InfoPanel> defaultListModel = (DefaultListModel<InfoPanel>) MainPanel.getInstance().getJListServices().getModel();
        defaultListModel.removeAllElements();

        for (InfoPanel infoPanel : Utils.createRestServicePanels()) {
            defaultListModel.addElement(infoPanel);
        }
    }
}
