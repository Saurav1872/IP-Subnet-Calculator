package com.network.subnet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubnetMask {
    int mask;
    String subnetMask;
    public SubnetMask(int mask) {
        this.mask = mask;
        subnetMask = maskToSubnetMask(mask);
    }


    public SubnetMask(String subnetMask) {
        if(isValidSubnetMask(subnetMask)) {
            this.subnetMask = subnetMask;
            this.mask = subnetMaskToMask(subnetMask);
        }
    }

    public SubnetMask() {
        this.mask = 32;
        this.subnetMask = "255.255.255.255";
    }

    public void setSubnetMask(String subnetMask) {
        if(isValidSubnetMask(subnetMask)) {
            this.subnetMask = subnetMask;
            this.mask = subnetMaskToMask(subnetMask);
        }
    }

    public void setMask(int mask) {
        this.mask = mask;
        this.subnetMask = maskToSubnetMask(mask);
    }

    public int getMask() {
        return this.mask;
    }

    public String getSubnetMask() {
        return this.subnetMask;
    }

    public static boolean isValidSubnetMask(String subnetMask) {
        if(!isValidPattern(subnetMask)) {
            return false;
        }

        int smInt = subnetMaskToInt(subnetMask);
        boolean zeroEnds = false;
        while(smInt > 0) {
            if((smInt & 1) == 1) {
                if(!zeroEnds) {
                    zeroEnds = true;
                }
            }
            else {
                if(zeroEnds) {
                    return false;
                }
            }
        }
        return true;
    }

    public int subnetMaskToMask(String subnetMask) {
        if(!isValidSubnetMask(subnetMask)) {
            return -1;
        }

        int mask = 0;
        int smInt = subnetMaskToInt(subnetMask);

        for (int i = 0; i < 32; i++) {
            mask += (smInt & 1);
            smInt  = smInt >> 1;
        }

        return mask;
    }

    public static int numHostBits(int host) {
        int bitCount = 0;
        host = host + 2;    // network and broadcast
        while(host > 0) {
            host = host >> 1;
            bitCount++;
        }

        return bitCount;
    }

    public static int numNetworkBits(int subnets) {
        int bitCount = 0;
        while (subnets > 0) {
            subnets = subnets >> 1;
            bitCount++;
        }
        return bitCount;
    }

    public static String intToSubnetMask(int sm) {
        StringBuilder smStr = new StringBuilder("");

        int octMask = 255;
        byte oct[] = new byte[4];
        for (int i = 0; i < 4; i++) {
            oct[i] = (byte) (octMask & sm);
            sm = sm >> 8;
        }

        for (int i = 3; i >= 0; i--) {
            smStr.append(Integer.toString(Byte.toUnsignedInt(oct[i])));
            if(i != 0) {
                smStr.append(".");
            }
        }

        return smStr.toString();
    }

    public static boolean isValidPattern(String subnetMask) {
        // 0-255 checker (0-9, 10 - 99, 100 - 255)
        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";

        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        Pattern p = Pattern.compile(regex);

        if (subnetMask == null) {
            return false;
        }

        Matcher m = p.matcher(subnetMask);
        
        return m.matches();
    }

    public static int subnetMaskToInt(String subnetMask) {

        if(!isValidPattern(subnetMask)) {
            return -1;
        }
        String[] octets = subnetMask.split("\\.");
        int oct = 0;
        int mask = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            mask = mask << 8;
            mask = mask | oct;
        }
        return mask;
    }

    public static String maskToSubnetMask(int mask) {
        int smInt = 0;
        int t = mask;
        for (int i = 0; i < 32; i++) {
            smInt = (smInt << 1);
            if(t > 0) {
                smInt = smInt | 1;
            }
            t--;
        }

        return intToSubnetMask(smInt);
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

    public void printSubnetBits() {
        String[] octets = subnetMask.split("\\."); 
        int oct = 0;
        for(String octet : octets) {
            oct = Integer.parseInt(octet);
            System.out.print(octetToBinaryString(oct)+" ");
        }
        System.out.println();
    }
}
