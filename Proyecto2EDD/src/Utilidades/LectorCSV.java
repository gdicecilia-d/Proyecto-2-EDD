package Utilidades;

import Modelo.TipoUsuario;
import Modelo.Usuario;

/**
 * Clase encargada de leer archivos CSV y convertirlos en objetos Usuario
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class LectorCSV {
    
    /**
     * Lee un archivo CSV y devuelve un arreglo de usuarios
     * El formato esperado es: nombre, tipo
     * 
     * @param rutaArchivo ruta completa del archivo CSV
     * @return arreglo de usuarios leídos del archivo
     * @throws Exception si hay error al leer el archivo
     */
    public static Usuario[] cargarUsuarios(String rutaArchivo) throws Exception {
        // Contar cuántas líneas tiene el archivo
        int cantidadLineas = contarLineas(rutaArchivo);
        
        // Crear arreglo de usuarios
        Usuario[] usuarios = new Usuario[cantidadLineas];
        
        // Leer el archivo
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
        String linea;
        int indice = 0;
        
        while ((linea = br.readLine()) != null) {
            // Ignorar líneas vacías
            if (linea.trim().isEmpty()) {
                continue;
            }
            
            // Dividir la línea por coma
            String[] partes = linea.split(",");
            
            // Verificar que tenga dos partes (nombre y tipo)
            if (partes.length >= 2) {
                String nombre = partes[0].trim();
                String tipoTexto = partes[1].trim();
                
                // Convertir el texto del tipo a TipoUsuario
                TipoUsuario tipo = TipoUsuario.fromString(tipoTexto);
                
                // Crear el usuario
                usuarios[indice] = new Usuario(nombre, tipo);
                indice++;
            }
        }
        
        br.close();
        
        return usuarios;
    }
    
    /**
     * Cuenta cuántas líneas tiene un archivo (ignorando vacías)
     * 
     * @param rutaArchivo ruta del archivo
     * @return número de líneas no vacías
     * @throws Exception si hay error al leer
     */
    private static int contarLineas(String rutaArchivo) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
        int contador = 0;
        String linea;
        
        while ((linea = br.readLine()) != null) {
            if (!linea.trim().isEmpty()) {
                contador++;
            }
        }
        
        br.close();
        return contador;
    }
}
