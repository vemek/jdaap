
package com.mk429.jdaap.chunks;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class ContainerData extends ChunkData {

    private final Map<ContentCode, ChunkData> codeToChunk;
    private final ArrayList<ChunkData> children;

    public ContainerData(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) throws Exception {
        super(contentCode, length, innerData, extraData);
    
        codeToChunk = new EnumMap<ContentCode, ChunkData>(ContentCode.class);
        children = new ArrayList<ChunkData>();
        
        ChunkData chunk = ChunkData.parse(innerData);
        codeToChunk.put(chunk.contentCode, chunk);
        children.add(chunk);
        while (chunk.extraData.length > 0) {
            chunk = ChunkData.parse(chunk.extraData);
            codeToChunk.put(chunk.contentCode, chunk);
            children.add(chunk);
        }
        
    }
    
    public ChunkData get(ContentCode code) {
        return this.codeToChunk.get(code);
    }
    
    public List<ChunkData> getChildren() {
        return ImmutableList.copyOf(children);
    }
}
