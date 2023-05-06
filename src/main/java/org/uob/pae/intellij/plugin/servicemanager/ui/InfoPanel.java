package org.uob.pae.intellij.plugin.servicemanager.ui;

import java.awt.*;

/**
 * @author Dushmantha Bandaranayake
 */
public abstract class InfoPanel {

    protected final String serviceName;
    private MainPanel mainPanel;

    public InfoPanel(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return serviceName;
    }

    public abstract Component getContentPanel();

    public String getServiceName() {
        return serviceName;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {

        this.mainPanel = mainPanel;
    }

    public abstract boolean isRunning();

    public abstract void setRunning(boolean running);

    @Override
    public int hashCode() {
        return serviceName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.serviceName.equals(((InfoPanel) obj).serviceName);
    }
}
