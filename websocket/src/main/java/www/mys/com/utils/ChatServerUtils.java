package www.mys.com.utils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class ChatServerUtils {

    private static final Logger log = Logger.getLogger(ChatServerUtils.class.getName());

    private final String host;
    private final String split;
    private final int port;
    private final ServerBack serverBack;
    private boolean running;
    private WebSocketChannelInitializer initializer;

    public static class BaseSimpleChannelInboundHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

        private ServerBack serverBack;

        public BaseSimpleChannelInboundHandler(ServerBack serverBack) {
            this.serverBack = serverBack;
        }

        protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
            serverBack.onReceiveMessage(ctx, frame);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            serverBack.onConnect(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            serverBack.onDisConnect(ctx);
        }
    }

    private void init() {
        running = true;
        log.info("host=" + host + ";port=" + port + ";split=" + split);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            initializer = new WebSocketChannelInitializer(split, serverBack);
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                    .childHandler(initializer);
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(host, port)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("ChatServerUtils init error.e=" + e);
            running = false;
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

        private String split;
        private ServerBack serverBack;

        public WebSocketChannelInitializer(String split, ServerBack serverBack) {
            this.split = split;
            this.serverBack = serverBack;
        }

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
            pipeline.addLast(new WebSocketServerProtocolHandler(split, null
                    , true, Integer.MAX_VALUE));
            pipeline.addLast(new BaseSimpleChannelInboundHandler(serverBack));
        }
    }

    public ChatServerUtils(String host, String split, int port, ServerBack serverBack) {
        this.host = host;
        this.split = split;
        this.port = port;
        this.serverBack = serverBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }).start();
    }

    public boolean isRunning() {
        return running;
    }

    public static interface ServerBack {
        public void onConnect(ChannelHandlerContext ctx);

        public void onDisConnect(ChannelHandlerContext ctx);

        public void onReceiveMessage(ChannelHandlerContext ctx, WebSocketFrame frame);
    }

}
