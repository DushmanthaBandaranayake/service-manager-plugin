package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.openapi.application.ApplicationManager;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Rest Service information panel
 */
public class RestServiceInfoPanel extends InfoPanel {

    private JPanel contentPanel;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logJTextArea;
    private JTextField argsTextField;
    private JCheckBox argsCheckBox;
    private JButton clearButton;
    private JTextField statusTextField;
    private Tailer tailer;

    RestServiceInfoPanel(String serviceName) {
        super(serviceName);
        initUi();
    }

    private void initUi() {

        startButton.addActionListener(new StartButtonActionListener(this));
        stopButton.addActionListener(new StopButtonActionListener(this));
        clearButton.addActionListener(new ClearButtonActionListener(this));
        initTailingLog();

    }

    public void addStartActionButtonListener(JarFileStartButtonListener jarFileStartButtonListener) {
        for (ActionListener actionListener : startButton.getActionListeners()) {
            startButton.removeActionListener(actionListener);
        }
        startButton.addActionListener(jarFileStartButtonListener);

    }

    public void addStopActionButtonListener(JarFileStopButtonListener jarFileStopButtonListener) {
        for (ActionListener actionListener : stopButton.getActionListeners()) {
            stopButton.removeActionListener(actionListener);
        }
        stopButton.addActionListener(jarFileStopButtonListener);
    }

    public class MyListener extends TailerListenerAdapter {
        @Override
        public void handle(String line) {
            logJTextArea.append(line + "\n");
        }
    }

    public void initTailingLog() {
        if (tailer != null) {
            tailer.stop();
        }
        var listener = new MyListener();
        String logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();

        tailer = new Tailer(new File(logPath + "\\" + serviceName + ".out"), listener, 1000, true);
        ApplicationManager.getApplication().executeOnPooledThread(tailer);

    }

    @Override
    public Component getContentPanel() {
        return contentPanel;
    }

    public JTextField getArgsTextField() {
        return argsTextField;
    }

    public JCheckBox getArgsCheckBox() {
        return argsCheckBox;
    }

    public JTextArea getLogJTextArea() {
        return logJTextArea;
    }

    public JTextField getStatusTextField() {
        return statusTextField;
    }
}
