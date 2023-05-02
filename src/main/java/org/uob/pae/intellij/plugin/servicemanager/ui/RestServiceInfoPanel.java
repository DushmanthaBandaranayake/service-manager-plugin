package org.uob.pae.intellij.plugin.servicemanager.ui;

import com.intellij.openapi.application.ApplicationManager;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.JarFileStartButtonListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.JarFileStopButtonListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.StartButtonActionListener;
import org.uob.pae.intellij.plugin.servicemanager.ui.listneners.StopButtonActionListener;

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

    RestServiceInfoPanel(String serviceName) {
        super(serviceName);
        initUi();
    }

    RestServiceInfoPanel(String serviceName,
                         ActionListener startButtonActionListener,
                         ActionListener stopButtonActionListener) {
        super(serviceName);
        startButton.addActionListener(startButtonActionListener);
        stopButton.addActionListener(stopButtonActionListener);
        tailLogFile();
    }

    private void initUi() {

        startButton.addActionListener(new StartButtonActionListener(this));
        stopButton.addActionListener(new StopButtonActionListener(this));
        tailLogFile();

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

    public void tailLogFile() {
        TailerListener listener = new MyListener();
        String logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();

        Tailer tailer = new Tailer(new File(logPath + "\\" + serviceName + ".out"), listener, 1000);
        ApplicationManager.getApplication().executeOnPooledThread(tailer);

    }

    @Override
    public Component getContentPanel() {
        return contentPanel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JTextArea getLogJTextArea() {
        return logJTextArea;
    }
}
