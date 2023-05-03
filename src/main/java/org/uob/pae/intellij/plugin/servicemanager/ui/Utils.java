package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.JarFileStartButtonListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.JarFileStopButtonListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Dushmantha Bandaranayake
 */
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

    public static String[] cleanArray(String[] array) {

        return Arrays.stream(array).filter(Objects::nonNull).toArray(String[]::new);

    }

    public static String getPort(RestServiceInfoPanel serviceInfoPanel) {

        String[] services = MasterConfigInfoPanel.getInstance().getServiceJTextArea().getText().split("\n");
        for (String service : services) {
            String[] serviceParams = service.split("=");
            if (serviceInfoPanel.getServiceName().equals(serviceParams[0])) {
                return serviceParams[1].split(":")[1];
            }
        }
        return "";
    }

    public static List<InfoPanel> createRestServicePanels() {
        List<InfoPanel> list = new ArrayList<>();
        list.add(MasterConfigInfoPanel.getInstance());

        var hazelcastServicePanel = new RestServiceInfoPanel("pae-hazelcast");
        hazelcastServicePanel.addStartActionButtonListener(new JarFileStartButtonListener(hazelcastServicePanel));
        hazelcastServicePanel.addStopActionButtonListener(new JarFileStopButtonListener(hazelcastServicePanel));

        list.add(hazelcastServicePanel);

        for (String serviceInfo : decodeServiceList()) {

            String[] service = serviceInfo.split("=");
            String serviceName = service[0];
            if (!serviceName.startsWith("#")) {
                String port = service[1].split(":")[1];
                list.add(new RestServiceInfoPanel(serviceName));
            }
        }
        return list;
    }

    private static String[] decodeServiceList() {
        return MasterConfigInfoPanel.getInstance().getServiceJTextArea().getText().split("\n");

    }
}
