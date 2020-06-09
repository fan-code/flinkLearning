package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static void main(String[] args) throws ParseException {

        String time="2020-04-25T23:59:59.956+0800";
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date parse = simpleDateFormat.parse(time);

        System.out.println(parse.getTime());

    }
}
