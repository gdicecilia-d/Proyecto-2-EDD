package Vistas.modelos;

import Modelo.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para la tabla de usuarios
 * Muestra nombre de usuario y tipo de prioridad
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class ModeloTablaUsuarios extends AbstractTableModel {
    // Nombres de las columnas 
    private String[] columnas = {"Usuario", "Tipo"};
    
    // Arreglo de usuarios a mostrar 
    private Usuario[] usuarios;
    
    /**
     * Constructor que inicializa el modelo con un arreglo vacío
     */
    public ModeloTablaUsuarios() {
        this.usuarios = new Usuario[0];
    }
    
    /**
     * Actualiza los datos de la tabla
     * 
     * @param usuarios nuevo arreglo de usuarios
     */
    public void setUsuarios(Usuario[] usuarios) {
        this.usuarios = usuarios;
        fireTableDataChanged(); // Notifica a la tabla que los datos cambiaron
    }
    
    @Override
    public int getRowCount() {
        return usuarios.length;
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
        Usuario usuario = usuarios[fila];
        
        if (columna == 0) {
            return usuario.getNombreUsuario();
        } else if (columna == 1) {
            return usuario.getTipo().toString();
        }
        
        return null;
    }
}
