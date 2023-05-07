package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.Context;
import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.uob.pae.intellij.plugin.servicemanager.ui.Context.SERVICE_PANELS;

/**
 * @author Dushmantha Bandaranayake
 */
public class ReloadButtonActionListener implements ActionListener {

    private final MasterConfigInfoPanel masterConfigInfoPanel;

    public ReloadButtonActionListener(MasterConfigInfoPanel masterConfigInfoPanel) {

        this.masterConfigInfoPanel = masterConfigInfoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            DefaultListModel<InfoPanel> defaultListModel = (DefaultListModel<InfoPanel>) masterConfigInfoPanel.getMainPanel().getJListServices().getModel();

            List<InfoPanel> existingPanels = (List<InfoPanel>) Context.getValue(SERVICE_PANELS);
            List<InfoPanel> requestedPanels = Utils.createRestServicePanels();

            List<InfoPanel> newPanels = new ArrayList<>(requestedPanels);
            newPanels.removeAll(existingPanels);

            List<InfoPanel> removedPanels = new ArrayList<>(existingPanels);
            removedPanels.removeAll(requestedPanels);


            for (InfoPanel infoPanel : removedPanels) {

                if (infoPanel.isRunning()) {
                    throw new RuntimeException("Cannot remove "+infoPanel.getServiceName() + ". service is still running. Please stop before reloading");
                }
                defaultListModel.removeElement(infoPanel);
                existingPanels.remove(infoPanel);
            }

            for (InfoPanel infoPanel : newPanels) {
                defaultListModel.addElement(infoPanel);
                existingPanels.add(infoPanel);
            }
        } catch (RuntimeException ex) {
            Utils.fireNotification(ex.getMessage(), NotificationType.ERROR);

        }

    }

    private boolean checkIfAnyRunning(DefaultListModel<InfoPanel> defaultListModel) {
        boolean isRunning = false;
        for (int i = 0; i < defaultListModel.getSize(); i++) {
            if (defaultListModel.getElementAt(i).isRunning()) {
                Utils.fireNotification(
                        defaultListModel.getElementAt(i).getServiceName() + " " + " is still running. Please terminate before reloading",
                        NotificationType.ERROR);
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
