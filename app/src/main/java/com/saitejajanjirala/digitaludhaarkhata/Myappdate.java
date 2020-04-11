package com.saitejajanjirala.digitaludhaarkhata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Myappdate {
    public String getMdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String  format= simpleDateFormat.format(new Date());
        return format;
    }
}
