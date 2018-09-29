package com.cn.eventandlistener.event;

import com.cn.eventandlistener.bean.UserBean;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


/**
 * 创建一个事件，监听都是围绕着事件来挂起的
 *
 * 我们自定义事件UserRegisterEvent继承了ApplicationEvent，继承后必须重载构造函数，构造函数的参数可以任意指定，
 * 其中source参数指的是发生事件的对象，一般我们在发布事件时使用的是this关键字代替本类对象，
 * 而user参数是我们自定义的注册用户对象，该对象可以在监听内被获取。
 */

@Getter
public class UserRegisterEvent extends ApplicationEvent
{
    //注册用户对象
    private UserBean user;

    /**
     * 重写构造函数
     * @param source 发生事件的对象
     * @param user 注册用户对象
     */
    public UserRegisterEvent(Object source,UserBean user) {
        super(source);
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
