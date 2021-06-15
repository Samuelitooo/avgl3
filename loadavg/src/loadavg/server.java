package loadavg;

import loadavg.Coordinador;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class server {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("miCoordinador", new Coordinador());
        //Revisar la ip de la mequina que corre el server
        System.out.println("Servidor, corriendo...");
    }
    
}
