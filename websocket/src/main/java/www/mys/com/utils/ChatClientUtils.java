package www.mys.com.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.logging.Logger;

public class ChatClientUtils {

    private static final Logger log = Logger.getLogger(ChatClientUtils.class.getName());

    private String url = "ws://localhost:8101/ws";
    private String userKey;
    private boolean running;
    private Channel channel;

    public SendBack sendText(String message) {
        if (message == null) {
            return new SendBack(-1, "baseChatPOJO is null.", null);
        }
        if (channel == null) {
            return new SendBack(-2, "user not connect.", null);
        }
        final StringBuilder result = new StringBuilder();
        ChatUtils.sendData(channel
                , message.getBytes()
                , future -> {
                    log.info("operationComplete future=" + future);
                    log.info("operationComplete isDone=" + future.isDone());
                    if (future.isDone()) {
                        result.append(ChatUtils.SUCCESS);
                    } else {
                        result.append(ChatUtils.FAILED);
                    }
                });
        while (result.length() == 0) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        if (ChatUtils.SUCCESS.equals(result.toString())) {
            return new SendBack(1, "send success.", message);
        } else {
            return new SendBack(-2, "send failed.", message);
        }
    }

    public static class MockClientHandler extends SimpleChannelInboundHandler<Object> {

        private void onTextBack(TextWebSocketFrame textFrame) {
            String text = textFrame.text();
            log.info("onTextBack text=" + text);
        }

        private void onBinaryBack(BinaryWebSocketFrame binaryFrame) {
            log.info("onBinaryBack" + new String(binaryFrame.content().array()));
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("channelRead0 ctx=" + ctx);
            log.info("channelRead0 msg=" + msg);
            log.info("channelRead0  " + webSocketClientHandshaker.isHandshakeComplete());
            Channel ch = ctx.channel();
            FullHttpResponse response;
            if (!webSocketClientHandshaker.isHandshakeComplete()) {
                handShake(ctx, msg);
            } else if (msg instanceof FullHttpResponse) {
                response = (FullHttpResponse) msg;
                throw new IllegalStateException("Unexpected FullHttpResponse (getStatus="
                        + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
            } else {
                WebSocketFrame frame = (WebSocketFrame) msg;
                if (frame instanceof TextWebSocketFrame) {
                    onTextBack((TextWebSocketFrame) frame);
                } else if (frame instanceof BinaryWebSocketFrame) {
                    onBinaryBack((BinaryWebSocketFrame) frame);
                } else if (frame instanceof PongWebSocketFrame) {
                    PongWebSocketFrame pongFrame = (PongWebSocketFrame) frame;
                    log.info("WebSocket Client received pong data=" + new String(pongFrame.content().array()));
                } else if (frame instanceof CloseWebSocketFrame) {
                    CloseWebSocketFrame closeFrame = (CloseWebSocketFrame) frame;
                    log.info("receive close frame data=" + new String(closeFrame.content().array()));
                    ch.close();
                }
            }
        }

        private final WebSocketClientHandshaker webSocketClientHandshaker;
        private final ChatClientUtils chatClientUtils;
        private final String userKey;

        public MockClientHandler(WebSocketClientHandshaker webSocketClientHandshaker
                , ChatClientUtils chatClientUtils, String userKey) {
            this.webSocketClientHandshaker = webSocketClientHandshaker;
            this.chatClientUtils = chatClientUtils;
            this.userKey = userKey;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            log.info("channelActive");
            chatClientUtils.channel = ctx.channel();
            webSocketClientHandshaker.handshake(ctx.channel());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            log.info("channelInactive");
            chatClientUtils.channel = null;
        }

        private void handShake(ChannelHandlerContext ctx, Object msg) {
            try {
                FullHttpResponse response = (FullHttpResponse) msg;
                webSocketClientHandshaker.finishHandshake(ctx.channel(), response);
                ctx.newPromise().setSuccess();
                log.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}" + response.headers());
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status()
                        , res.content().toString(CharsetUtil.UTF_8));
                ctx.newPromise().setFailure(new Exception(errorMsg));
            }
        }

    }

    public static class MockClientInitializer extends ChannelInitializer<SocketChannel> {
        private MockClientHandler mockClientHandler;

        MockClientInitializer(MockClientHandler mockClientHandler) {
            this.mockClientHandler = mockClientHandler;
        }

        @Override
        protected void initChannel(SocketChannel channel) {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 1024));
            pipeline.addLast(mockClientHandler);
        }
    }

    public ChatClientUtils(String userKey) {
        this.userKey = userKey;
        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }).start();
    }

    public ChatClientUtils(String userKey, String url) {
        this.userKey = userKey;
        this.url = url;
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

    private void init() {
        running = true;
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            URI uri = new URI(url);
            Bootstrap bootstrap = new Bootstrap();
            MockClientHandler webSocketClientHandler = new MockClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri
                            , WebSocketVersion.V13
                            , null
                            , false
                            , new DefaultHttpHeaders())
                    , this
                    , userKey);
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new MockClientInitializer(webSocketClientHandler));
            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.info("ChatClient2Utils init error.e=" + e);
            eventLoopGroup.shutdownGracefully();
            running = false;
        }
    }

}