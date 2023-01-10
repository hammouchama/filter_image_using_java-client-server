package ServerSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

import Ressource.FilterFunctions;
import Server.RmiImplFilterInterface;

public class transferfile {
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    
    

    transferfile(Socket soc) {
        try {
            this.ClientSoc = soc;
            System.out.println("FTP Client Connected ...");
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            this.execute();

        } catch (Exception ex) {}
    }
    void SendFile(File f) {
        try {
            System.out.println("Sending File ...");
            FileInputStream fin = new FileInputStream(f);
            int ch;
            do {
                ch = fin.read();
                dout.writeUTF(String.valueOf(ch));
            } while (ch != -1);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ReceiveFile() {
        try {
            FileOutputStream fout;
            fout = new FileOutputStream(new File("images/temp.png"));
            int ch;
            String temp;
            do {
                temp = din.readUTF();
                ch = Integer.parseInt(temp);
                if (ch != -1) {
                    fout.write(ch);
                }
            } while (ch != -1);
            fout.close();
            System.out.println("Well saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void execute() {
/*         while (true) { */
            try {
                System.out.println("Waiting for Command ...");

                //Recieve Filter
                String d= din.readUTF();
                System.out.println(d);

                ReceiveFile();
                FilterFunctions obj = new RmiImplFilterInterface();
                if(d.equals("BlurFilter")){
                    System.out.println("++++ choosed Blur");
                    obj.BlurFilter("images/temp.png");
                    SendFile(new File("images/output.png"));
                }else if(d.equals("LaplacianFilter")){
                    System.out.println("++++ choosed Laplacian");
                    obj.LaplacianFilter("images/temp.png");
                    SendFile(new File("images/output.png"));
                }else{
                    System.out.println("Sir roo7");
                    return;
                }
                
            } catch (Exception ex) {} 
        /* } */
        

    }
}