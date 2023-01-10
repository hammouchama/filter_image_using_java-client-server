package ServerSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.opencv.core.Core;

import Ressource.FilterFunctions;
import Server.RmiImplFilterInterface;

public class ServerSoc {
    //-Djava.library.path="C:/Users/Marwan/Downloads/opencv/build/java/x64"
    public static void main(String args[])throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ServerSocket soc = new ServerSocket(5217);
        System.out.println("Server is running on Port Number 5217");
        transferfile t = new transferfile(soc.accept());
        soc.close();
    }
}
