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
     * Formato esperado en cada línea: nombre, tipo
     * Tipos válidos: prioridad_alta, prioridad_media, prioridad_baja
     * 
     * @param rutaArchivo ruta completa del archivo CSV
     * @return arreglo de usuarios leídos del archivo
     * @throws Exception si hay error al leer el archivo o formato incorrecto
     */
    public static Usuario[] cargarUsuarios(String rutaArchivo) throws Exception {
        // Validar extensión
        if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
            throw new Exception("El archivo debe tener extensión .csv");
        }
        
        // Contar líneas válidas primero
        int cantidadLineas = contarLineasValidas(rutaArchivo);
        
        // Crear arreglo de usuarios
        Usuario[] usuarios = new Usuario[cantidadLineas];
        
        // Leer el archivo
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
        String linea;
        int indice = 0;
        int numeroLinea = 0;
        
        while ((linea = br.readLine()) != null) {
            numeroLinea++;
            
            // Ignorar líneas vacías
            if (linea.trim().isEmpty()) {
                continue;
            }
            
            // Dividir la línea por coma
            String[] partes = linea.split(",");
            
            // Validar que tenga exactamente dos partes
            if (partes.length != 2) {
                br.close();
                throw new Exception("Error en línea " + numeroLinea + 
                    ": formato incorrecto. Se esperaba 'usuario, tipo'");
            }
            
            String nombre = partes[0].trim();
            String tipoTexto = partes[1].trim();
            
            // Validar que el nombre no esté vacío
            if (nombre.isEmpty()) {
                br.close();
                throw new Exception("Error en línea " + numeroLinea + 
                    ": el nombre del usuario no puede estar vacío");
            }
            
            // Validar que el tipo sea exactamente uno de los permitidos
            TipoUsuario tipo = null;
            
            if (tipoTexto.equals("prioridad_alta")) {
                tipo = TipoUsuario.prioridad_alta;
            } else if (tipoTexto.equals("prioridad_media")) {
                tipo = TipoUsuario.prioridad_media;
            } else if (tipoTexto.equals("prioridad_baja")) {
                tipo = TipoUsuario.prioridad_baja;
            } else {
                br.close();
                throw new Exception("Error en línea " + numeroLinea + 
                    ": tipo de usuario inválido '" + tipoTexto + 
                    "'. Use: prioridad_alta, prioridad_media o prioridad_baja");
            }
            
            // Crear el usuario
            usuarios[indice] = new Usuario(nombre, tipo);
            indice++;
        }
        
        br.close();
        
        return usuarios;
    }
    
    /**
     * Cuenta cuántas líneas válidas tiene un archivo (ignorando vacías)
     * 
     * @param rutaArchivo ruta del archivo
     * @return número de líneas no vacías
     * @throws Exception si hay error al leer
     */
    private static int contarLineasValidas(String rutaArchivo) throws Exception {
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
