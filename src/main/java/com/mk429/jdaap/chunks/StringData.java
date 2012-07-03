
package com.mk429.jdaap.chunks;


public class StringData extends ChunkData {

    private final String value;

    public StringData(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) {
        super(contentCode, length, innerData, extraData);
    
        value = new String(innerData);
    }

    public String getValue() {
        return value;
    }

}
