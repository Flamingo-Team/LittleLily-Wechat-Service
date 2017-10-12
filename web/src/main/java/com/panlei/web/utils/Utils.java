package com.panlei.web.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lxl on 2017/8/31.
 */
public class Utils {
    public static List<String> findUrls(String text){
        String pattern = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        List<String> urls = new ArrayList<String>();
        while(m.find()){
            urls.add(m.group());
            System.out.println(m.group());
        }
        return new ArrayList<String>(new HashSet<String>(urls));
    }


    public static String convertColorTag(String text){

        String end = "</p>";
        String red = "<p style = \"color:red;\">";
        String green = "<p style = \"color:green;\">";
        String yellow = "<p style = \"color:yellow;\">";
        String blue = "<p style = \"color:blue;\">";
        String fuchsia = "<p style = \"color:fuchsia;\">";
        String aqua = "<p style = \"color:#00AEAE;\">";
        String black = "<p style = \"color:black;\">";


        String pattern = "\\u001b\\[(1;31|1;32|1;33|1;34|1;35|1;36|1;37|)m";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            //System.out.println(m.group());
            String match =  str2HexStr(m.group());
            //1.7jdk ������switch��String��
            if(match.equals("1B5B6D")){
                m.appendReplacement(sb, end);
            }else if(match.equals("1B5B313B33316D")){
                m.appendReplacement(sb,red);
            }else if(match.equals("1B5B313B33326D")){
                m.appendReplacement(sb, green);
            }else if(match.equals("1B5B313B33336D")){
                m.appendReplacement(sb, yellow);
            }else if(match.equals("1B5B313B33346D")){
                m.appendReplacement(sb, blue);
            }else if(match.equals("1B5B313B33356D")){
                m.appendReplacement(sb, fuchsia);
            }else if(match.equals("1B5B313B33366D")){
                m.appendReplacement(sb,aqua);
            }else if(match.equals("1B5B313B33376D")){
                m.appendReplacement(sb,black);
            }
        }
        m.appendTail(sb);
        String result = sb.toString();
        return result;
    }

    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            //sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static String getCurrentDay(){
        Calendar cal   =   Calendar.getInstance();
        String day = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
        System.out.println("getTime:   " + day);
        return day;
    }

    public static List<InetAddress> getIps(){
        List<InetAddress> ipList = new ArrayList<InetAddress>();
        InetAddress ip = null;
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
                        .nextElement();
                //System.out.println(netInterface.getName());
                if(!netInterface.getName().equals("eth0")){
                    continue;
                }
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
                        System.out.println("IP = " + ip.getHostAddress());
                        ipList.add(ip);
                    }
                }
            }
            System.out.println("get IP OK!");
            return ipList;
        }catch (Exception e){
            System.out.println("get IP ERR!");
            return ipList;
        }
    }
}
