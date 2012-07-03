package com.mk429.jdaap.chunks;

import java.nio.ByteBuffer;

public class VersionData extends ChunkData {

    private final String value;

    public VersionData(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) {
        super(contentCode, length, innerData, extraData);
    
        ByteBuffer b = ByteBuffer.wrap(innerData);
        value = String.format(
                "%d.%d.%d",
                b.getShort(),
                b.get(),
                b.get());
    }

    public String getValue() {
        return value;
    }
}
