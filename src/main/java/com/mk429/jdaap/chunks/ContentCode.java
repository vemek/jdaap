package com.mk429.jdaap.chunks;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;

public enum ContentCode {

    UNKNOWN("????", "UNKNOWN", ChunkData.class),
    ITEM_ID("miid", "dmap.itemid", LongData.class),
    ITEM_NAME("minm", "dmap.itemname", StringData.class),
    ITEM_KIND("mikd", "dmap.itemkind", LongData.class),
    PERSISTENT_ID("mper", "dmap.persistentid", LongData.class),
    CONTAINER("mcon", "dmap.container", ContainerData.class),
    CONTAINER_ITEM_ID("mcti", "dmap.containeritemid", LongData.class),
    PARENT_CONTAINER_ID("mpco", "dmap.parentcontainerid", LongData.class),
    STATUS("mstt", "dmap.status", LongData.class),
    STATUS_STRING("msts", "dmap.statusstring", StringData.class),
    ITEM_COUNT("mimc", "dmap.itemcount", LongData.class),
    CONTAINER_COUNT("mctc", "dmap.containercount", LongData.class),
    RETURNED_COUNT("mrco", "dmap.returnedcount", LongData.class),
    SPECIFIED_TOTAL_COUNT("mtco", "dmap.specifiedtotalcount", LongData.class),
    LISTING("mlcl", "dmap.listing", ContainerData.class),
    LISTING_ITEM("mlit", "dmap.listingitem", ContainerData.class),
    BAG("mbcl", "dmap.bag", ContainerData.class),
    DICTIONARY("mdcl", "dmap.dictionary", ContainerData.class),
    SERVER_INFO_RESPONSE("msrv", "dmap.serverinforesponse", ContainerData.class),
    AUTHENTICATION_METHOD("msau", "dmap.authenticationmethod", LongData.class),
    LOGIN_REQUIRED("mslr", "dmap.loginrequired", LongData.class),
    DMAP_PROTOCOL_VERSION("mpro", "dmap.protocolversion", VersionData.class),
    DAAP_PROTOCOL_VERSION("apro", "daap.protocolversion", VersionData.class),
    SUPPORTS_AUTO_LOGIN("msal", "dmap.supportsautologout", LongData.class),
    SUPPORTS_UPDATE("msup", "dmap.supportsupdate", LongData.class),
    SUPPORTS_PERSISTENT_IDS("mspi", "dmap.supportspersistentids", LongData.class),
    SUPPORTS_EXTENSIONS("msex", "dmap.supportsextensions", LongData.class),
    SUPPORTS_INDEX("msix", "dmap.supportsindex", LongData.class),
    SUPPORTS_BROWSE("msbr", "dmap.supportsbrowse", LongData.class),
    SUPPORTS_QUERY("msqy", "dmap.supportsquery", LongData.class),
    DATABASE_COUNT("msdc", "dmap.databasescount", LongData.class),
    TIMEOUT_INTERVAL("mstm", "dmap.timeoutinterval", LongData.class);
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