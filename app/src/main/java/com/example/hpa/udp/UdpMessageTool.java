package com.example.hpa.udp;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created time : 2018/5/25 9:45.
 *
 * @author HPA
 */
public class UdpMessageTool {
    private byte[] bytes = new byte[2048];
    // DatagramSocket代表UDP协议的Socket,作用就是接收和发送数据报
    private DatagramSocket mDatagramSocket = null;
    public static UdpMessageTool instance;

    //创建UdpMessageTool对象
    public UdpMessageTool() throws SocketException {
        //初始化DatagramSocket，也可以传入指定端口号
        mDatagramSocket = new DatagramSocket();
    }

    // 操作类获取单例实例
    public static UdpMessageTool getInstance() throws SocketException {
        if (instance == null) {
            instance = new UdpMessageTool();
        }
        return instance;
    }

    //设置超时时间
    public final void setTimeOut(final int timeOut) throws SocketException {
        mDatagramSocket.setSoTimeout(timeOut);
    }

    // 获取DatagramSocket对象
    public final DatagramSocket getDatagramSocket() {
        return mDatagramSocket;
    }

    // 向指定的服务端发送数据信息. 参数介绍： host 服务器主机地址 port 服务端端口 bytes 发送的数据信息
    public final synchronized void send(final String host, final int port, final byte[] bytes) throws IOException {
        Log.e("TAG", "实例化UPDSocket");
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port);
        Log.e("TAG", "长度==" + bytes.length);
        mDatagramSocket.send(datagramPacket);
        Log.e("TAG","发送完毕");
    }

    // 接收从指定的服务端发回的数据. hostName 服务端主机 hostPort 服务端端口 return 服务端发回的数据
    public final synchronized String receive(final String hostName, final int hostPort) {

        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        try {
            mDatagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        String result = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        return result;
    }

}
