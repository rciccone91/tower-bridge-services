package com.houndsoft.towerbridge.services.utils;

import com.houndsoft.towerbridge.services.response.PadreSimpleResponse;

import java.util.Comparator;

public class AlphabeticalOrder {

    public static Comparator<PadreSimpleResponse> PADRES_ALPHABETICAL_ORDER = new Comparator<PadreSimpleResponse>() {
        public int compare(PadreSimpleResponse p1, PadreSimpleResponse p2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(p1.getNombre(), p2.getNombre());
            if (res == 0) {
                res = p1.getNombre().compareTo(p2.getNombre());
            }
            return res;
        }
    };
}

