package com.briup.until;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.briup.bean.AliPay;
import com.briup.bean.Order;
import com.briup.config.AliPayConfig;
import org.springframework.stereotype.Component;

@Component
public class AliPayUtil {

    /**
     * 支付接口
     *
     * @param order
     * @return 返回支付结果
     * @throws AlipayApiException 抛出异常
     */
    public String pay(Order order, String returnPath) {
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = AliPayConfig.getAlipayClient();
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 回调地址
        alipayRequest.setReturnUrl(returnPath);
        // 服务器异步通知页面路径
        //alipayRequest.setNotifyUrl("http://localhost:8080/notifyPayResult");

        // 3、SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为SDK的model入参方式（model和biz_content同时存在的情况下取biz_content）
        AlipayTradePagePayModel aliModel = new AlipayTradePagePayModel();
        // 订单支付
        aliModel.setBody("商品描述：");
        // 订单名字
        aliModel.setSubject("杰普购物订单");
        // 订单号
        aliModel.setOutTradeNo(order.getId());
        // 过期时间
        aliModel.setTimeoutExpress("30m");
        // 订单金额
        aliModel.setTotalAmount(order.getSumPrice().toString());
        // 产品码
        aliModel.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 封装参数
        //alipayRequest.setBizContent(JSON.toJSONString(aliPay));
        alipayRequest.setBizModel(aliModel);
        // 4、请求支付宝进行付款，并获取支付结果
        String result = "";
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }
}
