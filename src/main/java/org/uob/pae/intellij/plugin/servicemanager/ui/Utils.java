package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String INTELLIJ_NOTIFICATION_GROUP = "pae-service-manager";

    public static void clearJTree(JTree componentTree, boolean removeRoot) {
        DefaultTreeModel model = (DefaultTreeModel) componentTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        if (root != null) {
            root.removeAllChildren();
            model.reload();

            if (removeRoot) {
                model.setRoot(null);
            }
        }
    }

    /**
     * Fires IntelliJ pop up {@link NotificationType#ERROR} type message with the given Exception.
     * The given prefix will be added
     * in front of the message.
     *
     * @param ex     Exception
     * @param prefix the prefix
     */
    public static void fireErrorNotification(Exception ex, String prefix) {
        NotificationGroupManager.getInstance().getNotificationGroup(INTELLIJ_NOTIFICATION_GROUP).createNotification(prefix + ex.getMessage() + " " + ex, NotificationType.ERROR).notify(null);
    }


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

        for (String serviceInfo : decodeServiceList()) {

            String[] service = serviceInfo.split("=");
            String serviceName = service[0];
            String port = service[1].split(":")[1];

            list.add(new RestServiceInfoPanel(serviceName, port, "C://logs", "java -jar"));
        }
        return list;
    }

    private static String[] decodeServiceList() {
        return MasterConfigInfoPanel.getInstance().getServiceJTextArea().getText().split("\n");

    }
}
