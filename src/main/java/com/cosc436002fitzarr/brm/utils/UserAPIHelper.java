package com.cosc436002fitzarr.brm.utils;

public class UserAPIHelper {
    public static void checkUserNotDeletingThemselves(String id1, String id2) {
        if (id1.equals(id2)) {
            throw new IllegalArgumentException("User cannot delete themselves.");
        }
    }
}
