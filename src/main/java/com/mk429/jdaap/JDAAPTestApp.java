package com.mk429.jdaap;

import com.mk429.jdaap.daapresponses.ServerInfoResponse;

public class JDAAPTestApp {


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please supply server URL to test against, e.g. http://localhost:3689/");
            return;
        }
        DAAP daap = new DAAP(args[0]);
        ServerInfoResponse sir = daap.getServerInfo();
        System.out.println(sir);
    }
}
