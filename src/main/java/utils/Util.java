package utils;

import java.util.regex.Pattern;

public class Util {
    public static boolean checkExtImg(String fileName){
        boolean x = false;
        String[] fileNameSplit = fileName.split(Pattern.quote("."));
        String ext = fileNameSplit[fileNameSplit.length - 1];
        if(ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("bmp")){
            x = true;
        }
        return x;
    }
}
