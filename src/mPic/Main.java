package mPic;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.*;
import java.io.FileWriter;

//64 32
public class Main {
    private static int wid = 400;
    private static int hei = 236;

    public static void main(String[] args) {
			if (args.length<4) {
				System.out.println("[ERROR] args error : please input args [pic] [outwidth] [outheight] [outfilename]");
				return;			
			}
            String as = args[0];
			String widss = args[1];
			String heiss = args[2];
			String outpath = args[3];
			if (!widss.equals("def"))
				wid = Integer.parseInt(widss);
			if (!heiss.equals("def"))
				hei = Integer.parseInt(heiss);
            BufferedImage bf = ImagetoArrayUtils.readImage(as);
			int[][] out = new int[hei][wid];
            int[][] rgbArray1 = ImagetoArrayUtils.convertImageToArray(bf);
	    	StringBuilder sb = new StringBuilder();

	    	int height = rgbArray1.length;          
	    	int width = rgbArray1[0].length;
		int yscale = height/hei;
		int xscale = width/wid;
		System.out.println("[HANDLE] will cut the picture "+as+" width:"+width+" height:"+height+" to width:"+wid+" height:"+hei);
		int[][] temp = new int[yscale][xscale];
			for (int i = 0, indexY = 0; indexY<hei; i+=yscale,indexY++) {
                for (int j = 0, indexX = 0; indexX<wid; j+=xscale,indexX++) {
					for(int iy = 0;iy<yscale;iy++)
						for(int ix=0;ix<xscale;ix++){

							temp[iy][ix] = rgbArray1[(i+iy)][(j+ix)];
						}

					out[indexY][indexX] = handle(temp,yscale,xscale);
                }
            }
			StringBuilder sb2 = new StringBuilder();
			for (int indexY = 0; indexY<hei;indexY++) {
				sb2.append("echo -e \"");
                for (int indexX = 0; indexX<wid; indexX++) {
					sb2.append(geti(out[indexY][indexX]));
				}
				sb2.append("\";");
			}
			System.out.println("[HANDLE] make the picture finished, writing data to "+outpath);
			write(sb2.toString(), outpath);
			System.out.println("[HINT] data saving finished , run "+outpath+" to view the picture.");
			
    }
	private static String geti (int k){
		StringBuilder sb = new StringBuilder();
		sb.append("\\033[");
		sb.append(40+k);
		sb.append(";3m  \\033[0m");
		return sb.toString();
	}
	private static int handle (int[][] x, int y,int xs){
				int r=0,g=0,b=0;
				for(int iy = 0;iy<y;iy++)
						for(int ix=0;ix<xs;ix++){
							int a[] = ImagetoArrayUtils.convertARGBToRGB(x[iy][ix]);
							r+=a[0];
							g+=a[1];
							b+=a[2];			
						}
				r/=(xs*y);b/=(xs*y);g/=(xs*y);
					if (r>128) r = 1; else r = 0;
					if (g>128) g = 2; else g = 0;
					if (b>128) b = 4; else b = 0;
				return r+g+b;
	}
    public static void write (String content, String out) {
        try {
            File file = new File(out);
            if (file.exists()) {

                System.out.println("文件已存在");
            }
            if (file.createNewFile()){
                System.out.println("成功");
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(content);
			Runtime.getRuntime().exec("chmod +x "+file.getPath());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

