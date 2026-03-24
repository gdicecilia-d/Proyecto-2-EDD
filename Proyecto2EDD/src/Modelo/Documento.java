package Modelo;

/**
 * Representa un documento que puede ser impreso
 * Un documento pertenece a un usuario y puede estar en cola o no
 * @author grazi
 * @author elohym
 * @author andres
 */
public class Documento {
    //Nombre del documento 
    private String nombre;
    
    // Número de páginas del documento 
    private int tamanio;
    
    // Tipo de documento (PDF, Texto, Imagen, PowerPoint)
    private String tipo;
    
    /**
     * Constructor para crear un documento nuevo
     * 
     * @param nombre nombre del documento
     * @param tamanio número de páginas (mayor a 0)
     * @param tipo tipo de documento
     */
    public Documento(String nombre, int tamanio, String tipo) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.tipo = tipo;
    }
    
    /**
     * Obtiene el nombre del documento
     * 
     * @return nombre del documento
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el número de páginas del documento
     * 
     * @return tamaño en páginas
     */
    public int getTamanio() {
        return tamanio;
    }
    
    /**
     * Obtiene el tipo de documento
     * 
     * @return tipo (PDF, Texto, Imagen, PowerPoint)
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Cambia el nombre del documento
     * 
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Cambia el tamaño del documento
     * 
     * @param tamanio nuevo tamaño en páginas
     */
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    
    /**
     * Cambia el tipo de documento
     * 
     * @param tipo nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Devuelve una representación en texto del documento
     * 
     * @return nombre y tamaño del documento
     */
    @Override
    public String toString() {
        return nombre + " (" + tamanio + " págs, " + tipo + ")";
    }
}
