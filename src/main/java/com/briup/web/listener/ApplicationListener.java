package com.briup.web.listener;

import com.briup.bean.Shop;
import com.briup.bean.vo.CategoryVO;
import com.briup.service.ICategoryService;
import com.briup.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class ApplicationListener implements ServletContextListener {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IShopService shopService;


    /**
     * 服务器ServletContext容器初始化时执行
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        // 在tomcat启动值就将这些数据加载到ServletContext中
        // 缺点：不能及时更新商品，造成数据一致性问题。
        // 分类商品
        List<CategoryVO> categories = categoryService.findAllCategory();
        // 特价商品
        List<Shop> discountList = shopService.findDiscount();
        // 发现好物
        List<Shop> shops = shopService.findAllShops();

        application.setAttribute("categories", categories);
        application.setAttribute("discountList", discountList);
        application.setAttribute("shops", shops);
    }
}
