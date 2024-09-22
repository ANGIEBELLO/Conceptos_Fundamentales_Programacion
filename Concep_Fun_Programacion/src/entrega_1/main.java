package entrega_1;

import java.io.*;
import java.util.*;


public class main {

    public static void main(String[] args) {
        try {
            // Leer los archivos de ventas y generar el reporte de vendedores
            Map<String, Double> ventasVendedores = procesarVentasVendedores();
            generarReporteVendedores(ventasVendedores);

            // Leer los archivos de ventas y generar el reporte de productos vendidos
            Map<Integer, Integer> productosVendidos = procesarProductosVendidos();
            generarReporteProductos(productosVendidos);

            System.out.println("Reporte generado exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al generar el reporte: " + e.getMessage());
        }
    }

    // Procesar las ventas de cada vendedor
    private static Map<String, Double> procesarVentasVendedores() throws IOException {
        Map<String, Double> ventasVendedores = new HashMap<>();

        // Leer archivo de vendedores
        try (BufferedReader br = new BufferedReader(new FileReader("vendedores.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosVendedor = linea.split(";");
                String nombreVendedor = datosVendedor[2] + " " + datosVendedor[3];
                long numeroDocumento = Long.parseLong(datosVendedor[1]);

                // Leer el archivo de ventas correspondiente al vendedor
                String archivoVentas = "ventas_" + numeroDocumento + ".txt";
                try (BufferedReader ventasReader = new BufferedReader(new FileReader(archivoVentas))) {
                    ventasReader.readLine(); // Saltar la primera línea con la info del vendedor
                    double totalVentas = 0.0;
                    String lineaVenta;
                    while ((lineaVenta = ventasReader.readLine()) != null) {
                        String[] datosVenta = lineaVenta.split(";");
                        int idProducto = Integer.parseInt(datosVenta[0]);
                        int cantidadVendida = Integer.parseInt(datosVenta[1]);
                        double precioProducto = obtenerPrecioProducto(idProducto);
                        totalVentas += cantidadVendida * precioProducto;
                    }
                    ventasVendedores.put(nombreVendedor, totalVentas);
                }
            }
        }

        return ventasVendedores;
    }

    // Obtener el precio de un producto desde el archivo de productos
    private static double obtenerPrecioProducto(int idProducto) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosProducto = linea.split(";");
                int id = Integer.parseInt(datosProducto[0]);
                if (id == idProducto) {
                    return Double.parseDouble(datosProducto[2]);
                }
            }
        }
        return -1;  // Devolver -1 si no se encuentra el producto
    }

    // Generar el archivo de reporte de vendedores ordenado por dinero recaudado
    private static void generarReporteVendedores(Map<String, Double> ventasVendedores) throws IOException {
        List<Map.Entry<String, Double>> listaVendedores = new ArrayList<>(ventasVendedores.entrySet());
        listaVendedores.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // Ordenar de mayor a menor

        try (FileWriter writer = new FileWriter("reporte_vendedores.csv")) {
            for (Map.Entry<String, Double> entry : listaVendedores) {
                writer.write(entry.getKey() + ";" + entry.getValue() + "\n");
            }
        }
    }

    // Procesar la cantidad de productos vendidos
    private static Map<Integer, Integer> procesarProductosVendidos() throws IOException {
        Map<Integer, Integer> productosVendidos = new HashMap<>();

        // Leer archivo de vendedores para procesar sus ventas
        try (BufferedReader br = new BufferedReader(new FileReader("vendedores.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosVendedor = linea.split(";");
                long numeroDocumento = Long.parseLong(datosVendedor[1]);

                // Leer el archivo de ventas correspondiente al vendedor
                String archivoVentas = "ventas_" + numeroDocumento + ".txt";
                try (BufferedReader ventasReader = new BufferedReader(new FileReader(archivoVentas))) {
                    ventasReader.readLine(); // Saltar la primera línea con la info del vendedor
                    String lineaVenta;
                    while ((lineaVenta = ventasReader.readLine()) != null) {
                        String[] datosVenta = lineaVenta.split(";");
                        int idProducto = Integer.parseInt(datosVenta[0]);
                        int cantidadVendida = Integer.parseInt(datosVenta[1]);

                        productosVendidos.put(idProducto, productosVendidos.getOrDefault(idProducto, 0) + cantidadVendida);
                    }
                }
            }
        }

        return productosVendidos;
    }

    // Generar el archivo de reporte de productos vendidos ordenado por cantidad vendida
    private static void generarReporteProductos(Map<Integer, Integer> productosVendidos) throws IOException {
        List<Map.Entry<Integer, Integer>> listaProductos = new ArrayList<>(productosVendidos.entrySet());
        listaProductos.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // Ordenar de mayor a menor

        try (FileWriter writer = new FileWriter("reporte_productos.csv")) {
            for (Map.Entry<Integer, Integer> entry : listaProductos) {
                int idProducto = entry.getKey();
                String nombreProducto = obtenerNombreProducto(idProducto);
                double precioProducto = obtenerPrecioProducto(idProducto);

                // Solo escribir en el archivo si el producto es válido
                if (nombreProducto != null && !nombreProducto.isEmpty() && precioProducto != -1) {
                    writer.write(nombreProducto + ";" + precioProducto + "\n");
                }
            }
        }
    }

    // Obtener el nombre de un producto desde el archivo de productos
    private static String obtenerNombreProducto(int idProducto) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosProducto = linea.split(";");
                int id = Integer.parseInt(datosProducto[0]);
                if (id == idProducto) {
                    return datosProducto[1];
                }
            }
        }
        return null;  // Devolver null si no se encuentra el producto
    }
}

