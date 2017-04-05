package vdll.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hocean on 2017/2/10.
 */
public class Tools {
    public static List<String> getTagIn(String txt, String ltag, String rtag) {
        /*String find = null;
        String regex = String.format("(?<=%s)(\\S+)(?=%s)",ltag,rtag);
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (text);
        while (matcher.find())
        {
            find = matcher.group ();
        }
        return find;*/
        List<String> list = new ArrayList<>();

        while (true){
            int p = txt.indexOf(ltag);
            int q = txt.indexOf(rtag);
            if((p >= 0 && q >= 0)) {
                list.add(txt.substring(p + ltag.length(), q));
                txt = txt.substring(q + rtag.length());
            }else{
                break;
            }
        }

        return list;
    }
}
