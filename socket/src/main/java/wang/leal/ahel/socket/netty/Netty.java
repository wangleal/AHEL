package wang.leal.ahel.socket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import wang.leal.ahel.socket.IConnection;

public class Netty implements IConnection {
    private OnMessageReceiveListener onMessageReceiveListener;
    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap().group(group)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel){
//                    socketChannel.pipeline().addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));//5s未发送数据，回调userEventTriggered
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                            System.out.println("netty msg:"+msg);
                            if (onMessageReceiveListener!=null){
                                onMessageReceiveListener.onMessageReceive(msg);
                            }
                        }

                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            super.userEventTriggered(ctx, evt);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            super.exceptionCaught(ctx, cause);
                            System.out.println("netty exception");
                        }
                    });
                }
            });
    private ChannelFuture channelFuture;
    private Channel channel;

    @Override
    public void connect(String host, int port){
        channelFuture = bootstrap.connect(host,port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    channel = channelFuture.channel();
                }
            }
        }).syncUninterruptibly();
    }

    @Override
    public void listen(OnMessageReceiveListener onMessageReceiveListener) {
        this.onMessageReceiveListener = onMessageReceiveListener;
    }

    @Override
    public void close(){
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

    @Override
    public void reconnect(String host,int port){
    }

    @Override
    public void sendMessage(String message) {
        if (channel!=null){
            channel.writeAndFlush(message);
        }
    }


}
