package org.uob.pae.intellij.plugin.servicemanager.ui.listneners;

import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.JavaProcessHandler;
import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JarFileStartButtonListener implements ActionListener {
    private final RestServiceInfoPanel restServiceInfoPanel;

    public JarFileStartButtonListener(RestServiceInfoPanel restServiceInfoPanel) {
        this.restServiceInfoPanel = restServiceInfoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            restServiceInfoPanel.getLogJTextArea().setText("");
            String serviceName = restServiceInfoPanel.getServiceName();
            String deployFolderPath = MasterConfigInfoPanel.getInstance().getDeployFolderJTextField().getText();
            String executableFile = deployFolderPath + "\\" + serviceName + ".jar";

            if (new File(executableFile).isFile()) {

                JavaProcessHandler.startJar(restServiceInfoPanel);

            } else {
                Utils.fireNotification(executableFile + " Not Found!!", NotificationType.ERROR);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
