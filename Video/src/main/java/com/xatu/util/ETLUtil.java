package com.xatu.util;

public class ETLUtil
{
    public static String etlStr(String oriStr)
    {
        StringBuffer sb = new StringBuffer();

        String[] fields = oriStr.split("\t");
        if (fields.length < 9) {
            return null;
        }
        fields[3] = fields[3].replaceAll(" ", "");
        for (int i = 0; i < fields.length; i++) {
            if (i < 9)
            {
                if (i == fields.length - 1) {
                    sb.append(fields[i]);
                } else {
                    sb.append(fields[i]).append("\t");
                }
            }
            else if (i == fields.length - 1) {
                sb.append(fields[i]);
            } else {
                sb.append(fields[i]).append("&");
            }
        }
        return sb.toString();
    }
}
