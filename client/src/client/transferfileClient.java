package client;

import java.io.*;
import java.net.Socket;

public class transferfileClient {

public static int type;
        Socket ClientSoc;

        DataInputStream din;
        DataOutputStream dout;
        BufferedReader br;
        public transferfileClient(Socket soc)
        {
            try
            {
                ClientSoc=soc;
                din=new DataInputStream(ClientSoc.getInputStream());
                dout=new DataOutputStream(ClientSoc.getOutputStream());
                br=new BufferedReader(new InputStreamReader(System.in));
            }
            catch(Exception ex)
            {
            }
        }
        void SendFile() throws Exception
        {

            String filename;
            System.out.print("Enter File Name :");
            filename=br.readLine();

            File f=new File(filename);
            if(!f.exists())
            {
                System.out.println("File not Exists...");
                dout.writeUTF("File not found");
                return;
            }

            dout.writeUTF(filename);

            String msgFromServer=din.readUTF();
            if(msgFromServer.compareTo("File Already Exists")==0)
            {
                String Option;
                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                Option=br.readLine();
                if(Option=="Y")
                {
                    dout.writeUTF("Y");
                }
                else
                {
                    dout.writeUTF("N");
                    return;
                }
            }

            System.out.println("Sending File ...");
            FileInputStream fin=new FileInputStream(f);
            int ch;
            do
            {
                ch=fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);
            fin.close();
            System.out.println(din.readUTF());

        }

        void ReceiveFile(String path) throws Exception
        {
            String msgFromServer=din.readUTF();

            if(msgFromServer.compareTo("File Not Found")==0)
            {
                System.out.println("File not found on Server ...");
                return;
            }
            //else
            if(msgFromServer.compareTo("READY")==0)
            {
                System.out.println("Receiving File ...");
                File f=new File(path);
         /*if(f.exists())
         {
             String Option;
             System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
             Option=br.readLine();
             if(Option=="N")
             {
                 dout.flush();
                 return;
             }
         }*/
                FileOutputStream fout=new FileOutputStream(f);
                int ch=0;
                String temp;
                do
                {
                    temp=din.readUTF();
                    if(temp.compareTo("\\0")!=0)
                    {
                        fout.write(temp.getBytes());
                        fout.write(new String("\n").getBytes());
                    }
                }while(temp.compareTo("\\0")!=0);
                fout.close();
                System.out.println(din.readUTF());

            }


        }

        public void displayMenu(String imag,String filter,Socket socket) throws Exception
        {
            InputStream ipS =socket.getInputStream();
            OutputStream opS=socket.getOutputStream();
            ObjectInputStream ObInput=new ObjectInputStream(ipS);
            ObjectOutputStream obj=new ObjectOutputStream(opS);
            Data d=new Data(new File(imag),filter);
            obj.writeObject(d);
        }
    }


