package org.wzx.mycasserver.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * * @Description: websocket的具体实现类
 * * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
 * * 但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
 * 所以可以用一个静态set保存起来。
 */
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static MyScheduler myScheduler;

    //请参考https://blog.csdn.net/m0_37202351/article/details/86255132
    @Autowired
    public void setSchedulerOperation(MyScheduler myScheduler) {
        MyWebSocket.myScheduler = myScheduler;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param taskId 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String taskId, Session session) throws IOException {
        session.getBasicRemote().sendText("发送消息", true);//同步发送消息.
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
