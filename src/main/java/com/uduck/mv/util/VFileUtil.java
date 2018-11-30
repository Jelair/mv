package com.uduck.mv.util;

import java.io.File;

public class VFileUtil {

    /**
     * 判断是不是一个文件
     * @param path
     * @return
     */
    public static boolean checkFile(String path){
        File file = new File(path);
        if (file.isFile()){
            return true;
        }
        return false;
    }

    /**
     * 提取文件格式
     * @param path
     * @return
     */
    public static String extractFmt(String path){
        if (checkFile(path)){
            String fmt = path.substring(path.lastIndexOf(".") + 1, path.length());
            return fmt;
        }
        return null;
    }

    /**
     * 提取文件名
     * @param path
     * @return
     */
    public static String extractName(String path){
        if (checkFile(path)){
            String name = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
            return name;
        }
        return null;
    }

    /**
     * 判断文件类型
     * @param path
     * @return
     */
    public static int checkContentType(String path){
        String fmt = extractFmt(path);
        if (fmt == null){
            return -1;
        }
        String type = fmt.toLowerCase();
        // ffmpeg 能解析的格式：（asx, asf, mpg, wmv, 3gp, mp4, mov, avi, flv等）
        // 对ffmpeg无法解析的文件格式（wmv9，rm, rmvb）,可以用 mencoder 解析
        if (type.equals("asx")||type.equals("asf")||type.equals("mpg")||type.equals("wmv")||type.equals("3gp")){
            return 0;
        } else if(type.equals("mp4")||type.equals("mov")||type.equals("avi")||type.equals("flv")){
            return 0;
        }else if (type.equals("wmv9")||type.equals("rm")||type.equals("rmvb")){
            return 1;
        }
        return -1;
    }
}
