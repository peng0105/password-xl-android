package com.passwordxl;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebAppInterface {

    private final File rootPath;

    public WebAppInterface(File rootPath) {
        this.rootPath = rootPath;
    }

    @JavascriptInterface
    public String getFile(String fileName) {
        File externalFile = new File(rootPath, fileName);
        if (!externalFile.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(externalFile)) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e("password-xl", "获取文件异常", e);
        }
        return null;
    }

    @JavascriptInterface
    public void uploadFile(String fileName, String content) {
        File internalFile = new File(rootPath, fileName);
        try (FileOutputStream fos = new FileOutputStream(internalFile)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            Log.e("password-xl", "上传文件异常", e);
        }
    }

    @JavascriptInterface
    public void deleteFile(String fileName) {
        File externalFile = new File(rootPath, fileName);
        if (externalFile.exists()) {
            externalFile.delete();
        }
    }

    @JavascriptInterface
    public void setTopic(String topic) {

    }

}
