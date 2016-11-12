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
			int[][][] out = new int[hei][wid][3];
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

			for (int indexY = 0; indexY<hei-1;indexY+=2) { 
				sb2.append("echo -e \"");
				sb2.append(geti(out [indexY], out [indexY+1], wid));
				sb2.append("\";");
			}
			System.out.println("[HANDLE] make the picture finished, writing data to "+outpath);
			write(sb2.toString(), outpath);
			System.out.println("[HINT] data saving finished , run "+outpath+" to view the picture.");
			
    }
	private static String geti (int[][] o1, int[][] o2, int width){
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<width;i++) {
			//\x1b[38;2;%d;%d;%dm\u2580
			sb.append("\\x1b[38;2;");
			sb.append(o1[i][0]);
			sb.append(";");
			sb.append(o1[i][1]);
			sb.append(";");
			sb.append(o1[i][2]);
			sb.append("m\\x1b[48;2;");
			sb.append(o2[i][0]);
			sb.append(";");
			sb.append(o2[i][1]);
			sb.append(";");
			sb.append(o2[i][2]);
			sb.append("m\u2580");		
		}
	
		return sb.toString();
	}
	private static int[] handle (int[][] x, int y,int xs){
				int r=0,g=0,b=0;
				int o[] = new int[3];
				for(int iy = 0;iy<y;iy++)
						for(int ix=0;ix<xs;ix++){
							int a[] = ImagetoArrayUtils.convertARGBToARGB(x[iy][ix]);
							r+=a[1];
							g+=a[2];
							b+=a[3];			
						}
				r/=(xs*y);b/=(xs*y);g/=(xs*y);
				o[0] = r;
				o[1] = g;
				o[2] = b;
				return o;
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

