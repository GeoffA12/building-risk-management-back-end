package com.cosc436002fitzarr.brm.utils;

import com.cosc436002fitzarr.brm.models.user.User;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {
    private static final String dataKey = "Data";
    private static final String totalPagesKey = "Total number of pages";
    private static final String totalElementsKey = "Total number of elements";
    private static final String currentPageNumberKey = "Current page number";

    public static Map<String, Object> getUserMappingResponse(Page<User> objectPages, List<User> filteredUserContent) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(dataKey, filteredUserContent);
        responseMap.put(totalPagesKey, objectPages.getTotalPages());
        responseMap.put(totalElementsKey, objectPages.getTotalElements());
        responseMap.put(currentPageNumberKey, objectPages.getNumber());
        return responseMap;
    }
}
