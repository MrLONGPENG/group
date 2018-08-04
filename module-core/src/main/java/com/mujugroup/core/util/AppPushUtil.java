package com.mujugroup.core.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import java.util.ArrayList;
import java.util.List;

public class AppPushUtil {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "u3MRmN8PYC6vUkMmIcRAf1";
    private static String appKey = "yuyshx2jAk92k5ba6PuqP4";
    private static String masterSecret = "IhMBouchdp5IUJmdqRisW9";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args)  {

        IGtPush push = new IGtPush(AppPushUtil.url, AppPushUtil.appKey, AppPushUtil.masterSecret);

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(AppPushUtil.appId);
        template.setAppkey(appKey);
        template.setTitle("测试通知标题");
        template.setText("测试通知内容");
        template.setUrl("http://api.mujugroup.com/");

        List<String> appIds = new ArrayList<>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}
