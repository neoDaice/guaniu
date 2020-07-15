package base.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("test.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("to.txt");
        //获取通道
        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
        //初始化一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(32);
        //从通道读取数据写入缓冲区,直到读到文件末尾
        while (inputChannel.read(buffer) != -1){
            //切换缓冲区模式
            buffer.flip();
            //输出数据到通道
            outputChannel.write(buffer);
            //清空缓存区
            buffer.clear();
        }
    }

    public static Selector getSelector() throws IOException {
        //创建selector对象
        Selector selector = Selector.open();
        //开启socket通道
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        //指定通道的端口
        ServerSocket socket = server.socket();
        InetSocketAddress address = new InetSocketAddress(9999);
        socket.bind(address);
        //向selector中注册，并绑定感兴趣的事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }

    public static void test() throws IOException {
        Selector selector = getSelector();
        //该调用会阻塞，直到至少有一个事件就绪、准备发生
        selector.select();
        // 一旦上述方法返回，线程就可以处理这些事件
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iter = keys.iterator();
        while (iter.hasNext()) {
            SelectionKey key = (SelectionKey) iter.next();
            iter.remove();
            //process(key);
        }
    }
}
