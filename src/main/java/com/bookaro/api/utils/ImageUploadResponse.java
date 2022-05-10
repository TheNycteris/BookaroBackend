package com.bookaro.api.utils;

/**
 * Clase para mostrar mensajes sobre la entidad Image
 * @author Pedro
 *
 */
public class ImageUploadResponse {
	
	// Atributo de clase
    private String message;

    /**
     * Constructor
     * @param message Recibe un String con el mensaje.
     */
    public ImageUploadResponse(String message) {
        this.message = message;
    }

    /**
     * Getter messate
     * @return retorna String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter message
     * @param message Recibe String con el mensaje
     */
    public void setMessage(String message) {
        this.message = message;
    }
}