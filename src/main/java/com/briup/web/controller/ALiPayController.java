package com.briup.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.briup.bean.AliPay;
import com.briup.bean.Order;
import com.briup.config.AliPayConfig;
import com.briup.service.IOrderService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/alipay")
public class ALiPayController {

    @Autowired
    private IOrderService orderService;


    @RequestMapping("/alipayTradePagePay")
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws IOException {
        /*Order payResult = orderService.paySuccess(aliPay.getTraceNo());
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(payResult);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();*/
    }

}
