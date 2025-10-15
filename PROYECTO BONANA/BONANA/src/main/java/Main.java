import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
public class Main {

   // private static Path RUTA = Paths.get("frutas.txt"); // va a la raíz de la carpeta donde está este archivo
    private static Path RUTA = Paths.get("C:/Users/Agus_Ana_María_Raúl/Agustin/DAM/SEGUNDO/ACCESO A DATOS/003 PROYECTO BONANA/BONANA/data/frutas.txt");

    public static void main(String[] args) {

        // Variables:
        boolean finalizar = true;
        int opcion;
        Scanner sc = new Scanner(System.in);
        BufferedReader archivo;
        ArrayList<Fruta> frutas = new ArrayList<>();
        ArrayList<Fruta> anadidas = new ArrayList<>();

        while (finalizar){
            System.out.println("Menu:" + "\n\t(1) Añadir Fruta" + "\n\t(2) Ver últimas frutas añadidas" +
                    "\n\t(3) Mostar Inventario" + "\n\t(4) Guardar Inventario" + "\n\t(5) Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    archivo = importarArchivo(); // Importamos el lector br del archivo .txt , si no existe lo crea
                    frutas = guardarArchivo(archivo); // Almacena el .txt leído en una ArrayList
                    anadidas = anadirFruta(anadidas); // Añadir nuevas frutas al ArrayList anadidas
                    break;

                case 2:  // Ver últimas frutas añadidas
                    listarFrutas(anadidas); // Mostrar lista de últimas frutas añadidas
                    break;

                case 3:  // Mostar Inventario
                    archivo = importarArchivo(); // Importamos el lector br del archivo .txt , si no existe lo crea
                    frutas = guardarArchivo(archivo); // Almacena el .txt leído en una ArrayList
                    listarFrutas(frutas);  // Mostrar ArrayList frutas --> Inventario
                    break;

                case 4:  // Guardar inventario
                    if (anadidas.isEmpty()){
                        System.out.println("No se ha añadido ninguna fruta al inventario");
                    } else {
                        frutas.addAll(anadidas); // Unir las frutas de anadidas en frutas
                        anadidas.clear(); // Limpiar Arraylist anadidas

                        guardarInventario(frutas);
                    }
                    break;

                case 5:  // Salir
                    finalizar = false;
                    break;
            }
        }
    }

    // Mètodos

    // Crear archivo si no existiera y obtener archivo
    public static BufferedReader importarArchivo() {
        try {
                // Crear el archivo si no existe
            if (Files.notExists(RUTA)) {  // Crear el archivo si no existe
                Files.createFile(RUTA);
                System.out.println("Archivo frutas.txt creado");
            }

                // Generar el lector BufferedReader
            BufferedReader br = Files.newBufferedReader(RUTA, StandardCharsets.UTF_8);
            System.out.println("Archivo de frutas importado correctamente.");
            return br;

        } catch (IOException e) {
            System.err.println("Error al crear o acceder al archivo: " + e.getMessage());
            return null;
        }
    }

    // Guardar archivo en ArrayList
    public static ArrayList<Fruta> guardarArchivo(BufferedReader br) {

        ArrayList<Fruta> leidos = new ArrayList<>(); // Crear el ArrayList leidos

        try {
            String linea; // Variable para guardar las líneas que se leen del archivo

                // Podemos saber cuando se ha acabado de leer el archivo cuando recibimos un null al intentar leer
            while ((linea = br.readLine()) != null){  // Mientras la lectura sea distinta de null seguirá leyendo

                String [] partes = linea.split(";", -1); // Crear una array de Strings partes, trocear la línea leída mediante .split(), parámetro carácter de separación = ";",
                                                                    // limit: -1, en caso de que un campo esté vacío (1; "Ana";;1200) no se lo saltará, sino nos devuelve la posición vacía

                if (partes.length != 4){  // Asegurar de que hayan cuatro campos en cada línea
                    System.err.println("Línea con formato incorrecto: " + linea);
                }
                try {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    int stock = Integer.parseInt(partes[3]);

                    leidos.add(new Fruta(id, nombre, precio, stock)); // Añadir al ArrayList leidos

                }catch (NumberFormatException ex){  // Excepción en caso de que id y saldo no sean números
                    System.err.println("Err: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return leidos;
    }

    // Añadir frutas
    public static ArrayList<Fruta> anadirFruta(ArrayList<Fruta> anadidas){

        Scanner sc = new Scanner(System.in);
        int id;
        String nombre;
        double precio;
        int stock;

        // Crear e inicializar las variables que luego serán los atributos del objeto Fruta
        System.out.println("Introduce el id de la fruta");
        id = sc.nextInt();
        sc.nextLine(); // limpiar el salto de línea pendiente

        do {
            System.out.println("Introduce el nombre de la fruta, más de dos caracteres");
            nombre = sc.nextLine().trim();
            if (nombre.length() <= 2){
                System.out.println("Nombre corto");
            }
        }while (nombre.length() <= 2);

        do {
            System.out.println("Introduce el precio por kilogramo de la fruta, ha de ser mayor o igual a cero");
            precio = sc.nextDouble();
            if (precio < 0){
                System.out.println("El precio no puede ser negativo");
            }
        } while (precio < 0);

        do {
            System.out.println("Introduce la cantidad de stock de la fruta, ha de ser mayor o igual a cero");
            stock = sc.nextInt();
            if (stock < 0){
                System.out.println("La cantidad de stock no puede ser negativa");
            }
        } while (stock < 0);


        // Crear objeto Fruta
        Fruta nueva = new Fruta(id, nombre, precio, stock);

        // Añadir al ArrayList frutas la nueva fruta
        anadidas.add(nueva);

        System.out.println("Fruta añadida correctamente: " + nueva);
        return anadidas;
    }

    // Mostrar la lista de frutas añadidas
    public static void listarFrutas(ArrayList<Fruta> anadidas){

        if (anadidas.isEmpty()){
            System.out.println("La lista está vacía");
        }
        for (int i = 0; i < anadidas.size(); i++){  //    for (Fruta f : frutas) {
            System.out.println(anadidas.get(i));    //      System.out.println(f);
        }
    }

    // Guardar inventario
    public static void guardarInventario(ArrayList<Fruta> frutas){

        try (
            BufferedWriter bw = Files.newBufferedWriter(RUTA, StandardCharsets.UTF_8) // Escribe y crea un archivo sino existe en esta RUTA y el conjunto de caracteres que queremos utilizar
        ) {
            for (Fruta f : frutas){
                bw.write(f.getId() + ";" + f.getNombre() + ";" + f.getPrecio() + ";" + f.getStock());
                bw.newLine(); // Salto de línea
            }
            System.out.println("Inventario guardado con éxito");
        } catch (IOException e) {
            e.printStackTrace(); // Muestra el tipo de excepción del fichero
        }
    }
}

