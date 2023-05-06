package org.uob.pae.intellij.plugin.servicemanager.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dushmantha
 */
public final class Context {

    public static final String SERVICE_PANELS = "SERVICE_PANELS";
    public final static Map<String, Process> PROCESS_CACHE = new ConcurrentHashMap<>();
    private final static Map<String, Object> DATA = new ConcurrentHashMap<>();
    private Context(){}

    public static Object getValue(String key){
        return DATA.get(key);
    }

    public static void put(String key,Object value){
        DATA.put(key,value);
    }


}
