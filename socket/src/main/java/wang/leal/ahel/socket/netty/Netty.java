package wang.leal.ahel.socket.netty;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

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
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import wang.leal.ahel.socket.client.IConnection;

public class Netty implements IConnection {
    private EventLoopGroup group = new NioEventLoopGroup();
    private ClientHandler clientHandler = new ClientHandler();
    private Bootstrap bootstrap = new Bootstrap().group(group)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel){
                    socketChannel.pipeline().addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(clientHandler);
                }
            });
    private ChannelFuture channelFuture;
    private Channel channel;

    @Override
    public void connect(String host, int port){
        try {
            channelFuture = bootstrap.connect(host,port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        channel = channelFuture.channel();
                    }
                }
            }).syncUninterruptibly();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void listen(OnMessageReceiveListener onMessageReceiveListener) {
        clientHandler.listen(new WeakReference<>(onMessageReceiveListener));
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

    private static class ClientHandler extends SimpleChannelInboundHandler<String>{
        WeakReference<OnMessageReceiveListener> listenerWeakReference;

        private void listen(WeakReference<OnMessageReceiveListener> listenerWeakReference){
            this.listenerWeakReference = listenerWeakReference;
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
            if (listenerWeakReference!=null&&listenerWeakReference.get()!=null){
                listenerWeakReference.get().onMessageReceive(msg);
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }
    }

}
