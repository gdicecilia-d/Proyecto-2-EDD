package Utilidades;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase encargada de manejar la selección de archivos mediante JFileChooser
 * 
 * @author grazia
 * @author elohym
 * @author andres
 */
public class ManejadorArchivos {
    /**
     * Abre un diálogo para seleccionar un archivo CSV
     * 
     * @param padre componente padre para centrar el diálogo
     * @return ruta completa del archivo seleccionado o null si se canceló
     */
    public static String seleccionarArchivoCSV(JComponent padre) {
        JFileChooser fileChooser = new JFileChooser();
        
        // Configurar filtro para solo mostrar archivos CSV
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filtro);
        
        // Abrir el diálogo
        int resultado = fileChooser.showOpenDialog(padre);
        
        // Si el usuario seleccionó un archivo, devolver su ruta
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return null;
    }
    
    /**
     * Abre un diálogo para seleccionar un archivo (cualquier tipo)
     * 
     * @param padre componente padre para centrar el diálogo
     * @return ruta completa del archivo seleccionado o null si se canceló
     */
    public static String seleccionarArchivo(JComponent padre) {
        JFileChooser fileChooser = new JFileChooser();
        
        int resultado = fileChooser.showOpenDialog(padre);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return null;
    }
}
