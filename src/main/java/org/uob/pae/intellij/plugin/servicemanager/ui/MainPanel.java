package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.ServiceListSelectionListener;

import javax.swing.*;

import static org.uob.pae.intellij.plugin.servicemanager.ui.Utils.createRestServicePanels;

public class MainPanel {

    private JPanel mainPanel;
    private JList<InfoPanel> jListServices;
    private JSplitPane jSplitPane;
    private static MainPanel instance;

    public static MainPanel getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }


    /**
     * Initialize main window. This method is also used  for reinitializing.
     */
    private static void init() {

        instance = new MainPanel();
        instance.jListServices.setCellRenderer(new ServiceListCellRenderer());
        //setup services list
        DefaultListModel<InfoPanel> defaultListModel = new DefaultListModel<>();

        try {
            for (InfoPanel infoPanel : createRestServicePanels()) {
                defaultListModel.addElement(infoPanel);
            }
        } catch (Exception e) {
            Utils.fireNotification("Invalid Service Configuration. Please make check your service name and ports ", NotificationType.ERROR);
        }

        instance.jListServices.setModel(defaultListModel);
        instance.jListServices.addListSelectionListener(new ServiceListSelectionListener());

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
