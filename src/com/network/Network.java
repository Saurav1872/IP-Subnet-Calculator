package com.network;

import com.network.subnet.IP;
import com.network.subnet.SubnetMask;

public class Network{
    IP ip;
    SubnetMask subnetMask;

    public Network(String ip, int mask) {
        this.ip = new IP(ip);
        this.subnetMask = new SubnetMask(mask);
    }

    public Network(String ip, String subnetMask) {
        this.ip = new IP(ip);
        this.subnetMask = new SubnetMask(subnetMask);
    }

    public Network() {
        this.ip = new IP();
        this.subnetMask = new SubnetMask("255.255.255.255");
    }

    public void setIP(String ip) {
        if(IP.isValidIP(ip)) {
            this.ip.setIP(ip);
        }
    }

    public void setSubnetMask(String subnetMask) {
        if(SubnetMask.isValidSubnetMask(subnetMask)) {
            this.subnetMask.setSubnetMask(subnetMask);
        }
    }

    public void setMask(int mask) {
        this.subnetMask.setMask(mask);
    }

    public static String getNetworkAddress(IP ip, SubnetMask subnetMask) {
        int ipInt = IP.ipToInt(ip.getIp());
        int smInt = SubnetMask.subnetMaskToInt(subnetMask.getSubnetMask());

        int netInt = (ipInt & smInt);

        return IP.intToIp(netInt);
    }

    public static String getBroadcastAddress(IP ip, SubnetMask subnetMask) {
        int ipInt = IP.ipToInt(ip.getIp());
        int smInt = SubnetMask.subnetMaskToInt(subnetMask.getSubnetMask());

        int boradInt = (ipInt | (~smInt));

        return IP.intToIp(boradInt);
    }

    public static String getNetworkRange(IP ip, SubnetMask subnetMask) {
        int ipInt = IP.ipToInt(ip.getIp());
        int smInt = SubnetMask.subnetMaskToInt(subnetMask.getSubnetMask());

        int firstIpInt = (ipInt & smInt) + 1;
        int lastIpInt = (ipInt | (~smInt)) - 1;

        return (IP.intToIp(firstIpInt) + " - " + IP.intToIp(lastIpInt));
    }

    public static int getHost(SubnetMask subnetMask) {
        int smInt = SubnetMask.subnetMaskToInt(subnetMask.getSubnetMask());
        smInt = ~smInt;

        return smInt+1;
    }

    public void printNetworkInfo() {
        printNetworkInfo(ip, subnetMask);
    }

    public static void printNetworkInfo(IP ip, SubnetMask subnetMask) {
        System.out.println("IP Address: " + ip.getIp()+"/"+subnetMask.getMask());
        System.out.println("Subnet Mask: " + subnetMask.getSubnetMask());
        System.out.println("Network Address: " + getNetworkAddress(ip, subnetMask));
        System.out.println("Boardcast Address: " + getBroadcastAddress(ip, subnetMask));
        System.out.println("Usable Host IP Range: " + getNetworkRange(ip, subnetMask));
        System.out.println("Total Hosts: " + getHost(subnetMask));
        System.out.println("Number of Usable Hosts: " + (getHost(subnetMask)-2));

    }

    public static void printNetworkInfo(String ip, String subnetMask) {
        printNetworkInfo(new IP(ip), new SubnetMask(subnetMask));
    }

    public static void printNetworkInfo(String ip, int mask) {
        printNetworkInfo(new IP(ip), new SubnetMask(mask));
    }
}
