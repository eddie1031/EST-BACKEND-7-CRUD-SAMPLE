package com.est.curdsample.util;

public class PriorityResolver {

    public static String resolve(Integer priority) {
        return switch (priority) {
            case 0 -> " ";
            case 1 -> "text-red-700";
            case 2 -> "text-red-500";
            case 3 -> "text-yellow-500";
            case 4 -> "text-green-500";
            default -> "text-green-700";
        };
    }

}
