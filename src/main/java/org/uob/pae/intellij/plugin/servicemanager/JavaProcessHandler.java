package org.uob.pae.intellij.plugin.servicemanager;

import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

public class JavaProcessHandler {
    private static final String pocCacheFile = "";
    final static Map<String, Process> processCache = new ConcurrentHashMap<>();

    public static void startProcess(RestServiceInfoPanel serviceInfoPanel) {

        String service = serviceInfoPanel.getServiceName();

        try {
            processCache.computeIfPresent(service, (k, v) -> {
                serviceInfoPanel.getLogJTextArea().append("Found existing process. PID =" + v.pid() + " This process will be terminated!\n");
                v.destroy();
                return null;
            });

            var deployFolderPath = MasterConfigInfoPanel.getInstance().getDeployFolderJTextField().getText();
            var logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();
            String[] args = MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n");
            String[] cmdArr = new String[args.length + 3];
            cmdArr[0] = "java";
            System.arraycopy(args, 0, cmdArr, 1, args.length);
            cmdArr[args.length + 1] = "-jar";
            cmdArr[args.length + 2] = deployFolderPath + "/" + service + "-fat.war";

            serviceInfoPanel.getLogJTextArea().append(Arrays.toString(cmdArr) + "\n");

            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(new File(logPath + "/" + service + ".out"));
            Process proc = processBuilder.start();
            serviceInfoPanel.getLogJTextArea().append("############" + service + " [ process started Process Id - " + proc.pid() + "] ###############\n");
            processCache.put(service, proc);

        } catch (Exception e) {
            serviceInfoPanel.getLogJTextArea().append("Error starting process " + (e.getMessage() != null ? e.getMessage() : e) + "/n");
        }

    }

    public static void stopProcess(RestServiceInfoPanel serviceInfoPanel) throws Exception {
        String serviceName = serviceInfoPanel.getServiceName();
        Process process = processCache.get(serviceName);

        serviceInfoPanel.getLogJTextArea().append("Stopping process..." + "\n");
        if (process == null) {
            serviceInfoPanel.getLogJTextArea().append("Stopping process failed!!. No running process found");
        } else {
            System.out.println("stopping pid=" + process.pid());
            process.destroy();
        }

    }

}
