package com.uduck.mv.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertVideo {

    //Windows下 ffmpeg.exe的路径
    private final static String ffmpegEXE = "E:/Dance/ffmpeg/bin/ffmpeg";

    /**
     * 视频截图
     * @param whichFrame
     * @param videoPath
     * @param imagePath
     * @throws IOException
     */
    public static void extractingImage(int whichFrame, String videoPath, String imagePath) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-ss");
        command.add(String.valueOf(whichFrame));
        command.add("-i");
        command.add(videoPath);
        command.add("-f");
        command.add("image2");
        command.add(imagePath);

        executeCommand(command);
    }

    /**
     * 视频转码
     * @param inputVideo
     * @param outputVideo
     * @throws IOException
     */
    public static void formatConversion(String inputVideo, String outputVideo) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(inputVideo);
        command.add("-c");
        command.add("copy");
        command.add(outputVideo);

        executeCommand(command);
    }

    private static void executeCommand(List<String> command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        if (br != null){
            br.close();
        }
        if (inputStreamReader != null){
            inputStreamReader.close();
        }
        if (errorStream != null){
            errorStream.close();
        }
    }
}
