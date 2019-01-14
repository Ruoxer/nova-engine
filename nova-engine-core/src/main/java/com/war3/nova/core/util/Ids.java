package com.war3.nova.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

/**
 * id生成工具类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午3:10:17
 * @version 1.0
 */
public class Ids {
    
    private final static SnowflakeIdGenerator idWorker = new SnowflakeIdGenerator(0, 0);
    
    private static String randomString = "";
    
    static {
        String ip;
        long macAddress = 0L;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            macAddress = Long.parseLong(getLocalMac().replace("-", ""), 16) % 31;
        } catch (Exception e) {
            ip = "127.0.0.1";
        }
        long randomValue = ipToLong(ip);
        int randomNum = ThreadLocalRandom.current().nextInt(1000);
        randomValue = randomNum + randomValue;
        randomValue = randomValue + macAddress;
        randomValue = randomValue + ThreadLocalRandom.current().nextInt(1000);
        randomString = String.valueOf(randomValue);
    }
    
    public final static String getId() {
        Long idValue = idWorker.nextId();
        String id = Long.toString(idValue);
        StringBuffer sb = new StringBuffer();
        if (id.startsWith("-")) {
            sb.append("Z").append(randomString).append(id.substring(1));
        } else {
            sb.append("A").append(randomString).append(id);
        }
        return sb.toString();
    }
    
    /**
     * ip地址转成long型数字
     * 将IP地址转化成整数的方法如下：
     * 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     * @param strIp
     * @return
     */
    private static long ipToLong(String strIp) {
        String[]ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }
 
    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     * 将整数形式的IP地址转化成字符串的方法如下：
     * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     * @param longIp
     * @return
     */
    protected static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
    
    private static String getLocalMac() throws Exception {
        return getLocalMac(InetAddress.getLocalHost());
    }
    
    private static String getLocalMac(InetAddress ia) throws Exception {

        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer("");
        for(int i=0; i<mac.length; i++) {
            if(i!=0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if(str.length()==1) {
                sb.append("0"+str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();

    }

}
