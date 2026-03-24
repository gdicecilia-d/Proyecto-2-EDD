package Servicios;

/**
 * Clase que simula el tiempo transcurrido desde el inicio de la simulación
 * Cada vez que se envía un documento, el tiempo avanza
 * 
 * @author grazi
 * @author elohym
 * @author andres
 */
public class RelojSimulacion {
    // Tiempo actual de la simulación 
    private int tiempoActual;
    
    /**
     * Constructor que inicia el reloj en cero
     */
    public RelojSimulacion() {
        this.tiempoActual = 0;
    }
    
    /**
     * Obtiene el tiempo actual de la simulación
     * 
     * @return tiempo actual
     */
    public int obtenerTiempoActual() {
        return tiempoActual;
    }
    
    /**
     * Avanza el tiempo en una unidad y devuelve el nuevo tiempo
     * Se usa cada vez que se envía un documento a la cola
     * 
     * @return nuevo tiempo después de avanzar
     */
    public int avanzarTiempo() {
        tiempoActual++;
        return tiempoActual;
    }
    
    /**
     * Reinicia el reloj a cero
     */
    public void reiniciar() {
        tiempoActual = 0;
    }
    
    /**
     * Devuelve el tiempo en formato HH:MM:SS
     * 
     * @return tiempo 
     */
    public String obtenerTiempoFormateado() {
        int horas = tiempoActual / 3600;
        int minutos = (tiempoActual % 3600) / 60;
        int segundos = tiempoActual % 60;
        
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}
