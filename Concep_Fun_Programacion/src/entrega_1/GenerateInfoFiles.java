
package entrega_1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


/**
 * Clase GenerateInfoFiles. Esta clase genera archivos pseudoaleatorios para los vendedores,
 * productos y ventas con el fin de realizar pruebas sobre el procesamiento de datos.
 */

public class GenerateInfoFiles {
	
	// Arrays con datos predefinidos de nombres, apellidos y productos
    private static final String[] NOMBRES = {"Juan", "Pedro", "Maria", "Ana", "Luis"};
    private static final String[] APELLIDOS = {"Perez", "Gomez", "Rodriguez", "Fernandez", "Lopez"};
    private static final String[] PRODUCTOS = {"Laptop", "Celular", "Televisor", "Auriculares", "Teclado", "RatÃ³n", "Monitor", "Impresora", "CÃ¡mara", "Consola"};
    
    /**
     * Genera un archivo de ventas de un vendedor con datos aleatorios.
     * El archivo contendrá el número de documento del vendedor y las ventas realizadas.
     * 
     * @param randomSalesCount Cantidad de productos vendidos por el vendedor.
     * @param name Nombre del vendedor.
     * @param id Número de documento del vendedor.
     */

    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        String fileName = "ventas_" + id + ".txt";//Nombre del archivo de ventas
        try (FileWriter writer = new FileWriter(fileName)) {
        	//Escribir el encabezado del archivo de ventas (tipo de documento y número)
            writer.write("CC;" + id + "\n");
            
            //Generar ventas aleatorias
            Random random = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(PRODUCTOS.length) + 1; //ID del producto
                int cantidadVendida = random.nextInt(100) + 1; //Cantidad de productos vendidos
                writer.write(productId + ";" + cantidadVendida + "\n");
            }
            System.out.println("Archivo de ventas generado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de ventas: " + e.getMessage());
        }
    }
    
    /**
     * Genera un archivo de información de productos con datos aleatorios.
     * Cada producto tiene un ID, nombre y precio.
     * 
     * @param productsCount Cantidad de productos a generar.
     */

    public static void createProductsFile(int productsCount) {
        String fileName = "productos.txt"; //Nombre del archivo de productos.
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1; //ID del producto
                String productName = PRODUCTOS[i % PRODUCTOS.length]; //Nombre del producto
                double price = Math.round((random.nextDouble() * 100) * 100.0) / 100.0; //Se genera el precio del producto.
                writer.write(productId + ";" + productName + ";" + price + "\n");
            }
            System.out.println("Archivo de productos generado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de productos: " + e.getMessage());
        }
    }
    
    
    /**
     * Genera un archivo con la información de los vendedores.
     * Cada vendedor tiene un tipo de documento, número de documento, nombre y apellido.
     * También se generan archivos de ventas correspondientes a cada vendedor.
     * 
     * @param salesmanCount Cantidad de vendedores a generar.
     */

    public static void createSalesManInfoFile(int salesmanCount) {
        String fileName = "vendedores.txt"; //Nombre del archivo de vendedores.
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                String tipoDocumento = "CC"; // Tipo de documento (cédula de ciudadanía)
                long numeroDocumento = 1000000000L + random.nextInt(1000000000); // Número de documento aleatorio
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)]; // Se genera un nombre aleatorio
                String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)]; // Se genera un apellido aleatorio
                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n");

                // Generar archivo de ventas para cada vendedor creado
                String nombreCompleto = nombre + " " + apellido;
                int numeroVentas = random.nextInt(10) + 1;  // Genera entre 1 y 10 ventas
                createSalesMenFile(numeroVentas, nombreCompleto, numeroDocumento);
            }
            System.out.println("Archivo de vendedores generado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de vendedores: " + e.getMessage());
        }
    }
    
    /**
     * Método principal que ejecuta la generación de archivos.
     * Genera un archivo de vendedores con sus ventas correspondientes y un archivo de productos.
     * 
     */

    public static void main(String[] args) {
        // Generar archivo de vendedores y sus archivos de ventas correspondientes
        createSalesManInfoFile(3);  // Crea 3 vendedores.

        // Generar el archivo de productos
        createProductsFile(5);  // Genera 5 productos
    }
}
