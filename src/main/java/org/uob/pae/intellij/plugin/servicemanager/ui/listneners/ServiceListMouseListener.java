package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import org.uob.pae.intellij.plugin.servicemanager.ui.InfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ServiceListMouseListener implements MouseListener {
    private MainPanel mainPanel;

    public ServiceListMouseListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        var jSplitPane = mainPanel.getjSplitPane();
        InfoPanel selectedValue = mainPanel.getJListServices().getSelectedValue();
        if (selectedValue != null) {
            jSplitPane.setRightComponent(selectedValue.getContentPanel());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
