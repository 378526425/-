package com.wxmblog.base.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class CPUUtils {
    /**
     * 获取当前系统CPU序列，可区分linux系统和windows系统
     */
    public static String getCpuId() {
        String osName = System.getProperty("os.name").toLowerCase();
        String cpuId = "wxmblog.com";
        if (osName.contains("windows")) {
            cpuId = getCpuIdWindows();
        } else if (osName.contains("linux")) {
            cpuId = getLinuxCpuId("dmidecode -t processor | grep 'ID'", "ID", ":");
        } else if (osName.contains("mac") || osName.contains("darwin")) {
            cpuId = getCpuIdMac();
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
            return "wxmblog.com";
        }
        return "wxmblog.com";
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
            return "wxmblog.com";
        }
        return "wxmblog.com";
    }

    /**
     * 获取linux系统CPU序列
     */
    public static String getLinuxCpuId(String cmd, String record, String symbol) {

        try {
            String execResult = executeLinuxCmd(cmd);
            ;
            String[] infos = execResult.split("\n");
            for (String info : infos) {
                info = info.trim();
                if (info.indexOf(record) != -1) {
                    info.replace(" ", "");
                    String[] sn = info.split(symbol);
                    return sn[1].trim().replace(" ","");
                }
            }
        } catch (Exception e) {
            return "wxmblog.com";
        }
        return "wxmblog.com";

    }

    public static String executeLinuxCmd(String cmd) throws Exception {
        Runtime run = Runtime.getRuntime();
        Process process;
        process = run.exec(cmd);
        InputStream in = process.getInputStream();
        BufferedReader bs = new BufferedReader(new InputStreamReader(in));
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[8192];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        in.close();
        process.destroy();
        return out.toString();
    }
}

