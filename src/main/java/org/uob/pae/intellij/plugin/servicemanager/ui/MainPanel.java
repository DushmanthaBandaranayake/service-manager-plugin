package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.ServiceListMouseListener;

import javax.swing.*;
import java.util.List;

import static org.uob.pae.intellij.plugin.servicemanager.ui.Context.*;
import static org.uob.pae.intellij.plugin.servicemanager.ui.Utils.createRestServicePanels;

/**
 * @author Dushmantha Bandaranayake
 */
public class MainPanel {

    private JPanel mainPanel;
    private JList<InfoPanel> jListServices;
    private JSplitPane jSplitPane;

    public MainPanel() {
        init();
    }

    /**
     * Initialize main window. This method is also used  for reinitializing.
     */
    private void init() {

        //setup services list
        DefaultListModel<InfoPanel> defaultListModel = null;

        try {

            List<InfoPanel> restServicePanels;
            if (Context.getValue(SERVICE_PANELS) != null) {
                restServicePanels = (List<InfoPanel>) Context.getValue(SERVICE_PANELS);
            } else {
                restServicePanels = createRestServicePanels();
                Context.put(SERVICE_PANELS, restServicePanels);
            }

            if (Context.getValue(SERVICE_LIST_MODEL) != null) {
                defaultListModel = (DefaultListModel<InfoPanel>) Context.getValue(SERVICE_LIST_MODEL);
            } else {
                defaultListModel = new DefaultListModel<>();
                Context.put(SERVICE_LIST_MODEL, defaultListModel);

                for (InfoPanel infoPanel : restServicePanels) {
                    defaultListModel.addElement(infoPanel);
                    infoPanel.setMainPanel(this);
                }
            }


        } catch (Exception e) {
            Utils.fireNotification("Invalid Service Configuration. Please make check your service name and ports ", NotificationType.ERROR);
        }

        jListServices.setModel(defaultListModel);
        jListServices.addMouseListener(new ServiceListMouseListener(this));
        jListServices.setCellRenderer(new ServiceListCellRenderer());
    }

    public JList<InfoPanel> getJListServices() {
        return jListServices;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JSplitPane getjSplitPane() {
        return jSplitPane;
    }

}
