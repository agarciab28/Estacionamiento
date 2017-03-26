
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Principal {

    public static void main(String[] args) throws IOException {
        int p = 5, c = 5;
        Estacionamiento uno = new Estacionamiento(p, c);

        uno.comprobar_archivo();
        ////////////////////////MENU///////////////////////////////////
        int n = 0, pe, ce, ps, cs, pi, ci;
        Scanner leer = new Scanner(System.in);
        do {
            try {
                System.out.println("1.-Ingresar auto al estacionamiento\n2.-Sacar auto del estacionamiento\n3.-Mostrar Estacionamiento\n4.-Mostrar informacion de un auto\n5.-Salir");
                n = leer.nextInt();
                if (n != 1 && n != 2 && n != 3 && n != 4 && n != 5) {
                    throw new ExcepInventada(c);
                }
                switch (n) {
                    case 1:
                        System.out.print("¿En que piso quiere dejar su auto?(El edificio contiene " + p + " pisos): ");
                        pe = leer.nextInt();
                        System.out.print("¿En que cajon del piso " + pe + "?(Este piso contiene " + c + " cajones): ");
                        ce = leer.nextInt();
                        System.out.println("");
                        uno.entrar(pe, ce);
                        break;
                    case 2:
                        System.out.print("¿En que piso se encuentra su auto?(El edificio contiene " + p + " pisos): ");
                        ps = leer.nextInt();
                        System.out.print("¿En que cajon del piso " + ps + "?(Este piso contiene " + c + " cajones): ");
                        cs = leer.nextInt();
                        System.out.println("");
                        uno.salir(ps, cs);
                        break;
                    case 3:
                        uno.monitorear_espacios();
                        break;
                    case 4:
                        System.out.print("¿En que piso se encuentra su auto?(El edificio contiene " + p + " pisos): ");
                        pi = leer.nextInt();
                        System.out.print("¿En que cajon del piso " + pi + "?(Este piso contiene " + c + " cajones): ");
                        ci = leer.nextInt();
                        System.out.println("");
                        uno.mostrar_info(pi, ci);
                        break;
                    case 5:
                        System.out.println("HASTA MAÑANA");
                        uno.salir();
                        break;

                }

            } catch (InputMismatchException valorerr) {
                System.out.println("OPCION NO VALIDA");
                leer.nextLine();
                n = 1;
            } catch (ExcepInventada valorerror) {
                System.out.println("OPCION NO VALIDA ");
                n = 1;
            }
        } while (n > 0 && n < 5);

    }
}
