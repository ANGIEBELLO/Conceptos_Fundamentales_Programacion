package entrega_1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {
    private static final String[] NOMBRES = {"Juan", "Pedro", "Maria", "Ana", "Luis"};
    private static final String[] APELLIDOS = {"Perez", "Gomez", "Rodriguez", "Fernandez", "Lopez"};
    private static final String[] PRODUCTOS = {"Laptop", "Celular", "Televisor", "Auriculares", "Teclado"};
    private static final double[] PRECIOS = {1000.0, 500.0, 800.0, 50.0, 30.0}; // Precios correspondientes a los productos

    // Lista para almacenar los productos generados
    private static List<Integer> productosGenerados = new ArrayList<>();

    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        if (productosGenerados.isEmpty()) {
            System.out.println("No hay productos generados para asignar ventas.");
            return;  // Salimos si no hay productos generados
        }

        String fileName = "ventas_" + id + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("CC;" + id + "\n");
            Random random = new Random();

            for (int i = 0; i < randomSalesCount; i++) {
                // Selecciona un producto existente en lugar de un ID aleatorio
                int productId = productosGenerados.get(random.nextInt(productosGenerados.size()));
                int cantidadVendida = random.nextInt(100) + 1;
                writer.write(productId + ";" + cantidadVendida + "\n");
            }
            System.out.println("Archivo de ventas generado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de ventas: " + e.getMessage());
        }
    }

    public static void createProductsFile(int productsCount) {
        String fileName = "productos.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();

            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;  // Los IDs de productos deben comenzar desde 1
                String productName = PRODUCTOS[i % PRODUCTOS.length];
                double price = PRECIOS[i % PRECIOS.length];  // Precio correspondiente al producto

                // Guardar el ID del producto en la lista de productos generados
                productosGenerados.add(productId);

                // Escribir el producto en el archivo
                writer.write(productId + ";" + productName + ";" + price + "\n");
            }
            System.out.println("Archivo de productos generado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de productos: " + e.getMessage());
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) {
        String fileName = "vendedores.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();

            for (int i = 0; i < salesmanCount; i++) {
                String tipoDocumento = "CC";
                long numeroDocumento = 1000000000L + random.nextInt(1000000000);
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
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

    public static void main(String[] args) {
        // Primero, generar el archivo de productos para que existan productos antes de crear ventas
        createProductsFile(5);  // Crea un archivo de productos con 5 productos

        // Luego, generar archivo de vendedores y crear archivos de ventas para cada uno
        createSalesManInfoFile(3);  // Crea 3 vendedores y sus archivos de ventas correspondientes
    }
}


