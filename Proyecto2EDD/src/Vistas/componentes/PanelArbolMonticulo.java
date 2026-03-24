package Vistas.componentes;

import Estructuras.BinaryHeap;
import Modelo.RegistroImpresion;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Panel que dibuja el montículo binario como un árbol
 * 
 * @author grazi
 * @author elohym
 * @author andres
 */
public class PanelArbolMonticulo extends JPanel {
    
    private BinaryHeap<RegistroImpresion> monticulo;
    private int radioNodo = 25;
    private int espaciadoHorizontal = 50;
    private int espaciadoVertical = 70;
    
    /**
     * Constructor del panel.
     */
    public PanelArbolMonticulo() {
        setBackground(Color.WHITE);
        monticulo = null;
    }
    
    /**
     * Actualiza el montículo a dibujar
     * 
     * @param monticulo el montículo a mostrar
     */
    public void setMonticulo(BinaryHeap<RegistroImpresion> monticulo) {
        this.monticulo = monticulo;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (monticulo == null || monticulo.estaVacio()) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString("Cola vacía", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }
        
        Object[] arreglo = monticulo.obtenerArreglo();
        if (arreglo.length == 0) {
            g2.drawString("Cola vacía", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }
        
        // Calcular posición inicial centrada
        int xInicial = getWidth() / 2;
        int yInicial = 50;
        
        dibujarNodo(g2, arreglo, 0, xInicial, yInicial, 0);
    }
    
    /**
     * Dibuja un nodo y sus hijos recursivamente
     */
    private void dibujarNodo(Graphics2D g2, Object[] arreglo, int indice, int x, int y, int nivel) {
        if (indice >= arreglo.length) return;
        
        RegistroImpresion registro = (RegistroImpresion) arreglo[indice];
        if (registro == null) return;
        
        // Calcular posiciones de los hijos
        int hijoIzq = 2 * indice + 1;
        int hijoDer = 2 * indice + 2;
        
        int offset = espaciadoHorizontal / (nivel + 1);
        int xIzq = x - offset;
        int xDer = x + offset;
        int yHijo = y + espaciadoVertical;
        
        // Dibujar líneas a los hijos
        if (hijoIzq < arreglo.length && arreglo[hijoIzq] != null) {
            g2.setColor(Color.BLACK);
            g2.drawLine(x, y + radioNodo, xIzq, yHijo);
            dibujarNodo(g2, arreglo, hijoIzq, xIzq, yHijo, nivel + 1);
        }
        
        if (hijoDer < arreglo.length && arreglo[hijoDer] != null) {
            g2.setColor(Color.BLACK);
            g2.drawLine(x, y + radioNodo, xDer, yHijo);
            dibujarNodo(g2, arreglo, hijoDer, xDer, yHijo, nivel + 1);
        }
        
        // Dibujar el círculo del nodo
        g2.setColor(new Color(175, 189, 255)); // Color morado claro
        g2.fillOval(x - radioNodo, y, radioNodo * 2, radioNodo * 2);
        g2.setColor(Color.BLACK);
        g2.drawOval(x - radioNodo, y, radioNodo * 2, radioNodo * 2);
        
        // Dibujar el texto 
        String texto = String.valueOf(registro.getEtiquetaTiempo());
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.BLACK);
        int anchoTexto = g2.getFontMetrics().stringWidth(texto);
        int altoTexto = g2.getFontMetrics().getHeight();
        g2.drawString(texto, x - anchoTexto / 2, y + radioNodo - 2);
        
        // Dibujar nombre del documento debajo 
        String nombre = registro.getDocumento().getNombre();
        if (nombre.length() > 12) {
            nombre = nombre.substring(0, 10) + "...";
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        anchoTexto = g2.getFontMetrics().stringWidth(nombre);
        g2.drawString(nombre, x - anchoTexto / 2, y + radioNodo + 15);
    }
    
}
