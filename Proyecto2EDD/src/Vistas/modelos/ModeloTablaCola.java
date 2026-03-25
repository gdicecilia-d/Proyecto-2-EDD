package Vistas.modelos;

import Modelo.RegistroImpresion;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para la tabla de la cola de impresión
 * Muestra documento, tamaño y prioridad 
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class ModeloTablaCola extends AbstractTableModel {
    // Nombres de las columnas 
    private String[] columnas = {"Documento", "Tamaño", "Prioridad"};
    
    // Arreglo de registros en la cola 
    private Object[] registros;
    
    /**
     * Constructor que inicializa el modelo vacío
     */
    public ModeloTablaCola() {
        this.registros = new Object[0];
    }
    
    /**
     * Actualiza los datos de la tabla
     * 
     * @param registros arreglo de registros de impresión
     */
    public void setRegistros(Object[] registros) {
        this.registros = registros;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return registros.length;
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
        RegistroImpresion registro = (RegistroImpresion) registros[fila];
        
        if (registro == null) {
            return null;
        }
        
        if (columna == 0) {
            return registro.getDocumento().getNombre();
        } else if (columna == 1) {
            return registro.getDocumento().getTamanio() + " págs";
        } else if (columna == 2) {
            return registro.getEtiquetaTiempo();
        }
        
        return null;
    }
}
