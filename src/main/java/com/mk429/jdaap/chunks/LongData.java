package com.mk429.jdaap.chunks;

import java.nio.ByteBuffer;

public class LongData extends ChunkData {

    private final long value;

    public LongData(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) {
        super(contentCode, length, innerData, extraData);
        
        ByteBuffer b = ByteBuffer.wrap(innerData);
        switch (b.capacity()) {
            case 1:
                value = b.get();
                break;
            case 2:
                value = b.getShort();
                break;
            case 4:
                value = b.getInt();
                break;
            case 8:
                value = b.getLong();
                break;
            default:
                throw new IllegalArgumentException("Cannot parse numeric type - data size incorrect");
        }
    }

    public long getValue() {
        return value;
    }

}
