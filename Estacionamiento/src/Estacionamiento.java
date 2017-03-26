
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.io.*;

public class Estacionamiento implements Serializable {

//////////////////ATRIBUTOS///////////////
    Auto edificio[][];
    float costo;
    int hr_entrada, min_entrada, hr_salida, min_salida;
    int dif_hr;
    int dif_min;
    float total;
    ObjectOutputStream salida;
    ObjectInputStream leer;
    File arch = null;
    String path = ""; //Dege ingresar una ruta para la creacion del archivo.

    ///////////////METODOS///////////////////
    Estacionamiento(int fil, int col) {
        try {
            edificio = new Auto[fil][col];
            for (int i = 0; i < edificio.length; i++) {
                for (int j = 0; j < edificio[i].length; j++) {
                    edificio[i][j] = null;
                }
            }
        } catch (NegativeArraySizeException arreglonegativo) {
            edificio = new Auto[3][3];
            for (int i = 0; i < edificio.length; i++) {
                for (int j = 0; j < edificio[i].length; j++) {
                    edificio[i][j] = null;
                }
            }
        }
    }

    public void entrar(int piso, int cajon) throws IOException {
        GregorianCalendar hr_e = new GregorianCalendar();
        try {
            if (edificio[piso - 1][cajon - 1] != null) {
                throw new ExcepInventada();
            } else {
                edificio[piso - 1][cajon - 1] = new Auto();
                System.out.println("Ha ingresado un auto con las siguientes caracteristicas");
                edificio[piso - 1][cajon - 1].crear_auto();
                hr_entrada = edificio[piso - 1][cajon - 1].hr_en = hr_e.get(Calendar.HOUR_OF_DAY);
                min_entrada = edificio[piso - 1][cajon - 1].min_en = hr_e.get(Calendar.SECOND);
                System.out.println("Hora de entrada " + edificio[piso - 1][cajon - 1].hr_en + ":" + edificio[piso - 1][cajon - 1].min_en + "\n");
                monitorear_espacios();

            }
        } catch (ArrayIndexOutOfBoundsException arreglolimites) {
            System.out.println("INGRESE VALORES VALIDOS DEL EDIFICIO\n");
        } catch (ExcepInventada ocupado) {
            System.out.println("ESE CAJON YA ESTA OCUPADO\n");
        }
    }

    public void salir(int piso, int cajon) throws IOException {
        GregorianCalendar hr_s = new GregorianCalendar();
        Random r = new Random();
        float cn, cd;
        try {
            System.out.println("Ha salido un auto con las siguientes caracteristicas");
            edificio[piso - 1][cajon - 1].caracteristicas();

            hr_salida = edificio[piso - 1][cajon - 1].hr_sa = hr_s.get(Calendar.HOUR_OF_DAY) + r.nextInt(2) + 1;
            min_salida = edificio[piso - 1][cajon - 1].min_sa = hr_s.get(Calendar.SECOND);
            System.out.println("Hora de entrada " + edificio[piso - 1][cajon - 1].hr_en + ":" + edificio[piso - 1][cajon - 1].min_en + "\n");
            System.out.println("Hora de salida " + edificio[piso - 1][cajon - 1].hr_sa + ":" + edificio[piso - 1][cajon - 1].min_sa + "\n");
            System.out.println("Se quedo " + edificio[piso - 1][cajon - 1].dia + " dias completos y " + edificio[piso - 1][cajon - 1].noche + " noches. ");
            cn = edificio[piso - 1][cajon - 1].noche * 400;
            cd = edificio[piso - 1][cajon - 1].dia * 216;
            edificio[piso - 1][cajon - 1] = null;

            monitorear_espacios();
            calcular_pago(cn, cd);
        } catch (ArrayIndexOutOfBoundsException arreglolimites) {
            System.out.println("INGRESE VALORES VALIDOS DEL EDIFICIO\n");
        } catch (NullPointerException vacio) {
            System.out.println("NO HAY UN AUTO EN ESE CAJON\n");
        }

    }

    public void mostrar_info(int piso, int cajon) {
        try {
            edificio[piso - 1][cajon - 1].caracteristicas();
        } catch (NullPointerException vacio) {
            System.out.println("NO HAY AUTO EN ESE CAJON\n");
        }
    }

    private void calcular_pago(float cn, float cd) {
        int min_total, min_30, hr, min, min_individuales;
        dif_hr = hr_salida - hr_entrada;
        dif_min = min_salida - min_entrada;
        min_total = (dif_hr * 60) + dif_min;
        hr = min_total / 60;
        min = min_total % 60;
        min_30 = min / 30;
        min_individuales = min % 30;
        costo = (hr * 18.0f) + (min_30 * 10.0f) + (min_individuales * 0.40f) + cn + cd;
        System.out.println("Usted debe pagar: $" + costo + "0");

    }

    public void monitorear_espacios() {
        for (int i = 0; i < edificio.length; i++) {
            for (int j = 0; j < edificio[i].length; j++) {
                if (edificio[i][j] == null) {
                    System.out.print("\033[31m x ");
                }
                if (edificio[i][j] != null) {
                    System.out.print("\033[32m o ");
                }

            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void salir() {
        try {
            salida = new ObjectOutputStream(new FileOutputStream(path));
        } catch (IOException ex) {
            System.out.println("Error de archivo");
        }
        int fila, columna;
        for (int i = 0; i < edificio.length; i++) {
            for (int j = 0; j < edificio[i].length; j++) {
                if (edificio[i][j] != null) {
                    edificio[i][j].noche++;
                    if (edificio[i][j].noche >= 2) {
                        edificio[i][j].dia++;
                    }
                    edificio[i][j].f = i;
                    edificio[i][j].c = j;
                    escribir(edificio[i][j]);
                }
            }
        }
        try {
            salida.close();
        } catch (IOException ex) {
            System.out.println("No se pudo cerrar el archivo");
        } catch (NullPointerException e) {

        }
    }

    public void escribir(Auto b) {

        try {
            salida.writeObject(b);
            //System.out.println("Se escribio archivo " + b.placa);
        } catch (IOException ex) {
            System.out.println("No se pudo escribir objeto");
        }
    }

    public void comprobar_archivo() {
        arch = new File(path);
        if (arch.exists()) {
            leer_archivo();
        } else {
            try {
                salida = new ObjectOutputStream(new FileOutputStream(path));
            } catch (IOException ex) {
                System.out.println("Error de archivo");
            }
        }
    }

    public void leer_archivo() {
        int fila, columna;
        try {
            leer = new ObjectInputStream(new FileInputStream(path));
            Auto e;

            while (true) {
                e = (Auto) leer.readObject();
                fila = e.f;
                columna = e.c;
                edificio[fila][columna] = e;
                //System.out.println("Se ha leido auto " + e.placa);
            }
        } catch (EOFException ex) {
            try {
                leer.close();
            } catch (IOException exc) {
                System.out.println("Error al cerrar archivo");
            }
        } catch (IOException ex) {
            System.out.println("No se pudo escribir objeto");
        } catch (ClassNotFoundException ex) {

        }
    }

}
