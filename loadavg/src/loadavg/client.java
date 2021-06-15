package loadavg;

import loadavg.objCoordinador;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        int logiar = 0;
        int cantidad = 0;
        //Aqui se registran las funciones 
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        objCoordinador miCoordinador = (objCoordinador) registry.lookup("miCoordinador");

        String cadena;

        System.out.print("Ingrese el tiempo(segundos) entre cada peticion: ");

        Scanner leer = new Scanner(System.in);
        int seg = leer.nextInt();
        cantidad = miCoordinador.iniClient(seg);//enviamos los segundos al coordinador y obtenemos la cantidad de monitores activos
        System.out.println("Monitores activos: " + cantidad);

        do {
            cantidad = miCoordinador.iniClient(seg);//obtenemos nuevamente la 

            if (cantidad == 0) {
                esperar(seg);
            } else {
                //CICLO
                while (!(cadena = miCoordinador.getLoadAvg()).equals("MonitorFalse")) {//mientras la cadena no este vacia imprimimos

                    System.out.println(logiar + ") " + cadena);
                    logiar++;//aumentamos el contador

                    esperar(seg);//esperamos el tiempo que le usuario ingreso
                }
            }

        } while (cantidad == 0);//cuando la cantidad sea 0 es porque no hay mas monitores

        System.out.println("Cliente terminado por falta de  monitores");
    }

    public static void esperar(int x) {
        //ESPERAMOS X SEGUNDOS PARA INVOCAR DE NUEVO LA FUNCION
        try {
            Thread.sleep(x * 1000);
            System.out.println("Entro\n");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    } 
}
