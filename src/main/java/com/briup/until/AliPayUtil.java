package com.briup.until;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.briup.bean.AliPay;
import com.briup.config.AliPayConfig;
import org.springframework.stereotype.Component;

@Component
public class AliPayUtil {

    /**
     * 支付接口
     *
     * @param aliPay 封装的支付宝入参
     * @return 返回支付结果
     * @throws AlipayApiException 抛出异常
     */
    public String pay(AliPay aliPay) {
        // 1、获得初始化的AlipayClient

        AlipayClient alipayClient = AliPayConfig.getAlipayClient();
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl("http://localhost:8080/goPaySuccPage");
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl("http://localhost:8080/notifyPayResult");
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(aliPay));
        // 3、请求支付宝进行付款，并获取支付结果
        String body = "";
        try {
            body = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return body;
    }
}
