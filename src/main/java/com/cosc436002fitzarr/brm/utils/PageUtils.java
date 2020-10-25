package com.cosc436002fitzarr.brm.utils;

import com.cosc436002fitzarr.brm.models.user.User;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {
    private static final String dataKey = "users";
    private static final String totalPagesKey = "totalPages";
    private static final String totalElementsKey = "totalElements";
    private static final String currentPageNumberKey = "currentPage";

    public static Map<String, Object> getUserMappingResponse(Page<User> objectPages, List<User> filteredUserContent) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(dataKey, filteredUserContent);
        responseMap.put(totalPagesKey, objectPages.getTotalPages());
        responseMap.put(totalElementsKey, filteredUserContent.size());
        responseMap.put(currentPageNumberKey, objectPages.getNumber());
        return responseMap;
    }
}
