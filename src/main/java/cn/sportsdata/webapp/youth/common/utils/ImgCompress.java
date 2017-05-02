/**
 * 
 */
package cn.sportsdata.webapp.youth.common.utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;  
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;  
import com.drew.metadata.exif.ExifIFD0Directory;  

/**
 * @author king
 *
 */
public class ImgCompress {



	/**
	 * resize base on fixed width
	 * 
	 * @param w int
	 */
//	public static boolean resizeByWidth(String srcFilePath, int w, String outPath) throws IOException {
//		File file = new File(srcFilePath);
//		BufferedImage img = ImageIO.read(file); 
//		int width = img.getWidth(null);
//		int high = img.getHeight(null);
//		
//		if (w > width) {
//			return false;
//		}
//		
//		int h = (int) (high * w / width);
//		resize(img, w, h, outPath);
//		return true;
//	}
	
    public static BufferedImage toBufferedImage(Image image) {  
        if (image instanceof BufferedImage) {  
            return (BufferedImage) image;  
        }  
        // This code ensures that all the pixels in the image are loaded  
        image = new ImageIcon(image).getImage();  
        BufferedImage bimage = null;  
        GraphicsEnvironment ge = GraphicsEnvironment  
                .getLocalGraphicsEnvironment();  
        try {  
            int transparency = Transparency.OPAQUE;  
            GraphicsDevice gs = ge.getDefaultScreenDevice();  
            GraphicsConfiguration gc = gs.getDefaultConfiguration();  
            bimage = gc.createCompatibleImage(image.getWidth(null),  
                    image.getHeight(null), transparency);  
        } catch (HeadlessException e) {  
            // The system does not have a screen  
        }  
        if (bimage == null) {  
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;  
            bimage = new BufferedImage(image.getWidth(null),  
                    image.getHeight(null), type);  
        }  
        // Copy image to buffered image  
        Graphics g = bimage.createGraphics();  
        // Paint the image onto the buffered image  
        g.drawImage(image, 0, 0, null);  
        g.dispose();  
        return bimage;  
    }  
    
    
    /** 
     * 图片翻转时，计算图片翻转到正常显示需旋转角度  
     */  
    public static int getRotateAngleForPhoto(String fileName){        
        File file = new File(fileName);  
        int angel = 0;  
        Metadata metadata;  
          
        try{  
            metadata = JpegMetadataReader.readMetadata(file);  
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
      
            if(directory!=null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){   
                // Exif信息中方向　　  
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);   
                // 原图片的方向信息  
                if(6 == orientation ){  
                    //6旋转90  
                    angel = 90;  
                }else if( 3 == orientation){  
                   //3旋转180  
                    angel = 180;  
                }else if( 8 == orientation){  
                   //8旋转90  
                    angel = 270;  
                }  
            }  
        } catch(JpegProcessingException e){  
            e.printStackTrace();  
        } catch(MetadataException e){  
            e.printStackTrace();  
        } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return angel;  
    }  
    
    
    /** 
    * 计算旋转参数 
    */  
    public static Rectangle CalcRotatedSize(Rectangle src,int angel){  
        // if angel is greater than 90 degree,we need to do some conversion.  
        if(angel > 90){  
            if(angel / 9%2 ==1){  
                int temp = src.height;  
                src.height = src.width;  
                src.width = temp;  
            }  
            angel = angel % 90;  
        }  
          
        double r = Math.sqrt(src.height * src.height + src.width * src.width ) / 2 ;  
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;    
        double angel_dalta_width = Math.atan((double) src.height / src.width);    
        double angel_dalta_height = Math.atan((double) src.width / src.height);    
      
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha    
                - angel_dalta_width));    
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha    
                - angel_dalta_height));    
        int des_width = src.width + len_dalta_width * 2;    
        int des_height = src.height + len_dalta_height * 2;    
        return new Rectangle(new Dimension(des_width, des_height));    
    }  
    
	/**
	 * resize base on fixed width
	 * 
	 * @param w int
	 */
	public static boolean resizeByFixedWidthAndHigh(String srcFilePath, int w, int h, String outPath) throws IOException {
		File file = new File(srcFilePath);
	    Image src=Toolkit.getDefaultToolkit().getImage(file.getPath());  
	    BufferedImage img = toBufferedImage(src);//Image to BufferedImage  
	    
	    BufferedImage  imgRotated = null;
	    int angel = getRotateAngleForPhoto(srcFilePath);  
        if(angel == 0){  
            //图片正常，不处理图片  
        	imgRotated = img;  
        }else{  
            //图片被翻转，调整图片  
            int src_width = img.getWidth(null);  
            int src_height = img.getHeight(null);  
            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);  
               
            imgRotated = new BufferedImage(rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);  
            Graphics2D g2 = imgRotated.createGraphics();  

            g2.translate((rect_des.width - src_width) / 2,  
                        (rect_des.height - src_height) / 2);  
            g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  

            g2.drawImage(img, null, null);  
        }  
        
        if(imgRotated == null) {
        	return false;
        }
        
		int width = imgRotated.getWidth(null);
		int high = imgRotated.getHeight(null);
		
		if (w > width || h > high) {
			if (width > high) {
				h = high;
				w = high;
			} else {
				h = width;
				w = width;
			}
		}
		
		resize(imgRotated, w, h, outPath, width, high);   
//		//BufferedImage img = ImageIO.read(file); 
//		int width = img.getWidth(null);
//		int high = img.getHeight(null);
//		
//		if (w > width || h > high) {
//			if (width > high) {
//				h = high;
//				w = high;
//			} else {
//				h = width;
//				w = width;
//			}
//		}
//		
//		resize(img, w, h, outPath, width, high);
		return true;
	}
	
	
	public static boolean fixImage(String srcFilePath, String outPath) throws IOException {
		File file = new File(srcFilePath);
		Image src = Toolkit.getDefaultToolkit().getImage(file.getPath());
		BufferedImage img = toBufferedImage(src);// Image to BufferedImage

		BufferedImage imgRotated = null;
		int angel = getRotateAngleForPhoto(srcFilePath);
		if (angel == 0) {
			// 图片正常，不处理图片
			imgRotated = img;
			return false;
		} else {
			// 图片被翻转，调整图片
			int src_width = img.getWidth(null);
			int src_height = img.getHeight(null);
			Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

			imgRotated = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = imgRotated.createGraphics();

			g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
			g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

			g2.drawImage(img, null, null);
			ImageIO.write(imgRotated, "jpeg", new File(outPath));
			return true;
		}
		
	}

	/**
	 * resize base on fixed high
	 * @param h  int
	 */
//	public static void resizeByHeight(String srcFilePath, int h, String outPath) throws IOException {
//		File file = new File(srcFilePath);
//		BufferedImage img = ImageIO.read(file); 
//		int width = img.getWidth(null);
//		int high = img.getHeight(null);
//		
//		int w = (int) (width * h / high);
//		resize(img, w, h, outPath);
//	}
	
	/**
	 * resize base on fixed width and high
	 * 
	 * @param w int
	 * @param h int
	 */
	public static void resize(BufferedImage bufferedImage, int w, int h,
			String outPath, int realWidth, int realHeight) throws IOException {
		Image image = null;
		BufferedImage temImage = null;
		if (realWidth > realHeight) {
			int offset = (realWidth - realHeight)/2;
			int lowX = offset;
			int lowY = 0;
			temImage = bufferedImage.getSubimage(lowX, lowY, realHeight, realHeight);
		} else {
			int offset = (realHeight - realWidth)/2;
			int lowX = 0;
			int lowY = offset;
			temImage = bufferedImage.getSubimage(lowX, lowY, realWidth, realWidth);
		}
		Image resultImage = temImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = outputImage.getGraphics();
		graphics.drawImage(resultImage, 0, 0, null);
		graphics.dispose();

		ImageIO.write(outputImage, "jpeg", new File(outPath));
	}
	
	public static void main(String[] args) {
		try {
			resizeByFixedWidthAndHigh("/Users/zhangdong/Documents/X/out_2222.jpg", 720, 720, "/Users/zhangdong/Documents/X/out_222211.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
