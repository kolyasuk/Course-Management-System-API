package edu.sombra.cms.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayUtil {

    public static List<Integer> rangeList(int start, int end){
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
