package cn.sportsdata.webapp.youth.common.utils.barcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.sportsdata.webapp.youth.common.utils.ConfigProps;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class ZXingCode
{
    public static BufferedImage getBarCode(String content) throws WriterException
    {
        try
        {
 
            ZXingCode zp = new ZXingCode();
 
            BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 120, 120, zp.getDecodeHintType());
            
            System.out.print( new File("Layer-0.png").getAbsolutePath());
            return zp.addLogo_QRCode(bim, ConfigProps.class.getResourceAsStream("/Layer-0.png"), new LogoConfig());
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
 
    /**
     *
     * @param qrPic
     * @param logoPic
     * @throws Exception 
     */
    public BufferedImage addLogo_QRCode(BufferedImage image, InputStream inStream, LogoConfig logoConfig) throws Exception
    {
        try
        {
            Graphics2D g = image.createGraphics();
 
            /**
             */
            BufferedImage logo = ImageIO.read(inStream);
            /**
             */
            int widthLogo = logo.getWidth(null)>image.getWidth()*5/10?(image.getWidth()*2/10):logo.getWidth(null), 
       		    heightLogo = logo.getHeight(null)>image.getHeight()*5/10?(image.getHeight()*2/10):logo.getWidth(null);
             
   		    /**
   		     */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
//            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//            g.setStroke(new BasicStroke(logoConfig.getBorder()));
//            g.setColor(logoConfig.getBorderColor());
//            g.drawRect(x, y, widthLogo, heightLogo);
             
            g.dispose();
            logo.flush();
            image.flush();
            return image;
        } catch (Exception e)
        {
            throw e;
        }
    }
 
    /**
     * @param file
     */
    public void parseQR_CODEImage(File file)
    {
        try
        {
            MultiFormatReader formatReader = new MultiFormatReader();
 
            // File file = new File(filePath);
            if (!file.exists())
            {
                return;
            }
 
            BufferedImage image = ImageIO.read(file);
 
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
 
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
 
            Result result = formatReader.decode(binaryBitmap, hints);
 
            System.out.println("result = " + result.toString());
            System.out.println("resultFormat = " + result.getBarcodeFormat());
            System.out.println("resultText = " + result.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
 
    /**
     * @param bm
     * @param imageFormat
     * @param file
     */
    public void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat, File file)
    {
        try
        {
            if (null == file || file.getName().trim().isEmpty())
            {
                throw new IllegalArgumentException("文件异常，或扩展名有问题！");
            }
 
            BufferedImage bi = fileToBufferedImage(bm);
            ImageIO.write(bi, "apng", file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
 
 
    /**
     * @param bm
     * @return
     */
    public BufferedImage fileToBufferedImage(BitMatrix bm)
    {
        BufferedImage image = null;
        try
        {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
 
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }
 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }
 
    /**
     *
     * @param content
     * @param barcodeFormat
     * @param width
     * @param height
     * @param hints
     * @return
     */
    public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
    {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try
        {
            multiFormatWriter = new MultiFormatWriter();
 
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
 
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
 
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        return image;
    }
 
    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType()
    {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);
 
        return hints;
    }
}
 
class LogoConfig
{
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;
 
    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;
 
    /**
     * Creates a default config with on color {@link #BLACK} and off color
     * {@link #WHITE}, generating normal black-on-white barcodes.
     */
    public LogoConfig()
    {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }
 
    public LogoConfig(Color borderColor, int logoPart)
    {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }
 
    public Color getBorderColor()
    {
        return borderColor;
    }
 
    public int getBorder()
    {
        return border;
    }
 
    public int getLogoPart()
    {
        return logoPart;
    }
}