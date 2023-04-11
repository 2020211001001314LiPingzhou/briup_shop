package com.briup.web.controller;

import com.briup.bean.ShippingAddress;
import com.briup.bean.ShopCar;
import com.briup.bean.User;
import com.briup.service.IShippingAddressService;
import com.briup.service.IShopCarService;
import com.briup.until.ShopCarUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class ShopCarController {

    @Autowired
    private IShopCarService shopCarService;

    @Autowired
    private IShippingAddressService shippingAddressService;

    @GetMapping("/addShopCar")
    @ResponseBody
    public String addShopCar(Long shopId, HttpSession session){
        Long userId = ((User) session.getAttribute("user")).getId();

        shopCarService.saveShopCar(1, userId, shopId);
        // 成功，返回信息
        return "";
    }

    @GetMapping("/toShopCar")
    public String toShopCar(HttpSession session, Model model){
        Long userId = ((User) session.getAttribute("user")).getId();
        List<ShopCar> shopCarList = shopCarService.findUserAllShopCar(userId);
        System.out.println("shopCarList =============" + shopCarList);
        model.addAttribute("shopCarList", shopCarList);
        return "shopCar";
    }


    @GetMapping("/updateShopCar")
    @ResponseBody
    public void updateShopCar(Long shopCarId, Integer num) {
        // 需要检验数量，小于等于0则不能再减少
        if (num <= 0)
            return;

        // 更新购物车数量
        shopCarService.updateShopCar(shopCarId, num);
    }


    @GetMapping("/delShopCar")
    @ResponseBody
    public void delShopCar(Long shopCarId){
        shopCarService.deleteShopCar(shopCarId);
    }

    @GetMapping("/advanceOrder")
    public String advanceOrder(Long[] ids, Model model, HttpSession session){
        // 查询要下单商品的信息
        List<ShopCar> shopCarList = shopCarService.findShopCars(ids);
        // 计算总价
        /*AtomicReference<BigDecimal> sumPrice = new AtomicReference<>(new BigDecimal(0));
        shopCarList.forEach(shopCar -> {
            if (shopCar.getShop().isDiscount()){
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getDiscountPrice()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }else {
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getSelling_price()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }
        });
        */
        // 将上述代码封装到ShopCarUntil类中
        BigDecimal sumPrice = ShopCarUntil.sumPrice(shopCarList);

        // 查询用户收件地址
        // 不能直接从session的user里面拿userAddressList，因为当数据更新后：比如添加了新的地址，或更改了默认地址后，
        // user里面的数据就与数据库中数据不匹配了，所以应该重新查询
        //List<ShippingAddress> userAddressList = ((User) session.getAttribute("user")).getAddresses();
        List<ShippingAddress> userAddressList
                = shippingAddressService.findUserAllShippingAddress(((User)session.getAttribute("user")).getId());
        // 把默认地址排到最前面
        if (userAddressList != null){
            userAddressList.sort((x,y) -> (y.isDefaultValue() ? 1 : 0) - (x.isDefaultValue() ? 1 : 0));
        }

        model.addAttribute("shopCarList", shopCarList);
        model.addAttribute("sumPrice", sumPrice);
        model.addAttribute("userAddressList", userAddressList);
        //System.out.println("shopCarList ======" + shopCarList);
        //System.out.println("sumPrice ======" + sumPrice);
        return "confirm";
    }

}
