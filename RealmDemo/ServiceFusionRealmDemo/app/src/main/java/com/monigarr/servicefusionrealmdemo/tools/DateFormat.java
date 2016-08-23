package com.monigarr.servicefusionrealmdemo.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monigarr on 8/22/16.
 */

public class DateFormat {

    private static String DATE_PATTERN = "dd/MM/yyyy";

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }
}
