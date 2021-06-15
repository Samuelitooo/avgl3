package loadavg;

import loadavg.objCoordinador;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Coordinador extends UnicastRemoteObject implements objCoordinador{
    int cantidad = 0;
    int time;
    String msj = "No hay monitores en este momento";

    public Coordinador() throws RemoteException {
        super();
    }

    @Override
    public int iniMonitor() throws RemoteException {
        cantidad++;
        return time;
    }

    @Override
    public void loadMonitor(String loadavg) throws RemoteException {
        if ("MonitorFalse".equals(loadavg)) {
            cantidad--;
        }

        msj = loadavg;
    }

    @Override
    public int iniClient(int segundos) throws RemoteException {
        time = segundos;

        //Aqui se llama a ping monitor

        return cantidad;
    }

    @Override
    public String getLoadAvg() throws RemoteException {
        return msj;
    }

}
