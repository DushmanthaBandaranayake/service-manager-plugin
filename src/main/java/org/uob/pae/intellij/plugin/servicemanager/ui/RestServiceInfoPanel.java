package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.openapi.application.ApplicationManager;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Rest Service information panel
 *
 * @author Dushmantha Bandaranayake
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

    private boolean isRunning;
    private String fileExtension;
    private String port;

    RestServiceInfoPanel(String serviceName, String fileExtension, String port) {
        super(serviceName);
        this.fileExtension = fileExtension;
        this.port = port;
        initUi();
    }

    private void initUi() {

        startButton.addActionListener(new StartButtonActionListener(this));
        stopButton.addActionListener(new StopButtonActionListener(this));
        clearButton.addActionListener(new ClearButtonActionListener(this));
        initTailingLog();

    }

    public void addStartActionButtonListener(StartButtonActionListener startButtonActionListener) {
        for (ActionListener actionListener : startButton.getActionListeners()) {
            startButton.removeActionListener(actionListener);
        }
        startButton.addActionListener(startButtonActionListener);
    }

    public void addStopActionButtonListener(StopButtonActionListener stopButtonActionListener) {
        for (ActionListener actionListener : stopButton.getActionListeners()) {
            stopButton.removeActionListener(actionListener);
        }
        stopButton.addActionListener(stopButtonActionListener);
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


    public boolean isRunning() {
        return isRunning;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getPort() {
        return port;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
