package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

public class NewFram extends JFrame{
    private  String icon;
    private String imageFiltre;
    public NewFram(String icon,String imageFiltre) throws HeadlessException {
        this.icon = icon;
        this.imageFiltre=imageFiltre;
    }
    public void newFram(){
        JFrame f=new JFrame();

        /**----------------------------------------------------*/
        f.setLayout(null);
        //setting the bounds for the JFrame
        f.setBounds(100,100,645,470);
         Border br = BorderFactory.createLineBorder(Color.black);
        // Container c=getContentPane();
        //Creating a JPanel for the JFrame
        //JPanel panel=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        //setting the panel layout as null
        // panel.setLayout(null);
       // panel2.setLayout(null);
       // panel3.setLayout(null);
        //adding a label element to the panel
        //JLabel label=new JLabel("Panel 1");
        JLabel label1=new JLabel("image orgine: ");
        JLabel label2=new JLabel(new ImageIcon(this.icon));
        JLabel label4=new JLabel("image filtered: ");
        //filter(this.icon);
       // sobel_edge_detecting(this.icon);
        JLabel label3=new JLabel(new ImageIcon(this.imageFiltre));


        //label.setBounds(120,50,200,50);
        label2.setBounds(120,50,200,50);
        label3.setBounds(120,50,200,50);
        // panel.add(label);
        //panel2.add(label2);
        panel3.add(label4);
        panel3.add(label3);


        // changing the background color of the panel to yellow

        //Panel 2
        //panel2.setBackground(Color.red);
        panel2.setBounds(10,10,600,300);

        //Panel 3
        label4.setBackground(Color.green);
        label1.setBackground(Color.blue);
        panel3.setBounds(10,300,600,300);
        // Panel border
        //panel.setBorder(br);
        panel2.add(label1);
        panel2.setBorder(br);
        panel3.setBorder(br);
        panel2.add(label2);
       // f.add(label2);
        f.add(panel2);
        f.add(panel3);
        System.out.println(this.icon);
        /**----------------------------------------------------*/
       // f.add(lb);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(600,600);
        f.setTitle("frame1");
        //f.pack();
        f.setBackground(Color.CYAN);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public static void filter(String image){
        BufferedImage img = null;
        File f = null;

        //read image
        try{
            f = new File(image);
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;


                img.setRGB(x, y, p);
            }
        }

        //write image
        try{
            f = new File("C:\\Users\\dell\\Desktop\\M1_exercice\\Java\\RMI\\Output.jpg");
            ImageIO.write(img, "jpg", f);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    public static  void sobel_edge_detecting(String img){

        File file = new File(img);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int x = image.getWidth();
        int y = image.getHeight();

        int maxGval = 0;
        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int val00 = getGrayScale(image.getRGB(i - 1, j - 1));
                int val01 = getGrayScale(image.getRGB(i - 1, j));
                int val02 = getGrayScale(image.getRGB(i - 1, j + 1));

                int val10 = getGrayScale(image.getRGB(i, j - 1));
                int val11 = getGrayScale(image.getRGB(i, j));
                int val12 = getGrayScale(image.getRGB(i, j + 1));

                int val20 = getGrayScale(image.getRGB(i + 1, j - 1));
                int val21 = getGrayScale(image.getRGB(i + 1, j));
                int val22 = getGrayScale(image.getRGB(i + 1, j + 1));

                int gx =  ((-1 * val00) + (0 * val01) + (1 * val02))
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                int gy =  ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if(maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int)(edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                image.setRGB(i, j, edgeColor);
            }
        }

        File outputfile = new File("C:\\Users\\dell\\Desktop\\M1_exercice\\Java\\RMI\\Output.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("max : " + maxGradient);
        System.out.println("Finished");
    }
    public static int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        //int gray = (r + g + b) / 3;

        return gray;
    }

}
