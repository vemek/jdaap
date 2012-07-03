package com.mk429.jdaap.chunks;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;

public enum ContentCode {

    // shell commands to lexically order this by code number
    // copy from "sort" to "//EOF", run, then fix the semi-colon
    // sort -t\" -k2,2 <<//EOF
    UNKNOWN("????", "UNKNOWN", ChunkData.class),
    DAAP_PROTOCOL_VERSION("apro", "daap.protocolversion", VersionData.class),
    BAG("mbcl", "dmap.bag", ContainerData.class),
    CONTAINER("mcon", "dmap.container", ContainerData.class),
    CONTAINER_COUNT("mctc", "dmap.containercount", LongData.class),
    CONTAINER_ITEM_ID("mcti", "dmap.containeritemid", LongData.class),
    DICTIONARY("mdcl", "dmap.dictionary", ContainerData.class),
    ITEM_ID("miid", "dmap.itemid", LongData.class),
    ITEM_KIND("mikd", "dmap.itemkind", LongData.class),
    ITEM_COUNT("mimc", "dmap.itemcount", LongData.class),
    ITEM_NAME("minm", "dmap.itemname", StringData.class),
    LISTING("mlcl", "dmap.listing", ContainerData.class),
    LISTING_ITEM("mlit", "dmap.listingitem", ContainerData.class),
    PARENT_CONTAINER_ID("mpco", "dmap.parentcontainerid", LongData.class),
    PERSISTENT_ID("mper", "dmap.persistentid", LongData.class),
    DMAP_PROTOCOL_VERSION("mpro", "dmap.protocolversion", VersionData.class),
    RETURNED_COUNT("mrco", "dmap.returnedcount", LongData.class),
    SUPPORTS_AUTO_LOGIN("msal", "dmap.supportsautologout", LongData.class),
    AUTHENTICATION_METHOD("msau", "dmap.authenticationmethod", LongData.class),
    SUPPORTS_BROWSE("msbr", "dmap.supportsbrowse", LongData.class),
    DATABASE_COUNT("msdc", "dmap.databasescount", LongData.class),
    SUPPORTS_EXTENSIONS("msex", "dmap.supportsextensions", LongData.class),
    SUPPORTS_INDEX("msix", "dmap.supportsindex", LongData.class),
    LOGIN_REQUIRED("mslr", "dmap.loginrequired", LongData.class),
    SUPPORTS_PERSISTENT_IDS("mspi", "dmap.supportspersistentids", LongData.class),
    SUPPORTS_QUERY("msqy", "dmap.supportsquery", LongData.class),
    SERVER_INFO_RESPONSE("msrv", "dmap.serverinforesponse", ContainerData.class),
    TIMEOUT_INTERVAL("mstm", "dmap.timeoutinterval", LongData.class),
    STATUS_STRING("msts", "dmap.statusstring", StringData.class),
    STATUS("mstt", "dmap.status", LongData.class),
    SUPPORTS_UPDATE("msup", "dmap.supportsupdate", LongData.class),
    SPECIFIED_TOTAL_COUNT("mtco", "dmap.specifiedtotalcount", LongData.class);
//EOF
    public String contentCodesNumber, contentCodesName;
    public Class<? extends ChunkData> dataType;

    private ContentCode(String contentCodesNumber, String contentCodesName, Class<? extends ChunkData> dataType) {
        this.contentCodesNumber = contentCodesNumber;
        this.contentCodesName = contentCodesName;
        this.dataType = dataType;
    }

    public ChunkData parse(ContentCode contentCode, int length, byte[] innerData, byte[] extraData) throws Exception {
        return this.dataType.getConstructor(
                ContentCode.class,
                int.class,
                byte[].class,
                byte[].class).newInstance(contentCode, length, innerData, extraData);
    }

    public static ContentCode lookupByNumber(String number) {
        ContentCode cc = numberLookup.get(number);
        if (cc == null) {
            return ContentCode.UNKNOWN;
        } else {
            return cc;
        }
    }
    // provide a lookup by content code names
    private final static Map<String, ContentCode> numberLookup =
            Maps.uniqueIndex(Arrays.asList(ContentCode.values()),
            new Function<ContentCode, String>() {

                public String apply(ContentCode c) {
                    return c.contentCodesNumber;
                }
            });
}