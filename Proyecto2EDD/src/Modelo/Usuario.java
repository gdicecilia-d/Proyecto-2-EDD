package Modelo;

/**
 * Representa un usuario del sistema de impresión
 * Cada usuario tiene un nombre, un tipo de prioridad,
 * una lista de documentos creados y una lista de documentos que están en cola
 * @author grazi
 * @author elohym
 * @author andres
 */
public class Usuario {
    //Nombre del usuario
    private String nombreUsuario;
    
    // Tipo de prioridad del usuario (alta, media, baja) 
    private TipoUsuario tipo;
    
    // Arreglo de documentos que ha creado el usuario 
    private Documento[] documentosCreados;
    
    // Arreglo de documentos que ya están en la cola de impresión 
    private Documento[] documentosEnCola;
    
    //Cantidad de documentos creados
    private int totalCreados;
    
    //Cantidad de documentos en cola 
    private int totalEnCola;
    
    //Capacidad inicial para los arreglos 
    private static final int CAPACIDAD_INICIAL = 10;
    
    /**
     * Constructor que crea un nuevo usuario
     * 
     * @param nombreUsuario nombre del usuario
     * @param tipo tipo de prioridad del usuario
     */
    public Usuario(String nombreUsuario, TipoUsuario tipo) {
        this.nombreUsuario = nombreUsuario;
        this.tipo = tipo;
        this.documentosCreados = new Documento[CAPACIDAD_INICIAL];
        this.documentosEnCola = new Documento[CAPACIDAD_INICIAL];
        this.totalCreados = 0;
        this.totalEnCola = 0;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public TipoUsuario getTipo() {
        return tipo;
    }
    
    /**
     * Obtiene una copia de los documentos creados
     * 
     * @return arreglo con los documentos creados
     */
    public Documento[] getDocumentosCreados() {
        Documento[] resultado = new Documento[totalCreados];
        System.arraycopy(documentosCreados, 0, resultado, 0, totalCreados);
        return resultado;
    }
    
    /**
     * Obtiene una copia de los documentos en cola
     * 
     * @return arreglo con los documentos en cola 
     */
    public Documento[] getDocumentosEnCola() {
        Documento[] resultado = new Documento[totalEnCola];
        System.arraycopy(documentosEnCola, 0, resultado, 0, totalEnCola);
        return resultado;
    }
    
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Expande el arreglo de documentos creados si se necesita
     */
    private void expandirCreados() {
        if (totalCreados >= documentosCreados.length) {
            Documento[] nuevo = new Documento[documentosCreados.length * 2];
            System.arraycopy(documentosCreados, 0, nuevo, 0, documentosCreados.length);
            documentosCreados = nuevo;
        }
    }
    
    /**
     * Expande el arreglo de documentos en cola si es necesario
     */
    private void expandirEnCola() {
        if (totalEnCola >= documentosEnCola.length) {
            Documento[] nuevo = new Documento[documentosEnCola.length * 2];
            System.arraycopy(documentosEnCola, 0, nuevo, 0, documentosEnCola.length);
            documentosEnCola = nuevo;
        }
    }
    
    /**
     * Busca la posición de un documento por su nombre
     * 
     * @param nombreDocumento nombre del documento a buscar
     * @return índice del documento, -1 si no se encuentra
     */
    private int buscarIndiceDocumento(String nombreDocumento) {
        for (int i = 0; i < totalCreados; i++) {
            if (documentosCreados[i] != null && 
                documentosCreados[i].getNombre().equals(nombreDocumento)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Agrega un documento a la lista de documentos creados
     * 
     * @param documento el documento a agregar
     */
    public void agregarDocumento(Documento documento) {
        if (documento == null) return;
        
        // Verificar si ya existe
        if (buscarIndiceDocumento(documento.getNombre()) != -1) return;
        
        expandirCreados();
        documentosCreados[totalCreados] = documento;
        totalCreados++;
    }
    
    /**
     * Elimina un documento de la lista de documentos creados
     * Solo se puede eliminar si el documento no está en la cola
     * 
     * @param nombreDocumento nombre del documento a eliminar
     * @return true si se eliminó, false si no se encontró o está en cola
     */
    public boolean eliminarDocumento(String nombreDocumento) {
        // Verificar si está en cola
        if (documentoEstaEnCola(nombreDocumento)) {
            return false;
        }
        
        int indice = buscarIndiceDocumento(nombreDocumento);
        if (indice == -1) return false;
        
        // Mover los elementos hacia la izquierda
        for (int i = indice; i < totalCreados - 1; i++) {
            documentosCreados[i] = documentosCreados[i + 1];
        }
        documentosCreados[totalCreados - 1] = null;
        totalCreados--;
        return true;
    }
    
    /**
     * Busca un documento por su nombre
     * 
     * @param nombreDocumento nombre del documento a buscar
     * @return el documento si existe, null si no se encuentra
     */
    public Documento buscarDocumento(String nombreDocumento) {
        int indice = buscarIndiceDocumento(nombreDocumento);
        if (indice == -1) return null;
        return documentosCreados[indice];
    }
    
    /**
     * Marca un documento como enviado a la cola de impresión
     * 
     * @param nombreDocumento nombre del documento
     */
    public void marcarDocumentoEnCola(String nombreDocumento) {
        Documento doc = buscarDocumento(nombreDocumento);
        if (doc == null) return;
        
        // Verificar si ya está en cola
        for (int i = 0; i < totalEnCola; i++) {
            if (documentosEnCola[i] != null && 
                documentosEnCola[i].getNombre().equals(nombreDocumento)) {
                return; // ya está en cola
            }
        }
        
        expandirEnCola();
        documentosEnCola[totalEnCola] = doc;
        totalEnCola++;
    }
    
    /**
     * Marca un documento como impreso (eliminar de la lista de en cola)
     * 
     * @param nombreDocumento nombre del documento
     */
    public void marcarDocumentoImpreso(String nombreDocumento) {
        for (int i = 0; i < totalEnCola; i++) {
            if (documentosEnCola[i] != null && 
                documentosEnCola[i].getNombre().equals(nombreDocumento)) {
                // Mover elementos hacia la izquierda
                for (int j = i; j < totalEnCola - 1; j++) {
                    documentosEnCola[j] = documentosEnCola[j + 1];
                }
                documentosEnCola[totalEnCola - 1] = null;
                totalEnCola--;
                return;
            }
        }
    }
    
    /**
     * Verifica si un documento está actualmente en la cola de impresión
     * 
     * @param nombreDocumento nombre del documento
     * @return true si está en cola, false si no
     */
    public boolean documentoEstaEnCola(String nombreDocumento) {
        for (int i = 0; i < totalEnCola; i++) {
            if (documentosEnCola[i] != null && 
                documentosEnCola[i].getNombre().equals(nombreDocumento)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica si el usuario tiene un documento creado con ese nombre
     * 
     * @param nombreDocumento nombre del documento
     * @return true si existe, false si no
     */
    public boolean tieneDocumento(String nombreDocumento) {
        return buscarIndiceDocumento(nombreDocumento) != -1;
    }
    
    /**
     * Obtiene la cantidad de documentos creados
     * 
     * @return número de documentos creados
     */
    public int getTotalDocumentosCreados() {
        return totalCreados;
    }
    
    /**
     * Obtiene la cantidad de documentos en cola
     * 
     * @return número de documentos en cola
     */
    public int getTotalDocumentosEnCola() {
        return totalEnCola;
    }
    
    @Override
    public String toString() {
        return nombreUsuario + " (" + tipo + ")";
    }
}
