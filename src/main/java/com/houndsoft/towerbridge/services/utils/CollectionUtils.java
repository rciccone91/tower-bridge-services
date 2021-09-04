package com.houndsoft.towerbridge.services.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> iterableToList(Iterable<T> iterable){
        final ArrayList<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
