package Server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.opencv.core.Core;

import Ressource.FilterFunctions;

public class ServerF extends RmiImplFilterInterface {
   public ServerF() throws RemoteException {
      super();
   }

   public static void main(String args[]) {
      try {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         LocateRegistry.createRegistry(2022);
         // Instantiating the implementation class
         // RmiImplHelloInterface obj = new RmiImplHelloInterface();
         FilterFunctions obj = new RmiImplFilterInterface();
         Naming.rebind("rmi://localhost:2022/Test", (FilterFunctions)obj);
         // Exporting the object of implementation class
         // (here we are exporting the remote object to the stub)
         // !Hello skeleton = (Hello) UnicastRemoteObject.exportObject(obj, 0);

         // Binding the remote object (stub) in the registry
         // !Registry registry = LocateRegistry.getRegistry(2022);

         // !registry.bind("Test", skeleton );
         System.out.println("Server ready ....");
      } catch (Exception e) {
         System.err.println("Server exception: " + e.toString());
         e.printStackTrace();
      }
   }
}
