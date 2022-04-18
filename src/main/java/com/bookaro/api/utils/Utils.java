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
 * @author Pedro
 *
 */
public class Utils {
	public static Object applyPatch (JsonPatch patch, Object obj)
				throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode patched = patch.apply(objectMapper.convertValue(obj, JsonNode.class));
	    return objectMapper.treeToValue(patched, obj.getClass());
	}
	
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