package com.mk429.jdaap;

import com.mk429.jdaap.daapresponses.ServerInfoResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DAAP {

    private static enum Endpoint {

        SERVER_INFO("server-info");
        public String url;

        private Endpoint(String url) {
            this.url = url;
        }
    }
    private static final int READ_BLOCK = 128; // size of block read from server
    private final String serverURL;

    public DAAP(String serverURL) {
        this.serverURL = serverURL;
    }

    public ServerInfoResponse getServerInfo() throws Exception {
        byte[] data = request(Endpoint.SERVER_INFO);
        
        return new ServerInfoResponse(data);
    }

    /*
     * Get raw data from the DAAP server
     */
    private byte[] request(Endpoint endpoint) {
        byte[] data = null;
        try {
            URL url = new URL(serverURL + endpoint.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // read data
            InputStream in = conn.getInputStream();
            ReadableByteChannel bc = Channels.newChannel(in);
            ByteBuffer bb = ByteBuffer.allocate(READ_BLOCK);

            while (bc.read(bb) != -1) {
                ByteBuffer result = bb;
                if (bb.remaining() < READ_BLOCK) {
                    //create new buffer  
                    result = ByteBuffer.allocate(bb.capacity() * 2);
                    //set limit to current position in buffer and set position to zero.  
                    bb.flip();
                    //put original buffer to new buffer  
                    result.put(bb);
                }
                bb = result;
            }

            data = new byte[bb.position()];
            bb.rewind();
            bb.get(data);
        } catch (IOException ex) {
            System.err.println("Error connecting to server");
            System.err.println(ex);
        }

        return data;
    }
}
