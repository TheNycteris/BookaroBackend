package com.bookaro.api.utils;

import javax.servlet.http.HttpServletRequest;

import com.bookaro.api.security.SecurityConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

/**
 * 
 * @author Pedro y Pol Casals
 *
 */
public class Utils {
	
	/**
	 * @author Pol Casals
	 * Metodo para mapear un objeto Json a Object
	 * @param patch Recibe un objeto de tipo JsonPatch
	 * @param obj Recibe un objeto de tipo Object
	 * @return Retorna un Object
	 * @throws JsonPatchException Puede lanzar una excepción del tipo JsonPatchException
	 * @throws JsonProcessingException Puede lanzar una excepción del tipo JsonProcessingException
	 */
	public static Object applyPatch (JsonPatch patch, Object obj)
				throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode patched = patch.apply(objectMapper.convertValue(obj, JsonNode.class));
	    return objectMapper.treeToValue(patched, obj.getClass());
	}
	
	/**
	 * @author Pedro
	 * Metodo que verifica si un token está o no en la lista de tokens invalidados
	 * @param request Recibe un objeto de tipo HttpServletRequest
	 * @return Retorna true o false dependiendo de si el token está o no en la lista.
	 */
	public static boolean blackList (HttpServletRequest request) {
    	boolean blackList = false;

    	String token = request.getHeader(SecurityConstants.HEADER_STRING);
    	if (token != null) {
    		for (String t: SecurityConstants.tokens) {
    			if (t.equals(token)) {
    				blackList = true;					
    			}
    		}
    	}
    	return blackList;
    }
}