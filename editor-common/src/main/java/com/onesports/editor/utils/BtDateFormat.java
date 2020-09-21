package com.onesports.editor.utils;

import org.beetl.core.Format;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zero
 */
public class BtDateFormat implements Format {

    private static final  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public BtDateFormat() {
    }
    @Override
    public Object format(Object data, String pattern) {
        if (data == null) {
            return null;
        } else {
            if (Date.class.isAssignableFrom(data.getClass())) {
                Instant instant = ((Date) data).toInstant();
                ZoneId zone = ZoneId.systemDefault();
                return dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, zone));
            } else if (data.getClass() == Long.class) {
                Instant instant = Instant.ofEpochMilli((Long) data);
                ZoneId zone = ZoneId.systemDefault();
                return dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, zone));
            } else {
                throw new RuntimeException("参数错误，输入为日期或者Long:" + data.getClass());
            }
        }
    }

}
