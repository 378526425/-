package com.wxmblog.base.common.utils;

import java.io.*;
import java.util.Scanner;

public class CPUUtils {
    /**
     * 获取当前系统CPU序列，可区分linux系统和windows系统
     */
    public static String getCpuId(){
        String osName = System.getProperty("os.name").toLowerCase();
        String cpuId="wxmblog.com";
        if (osName.contains("windows")) {
            cpuId=getCpuIdWindows();
        } else if (osName.contains("linux")) {
            cpuId=getCpuIdLinux();
        } else if (osName.contains("mac") || osName.contains("darwin")) {
            cpuId=getCpuIdMac();
        }
        return cpuId;
    }

    private static String getCpuIdWindows() {
        try {
            String cmd = "wmic cpu get ProcessorId";
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("ProcessorId")) {
                    return line.trim();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getCpuIdLinux() {
        try {
            File file = new File("/proc/cpuinfo");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("processor")) {
                    return line.split(":")[1].trim();
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getCpuIdMac() {
        try {
            String cmd = "sysctl -a";
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean foundCpuInfo = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("machdep.cpu")) {
                    return line.split(":")[1].trim();
                }
            }
            reader.close();
            if (!foundCpuInfo) {
               return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

