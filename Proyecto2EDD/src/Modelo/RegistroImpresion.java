package Modelo;

import Estructuras.Identificable;

/**
 * Representa un documento que ha sido enviado a la cola de impresión
 * Este es el objeto que se guarda en el montículo binario
 * Implementa Comparable para ordenar por etiqueta de tiempo (prioridad)
 * e Identificable para poder ser eliminado por ID.
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class RegistroImpresion implements Comparable<RegistroImpresion>, Identificable {
    
    //Contador estático para generar IDs únicos
    private static int contadorId = 0;
    
    // El documento que se va a imprimir 
    private Documento documento;
    
    // Etiqueta de tiempo 
    private int etiquetaTiempo;
    
    // Identificador único de este registro 
    private int id;
    
    /**
     * Constructor para crear un nuevo registro de impresión
     * 
     * @param documento el documento a imprimir
     * @param etiquetaTiempo valor de prioridad (menor = más prioridad)
     */
    public RegistroImpresion(Documento documento, int etiquetaTiempo) {
        this.documento = documento;
        this.etiquetaTiempo = etiquetaTiempo;
        this.id = ++contadorId;
    }
    
    
    /**
     * Obtiene el documento
     * 
     * @return el documento
     */
    public Documento getDocumento() {
        return documento;
    }
    
    /**
     * Obtiene la etiqueta de tiempo (prioridad)
     * 
     * @return valor de prioridad (menor = más prioritario)
     */
    public int getEtiquetaTiempo() {
        return etiquetaTiempo;
    }
    
    /**
     * Obtiene el identificador único del registro
     * Implementa el método de la interfaz Identificable
     * 
     * @return ID del registro
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Cambia la etiqueta de tiempo
     * Este método se usa para modificar prioridad cuando se elimina un documento
     * de la cola 
     * 
     * @param etiquetaTiempo nueva etiqueta de tiempo
     */
    public void setEtiquetaTiempo(int etiquetaTiempo) {
        this.etiquetaTiempo = etiquetaTiempo;
    }

    /**
     * Compara este registro con otro por su etiqueta de tiempo
     * Se usa para ordenar en el montículo 
     * 
     * @param otro el otro registro a comparar
     * @return negativo si este es menor (más prioritario),
     *         positivo si este es mayor (menos prioritario),
     *         cero si son iguales
     */
    @Override
    public int compareTo(RegistroImpresion otro) {
        if (otro == null) {
            return -1;
        }
        return Integer.compare(this.etiquetaTiempo, otro.etiquetaTiempo);
    }
    
    /**
     * Reinicia el contador de IDs
     * Útil si se reinicia la simulación
     */
    public static void reiniciarContador() {
        contadorId = 0;
    }
    
    /**
     * Devuelve una representación en texto del registro
     * 
     * @return información del registro
     */
    @Override
    public String toString() {
        return documento.getNombre() + " (prioridad: " + etiquetaTiempo + ")";
    }
}
