package org.woodwhales.tank.net.msg;

import java.util.List;

import org.woodwhales.tank.net.MsgType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println(in.readableBytes());

        // 等待 --> 消息的类型(一个int占4个字节)+消息的长度(一个int占4个字节)
        if(in.readableBytes() < 8) {
            return;
        }
        
        // 标记读取的位置
        in.markReaderIndex();
        
        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();
        
        if(in.readableBytes() < length) {
        	in.resetReaderIndex();
        	return;
        }
        
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        
        BaseMsg msg = null;
        switch (msgType) {
		case TankJoin:
			msg = new TankJoinMsg();
			break;
		case TankStartMoving:
			msg = new TankStartMovingMsg();
			break;
		case TankStop:
			msg = new TankStopMsg();
			break;
		default:
			break;
		}

        msg.parse(bytes);
		out.add(msg);

    }
}
