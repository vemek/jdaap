package com.mk429.jdaap.daapresponses;

import com.mk429.jdaap.chunks.*;

public class ServerInfoResponse {

    private final long status; // status code
    private final String dmapVersion; // DMAP protocol version
    private final String daapVersion; // DAAP protocol version
    private final long timeoutInterval;
    private final String serverName;
    private final long databaseCount;

    public ServerInfoResponse(byte[] rawData) throws Exception {
        
        ContainerData data = (ContainerData) ChunkData.parse(rawData);
        status = ((LongData) data.get(ContentCode.STATUS)).getValue();
        dmapVersion = ((VersionData) data.get(ContentCode.DMAP_PROTOCOL_VERSION)).getValue();
        daapVersion = ((VersionData) data.get(ContentCode.DAAP_PROTOCOL_VERSION)).getValue();
        timeoutInterval = ((LongData) data.get(ContentCode.TIMEOUT_INTERVAL)).getValue();
        serverName = ((StringData) data.get(ContentCode.ITEM_NAME)).getValue();
        databaseCount = ((LongData) data.get(ContentCode.DATABASE_COUNT)).getValue();
    }

    @Override
    public String toString() {
        return String.format("Status: %d\n"
                + "DMAP Protocol Version: %s\n"
                + "DAAP Protocol Version: %s\n"
                + "Timeout Interval: %d\n"
                + "Server name: %s\n"
                + "Database Count: %d\n",
                status,
                dmapVersion,
                daapVersion,
                timeoutInterval,
                serverName,
                databaseCount);
    }
}
