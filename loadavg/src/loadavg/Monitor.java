package loadavg;

import loadavg.objMonitor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Monitor extends UnicastRemoteObject implements objMonitor{
    public Monitor() throws RemoteException {
        super();
    }

    @Override
    public void pingMonitor() throws RemoteException {

    }
}
