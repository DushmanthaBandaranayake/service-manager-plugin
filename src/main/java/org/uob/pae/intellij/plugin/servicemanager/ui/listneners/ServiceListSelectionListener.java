package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MainPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServiceListSelectionListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {

        var jSplitPane = MainPanel.getInstance().getjSplitPane();
        InfoPanel selectedValue = MainPanel.getInstance().getJListServices().getSelectedValue();
        if (selectedValue != null) {
            jSplitPane.setRightComponent(selectedValue.getContentPanel());
        }

    }
}
