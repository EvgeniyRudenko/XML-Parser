package yevhen;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            System.out.println("You need to pass pathes to files comparing");
            System.exit(0);
        }
        int order = getOrder(args);
        String filePath1 = args[0];
        String filePath2 = args[1];
        String fileName1 = extractFileName(filePath1);
        String fileName2 = extractFileName(filePath2);
        FileInputStream file1 = getFileInputStream(filePath1);
        FileInputStream file2 = getFileInputStream(filePath2);
        TasksList list1 = parse(file1);
        TasksList list2 = parse(file2);
        Map<String,Integer> map1 = getMap(list1, order);
        Map<String,Integer> map2 = getMap(list2, order);
        MapDifference<String, Integer> difference = Maps.difference(map1, map2);
        Path outputFilePath = Paths.get("./output.txt");
        MapUtils.writeMapToFile("Entries differing",
                                difference.entriesDiffering(),
                                outputFilePath);
        MapUtils.writeMapToFile("Entries in common",
                                difference.entriesInCommon(),
                                outputFilePath,
                                StandardOpenOption.APPEND);
        MapUtils.writeMapToFile("Entries specific for file " + fileName1,
                                difference.entriesOnlyOnLeft(),
                                outputFilePath,
                                StandardOpenOption.APPEND);
        MapUtils.writeMapToFile("Entries specific for file " + fileName2,
                                difference.entriesOnlyOnRight(),
                                outputFilePath,
                                StandardOpenOption.APPEND);
    }

    private static int getOrder(String[] args) {
        int order = 0;
        if (args.length<3) return order;
        try{
            order = Integer.parseInt(args[2]);
            if (order!=0 && order!=1 && order!=2){
                incorrectOrderValue();
                order=0;
            }
        } catch (NumberFormatException ignored){
            incorrectOrderValue();
        }
        return order;
    }

    private static void incorrectOrderValue() {
        System.out.println("The 3-rd parameter is incorrect. No sorting will be applied"
                + System.lineSeparator()
                + "In future please pass 0(no sorting), 1(sort by name), 2(sort by memory)");
    }

    private static String extractFileName(String filePath) {
        String[] array = filePath.split("\\\\");
        return array[array.length-1];
    }

    private static FileInputStream getFileInputStream(String filePath) {
        FileInputStream file=null;
        try {
            file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("File " + filePath +  " can not be found");
            System.exit(0);
        }
        return file;
    }

    private static Map<String, Integer> getMap(TasksList list, int order) {
        Map<String, Integer> map = null;
        switch (order){
            case 0: map = list.getMap(); break;
            case 1: map = MapUtils.getMapSortedByKeys(list.getMap()); break;
            case 2: map = MapUtils.getMapSortedByValues(list.getMap()); break;
        }
        return map;
    }

    private static TasksList parse(FileInputStream file){
        JAXBContext ctx = null;
        try {
            ctx = JAXBContext.newInstance(TasksList.class);
            Unmarshaller um = ctx.createUnmarshaller();
            return (TasksList) um.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println("Something must be wrong with the file" + file +
                    " and therefore it can not be parsed");
        }
        return null;
    }

}
