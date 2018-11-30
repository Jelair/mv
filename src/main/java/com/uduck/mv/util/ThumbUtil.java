package com.uduck.mv.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ThumbUtil {

    /**
     * 按指定大小把图片进行缩放（会遵循原图高宽比例）
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void thumbResize(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).size(348,196).toFile(outputFile);
    }

    /**
     * 按照指定比例进行缩小和放大
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method02(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).scale(0.2f).toFile(outputFile);
    }

    /**
     * 按指定的大小进行缩放（不遵循原图比例）
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method03(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).size(100,100).keepAspectRatio(false).toFile(outputFile);
        // 或者 Thumbnails.of(inputFile).forceSize(100,100).toFile(outputFile);
    }

    /**
     * 旋转图片，rotate(角度),正数则为顺时针，负数则为逆时针
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method04(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).size(200,200).rotate(90).toFile(outputFile);
    }

    /**
     * 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method05(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).scale(1f).outputQuality(0.25f).toFile(outputFile);
    }

    /**
     * 给图片加水印，watermark(位置，水印图，透明度)Positions.CENTER表示加在中间
     * @param inputImage
     * @param waterMark
     * @param outputImage
     * @throws IOException
     */
    public static void method06(String inputImage, String waterMark, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File waterFile = new File(waterMark);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).size(400,400).watermark(Positions.CENTER, ImageIO.read(waterFile), 0.5f)
                .outputQuality(0.8f).toFile(outputFile);
    }

    /**
     * 用sourceRegion()实现图片裁剪
     * 图片中心300*300的区域,Positions.CENTER表示中心，还有许多其他位置可选
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method07(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).sourceRegion(Positions.CENTER,300,300).size(300,300).toFile(outputFile);
        // 图片中上区域300*300区域
        // Thumbnails.of(inputFile).sourceRegion(Positions.TOP_CENTER,300,300).size(300,300).toFile(outputFile);
        // Thumbnails.of(inputFile).sourceRegion(0,0,200,200).size(300,300).toFile(outputFile);
    }

    /**
     * 转换图片格式
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method08(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        Thumbnails.of(inputFile).scale(1f).outputFormat("png").toFile(outputImage);
    }

    /**
     * 输出成文件流OutputStream
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method09(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        OutputStream os = new FileOutputStream(outputFile);
        Thumbnails.of(inputFile).size(120,120).toOutputStream(os);
    }

    /**
     * 输出BufferedImage,asBufferedImage()返回BufferedImage
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void method10(String inputImage, String outputImage) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        BufferedImage bi = Thumbnails.of(inputFile).size(120,120).asBufferedImage();
        ImageIO.write(bi,"jpg",outputFile);
    }

    /**
     * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
     * @param inputImage
     * @param outputImage
     * @throws IOException
     */
    public static void compressImageByOriginalScale(String inputImage, String outputImage, int fWidth, int fHeight) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        BufferedImage image = ImageIO.read(inputFile);
        Thumbnails.Builder<BufferedImage> builder = null;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if ((float)fWidth / fHeight != (float)imageWidth / imageHeight){
            if ((float)fWidth / fHeight > (float)imageWidth / imageHeight){
                image = Thumbnails.of(inputFile).width(fWidth).asBufferedImage();
            } else {
                image = Thumbnails.of(inputFile).height(fHeight).asBufferedImage();
            }
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER,fWidth,fHeight).size(fWidth,fHeight);
        } else {
            builder = Thumbnails.of(image).size(fWidth, fHeight);
        }
        builder.outputFormat("jpg").toFile(outputFile);
    }

    /**
     * 根据高度压缩
     * @param inputImage
     * @param outputImage
     * @param fWidth
     * @param fHeight
     * @throws IOException
     */
    public static void compressImageByHeight(String inputImage, String outputImage, int fWidth, int fHeight) throws IOException {
        File inputFile = new File(inputImage);
        File outputFile = new File(outputImage);
        BufferedImage image = ImageIO.read(inputFile);
        Thumbnails.Builder<BufferedImage> builder = null;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if ((float)fWidth / fHeight != (float)imageWidth / imageHeight){
            image = Thumbnails.of(inputFile).height(fHeight).asBufferedImage();
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER,fWidth,fHeight).size(fWidth,fHeight);
        } else {
            builder = Thumbnails.of(image).size(fWidth, fHeight);
        }
        builder.outputFormat("jpg").toFile(outputFile);
    }


}
