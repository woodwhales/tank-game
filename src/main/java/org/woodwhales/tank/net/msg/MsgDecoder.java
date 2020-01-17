package org.woodwhales.tank.net.msg;

import java.util.List;

import org.woodwhales.tank.net.MsgType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 等待 --> 消息的类型(一个int占4个字节)+消息的长度(一个int占4个字节)
        if(in.readableBytes() < 8) {
            return;
        }
        
        // 标记读取的位置
        in.markReaderIndex();
        
        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();
        
        // 直到读取到length长度才接收
        if(in.readableBytes() < length) {
        	// 读取长度不够，需要重新回到标记位置读取，直到读取length长度的数据
        	in.resetReaderIndex();
        	return;
        }
        
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        
		// 使用反射自动创建出BaseMsg对象
        BaseMsg msg = (BaseMsg) Class.forName("org.woodwhales.tank.net.msg."+msgType.name()+"Msg")
        					.newInstance();

        /* 这种方式灵活性不如上述的反射机制好
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
		*/

        msg.parse(bytes);
		out.add(msg);

    }
}
