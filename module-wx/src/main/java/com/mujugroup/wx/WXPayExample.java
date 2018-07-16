package com.mujugroup.wx;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.mujugroup.wx.config.MyConfig;

import java.util.HashMap;
import java.util.Map;

public class WXPayExample {
    public static void main(String[] args) throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxPay = new WXPay(config);
        unifiedOrder(wxPay);
        orderQuery(wxPay);
        refundQuery(wxPay);
    }

    //退款查询：
    private static void refundQuery(WXPay wxPay) {

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000012");

        try {
            Map<String, String> resp = wxPay.refundQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    private static void orderQuery(WXPay wxPay) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000012");
        try {
            Map<String, String> resp = wxPay.orderQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String,String> unifiedOrder(WXPay wxPay) {

        Map<String, String> data = new HashMap<>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "2016090910595900000012");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");
        try {
            Map<String, String> resp = wxPay.unifiedOrder(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
