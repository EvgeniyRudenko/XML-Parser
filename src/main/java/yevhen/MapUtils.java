package yevhen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils{

    public static <K, V> String mapToString(Map<K,V> map){
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static <K, V> void writeMapToFile(String explanation, Map<K, V> map,
                                              Path outputFilePath, StandardOpenOption... operation)
                                              throws IOException {
        Files.write(outputFilePath,
                (explanation
                        + System.lineSeparator()
                        + MapUtils.mapToString(map)
                        + System.lineSeparator()
                        + System.lineSeparator()).getBytes(),
                operation);
    }

    public static Map<String, Integer> getMapSortedByKeys(Map<String, Integer> map){
        return getMapSortedBy(map, Map.Entry.comparingByKey(String::compareToIgnoreCase));
    }

    public static Map<String, Integer> getMapSortedByValues(Map<String, Integer> map){
        return getMapSortedBy(map, Map.Entry.comparingByValue());
    }

    private static Map<String, Integer> getMapSortedBy(Map<String, Integer> map, Comparator<Map.Entry<String, Integer>> comparator){
        return map.entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

    }

}
