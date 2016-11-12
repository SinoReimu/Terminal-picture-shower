package mPic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagetoArrayUtils {

    public static BufferedImage readImage(String imageFile){

        File file = new File(imageFile);

        BufferedImage bf = null;

        try {

            bf = ImageIO.read(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return bf;

    }

    public static void writeImageFromBufferedImage(String imageFile, String type, BufferedImage bf){

        File file= new File(imageFile);

        try {

            ImageIO.write((RenderedImage)bf, type, file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static int[][] convertImageToArray(BufferedImage bf) {

// 获取图片宽度和高度

        int width = bf.getWidth();

        int height = bf.getHeight();

// 将图片sRGB数据写入一维数组

        int[] data = new int[width*height];

        bf.getRGB(0, 0, width, height, data, 0, width);

// 将一维数组转换为为二维数组

        int[][] rgbArray = new int[height][width];

        for(int i = 0; i < height; i++)

            for(int j = 0; j < width; j++)

                rgbArray[i][j] = data[i*width + j];

        return rgbArray;

    }

    public static int[][] convertImageToArrayByCoordinate(BufferedImage bf, int startX, int startY, int outWidth, int outHeight) {

// 获取图片宽度和高度

        int width = bf.getWidth();

        int height = bf.getHeight();

// 判断截取区域是否大于图片

        if(outWidth+startX > width  ||

        outHeight+startY > height)

        throw new IllegalArgumentException("截取区域大于图片");

// 将图片sRGB数据写入一维数组

        int[] data = new int[outWidth*outHeight];

        bf.getRGB(startX, startY, outWidth, outHeight, data, 0, outWidth);

// 将一维数组转换为为二维数组

        int[][] rgbArray = new int[outHeight][outWidth];

        for(int i = 0; i < outHeight; i++)

            for(int j = 0; j < outWidth; j++)

                rgbArray[i][j] = data[i*outWidth + j];

        return rgbArray;

    }

    public static void writeImageFromArray(String imageFile, String type, int[][] rgbArray){

// 获取数组宽度和高度

        int width = rgbArray[0].length;

        int height = rgbArray.length;

// 将二维数组转换为一维数组

        int[] data = new int[width*height];

        for(int i = 0; i < height; i++)

            for(int j = 0; j < width; j++)

                data[i*width + j] = rgbArray[i][j];

// 将数据写入BufferedImage

        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        bf.setRGB(0, 0, width, height, data, 0, width);

// 输出图片

        try {

            File file= new File(imageFile);

            ImageIO.write((RenderedImage)bf, type, file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static int[] convertARGBToARGB(int ARGB) {

        int[] rgb = new int[4];

		rgb[0] = (ARGB >> 24) & 0xff;		
		
        rgb[1] = (ARGB >> 16) & 0xff;

        rgb[2] = (ARGB >> 8) & 0xff;

        rgb[3] = ARGB & 0xff;

        return rgb;

    }

    public static int convertRGBToARGB(int r,int g, int b) {
        int color = ((0xFF << 24) |

        (r << 16) |

        (g << 8) |

        b);

        return color;

    }

}
