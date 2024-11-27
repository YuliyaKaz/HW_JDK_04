package org.example;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileLoader implements Runnable {
    private final String fileURL;
    private final String saveDir;

    public FileLoader(String fileURL, String saveDir) {
        this.fileURL = fileURL;
        this.saveDir = saveDir;
    }
    @Override
    public void run() {
        try {
            downloadFile(fileURL, saveDir);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файла: " + fileURL + " - " + e.getMessage());
        }
    }
    private void downloadFile(String fileUrl, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // Проверяем, что ответ от сервера - 200 (ОК)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            InputStream inputStream = new BufferedInputStream(httpConn.getInputStream());
            FileOutputStream outputStream = new FileOutputStream(saveDir + "/" + fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            System.out.println("Файл загружен: " + fileName);
        } else {
            System.out.println("Не удалось загрузить файл: " + fileURL + " - Код ответа: " + responseCode);
        }
        httpConn.disconnect();
    }
}
