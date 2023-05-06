package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.ide.util.PropertiesComponent;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.PersistableJTexAreaActionKeyListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.PersistableJTexFielsActionKeyListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.ReloadButtonActionListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class MasterConfigInfoPanel extends InfoPanel  {

    private JPanel configPanel;
    private JTextField javaHomeTextField;
    private JTextField logFolderTextField;
    private JTextField deployFolderJTextField;
    private JTextField profileTextField;
    private JTextArea serviceJTextArea;
    private JTextArea argsJTextArea;
    private JButton reloadButton;

    private static MasterConfigInfoPanel instance;

    public static final String PERSISTENCE_KEY_JAVA_HOME = "javaHomeTextField";
    public static final String PERSISTENCE_KEY_LOG_FOLDER = "logFolderTextField";
    public static final String PERSISTENCE_KEY_DEPLOY_FOLDER = "deployFolderJTextField";
    public static final String PERSISTENCE_KEY_PROFILE = "profileTextField";
    public static final String PERSISTENCE_KEY_SERVICE_INFO = "serviceJTextArea";
    public static final String PERSISTENCE_KEY_ARGS = "argsJTextArea";

    private MasterConfigInfoPanel(String serviceName) {
        super(serviceName);
    }

    public static MasterConfigInfoPanel getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public static void init() {

        instance = new MasterConfigInfoPanel("[Configurations]");

        instance.serviceJTextArea.addKeyListener(new PersistableJTexAreaActionKeyListener(instance.serviceJTextArea, PERSISTENCE_KEY_SERVICE_INFO));
        restore(instance.serviceJTextArea, PERSISTENCE_KEY_SERVICE_INFO);

        instance.argsJTextArea.addKeyListener(new PersistableJTexAreaActionKeyListener(instance.argsJTextArea, PERSISTENCE_KEY_ARGS));
        restore(instance.argsJTextArea, PERSISTENCE_KEY_ARGS);

        instance.deployFolderJTextField.addKeyListener(new PersistableJTexFielsActionKeyListener(instance.deployFolderJTextField, PERSISTENCE_KEY_DEPLOY_FOLDER));
        restore(instance.deployFolderJTextField, PERSISTENCE_KEY_DEPLOY_FOLDER);

        instance.logFolderTextField.addKeyListener(new PersistableJTexFielsActionKeyListener(instance.logFolderTextField, PERSISTENCE_KEY_LOG_FOLDER));
        restore(instance.logFolderTextField, PERSISTENCE_KEY_LOG_FOLDER);

        instance.javaHomeTextField.addKeyListener(new PersistableJTexFielsActionKeyListener(instance.javaHomeTextField, PERSISTENCE_KEY_JAVA_HOME));
        restore(instance.javaHomeTextField, PERSISTENCE_KEY_JAVA_HOME);

        instance.profileTextField.addKeyListener(new PersistableJTexFielsActionKeyListener(instance.profileTextField, PERSISTENCE_KEY_PROFILE));
        restore(instance.profileTextField, PERSISTENCE_KEY_PROFILE);

        instance.reloadButton.addActionListener(new ReloadButtonActionListener(instance));
    }

    private static void restore(JTextComponent jTextComponent, String key) {
        String persistedValue = PropertiesComponent.getInstance().getValue(key);
        if (persistedValue != null) {
            jTextComponent.setText(persistedValue);
        }
    }

    public JTextField getJavaHomeTextField() {
        return javaHomeTextField;
    }

    public JTextField getLogFolderTextField() {
        return logFolderTextField;
    }

    public JTextField getDeployFolderJTextField() {
        return deployFolderJTextField;
    }

    public JTextArea getServiceJTextArea() {
        return serviceJTextArea;
    }

    public JPanel getConfigPanel() {
        return configPanel;
    }

    public JButton getReloadButton() {
        return reloadButton;
    }

    public JTextField getProfileTextField() {
        return profileTextField;
    }

    public JTextArea getArgsJTextArea() {
        return argsJTextArea;
    }

    @Override
    public Component getContentPanel() {
        return configPanel;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setRunning(boolean running) {

    }

}
