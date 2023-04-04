package com.briup;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.briup.config.AliPayConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootTest
class BriupShopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void payTradePagePay(HttpServletResponse response) throws AlipayApiException, IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = AliPayConfig.getAlipayClient();
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        alipayRequest.setBizModel(model);
        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        AlipayTradePagePayResponse alipayResponse = alipayClient.execute(alipayRequest);
        /*System.out.print(alipayResponse.getBody());
        System.out.print(alipayResponse.getSubCode());*/
        PrintWriter out = response.getWriter();
        out.println(alipayResponse.getBody());
    }

}
