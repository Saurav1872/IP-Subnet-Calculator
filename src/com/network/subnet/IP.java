package com.network.subnet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IP {
    String ip;

    public IP() {
        ip = "0.0.0.0";
    }

    public IP(String ip) {
        if(isValidIP(ip)) {
            this.ip = ip;
        }
        else {
            ip = "0.0.0.0";
        }
    }

    public boolean setIP(String ip) {
        if(isValidIP(ip)) {
            this.ip = ip;
            return true;
        }
        else {
            return false;
        }
    }

    public String getIp() {
        return this.ip;
    }

    public static boolean isValidIP(String ip) {
        // 0-255 checker (0-9, 10 - 99, 100 - 255)
        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";

        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        Pattern p = Pattern.compile(regex);

        if (ip == null) {
            return false;
        }

        Matcher m = p.matcher(ip);
        
        return m.matches();
    }

    public int ipToInt() {
        if(!isValidIP(ip)) {
            return -1;
        }
        String[] octets = ip.split("\\.");
        int oct = 0;
        int ipInt = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            ipInt = ipInt << 8;
            ipInt = ipInt | oct;
        }
        return ipInt;
    }

    public static int ipToInt(String ip) {
        if(!isValidIP(ip)) {
            return -1;
        }
        String[] octets = ip.split("\\.");
        int oct = 0;
        int ipInt = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            ipInt = ipInt << 8;
            ipInt = ipInt | oct;
        }
        return ipInt;
    }

    public static String intToIp(int ipInt) {
        StringBuilder ipadd = new StringBuilder("");

        int octMask = 255;
        byte oct[] = new byte[4];
        for (int i = 0; i < 4; i++) {
            oct[i] = (byte) (octMask & ipInt);
            ipInt = ipInt >> 8;
        }

        for (int i = 3; i >= 0; i--) {
            ipadd.append(Integer.toString(Byte.toUnsignedInt(oct[i])));
            if(i != 0) {
                ipadd.append(".");
            }
        }

        return ipadd.toString();
    }
    
    private static String octetToBinaryString(int octet) {
        StringBuilder binary = new StringBuilder("");
        for (int i = 0; i < 8; i++) {
            if((octet & 1) == 0) {
                binary.append("0");
            }
            else {
                binary.append("1");
            }
            octet = octet >> 1;
        }

        return binary.reverse().toString();
    }


    public void printIpBits() {
        if(!isValidIP(ip)) {
            System.out.println("[Error] Invalid IP (IP.ip is a valid ip)");
            return;
        }

        String[] octets = ip.split("\\.");
        int oct = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            System.out.print(octetToBinaryString(oct)+" ");
        }
        System.out.println();
    }

    public static void printIpBits(String ip) {
        if(!isValidIP(ip)) {
            System.out.println("[Error] Invalid IP Address");
            return;
        }

        String[] octets = ip.split("\\.");
        int oct = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            System.out.print(octetToBinaryString(oct)+" ");
        }
        System.out.println();
    }


}
