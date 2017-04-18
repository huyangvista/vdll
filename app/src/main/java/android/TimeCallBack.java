package android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 系统级定时器
 * 系统为了节约电量对齐唤醒手机   基本1分钟  扫描一次 即误差 1分钟  loopTime循环误差1分钟  accurateLoopTime循环误差5秒钟
 * [广播实现 缺点: 系统轮询消息会占用时间故根据系统情况 周期太小会延迟调用  虚拟机6.0测试小于2S 会延迟执行]
 * 重要 本类使用需要提前调用注册方法  register();  开始start(ITimeCall icall);  用户退出程序后 仍然能够执行调用方法   直到调用stop();
 * Created by Hocean on 2016/11/10.
 */

public class TimeCallBack {
    private Context context; //上下文
    private int requestCode;  //回调标记服务ID
    private String actionTag;  //回调标记服务名  只有广播 服务 使用
    private Long startTime;  //开始时间
    private Long loopTime;  //循环周期时间
    private Long accurateLoopTime; //精准度循环周期时间
    private ITimeCall icall;  //回调方法 只有广播 服务 使用
    private TimeCallBackBroadcastReceiver receiver; //设置回调接口
    protected PendingIntent pi; //取消使用
    protected AlarmManager am; //取消使用
    private boolean isRegister = false; //是否已经注册 只有广播 服务 使用
    private IPendingIntent ipendingIntent; //需要回调的 意图接口
    private ETyoe etype = ETyoe.receiv; //当前类型状态  用处不大

    /**
     * 构造
     *
     * @param context 上下文
     */
    public TimeCallBack(Context context, int requestCode) {
        this.context = context;
        this.requestCode = requestCode;
        actionTag = getClass().getName() + requestCode;
        receiver = new TimeCallBackBroadcastReceiver();
    }

    /**
     * 枚举 当前使用类型
     */
    public enum ETyoe {
        receiv, activi, serice;
    }

    /**
     * 得到 服务名称
     * @return
     */
    public String getActionTag() {
        return actionTag;
    }

    /**
     * 获取类型
     * @return
     */
    public ETyoe getEtype() {
        return etype;
    }

    public Long getAccurateLoopTime() {
        return accurateLoopTime;
    }

    /**
     * 精准度 循环周期  注意这两项 setLoopTime  setAccurateLoopTime 同时使用时请谨慎
     * @param accurateLoopTime
     */
    public void setAccurateLoopTime(long accurateLoopTime) {
        this.accurateLoopTime = accurateLoopTime;
        if (this.startTime == null) setWaitTime(0);
    }

    /**
     * 设置后 根据时间启动
     *
     * @param startTime 开始时间
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * 设置后 循环调用  注意这两项 setLoopTime  setAccurateLoopTime 同时使用时请谨慎
     * @param loopTime
     */
    public void setLoopTime(long loopTime) {
        if (this.startTime == null) setWaitTime(0);
        this.loopTime = loopTime;
    }

    /**
     * 得到开始时间
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 设置循环周期
     * @return
     */
    public long getLoopTime() {
        return loopTime;
    }

    /**
     * 设置后 等待时间执行
     *
     * @param waitTime
     */
    public void setWaitTime( long waitTime) {
        setStartTime(System.currentTimeMillis() + waitTime);
    }

    /**
     * 注册  广播 服务 使用
     */
    public void register() {
        IntentFilter filter_dynamic = new IntentFilter();
        filter_dynamic.addAction(actionTag);
        context.registerReceiver(receiver, filter_dynamic);
        isRegister = true;
    }

    /**
     * 设置为 回调广播
     */
    public void setCallReceiver() {
        setCallReceiver(null);
    }

    /**
     * 设置 回调窗口请使用  start(Intent mintent)
     * @param mintent
     */
    public void setCallReceiver(final Intent mintent) {
        etype = ETyoe.receiv;
        ipendingIntent = new IPendingIntent() {
            @Override
            public PendingIntent getPendingIntent() {
                Intent intent = mintent==null?new Intent(actionTag):mintent;
                return  PendingIntent.getBroadcast(context, requestCode, intent, 0);//通过广播来实现闹钟提示 参数2是不同定时器的 标志
            }
        };
    }

    /**
     * 设置 回调窗口请使用  start(Intent mintent)
     * @param mintent
     */
    private void setCallActivity(final Intent mintent) {
        etype = ETyoe.activi;
        ipendingIntent = new IPendingIntent() {
            @Override
            public PendingIntent getPendingIntent() {
                Intent intent = mintent;
                return  PendingIntent.getActivity(context, requestCode, intent, 0);//通过广播来实现闹钟提示 参数2是不同定时器的 标志
            }
        };
    }

    /**
     * 设置为 回调service
     */
    public void setCallSerice() {
        setCallSerice(null);
    }

    /**
     * 设置为 回调service
     * @param mintent
     */
    public void setCallSerice(final Intent mintent) {
        etype = ETyoe.serice;
        ipendingIntent = new IPendingIntent() {
            @Override
            public PendingIntent getPendingIntent() {
                Intent intent = mintent==null?new Intent(actionTag):mintent;
                return  PendingIntent.getService(context, requestCode, intent, 0);//通过广播来实现闹钟提示 参数2是不同定时器的 标志
            }
        };
    }

    /**
     * 启动 需要注册  广播 服务方式
     */
    public void start(ITimeCall icall) {
        if (!isRegister){
            System.out.println("Error:Place call method register();  错误:请先调用方法 register();");
        }
        if(ipendingIntent == null){  //如果没有设置  类型接口 默认 广播方式
            setCallReceiver();
        }
        this.icall = icall;
        if (this.startTime == null) this.startTime = System.currentTimeMillis();
        startAM();
    }

    /**
     * 启动  Acrivi已经注册的不需要 注册 不需要注册    窗口方式
     * @param mintent
     */
    public void start(Intent mintent) {
        setCallActivity(mintent);
        if (this.startTime == null) this.startTime = System.currentTimeMillis();
        startAM();
    }

    /**
     * 开发发送广播
     * 多次调用 参数必须为空
     */
    public void startAM() {
        if(ipendingIntent != null){
            pi = ipendingIntent.getPendingIntent();
        }
        //sendBroadcast(intent1);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(loopTime == null){
            am.set(AlarmManager.RTC_WAKEUP, startTime, pi);  //执行一次
        }else{
            am.setRepeating(AlarmManager.RTC_WAKEUP, startTime, loopTime, pi); //重要：本方法 API 19 之后为了省电不再准确  5秒后通过PendingIntent pi对象发送广播
        }
        //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5 * 1000, pi2); //执行多次 不固定时间
        //am.cancel(pi); //取消
    }

    /**
     * 需要注册后才能使用的广播类
     */
    public class TimeCallBackBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(accurateLoopTime != null) startAM();
            if (icall != null) icall.call(context, intent); //调用
        }
    }

    /**
     * 强行停止广播
     */
    public void stop() { //取消
        loopTime = null;
        if (am != null && pi != null) {
            am.cancel(pi);
        }
    }

    /**
     * 内部使用 类型接口
     */
    private interface IPendingIntent{
        PendingIntent getPendingIntent();
    }
    /**
     * 接口
     */
    public interface ITimeCall {
        void call(Context context, Intent intent);
    }

}

/////实现方式 IDE 创建的Receiver 自动添加了 AndroidMainFest.xml 文件的声明
/*
        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);  //窗口
        intent.putExtra("msg", "你该打酱油了");
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0); //方法；如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取就应该采用

        Intent intent1 = new Intent("com.example.hocean.myapplication.MyReceiver");  //广播
        intent1.putExtra("msg", "你该打酱油了");
        PendingIntent pi1 = PendingIntent.getBroadcast(this, 0, intent1, 0);//通过广播来实现闹钟提示的话，PendingIntent对象的获取就应该采用   参数2是不同定时器的 标志
        sendBroadcast(intent1);

        Intent intent2 = new Intent("com.example.hocean.myapplication.MyService");  //服务
        intent2.putExtra("msg", "你该打酱油了");
        PendingIntent pi2 = PendingIntent.getService(this, 0, intent2, 0);//如果是通过启动服务来实现闹钟提示的话

        //AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3 * 1000, pi);  //执行一次
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, pi1); //重要：本方法 API 19 之后为了省电不再准确  5秒后通过PendingIntent pi对象发送广播
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5 * 1000, pi2); //执行多次 不固定时间

        //取消定时
        //am.cancel(pi);
*/

//本类使用方法
/*
package com.example.hocean.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TimeCallBack timecall;
    TimeCallBack timecall2;
    TimeCallBack timecall3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timecall = new TimeCallBack(this,0);
        timecall.setWaitTime(2000);
        timecall.setAccurateLoopTime(4000);  //精准循环
        timecall.start(new TimeCallBack.ITimeCall() {
            @Override
            public void call(Context context, Intent intent) {
                Toast.makeText(context, "rrrrrrrrrrrr", Toast.LENGTH_SHORT).show();
            }
        });

        timecall2 = new TimeCallBack(this,1);
        timecall2.setLoopTime(8000);
        timecall2.start(new TimeCallBack.ITimeCall() {
            @Override
            public void call(Context context, Intent intent) {
                //timecall.stop();
                //Toast.makeText(context, "2222222222222", Toast.LENGTH_SHORT).show();
                TextView tv = (TextView) findViewById(R.id.heelo);
                tv.setText((new Date()).toString());
            }
        });
        timecall3 = new TimeCallBack(this,2);
        timecall3.setWaitTime(10000);
        timecall3.setLoopTime(10000);
        timecall3.start(new Intent(MainActivity.this,Main2Activity.class));
}

@Override
protected void onStart() {
        super.onStart();
        timecall.register();
        timecall2.register();
        }

@Override
protected void onStop() {
        super.onStop();

        }
public void onClike(View v){
        timecall.stop();
        timecall2.stop();

        }
        }
*/


