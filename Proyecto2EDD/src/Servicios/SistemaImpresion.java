package Servicios;

import Estructuras.BinaryHeap;
import Estructuras.HashTable;
import Modelo.Documento;
import Modelo.RegistroImpresion;
import Modelo.TipoUsuario;
import Modelo.Usuario;
import Utilidades.LectorCSV;

/**
 * Clase principal que coordina el sistema de cola de impresión
 * Maneja usuarios, documentos y la cola de prioridad
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class SistemaImpresion {
    // Montículo binario que funciona como cola de prioridad 
    private BinaryHeap<RegistroImpresion> colaImpresion;
    
    // Tabla hash para mapear (usuario|documento) 
    private HashTable<String, RegistroImpresion> tablaDocumentosEnCola;
    
    // Tabla hash para mapear ID del registro 
    private HashTable<Integer, String> tablaIdAClave;
    
    // Arreglo de usuarios del sistema 
    private Usuario[] usuarios;
    
    // Cantidad de usuarios actuales 
    private int totalUsuarios;
    
    // Capacidad inicial para el arreglo de usuarios 
    private static final int CAPACIDAD_USUARIOS = 20;
    
    // Reloj de la simulación 
    private RelojSimulacion reloj;
    
    /**
     * Constructor que inicializa el sistema
     */
    public SistemaImpresion() {
        this.colaImpresion = new BinaryHeap<>(10);
        this.tablaDocumentosEnCola = new HashTable<>();
        this.tablaIdAClave = new HashTable<>();
        this.usuarios = new Usuario[CAPACIDAD_USUARIOS];
        this.totalUsuarios = 0;
        this.reloj = new RelojSimulacion();
    }
    
    /**
     * Genera una clave única para la tabla hash
     * 
     * @param nombreUsuario nombre del usuario
     * @param nombreDocumento nombre del documento
     * @return clave única
     */
    private String generarClave(String nombreUsuario, String nombreDocumento) {
        return nombreUsuario + "|" + nombreDocumento;
    }
    
    /**
     * Carga usuarios desde un archivo CSV
     * 
     * @param rutaArchivo ruta del archivo CSV
     * @throws Exception si hay error al leer el archivo
     */
    public void cargarUsuariosDesdeCSV(String rutaArchivo) throws Exception {
        Usuario[] nuevosUsuarios = LectorCSV.cargarUsuarios(rutaArchivo);
        
        for (int i = 0; i < nuevosUsuarios.length; i++) {
            if (nuevosUsuarios[i] != null) {
                agregarUsuario(nuevosUsuarios[i]);
            }
        }
    }
    
    /**
     * Agrega un usuario al sistema
     * 
     * @param usuario usuario a agregar
     */
    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) return;
        
        // Verificar si ya existe
        if (buscarUsuario(usuario.getNombreUsuario()) != null) return;
        
        // Expandir arreglo si es necesario
        if (totalUsuarios >= usuarios.length) {
            expandirUsuarios();
        }
        
        usuarios[totalUsuarios] = usuario;
        totalUsuarios++;
    }
    
    /**
     * Elimina un usuario del sistema
     * Los documentos que ya están en cola no se eliminan
     * 
     * @param nombreUsuario nombre del usuario a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarUsuario(String nombreUsuario) {
        int indice = buscarIndiceUsuario(nombreUsuario);
        if (indice == -1) return false;
        
        // Mover elementos hacia la izquierda
        for (int i = indice; i < totalUsuarios - 1; i++) {
            usuarios[i] = usuarios[i + 1];
        }
        usuarios[totalUsuarios - 1] = null;
        totalUsuarios--;
        return true;
    }
    
    /**
     * Busca un usuario por su nombre
     * 
     * @param nombreUsuario nombre del usuario
     * @return el usuario o null si no existe
     */
    public Usuario buscarUsuario(String nombreUsuario) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getNombreUsuario().equals(nombreUsuario)) {
                return usuarios[i];
            }
        }
        return null;
    }
    
    /**
     * Busca el índice de un usuario por su nombre
     * 
     * @param nombreUsuario nombre del usuario
     * @return índice o -1 si no existe
     */
    private int buscarIndiceUsuario(String nombreUsuario) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getNombreUsuario().equals(nombreUsuario)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Expande el arreglo de usuarios
     */
    private void expandirUsuarios() {
        Usuario[] nuevos = new Usuario[usuarios.length * 2];
        System.arraycopy(usuarios, 0, nuevos, 0, usuarios.length);
        usuarios = nuevos;
    }
    
    /**
     * Crea un nuevo documento para un usuario
     * 
     * @param nombreUsuario nombre del usuario
     * @param nombreDoc nombre del documento
     * @param tamanio tamaño en páginas
     * @param tipo tipo de documento
     * @return true si se creó, false si el usuario no existe
     */
    public boolean crearDocumento(String nombreUsuario, String nombreDoc, int tamanio, String tipo) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;
        
        Documento documento = new Documento(nombreDoc, tamanio, tipo);
        usuario.agregarDocumento(documento);
        return true;
    }
    
    /**
     * Envía un documento a la cola de impresión
     * 
     * @param nombreUsuario nombre del usuario
     * @param nombreDocumento nombre del documento
     * @param esPrioritario true si se aplica prioridad por tipo de usuario
     * @return true si se envió, false si hubo error
     */
    public boolean enviarAImpresion(String nombreUsuario, String nombreDocumento, boolean esPrioritario) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;
        
        Documento documento = usuario.buscarDocumento(nombreDocumento);
        if (documento == null) return false;
        
        // Obtener tiempo base
        int tiempoBase = reloj.avanzarTiempo();
        
        // Calcular etiqueta de tiempo
        int etiquetaTiempo = tiempoBase;
        if (esPrioritario) {
            int prioridad = usuario.getTipo().getValorPrioridad();
            etiquetaTiempo = tiempoBase - prioridad;
        }
        
        // Crear registro 
        RegistroImpresion registro = new RegistroImpresion(documento, etiquetaTiempo);
        
        // Insertar en la cola
        colaImpresion.insertar(registro);
        
        // Guardar en ambas tablas
        String clave = generarClave(nombreUsuario, nombreDocumento);
        tablaDocumentosEnCola.put(clave, registro);
        tablaIdAClave.put(registro.getId(), clave);
        
        // Marcar documento como en cola
        usuario.marcarDocumentoEnCola(nombreDocumento);
        
        return true;
    }
    
    /**
     * Libera la impresora: imprime el documento con mayor prioridad
     * 
     * @return el documento impreso o null si la cola está vacía
     */
    public RegistroImpresion liberarImpresora() {
        if (colaImpresion.estaVacio()) {
            return null;
        }
        
        RegistroImpresion impreso = colaImpresion.eliminarMin();
        
        if (impreso != null) {
            // Obtener la clave en O(1) usando el ID
            String clave = tablaIdAClave.get(impreso.getId());
            
            if (clave != null) {
                // Eliminar de ambas tablas
                tablaDocumentosEnCola.remove(clave);
                tablaIdAClave.remove(impreso.getId());
                
                // Extraer nombreUsuario y nombreDocumento de la clave
                String[] partes = clave.split("\\|");
                if (partes.length >= 2) {
                    String nombreUsuario = partes[0];
                    String nombreDocumento = partes[1];
                    Usuario usuario = buscarUsuario(nombreUsuario);
                    if (usuario != null) {
                        usuario.marcarDocumentoImpreso(nombreDocumento);
                    }
                }
            }
        }
        
        return impreso;
    }
    
    /**
     * Elimina un documento de la cola de impresión usando la tabla hash
     * 
     * @param nombreUsuario nombre del usuario dueño
     * @param nombreDocumento nombre del documento
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarDocumentoDeCola(String nombreUsuario, String nombreDocumento) {
        String clave = generarClave(nombreUsuario, nombreDocumento);
        RegistroImpresion registro = tablaDocumentosEnCola.get(clave);
        
        if (registro == null) {
            return false;
        }
        
        // Cambiar prioridad al valor más bajo posible 
        registro.setEtiquetaTiempo(Integer.MIN_VALUE);
        
        // Reubicar en el montículo usando el método reubicarPorId
        boolean eliminado = colaImpresion.reubicarPorId(registro.getId());
        
        if (eliminado) {
            // Eliminar de ambas tablas
            tablaDocumentosEnCola.remove(clave);
            tablaIdAClave.remove(registro.getId());
            
            // Actualizar el estado del usuario
            Usuario usuario = buscarUsuario(nombreUsuario);
            if (usuario != null) {
                usuario.marcarDocumentoImpreso(nombreDocumento);
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * Obtiene todos los usuarios
     * 
     * @return arreglo con los usuarios 
     */
    public Usuario[] listarUsuarios() {
        Usuario[] resultado = new Usuario[totalUsuarios];
        System.arraycopy(usuarios, 0, resultado, 0, totalUsuarios);
        return resultado;
    }
    
    /**
     * Obtiene los nombres de todos los usuarios
     * 
     * @return arreglo con los nombres de usuarios
     */
    public String[] listarNombresUsuarios() {
        String[] nombres = new String[totalUsuarios];
        for (int i = 0; i < totalUsuarios; i++) {
            nombres[i] = usuarios[i].getNombreUsuario();
        }
        return nombres;
    }
    
    /**
     * Obtiene los documentos de un usuario
     * 
     * @param nombreUsuario nombre del usuario
     * @param soloNoEnviados true para solo los que no están en cola
     * @return arreglo de documentos
     */
    public Documento[] listarDocumentosUsuario(String nombreUsuario, boolean soloNoEnviados) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) {
            return new Documento[0];
        }
        
        Documento[] todos = usuario.getDocumentosCreados();
        
        if (!soloNoEnviados) {
            return todos;
        }
        
        // Filtrar solo los que no están en cola
        int contador = 0;
        for (int i = 0; i < todos.length; i++) {
            if (!usuario.documentoEstaEnCola(todos[i].getNombre())) {
                contador++;
            }
        }
        
        Documento[] resultado = new Documento[contador];
        int indice = 0;
        for (int i = 0; i < todos.length; i++) {
            if (!usuario.documentoEstaEnCola(todos[i].getNombre())) {
                resultado[indice] = todos[i];
                indice++;
            }
        }
        
        return resultado;
    }
    
    /**
     * Obtiene la cola de impresión como arreglo
     * 
     * @return arreglo con los registros en la cola
     */
    public Object[] obtenerColaImpresion() {
        return colaImpresion.obtenerArreglo();
    }
    
    /**
     * Obtiene el montículo 
     * 
     * @return el montículo binario
     */
    public BinaryHeap<RegistroImpresion> getColaImpresion() {
        return colaImpresion;
    }
    
    /**
     * Obtiene el tiempo actual formateado
     * 
     * @return tiempo 
     */
    public String getTiempoFormateado() {
        return reloj.obtenerTiempoFormateado();
    }
    
    /**
     * Obtiene la cantidad de documentos en cola
     * 
     * @return número de documentos en cola
     */
    public int getDocumentosEnCola() {
        return colaImpresion.tamano();
    }
    
    /**
     * Reinicia todo el sistema
     */
    public void reiniciar() {
        colaImpresion = new BinaryHeap<>(10);
        tablaDocumentosEnCola = new HashTable<>();
        tablaIdAClave = new HashTable<>();
        usuarios = new Usuario[CAPACIDAD_USUARIOS];
        totalUsuarios = 0;
        reloj.reiniciar();
        RegistroImpresion.reiniciarContador();
    }
}
