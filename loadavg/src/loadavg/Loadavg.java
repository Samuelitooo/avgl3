package loadavg;

import loadavg.Monitor;
import loadavg.objCoordinador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.rmi.Naming;
public class Loadavg extends Thread {

    static objCoordinador Coordinador;
    static int seg;
    static int contador = 1;
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String Ip = JOptionPane.showInputDialog("Ingresa la direccion ip del server");
        try
        {
             Coordinador = (objCoordinador) Naming.lookup("rmi://"+Ip+"/miCoordinador");//en coordinador se guarda la informacion de cada monitor
            seg = Coordinador.iniMonitor();
            
        }
        catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());}
        
        

        Loadavg ejecucion = new Loadavg();
        ejecucion.start();//se crea un hilo para ejecutar el metodo run
        
        System.out.print("Ingrese 0 para finalizar:");
        Scanner leer = new Scanner(System.in);
        contador = leer.nextInt();
    }
    
    public void run() {//corremos el monitor para mostrar el resultado de proc/loadavg
        //se saca el valor del fichero
        while (contador == 1) {

            File comando = new File("/proc/loadavg");//creamos un archivo con el comando a ejecutar
            FileReader leer;//creamos la variable donde guardaremos lo leido del comando ejecutado
            String cadena = "";//cadena vacia
            try {
                leer = new FileReader(comando);//leemos el resultado del comando ejecutado
                BufferedReader leido = new BufferedReader(leer);//en la variable leido guardamos lo que contiene la variable leer
                cadena = leido.readLine();//en la variable cadena guardamos lo mismo para poder mostrarlo en pantalla
            } catch (FileNotFoundException ex) {
                System.out.print(ex.getMessage());
            } catch (IOException ex) {
                System.out.print(ex.getMessage());
            }

            try {
                Coordinador.loadMonitor(cadena);//enviamos el dato al coordinador
            } catch (RemoteException ex) {
                System.out.print(ex.getMessage());
            }

            try {
                Thread.sleep(seg * 1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }

        try {
            Coordinador.loadMonitor("MonitorFalse");//enviamos la informacion al coordinador
        } catch (RemoteException ex) {
            System.out.print(ex.getMessage());
        }

        System.out.println("Este monitor ha terminado su funcion...");
    }
    
}
