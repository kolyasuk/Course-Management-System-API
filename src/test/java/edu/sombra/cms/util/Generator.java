package edu.sombra.cms.util;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static <T> List<T> generateEmptyList(T object, int listSize) {
        List<T> res = new ArrayList<>();

        for (int i = 0; i < listSize; i++) {
            res.add(object);
        }

        return res;
    }
}
