package Vistas.modelos;

import Modelo.Documento;
import Modelo.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para la tabla de documentos de un usuario
 * Muestra nombre, tamaño, tipo y estado (creado/en cola)
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class ModeloTablaDocumentos extends AbstractTableModel {
    // Nombres de las columnas 
    private String[] columnas = {"Nombre", "Tamaño", "Tipo", "Estado"};
    
    // Arreglo de documentos a mostrar 
    private Documento[] documentos;
    
    //  Usuario dueño de los documentos  
    private Usuario usuarioActual;
    
    /**
     * Constructor que inicializa el modelo vacío
     */
    public ModeloTablaDocumentos() {
        this.documentos = new Documento[0];
        this.usuarioActual = null;
    }
    
    /**
     * Actualiza los datos de la tabla
     * 
     * @param documentos arreglo de documentos
     * @param usuario usuario dueño de los documentos
     */
    public void setDocumentos(Documento[] documentos, Usuario usuario) {
        this.documentos = documentos;
        this.usuarioActual = usuario;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return documentos.length;
    }
    
    @Override
    public int getColumnCount() {
        return columnas.length;
    }
    
    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }
    
    @Override
    public Object getValueAt(int fila, int columna) {
        Documento doc = documentos[fila];
        
        if (columna == 0) {
            return doc.getNombre();
        } else if (columna == 1) {
            return doc.getTamanio() + " págs";
        } else if (columna == 2) {
            return doc.getTipo();
        } else if (columna == 3) {
            // Verificar si el documento está en cola
            if (usuarioActual != null && usuarioActual.documentoEstaEnCola(doc.getNombre())) {
                return "En cola";
            } else {
                return "Creado";
            }
        }
        
        return null;
    }
}
