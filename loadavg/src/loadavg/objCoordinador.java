package loadavg;

import java.rmi.RemoteException;
import java.rmi.Remote;


public interface objCoordinador extends Remote{
    int iniMonitor() throws RemoteException;

    void loadMonitor(String loadavg) throws RemoteException;

    int iniClient(int segundos) throws RemoteException;

    String getLoadAvg() throws RemoteException;
}
