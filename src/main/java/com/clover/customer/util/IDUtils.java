package com.clover.customer.util;

import java.util.UUID;

/**
 * Created by clover on 2017/11/3.
 */
public class IDUtils {
    public static String generateID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
