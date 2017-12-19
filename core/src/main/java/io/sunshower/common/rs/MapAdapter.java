package io.sunshower.common.rs;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.eclipse.persistence.oxm.annotations.XmlVariableNode;
 
public class MapAdapter extends XmlAdapter<MapAdapter.AdaptedMap, Map<String, String>> {
 
    public static class AdaptedMap {
         
        @XmlVariableNode("key")
        List<AdaptedEntry> entries = new ArrayList<AdaptedEntry>();
         
    }
 
    public static class AdaptedEntry {
         
        @XmlTransient
        public String key;
         
        @XmlValue
        public String value;
 
    }
 
    @Override
    public AdaptedMap marshal(Map<String, String> map) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Entry<String, String > entry : map.entrySet()) {
            AdaptedEntry adaptedEntry = new AdaptedEntry();
            adaptedEntry.key = transform(entry.getKey());
            adaptedEntry.value = entry.getValue();
            adaptedMap.entries.add(adaptedEntry);
        }
        return adaptedMap;
    }



    @Override
    public Map<String, String> unmarshal(AdaptedMap adaptedMap) throws Exception {
        List<AdaptedEntry> adaptedEntries = adaptedMap.entries;
        Map<String, String> map = new HashMap<>(adaptedEntries.size());
        for(AdaptedEntry adaptedEntry : adaptedEntries) {
            map.put(adaptedEntry.key, adaptedEntry.value);
        }
        return map;
    }

    static String untransform(String key) {
        if(key == null) {
            return "key";
        } else {
            return key.replaceAll("__", " ");
        }
    }


    static String transform(String key) {
        if(key != null) {
            return key.replaceAll(" ", "__");
        } else {
            return "key";
        }
    }
}