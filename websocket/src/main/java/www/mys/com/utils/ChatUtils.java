package www.mys.com.utils;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class ChatUtils {

    private static final Logger log = Logger.getLogger(ChatUtils.class.getName());
    
    public static final String SUCCESS = "S";
    public static final String FAILED = "F";
    public static final String SPLIT = "#";

    public static void sendData(Channel channel, String data, ChannelFutureListener channelFutureListener) {
        TextWebSocketFrame frame = new TextWebSocketFrame(data);
        channel.writeAndFlush(frame).addListener(channelFutureListener);
    }

    public static void sendText(Integer id, Channel toChannel, String data) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sendData(toChannel, data, future -> {
                result.append(future.isDone() ? SUCCESS : FAILED);
            });
            while (result.length() == 0) {
                sleep(5);
            }
            if (SUCCESS.equals(result.toString())) {
                break;
            }
        }
        switch (result.toString()) {
            case SUCCESS:
                log.info("send text success.id=" + id);
                break;
            case FAILED:
                log.info("send text failed.id=" + id);
                break;
        }
    }

    public static void sendData(Channel channel, byte[] data, ChannelFutureListener channelFutureListener) {
        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(Unpooled.buffer().writeBytes(data));
        channel.writeAndFlush(binaryWebSocketFrame).addListener(channelFutureListener);
    }

    public static String getChannelId(Channel channel) {
        return channel.id().asLongText();
    }

    public static String getChannelIpPort(Channel channel) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
        return inetSocketAddress.getHostName() + SPLIT + inetSocketAddress.getPort();
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }

}
