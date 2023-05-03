package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MainPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Dushmantha Bandaranayake
 */
public class ServiceListSelectionListener implements ListSelectionListener {

    private MainPanel mainPanel;

    public ServiceListSelectionListener(MainPanel mainPanel) {

        this.mainPanel = mainPanel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        var jSplitPane = mainPanel.getjSplitPane();
        InfoPanel selectedValue = mainPanel.getJListServices().getSelectedValue();
        if (selectedValue != null) {
            jSplitPane.setRightComponent(selectedValue.getContentPanel());
        }

    }
}
