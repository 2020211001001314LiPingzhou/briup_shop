package com.briup.web.controller;

import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.briup.bean.AliPay;
import com.briup.bean.Order;
import com.briup.bean.User;
import com.briup.config.AliPayConfig;
import com.briup.service.IOrderService;
import com.briup.service.IShopCarService;
import com.briup.until.AliPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private AliPayUtil aliPayUtil;


    /**
     * sumPrice是重要信息，参数不把confirm.html中的sumPrice传递过来，是不信任前端的数据，
     * 比如用Postman绕过前端直接发送请求到后端，自己设置sumPrice会造成严重后果
     * @param addressId
     * @param payType
     * @param sendType
     * @param ids  shopCarId数组
     * @param session
     * @return
     */
    @GetMapping("/createOrder")
    public String createOrder(Long addressId, String payType, String sendType, Long[] ids,
                              HttpSession session, Model model){
        // 保存订单（未支付状态）
        Order order = orderService.saveOrder(ids, (User) session.getAttribute("user"), addressId);
        model.addAttribute("order", order);

        //跳转到阿里支付
        if (order.getId() != null) {
            return "forward:/payOrder";
        }


        return "forward:/toOrder";
    }

    @GetMapping("/toOrder")
    public String toOrder(HttpSession session, Model model){
        List<Order> orderList = orderService.findUserAllOrders(((User) session.getAttribute("user")).getId());
        model.addAttribute("orderList", orderList);
        return "order";
    }


    @GetMapping("/toPayOrder")
    public String toPayOrder(String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "forward:/payOrder";
    }

    /**
     * 这里应该是先跳转到阿里的支付界面，
     * 支付成功后支付宝回调/goPaySuccPage更新订单状态，
     * 最后跳转到confirmSuc.html
     * @param
     * @return
     */
    @GetMapping("/payOrder")
    @ResponseBody
    public void payOrder(HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        Order order = (Order) request.getAttribute("order");
        // 成功后的跳转页面
        String returnPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/goPaySuccPage?id=" + order.getId();
        String payResult = aliPayUtil.pay(order, returnPath);

        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(payResult);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 支付成功的跳转页面
     * @return
     */
    @GetMapping("/goPaySuccPage")
    public String goPaySuccPage(@RequestParam("id") String orderId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Order order = orderService.paySuccess(orderId);
        model.addAttribute("order", order);
        log.debug("user:" + user.getLoginName() + "订单支付成功");
        return "confirmSuc";
    }


    /**
     * 支付成功的回调接口
     *
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/notifyPayResult")
    public String notifyPayResult(HttpServletRequest request) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<进入支付宝回调->>>>>>>>>>>>>>>>>>>>>>>>>");
        // 1.从支付宝回调的request域中取值放到map中
        Map<String, String[]> requestParams = request.getParameterMap();

        Map<String, String> params = new HashMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        //2.封装必须参数
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        //交易状态
        String tradeStatus = params.get("trade_status");

        System.out.println("outTradeNo:" + outTradeNo + " tradeStatus:" + tradeStatus);

        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.getAlipayPublicKey(), AliPayConfig.getCHARSET(), AliPayConfig.getSignType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------->验签结果:" + signVerified);

        //4.对验签进行处理

        if (signVerified) {
            //验签通过
            //只处理支付成功的订单: 修改交易表状态,支付成功
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
                //根据订单号查找订单,防止多次回调的问题
                Order orderByOrder = orderService.findById(outTradeNo);
                if (orderByOrder != null && orderByOrder.getStatus() == 1) {
                    //修改订单状态
                    orderByOrder.setStatus(2);
                    orderService.paySuccess(orderByOrder);
                }
                return "success";
            } else {
                return "failure";
            }
        } else {
            //验签不通过
            System.err.println("-------------------->验签失败");
            return "failure";
        }
    }*/

    @GetMapping("/deleteOrder")
    public String deleteOrder(String orderId, HttpSession session){
        Long userId = ((User) session.getAttribute("user")).getId();
        orderService.deleteOrder(orderId, userId);
        // 因为前端发的不是ajax请求，所以直接重定向到/toOrder，重新查order数据并跳转到order.html
        return "redirect:/toOrder";
    }

}
