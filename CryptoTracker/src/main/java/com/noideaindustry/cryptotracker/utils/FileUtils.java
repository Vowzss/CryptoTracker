package com.noideaindustry.cryptotracker.utils;

import org.simpleyaml.configuration.file.YamlFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUtils {
    public static YamlFile getYamlFile(String filename) {
        YamlFile yamlFile = new YamlFile(filename);
        try {
            if (!yamlFile.exists()) {
                saveResourceAs(filename);
                yamlFile = new YamlFile(filename);
            }
            yamlFile.loadWithComments();
            return yamlFile;
        } catch (Exception e) {
            getLogger().error("Couldn't load the file!");
            e.printStackTrace();
        }
        return null;
    }

    public static File getFile(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                saveResourceAs(filename);
                file = new File(filename);
            }
            return file;
        } catch (Exception e) {
            getLogger().error("Couldn't load the file!");
            e.printStackTrace();
        }
        return null;
    }

    private static void saveResourceAs(String filename) {
        if (filename == null || filename.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty!");

        InputStream in = getResourceInJar(filename);

        if (in == null)
            throw new IllegalArgumentException("Couldn't find this file in jar!");

        File outFile = new File(filename);

        try {
            if (!outFile.exists()) {
                getLogger().info("Creating file...");
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;
                while ((n = in.read(buf)) >= 0)
                    out.write(buf, 0, n);

                out.close();
                in.close();

                if (!outFile.exists()) {
                    getLogger().error("Couldn't create file!");
                    getLogger().error("exiting program...");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveYamlFile(YamlFile yamlFile) {
        try {
            yamlFile.save();
        } catch (IOException e) {
            getLogger().error("Couldn't save the file!");
            e.printStackTrace();
        }
    }

    private static InputStream getResourceInJar(String filename) {
        return FileUtils.class.getClassLoader().getResourceAsStream(filename);
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(FileUtils.class);
    }
}
