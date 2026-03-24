package Modelo;

/**
 * Enum que representa los tipos de usuario del sistema
 * Cada tipo tiene un valor numérico de prioridad:
 * Prioridad_alta: prioridad 3 (más prioridad)
 * Prioridad_media: prioridad 2
 * Prioridad_baja: prioridad 1 (menos prioridad)
 * 
 * @author grazi
 * @author elohym
 * @author 
 */
public enum TipoUsuario {
    //Tipo de usuario con mayor prioridad (valor 3) 
    prioridad_alta(3),
    
    //Tipo de usuario con mayor prioridad (valor 2)
    prioridad_media(2),
    
    //Tipo de usuario con mayor prioridad (valor 1)
    prioridad_baja(1);
    
    private final int valorPrioridad;
    
    /**
     * Constructor privado del enum
     * 
     * @param valorPrioridad valor numérico de prioridad (3, 2 o 1)
     */
    private TipoUsuario(int valorPrioridad) {
        this.valorPrioridad = valorPrioridad;
    }
    
    /**
     * Obtiene el valor numérico de prioridad del tipo de usuario
     * 
     * @return valor de prioridad (3 para alta, 2 para media, 1 para baja)
     */
    public int getValorPrioridad() {
        return valorPrioridad;
    }
    
    /**
     * Convierte un String a TipoUsuario
     * Método útil para procesar el archivo CSV
     * 
     * @param texto texto a convertir 
     * @return TipoUsuario correspondiente o prioridad_media si no coincide
     */
    public static TipoUsuario fromString(String texto) {
        if (texto == null) {
            return prioridad_media;
        }
        
        String textoLower = texto.toLowerCase().trim();
        
        if (textoLower.equals("prioridad_alta") || textoLower.equals("alta")) {
            return prioridad_alta;
        }
        
        if (textoLower.equals("prioridad_baja") || textoLower.equals("baja")) {
            return prioridad_baja;
        }
        
        return prioridad_media;
    }
    
    /**
     * Devuelve el nombre del tipo de usuario
     * 
     * @return "Alta", "Media" o "Baja"
     */
    @Override
    public String toString() {
        switch (this) {
            case prioridad_alta:
                return "Alta";
            case prioridad_media:
                return "Media";
            case prioridad_baja:
                return "Baja";
            default:
                return "Media";
        }
    }
}
