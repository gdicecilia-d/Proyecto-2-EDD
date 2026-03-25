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
     * Solo permite seleccionar archivos con extensión .csv
     * 
     * @param padre componente padre para centrar el diálogo
     * @return ruta completa del archivo seleccionado, o null si se canceló
     */
    public static String seleccionarArchivoCSV(JComponent padre) {
        JFileChooser fileChooser = new JFileChooser();
        
        // Configurar filtro para solo mostrar archivos CSV
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filtro);
        
        // No permitir seleccionar archivos que no sean CSV
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        // Abrir el diálogo
        int resultado = fileChooser.showOpenDialog(padre);
        
        // Si el usuario seleccionó un archivo, devolver su ruta
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Validar que la extensión sea .csv
            if (!ruta.toLowerCase().endsWith(".csv")) {
                javax.swing.JOptionPane.showMessageDialog(padre, 
                    "Solo se permiten archivos con extensión .csv",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            return ruta;
        }
        
        return null;
    }
}
