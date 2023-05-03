package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.ide.DataManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.uob.pae.intellij.plugin.servicemanager.ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
