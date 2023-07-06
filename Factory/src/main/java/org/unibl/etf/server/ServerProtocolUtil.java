package org.unibl.etf.server;

public enum ServerProtocolUtil {

	OK("OK"),
	NOT_OK("NOT_OK"),
	LOGIN("LOGIN"),
	SEPARATOR("#"),
	END("END"),
	ORDER_STATUS("ORDER_STATUS"),
	INVALID_REQUEST("INVALID_REQUEST");
	
	private final String value;

    private ServerProtocolUtil(String value) { 
        this.value = value;
    }

    public String getMessage() {
        return value; 
    }
}
