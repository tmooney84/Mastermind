package org.mastermind.db;

import java.io.File;

public class AppDataPath {
    public static String getAppDataDirectory(String appName) {
        String os = System.getProperty("os.name").toLowerCase();
        String basePath;

        if (os.contains("win")) {
            basePath = System.getenv("APPDATA");
        } else if (os.contains("mac")) {
            basePath = System.getProperty("user.home") + "/Library/Application Support";
        } else {
            basePath = System.getProperty("user.home");
        }

        String appDataPath;

        if (os.contains("linux")) {
            appDataPath = basePath + "/." + appName;
        } else {
            appDataPath = basePath + "/" + appName;
        }

        File dir = new File(appDataPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return appDataPath;
    }


}
