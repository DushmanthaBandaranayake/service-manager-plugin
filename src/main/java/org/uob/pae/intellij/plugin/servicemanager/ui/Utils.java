package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dushmantha Bandaranayake
 */
public class Utils {
    public static final String INTELLIJ_NOTIFICATION_GROUP = "pae-service-manager";

    /**
     * Fires IntelliJ pop up with the given type and the message.
     *
     * @param message Message
     */
    public static void fireNotification(String message, NotificationType notificationType) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(INTELLIJ_NOTIFICATION_GROUP)
                .createNotification(message, notificationType)
                .notify(null);

    }

    public static List<InfoPanel> createRestServicePanels() {

        List<InfoPanel> list = new ArrayList<>();
        list.add(MasterConfigInfoPanel.getInstance());

        try {
            for (String serviceInfo : getConfigs()) {

                String[] service = serviceInfo.split("=");
                String fileName = service[0].trim();
                String serviceName = fileName.split("\\.")[0];
                String fileExtension = fileName.split("\\.")[1];//.war, .jar

                if (!serviceName.startsWith("#")) {
                    String port = service[1].split(":")[1];
                    var restServiceInfoPanel = new RestServiceInfoPanel(serviceName, fileExtension, port);
                    list.add(restServiceInfoPanel);
                }
            }
        } catch (Exception e) {
            fireNotification("PAE Service Manager Plugin. Invalid service configurations", NotificationType.ERROR);
        }
        return list;
    }

    private static String[] getConfigs() {
        return MasterConfigInfoPanel.getInstance().getServiceJTextArea().getText().split("\n");

    }
}
