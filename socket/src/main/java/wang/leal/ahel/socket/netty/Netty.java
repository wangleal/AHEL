package wang.leal.ahel.socket.netty;

import android.util.Base64;

import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import wang.leal.ahel.socket.client.IConnection;
import wang.leal.ahel.socket.log.Logger;

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
                    socketChannel.pipeline().addLast(new ByteArrayDecoder());
                    socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(clientHandler);
                }
            });
    private Channel channel;

    @Override
    public void connect(String host, int port,OnConnectionListener onConnectionListener){
        clientHandler.listen(new WeakReference<>(onConnectionListener));
        try {
            bootstrap.connect(host,port).addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    channel = channelFuture.channel();
                    if (onConnectionListener!=null){
                        onConnectionListener.onConnected();
                    }
                }
            }).syncUninterruptibly();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        try {
            Logger.e("netty close");
            channel.close();
        }catch (Exception e){
            Logger.e("netty close failed");
            e.printStackTrace();
        }
        Logger.e("netty close success");
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

    private static class ClientHandler extends SimpleChannelInboundHandler<byte[]>{
        WeakReference<OnConnectionListener> listenerWeakReference;

        private void listen(WeakReference<OnConnectionListener> listenerWeakReference){
            this.listenerWeakReference = listenerWeakReference;
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, byte[] msg) {
            if (listenerWeakReference!=null&&listenerWeakReference.get()!=null){
                Logger.e("netty receive message:"+new String(msg, Charset.forName("UTF-8")));
                listenerWeakReference.get().onMessageReceive(Base64.encodeToString(msg,Base64.DEFAULT));
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }
    }

}
