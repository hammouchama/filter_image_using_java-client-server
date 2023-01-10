package Server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import Ressource.FilterFunctions;

//Implementing the remote interface
public class RmiImplFilterInterface extends UnicastRemoteObject implements FilterFunctions {

    public RmiImplFilterInterface() throws RemoteException {
    }

    public byte[] SmoothFilter(byte[] imgBytes) throws RemoteException {
        System.out.println("Choosed SmoothFilter : ");

        // Reading the Image from the file and storing it in to a Matrix object
        Mat src = Imgcodecs.imdecode(new MatOfByte(imgBytes), Imgcodecs.IMREAD_UNCHANGED);

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying SmoothFilter on the Image
        Imgproc.GaussianBlur(src, dst, new Size(3, 3), 0);

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", dst, mob);

        byte[] Res = mob.toArray();

        // BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
        // Writing the image
        Imgcodecs.imwrite("images/output.png", dst);
        System.out.println("Image Processed : images/output.png");
        return Res;
    }

    public byte[] SobelFilter(byte[] imgBytes) throws RemoteException {
        System.out.println("Choosed SobelFilter : ");
        // Reading the Image from the file and storing it in to a Matrix object
        Mat src = Imgcodecs.imdecode(new MatOfByte(imgBytes), Imgcodecs.IMREAD_UNCHANGED);

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying SobelFilter on the Image
        Imgproc.Sobel(src, dst, -1, 0, 1);

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", dst, mob);

        byte[] Res = mob.toArray();

        // Writing the image
        Imgcodecs.imwrite("images/output.png", dst);
        System.out.println("Image Processed : images/output.png");
        return Res;
    }

    public void BlurFilter(String file) throws RemoteException {
        System.out.println("Choosed BlurFilter : " + file);
        // Reading the Image from the file and storing it in to a Matrix object
        Mat src = Imgcodecs.imread(file);

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying GaussianBlur on the Image
        Imgproc.blur(src, dst, new Size(45, 45), new Point(-1, -1));

        // Writing the image
        Imgcodecs.imwrite("images/output.png", dst);
        System.out.println("Image Processed : images/output.png");
    }

    public void LaplacianFilter(String file) throws RemoteException {
        System.out.println("Choosed LaplacianFilter : " + file);
        // Reading the Image from the file and storing it in to a Matrix object
        Mat src = Imgcodecs.imread(file);

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying GaussianBlur on the Image
        Imgproc.Laplacian(src, dst, 8);

        // Writing the image
        Imgcodecs.imwrite("images/output.png", dst);
        System.out.println("Image Processed : images/output.png");
    }

}