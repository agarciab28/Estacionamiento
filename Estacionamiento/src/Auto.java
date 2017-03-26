
import java.util.Random;
import java.io.*;

public class Auto implements Serializable {
///////ATRIBUTOS////////////   

    Random r = new Random();
    String color[] = new String[9];
    String placa;
    int modelo;
    int col = r.nextInt(9);
    public int f, c, hr_en, hr_sa, min_en, min_sa, noche = 0, dia = 0;

////////METODOS////////
    public void crear_auto() {
        color();
        modelo();
        placa();
        caracteristicas();
    }

    public void caracteristicas() {
        System.out.println("Color: " + color[col]);
        System.out.print("Placa: " + placa);
        System.out.println("");
        System.out.println("Modelo: " + modelo);
        System.out.println("");
    }

    private void color() {
        color[0] = "Amarillo";
        color[1] = "Rojo";
        color[2] = "Verde";
        color[3] = "Negro";
        color[4] = "Blanco";
        color[5] = "Azul";
        color[6] = "Morado";
        color[7] = "Rosa";
        color[8] = "Cafe";
    }

    private void modelo() {
        modelo = r.nextInt(46) + 1970;
    }

    private void placa() {
        char[] p = new char[7];
        int c;
        char a;
        for (int i = 0; i < p.length; i++) {
            if (i < 3) {
                c = r.nextInt(24) + 65;
                p[i] = (char) c;
            } else {
                c = r.nextInt(10) + 48;
                p[i] = (char) c;
            }
        }
        placa = String.valueOf(p);
    }

}
