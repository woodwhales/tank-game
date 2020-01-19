package org.woodwhales.tank.net;

import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.net.msg.BaseMsg;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;
import org.woodwhales.tank.net.msg.TankJoinMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {

    private Channel channel = null;
    
    public static final Client INSTANCE = new Client();
    
    private Client() {}
    
    public void connect() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.TCP_NODELAY, false);

        try {
            ChannelFuture future = bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        log.info("this client connected to server!");
                        // 成功连接服务器时初始化 channel
                        channel = future.channel();
                    } else {
                    	log.error("this client not connected to server!");
                    }

                }
            });

            future.sync();
            future.channel().closeFuture().sync();

        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void send(BaseMsg msg) {
    	channel.writeAndFlush(msg);
    }
    
    public void send(String message) {
        ByteBuf buf = Unpooled.copiedBuffer(message.getBytes());
        channel.writeAndFlush(buf);
    }

    public void closeConnect() {
        this.send("_bye_");
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
            .addLast(new MsgEncoder())
            .addLast(new MsgDecoder())
            .addLast(new ClientHandler());
    }
}

@Slf4j
class ClientHandler extends SimpleChannelInboundHandler<BaseMsg> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {
        log.info("client --> {}", msg);
        msg.handle();
	}

}