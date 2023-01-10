package client;

import Ressource.FilterFunctions;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.rmi.Naming;

public class Client {
    public static String image;

    public static void main(String[] args) {
        JMenuItem i1, i2, i3, i4, i5;
        JFrame f = new JFrame();
        JPanel panel = new JPanel();
        JPanel panl2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel.setBounds(10, 10, 100, 100);
        panl2.setBounds(10, 100, 100, 100);
        panel3.setBounds(10, 200, 400, 100);
        f.setLayout(new GridLayout(0, 1));
        JLabel text = new JLabel("Choose a peicter:  ");
        text.setBounds(2, 5, 200, 10);
        panel.add(text);
        JButton b = new JButton("Choose a picture");
        JButton btn = new JButton("Processing");
        panel3.add(btn);
        b.setBounds(20, 20, 30, 40);
        b.setBounds(130, 100, 100, 40);
        panel.add(b);
        Border br = BorderFactory.createLineBorder(Color.red);
        panel.setBorder(br);
        panel.setBackground(Color.cyan);
        panl2.setBackground(Color.ORANGE);
        panel3.setBackground(Color.GREEN);
        String country[] = { "", "SmoothFilter", "SobelFilter", "BlurFilter", "LaplacianFilter" };
        JComboBox cb = new JComboBox(country);
        panl2.add(new JLabel("choose filter: "));
        panl2.add(cb);
        /*
         * JSeparator sep = new JSeparator();
         * ImageIcon img = new
         * ImageIcon("C:\\Users\\dell\\Pictures\\cocacola-600x900-min.jpg");
         * Image image= img.getImage();
         * icon=img;
         * lab = new JLabel(icon);
         * panel.add(lab);
         * panel.add(sep);
         */
        f.add(panel);
        f.add(panl2);
        f.add(panel3);
        f.setSize(300, 300);
        /* ____________________________________ */
        /*---------------------------------------*/
        b.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                // set the selection mode to directories only
                j.setMultiSelectionEnabled(true);

                // invoke the showsSaveDialog function to show the save dialog
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    image = j.getSelectedFile().getAbsolutePath();
                    text.setText(text.getText() + " " + image);
                } else {
                    JOptionPane.showMessageDialog(f, "Plase choose the image .");
                    System.exit(-1);
                }

            }
        });
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filter = (String) cb.getItemAt(cb.getSelectedIndex());
                System.out.println(image);
                System.out.println(filter);

                if (filter.length() == 0) {
                    JOptionPane.showMessageDialog(f, "Plase choose the filter .");
                    System.exit(-1);
                } else {
                    if (filter.equals("SmoothFilter") || filter.equals("SobelFilter")) {
                        try {
                            FilterFunctions stub = (FilterFunctions) Naming.lookup("rmi://localhost:2022/Test");
                            if (filter.equals("SmoothFilter")) {
                                image.replaceAll("\\\\", "/");

                                // *Create array of byte from the choosen image
                                byte[] bytes = FileUtils.readFileToByteArray(new File(image));

                                // *Apply the filter
                                byte[] res = stub.SmoothFilter(bytes);

                                // *Regenerate the new image from array of byte
                                BufferedImage bi = ImageIO.read(new ByteArrayInputStream(res));
                                File ff = new File("images\\output.png");
                                ImageIO.write(bi, "jpg", ff);

                                NewFram f = new NewFram(image, "images\\output.png");
                                f.newFram();

                            } else {
                                image.replaceAll("\\\\", "/");


                                // *Create array of byte from the choosen image
                                byte[] bytes = FileUtils.readFileToByteArray(new File(image));

                                // *Apply the filter
                                byte[] res = stub.SobelFilter(bytes);

                                // *Regenerate the new image from array of byte
                                BufferedImage bi = ImageIO.read(new ByteArrayInputStream(res));
                                File ff = new File("images\\output.png");
                                ImageIO.write(bi, "jpg", ff);


                                NewFram f = new NewFram(image, "images\\output.png");
                                f.newFram();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {

                        try {
                            Socket socket = new Socket("localhost", 5217);
                            System.out.println("connected");
                            sendFile(image, socket, filter);
                            Thread.sleep(1000);
                            reciveFile(socket);
                            NewFram nf = new NewFram(image,
                                    "C:\\Users\\dell\\Desktop\\M1_exercice\\Java\\RMI\\rmiUI\\images\\output.png");
                            nf.newFram();
                            socket.close();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }

            }
        });

        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void sendFile(String path, Socket socket, String filter) {
        try {
            DataOutputStream obOutput = new DataOutputStream(socket.getOutputStream());
            obOutput.writeUTF(filter);
            File f = new File(path);
            FileInputStream file = new FileInputStream(f);
            int ch;
            do {
                ch = file.read();
                obOutput.writeUTF(String.valueOf(ch));

            } while (ch != -1);
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void reciveFile(Socket socket) {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            File f = new File("C:\\Users\\dell\\Desktop\\M1_exercice\\Java\\RMI\\rmiUI\\images\\output.png");
            FileOutputStream fout = new FileOutputStream(f);
            int ch;
            String tmp;
            do {
                tmp = input.readUTF();
                ch = Integer.parseInt(tmp);
                if (ch != -1) {
                    fout.write(ch);
                }
            } while (ch != -1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
