package com.mk429.jdaap.chunks;

import java.nio.ByteBuffer;
import javax.naming.OperationNotSupportedException;


/*
 * Generic data type Inheriting data types will contain primitive values, or be
 * collections
 */
public abstract class ChunkData {

    protected final ContentCode contentCode;
    protected final int length;
    protected final byte[] innerData;
    protected final byte[] extraData;

    protected ChunkData(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) {
        this.contentCode = contentCode;
        this.length = length;
        this.innerData = innerData;
        this.extraData = extraData;
    }

    public static ChunkData parse(byte[] data) throws Exception {
        ContentCode contentCode;
        int length;
        byte[] innerData;
        byte[] extraData;

        if (data == null) {
            throw new IllegalArgumentException("Input data was null");
        }
        if (data.length < 9) {
            throw new IllegalArgumentException("Data chunk smaller than minimum valid length");
        }

        ByteBuffer b = ByteBuffer.wrap(data);

        byte[] contentCodeNumberBytes = new byte[4];
        b.get(contentCodeNumberBytes);
        contentCode = ContentCode.lookupByNumber(new String(contentCodeNumberBytes));
        System.out.println(String.format(
                "Parsing (%s) %s",
                contentCode.contentCodesNumber,
                contentCode.contentCodesName));
        if (contentCode == ContentCode.UNKNOWN) {
            throw new OperationNotSupportedException(String.format("Unknown chunk type - %s", new String(contentCodeNumberBytes)));
        }

        length = b.getInt();

        innerData = new byte[length];
        b.get(innerData);

        extraData = new byte[b.capacity() - b.position()];
        b.get(extraData);

        ChunkData chunk = contentCode.parse(contentCode, length, innerData, extraData);
        return chunk;

    }
}
