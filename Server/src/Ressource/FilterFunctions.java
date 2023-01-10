package Ressource;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

// Creating Remote interface for our application 
public interface FilterFunctions extends Remote {
   public byte[] SmoothFilter(byte[] imgBytes) throws RemoteException;

   public byte[] SobelFilter(byte[] file) throws RemoteException;

   public void BlurFilter(String file) throws RemoteException;

   public void LaplacianFilter(String file) throws RemoteException;
}
