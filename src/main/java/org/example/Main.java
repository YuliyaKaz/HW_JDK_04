package org.example;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите количество потоков для выгрузки: ");
        Scanner in = new Scanner(System.in);
        int nTheads = in.nextInt();

        String[] fileURLs = {
                "https://gbcdn.mrgcdn.ru/uploads/asset/5580403/attachment/0b560005794a2544c604ab61930eb963.pdf",
                "https://gbcdn.mrgcdn.ru/uploads/asset/5580414/attachment/016e87c1fd9385be4e385dd4aca58d1a.pdf",
                "https://gbcdn.mrgcdn.ru/uploads/asset/5580366/attachment/736123b61417d7353e689b44cf2fc46f.pdf"
        };
        String saveDir = "/home/yuliya/Downloads/Examples/"; //директория для сохранения файлов

        ExecutorService executorService = Executors.newFixedThreadPool(nTheads); // Пул из потоков

        for (String fileURL : fileURLs) {
            FileLoader downloader = new FileLoader(fileURL, saveDir);
            executorService.submit(downloader);
        }

        executorService.shutdown(); // Остановка пула после завершения всех задач
    }
}