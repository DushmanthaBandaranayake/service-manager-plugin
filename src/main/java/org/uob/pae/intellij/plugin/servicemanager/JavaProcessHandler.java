package org.uob.pae.intellij.plugin.servicemanager;

import org.apache.commons.lang.StringUtils;
import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Dushmantha Bandaranayake
 */
public class JavaProcessHandler {

    public final static Map<String, Process> PROCESS_CACHE = new ConcurrentHashMap<>();

    public static void startFatWar(RestServiceInfoPanel serviceInfoPanel) {
        startProcess(serviceInfoPanel, true);
    }

    public static void startJar(RestServiceInfoPanel serviceInfoPanel) {
        startProcess(serviceInfoPanel, false);
    }

    private static void startProcess(RestServiceInfoPanel serviceInfoPanel, boolean isFatJar) {
        String service = serviceInfoPanel.getServiceName();
        try {
            PROCESS_CACHE.computeIfPresent(service, (k, v) -> {
                serviceInfoPanel.getLogJTextArea().append("Found existing process. PID =" + v.pid() + " This process will be terminated!\n");
                v.destroy();
                return null;
            });

            var logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();
            var deployFolderPath = MasterConfigInfoPanel.getInstance().getDeployFolderJTextField().getText();

            ArrayList<String> commands = getExecutionCommonCommands(serviceInfoPanel);
            commands.add("-jar");
            commands.add((deployFolderPath + "/" + service) + (isFatJar ? "-fat.war" : ".jar"));
            commands.add(MasterConfigInfoPanel.getInstance().getProfileTextField().getText());
            if (isFatJar) {
                commands.add("-Dserver.port=" + Utils.getPort(serviceInfoPanel));
            }

            serviceInfoPanel.getLogJTextArea().append(StringUtils.join(commands, " ") + "\n");

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            serviceInfoPanel.getLogJTextArea().append("Echo Java Version \n");
            String log = logPath + "/" + serviceInfoPanel.getServiceName() + ".out";
            echoJavaVersion(log, MasterConfigInfoPanel.getInstance().getJavaHomeTextField().getText());

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(new File(log));
            Process proc = processBuilder.start();

            serviceInfoPanel.getLogJTextArea().append("############" + serviceInfoPanel.getServiceName() + " [ process started Process Id - " + proc.pid() + "] ###############\n");
            proc.waitFor(3, TimeUnit.SECONDS);

            if (proc.isAlive()) {
                PROCESS_CACHE.put(service, proc);
                if (isFatJar) {

                    serviceInfoPanel.getStatusTextField().setText("Service started ## localhost:" + Utils.getPort(serviceInfoPanel) + "/" + service + " ##");
                } else {
                    serviceInfoPanel.getStatusTextField().setText("Service started ##" + service + " ##");
                }
            }

        } catch (Exception e) {
            serviceInfoPanel.getLogJTextArea().append("Error starting process " + (e.getMessage() != null ? e.getMessage() : e) + "/n");
        }
    }

    private static ArrayList<String> getExecutionCommonCommands(RestServiceInfoPanel serviceInfoPanel) {

        var javaHome = MasterConfigInfoPanel.getInstance().getJavaHomeTextField().getText();
        String[] args = MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n");

        ArrayList<String> commands = new ArrayList<>();
        commands.add(javaHome.isBlank() ? "java" : javaHome + "/bin/java");
        for (String param : args) {
            if (!param.startsWith("#")) {
                commands.add(param.trim());
            }
        }

        if (serviceInfoPanel.getArgsCheckBox().isSelected()) {
            commands.add(serviceInfoPanel.getArgsTextField().getText());
        }
        commands.add("-Dspring-boot.run.profiles=" + MasterConfigInfoPanel.getInstance().getProfileTextField().getText());
        return commands;

    }

    private static void echoJavaVersion(String log, String javaHome) throws IOException, InterruptedException {

        var processBuilder = new ProcessBuilder(javaHome.isBlank() ? "java" : javaHome + "/bin/" + "java", "--version");
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(new File(log));
        Process proc = processBuilder.start();
        proc.waitFor(5, TimeUnit.SECONDS);
    }

    public static void stopProcess(RestServiceInfoPanel serviceInfoPanel) {

        String serviceName = serviceInfoPanel.getServiceName();
        Process process = PROCESS_CACHE.get(serviceName);

        serviceInfoPanel.getLogJTextArea().append("Stopping process..." + "\n");
        if (process == null) {
            serviceInfoPanel.getLogJTextArea().append("Stopping process failed!!. No running process found");
        } else {
            serviceInfoPanel.getLogJTextArea().append("stopping pid=" + process.pid() + "\n");
            process.destroy();
            PROCESS_CACHE.remove(serviceName);
            serviceInfoPanel.getLogJTextArea().append("stopped pid=" + process.pid() + "\n");
        }

    }


}
