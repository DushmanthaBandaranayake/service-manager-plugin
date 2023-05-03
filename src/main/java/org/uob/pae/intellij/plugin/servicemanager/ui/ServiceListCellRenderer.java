package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.icons.AllIcons;
import org.uob.pae.intellij.plugin.servicemanager.JavaProcessHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Customized JTList cell renderer. This will decorate the JList with image icon.
 *
 * @author dushmantha
 */
public class ServiceListCellRenderer extends DefaultListCellRenderer {

    private static final Map<String, ImageIcon> imageIconCache = new HashMap<>();

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        Component comp = super.getListCellRendererComponent(list, value, index, selected, expanded);

        if (value instanceof RestServiceInfoPanel) {
            RestServiceInfoPanel restServiceInfoPanel = (RestServiceInfoPanel) value;

            Process process = JavaProcessHandler.PROCESS_CACHE.get(restServiceInfoPanel.getServiceName());
            if (process != null && process.isAlive()) {
                this.setIcon(AllIcons.Actions.Execute);
            } else {
                this.setIcon(AllIcons.Actions.Suspend);
            }
        }
        return comp;
    }

}
