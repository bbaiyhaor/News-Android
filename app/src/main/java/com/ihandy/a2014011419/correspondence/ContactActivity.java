package com.ihandy.a2014011419.correspondence;

import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by byr on 2016/9/3.
 */
public class ContactActivity {
    static public void send(Context sender, Class reciever, List<String> name, List<String> info){
        Intent intent = new Intent();
        // 在Intent中传递数据
        for (int i = 0; i < name.size(); i++)
            intent.putExtra(name.get(i), info.get(i));
        //setClass函数的第一个参数是一个Context对象
        //Context是一个类，Activity是Context类的子类，也就是说，所有的Activity对象，都可以向上转型为Context对象
        //setClass函数的第二个参数是一个Class对象，在当前场景下，应该传入需要被启动的Activity类的class对象
        intent.setClass(sender, reciever);
        sender.startActivity(intent);
    }
}
