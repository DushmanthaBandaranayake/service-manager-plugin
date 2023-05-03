package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.ServiceListSelectionListener;

import javax.swing.*;

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

        this.jListServices.setCellRenderer(new ServiceListCellRenderer());

        //setup services list
        DefaultListModel<InfoPanel> defaultListModel = new DefaultListModel<>();

        try {
            for (InfoPanel infoPanel : createRestServicePanels()) {
                defaultListModel.addElement(infoPanel);
                infoPanel.setMainPanel(this);
            }
        } catch (Exception e) {
            Utils.fireNotification("Invalid Service Configuration. Please make check your service name and ports ", NotificationType.ERROR);
        }

        jListServices.setModel(defaultListModel);
        jListServices.addListSelectionListener(new ServiceListSelectionListener(this));

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
