package org.uob.pae.intellij.plugin.servicemanager.ui;

import java.awt.*;

public abstract class InfoPanel {

    protected final String serviceName;

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
}
