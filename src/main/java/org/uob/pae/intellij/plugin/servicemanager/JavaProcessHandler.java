package org.uob.pae.intellij.plugin.servicemanager;

import org.uob.pae.intellij.plugin.servicemanager.ui.MasterConfigInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.RestServiceInfoPanel;
import org.uob.pae.intellij.plugin.servicemanager.ui.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JavaProcessHandler {

    public final static Map<String, Process> PROCESS_CACHE = new ConcurrentHashMap<>();

    public static void startProcess(RestServiceInfoPanel serviceInfoPanel) {

        String service = serviceInfoPanel.getServiceName();

        try {
            PROCESS_CACHE.computeIfPresent(service, (k, v) -> {
                serviceInfoPanel.getLogJTextArea().append("Found existing process. PID =" + v.pid() + " This process will be terminated!\n");
                v.destroy();
                return null;
            });

            var deployFolderPath = MasterConfigInfoPanel.getInstance().getDeployFolderJTextField().getText();
            var logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();
            var javaHome = MasterConfigInfoPanel.getInstance().getJavaHomeTextField().getText();

            String[] args = new String[MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n").length + 2];

            int index = 0;
            for (String param : MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n")) {
                if (!param.startsWith("#")) {
                    args[index++] = param;
                }
            }
            args[index++] = "-Dserver.port=" + getPort(serviceInfoPanel);
            args[index] = "-Dspring-boot.run.profiles=" + MasterConfigInfoPanel.getInstance().getProfileTextField().getText();

            args = Utils.cleanArray(args);//remove null values

            String[] cmdArr = new String[args.length + 4];

            cmdArr[0] = javaHome.isBlank() ? "java " : javaHome + "/bin/" + "java";
            System.arraycopy(args, 0, cmdArr, 1, args.length);
            cmdArr[args.length + 1] = "-jar";
            cmdArr[args.length + 2] = deployFolderPath + "/" + service + "-fat.war";
            cmdArr[args.length + 3] = MasterConfigInfoPanel.getInstance().getProfileTextField().getText();

            serviceInfoPanel.getLogJTextArea().append(Arrays.toString(cmdArr).replace(",", "") + "\n");

            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);
            serviceInfoPanel.getLogJTextArea().append("Echo Java Version \n");
            String log = logPath + "/" + service + ".out";
            echoJavaVersion(log, javaHome);

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(new File(log));
            Process proc = processBuilder.start();

            serviceInfoPanel.getLogJTextArea().append("############" + service + " [ process started Process Id - " + proc.pid() + "] ###############\n");
            proc.waitFor(5, TimeUnit.SECONDS);

            if (proc.isAlive()) {
                PROCESS_CACHE.put(service, proc);
            }

        } catch (Exception e) {
            serviceInfoPanel.getLogJTextArea().append("Error starting process " + (e.getMessage() != null ? e.getMessage() : e) + "/n");
        }

    }

    private static String getPort(RestServiceInfoPanel serviceInfoPanel) {

        String[] services = MasterConfigInfoPanel.getInstance().getServiceJTextArea().getText().split("\n");
        for (String service : services) {
            String[] serviceParams = service.split("=");
            if (serviceInfoPanel.getServiceName().equals(serviceParams[0])) {
                return serviceParams[1].split(":")[1];
            }
        }
        return "";
    }

    private static void echoJavaVersion(String log, String javaHome) throws IOException, InterruptedException {

        var processBuilder = new ProcessBuilder(javaHome.isBlank() ? "java" : javaHome + "/bin/" + "java", "--version");
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(new File(log));
        Process proc = processBuilder.start();
        proc.waitFor();
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

    public static void startJar(RestServiceInfoPanel serviceInfoPanel) {

        String service = serviceInfoPanel.getServiceName();

        try {
            PROCESS_CACHE.computeIfPresent(service, (k, v) -> {
                serviceInfoPanel.getLogJTextArea().append("Found existing process. PID =" + v.pid() + " This process will be terminated!\n");
                v.destroy();
                return null;
            });

            var deployFolderPath = MasterConfigInfoPanel.getInstance().getDeployFolderJTextField().getText();
            var logPath = MasterConfigInfoPanel.getInstance().getLogFolderTextField().getText();
            var javaHome = MasterConfigInfoPanel.getInstance().getJavaHomeTextField().getText();

            String[] args = new String[MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n").length + 1];

            int index = 0;
            for (String param : MasterConfigInfoPanel.getInstance().getArgsJTextArea().getText().split("\\n")) {
                if (!param.startsWith("#")) {
                    args[index++] = param;
                }
            }
            args[index] = "-Dspring-boot.run.profiles=" + MasterConfigInfoPanel.getInstance().getProfileTextField().getText();

            args = Utils.cleanArray(args);//remove null values

            String[] cmdArr = new String[args.length + 4];

            cmdArr[0] = javaHome.isBlank() ? "java " : javaHome + "/bin/" + "java";
            System.arraycopy(args, 0, cmdArr, 1, args.length);
            cmdArr[args.length + 1] = "-jar";
            cmdArr[args.length + 2] = deployFolderPath + "/" + service + ".jar";
            cmdArr[args.length + 3] = MasterConfigInfoPanel.getInstance().getProfileTextField().getText();

            serviceInfoPanel.getLogJTextArea().append(Arrays.toString(cmdArr).replace(",", "") + "\n");

            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);
            serviceInfoPanel.getLogJTextArea().append("Echo Java Version \n");
            String log = logPath + "/" + service + ".out";
            echoJavaVersion(log, javaHome);

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(new File(log));
            Process proc = processBuilder.start();

            serviceInfoPanel.getLogJTextArea().append("############" + service + " [ process started Process Id - " + proc.pid() + "] ###############\n");
            proc.waitFor(5, TimeUnit.SECONDS);

            if (proc.isAlive()) {
                PROCESS_CACHE.put(service, proc);
            }

        } catch (Exception e) {
            serviceInfoPanel.getLogJTextArea().append("Error starting process " + (e.getMessage() != null ? e.getMessage() : e) + "/n");
        }

    }

}
