/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.services;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by monicapeters on 8/29/16.
 */

public class DateFormat {

    private static String DATE_PATTERN = "dd/MM/yyyy";

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }
}
