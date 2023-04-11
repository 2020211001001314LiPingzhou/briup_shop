## 注册模块

需求：

1.注册登录名校验，注册时相同登录名拒接获取。





技巧：
1.使用抛异常的方式来从 service 向 controller传递错误信息，代替直接return返回错误信息。
	将错误信息封装到异常中，controller在catch到异常时获取到异常信息，添加到model中返回给前端

```java
------------------- service ------------------------------------- 
public void register(User user) throws Exception {
        // 1.判断登录名是否已存在
        User byLoginName = userDao.findByLoginName(user.getLoginName());
        if (byLoginName != null) {
            throw new Exception("登录名已存在");
        }
     ...
 }
------------------- controller ---------------------------------
@PostMapping("/register")
    public String register(User user, Model model){
        try {
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            // 注册失败返回错误信息
            model.addAttribute("msg", e.getMessage());
            return "register";
        }
        return "login";
    }


```



## 登录模块

技巧：
1.在登录查询用户信息时，有一个SQL语句的bug：如果使用等值连接t_user和t_shipping_address表，新注册的用户在t_shipping_address表中是没有相关数据的，所以查询后的结果就为null，导致登录失败。所以需要使用左外连接left outer join。

2.密码使用Md5加密，如果在set方法中使用会出现SpringMVC 和 MyBatis重复加密问题：

```java
public void setPasswordMd5(String passwordMd5) {
    // 对密码采用Md5进行加密
    this.passwordMd5 = DigestUtils.md5DigestAsHex(passwordMd5.getBytes());
}
```

SpringMVC在接收参数封装成对象时，调用的是set方法，所以会进行一次加密，后面就不需要我们自己再次加密了（如果是直接用String接收账号密码，则还是要加密一次登录密码）。重点是Mybatis查询，从数据库中查询到的是已经加密过的密码，但是MyBatis在封装成对象返回时也是调用set方法，导致重复加密密码。

所以最好不要直接在实体类set方法里面加入操作，可能会使很多框架在调用set时出现问题。

## 商品列表模块

1.前端有一个bug，shopList为null时，shop.id会空指针异常

【解决方法】
		在div标签中加入 **th:if="${shop}"**，进行null判断，这里shop如果是null，判断结果就为false，便不会生成该标签及子标签中内容。一开始令我感到疑惑的是按理 th:if="${shopList}"判断集合是否为null应该也行，可是实测代码是不行的。经过在ShopController打印shopList结果为：[null]，这就表示集合本身是存在的，只是里面没有元素，如果是集合为空，打印结果应该为：null （没有中括号）。所以直接判断集合为null不行

```html
<div th:each="shop: ${shopList}" th:if="${shop}" class="col-md-3 onebook">
    <a class="viewShop" th:href="'toViewShop?shopId='+${shop.id}" href="viewShop.html">
        <img src="images/book_index/book1.jpg" th:src="${shop.img}"/>
        <h4 th:text="${shop.name}">
            JAVAEE轻量级框架
        </h4>
        <p th:text="${shop.info}">商品信息</p>
        <p th:text="${shop.selling_price+'元'}" th:if="${!shop.discount}">45.00元</p>
        <p th:text="${shop.discountPrice+'元'}" th:if="${shop.discount}">45.00元</p>
    </a>
</div>
```

2.如果th:href=""中写的路径带 / ，则需要将路径再用单引号括起来

```html
<span><a th:href="'user/toLogin'" href="login.html">登录</a></span>
<span><a th:href="'user/toRegister'" href="register.html">注册</a></span>
```

3.模糊查询拼接使用concat('%',#{item},'%')，而不使用'%${item}%'，防止sql注入

```sql
<select id="findByNameContaining" resultType="Shop">
    select
        s.id, s.discount, s.discount_price, s.img, s.info, s.name, s.price,
        s.selling_price, s.stat, s.stock_num, s.store, s.category_id, s.sales_volume, s.visit_volume, s.intro, s.tags
    from t_shop s
    where s.name like concat('%', #{shopName}, '%')
</select>
```

4.“为你推荐”是用访问量来模拟实现的，存放到applicationContext；
	“发现好物”原则上是要通过大数据分析具体用户的数据来进行商品推荐，但不会（ >-< ），所以先随便设计，这部分是放在sessionContext中的。

5.改进：将分类商品、特价商品、发现好物的查询，从/index请求方法迁移到监听器中，让服务器在启动初始化ServletContext后执行这些查询操作，将查询结果放入ServletContext中，这样可以避免每个用户访问index时都要进行查询。缺点：不能及时更新商品，造成数据一致性问题。

另外有趣的一点是，监听器本来也应该在实现WebMvcConfigurer接口的配置类中注册，但是我在没有注册的情况下运行程序也成功了。百度搜索的解释如下：
![](D:\系统默认\图片\课程\屏幕截图 2023-04-04 233119.png)

（这是否说明Tomcat是在SpringBoot容器之后再初始化的？）

## 添加购物车模块

表t_shop_car中一行记录代表含义是一个用户的购物车中的一个商品及其添加数量，一个用户的购物车可拥有很多商品，因此有多行记录。

1.添加购物车模块前端是发送了一个简单的ajax请求，失败时只是弹窗提示，应该还要自动跳转到登录界面才对。
2.Sql查询其实有很多冗余，包含不必要的列，可以省略。
3.购物车表不完善，应该有“添加时间/更新时间”字段，便于用户查看购物车时按添加时间排序展示；但这里我们可以借助自增的id来进行排序，id大的排在前面，即order by id desc。

4.根据ids去查询购物车商品，不应该在sql中使用 < if >标签来判断list是否为0来决定是否添加c.id in (...)条件，因为当list真为null了，此条件不存在，查询出的将是该用户所有的购物车商品。

使用判断list是否为null这一步，最好还是放在业务层代码中判断，另外list是ids数组用Arrays.toList()方法转变而来的，如果ids为null，方法会报错空指针异常，所以list不会为null，反而一个提前判断ids数组是否为null，如果用户在购物车一件商品都没有选择时点击“去结算”，前端发送请求就不会携带ids参数，就会出现ids数组为空的情况。

应该在前端点击“去结算”按钮时就应该判断是否选择了至少一件商品，如果没有选择商品会弹出提示或直接让“去结算”按钮变灰，并处于不可选中状态。



一个商品都不选择时点击结算：

![](D:\系统默认\图片\课程\屏幕截图 2023-04-10 170859.png)


请求路径没有携带ids参数

![](D:\系统默认\图片\课程\屏幕截图 2023-04-10 171122.png)

```java
// ShopCarController
@GetMapping("/advanceOrder") // 没有接收到ids参数，所以ids = null
public String advanceOrder(Long[] ids, Model model, HttpSession session){
```

```java
// IShopCarServiceImpl
@Override
public List<ShopCar> findShopCars(Long[] ids) {
	// ids为null时，转list 会 报错
    List<Long> list = Arrays.asList(ids);
    
    return shopCarDao.findShopCarByIds(list);
}
```

```sql
select
    c.id, c.num,
    s.id sid, s.discount, s.discount_price, s.img, s.info, s.name, s.price,
    s.selling_price, s.stat, s.stock_num, s.store, s.category_id, s.sales_volume, s.visit_volume, s.intro, s.tags
from t_shop_car c
left join
     t_shop s
     on c.shop_id = s.id
where c.id in
      <foreach collection="list" open="(" close=")" separator="," item="id">
        #{id}
      </foreach>
      
-------------------------------------------------------------
错误写法：
select
    c.id, c.num,
    s.id sid, s.discount, s.discount_price, s.img, s.info, s.name, s.price,
    s.selling_price, s.stat, s.stock_num, s.store, s.category_id, s.sales_volume, s.visit_volume, s.intro, s.tags
from t_shop_car c
left join
     t_shop s
     on c.shop_id = s.id
<if test="list != null">
where c.id in
      <foreach collection="list" open="(" close=")" separator="," item="id">
        #{id}
      </foreach>
</if>
```



## 下单模块

1.在计算订单总价时，由于价格是BigDecimal类型，其相加方法add是与参数中的另一个BigDecimal对象的相加（内部某个属性相加）后，将结果封装成一个新的BigDecimal对象，且BigDecimal的变量赋值后是不可修改指向的（应该是final的原因），所以不能直接用foreach循环来进行覆盖累加。

这里用到了AtomicReference类进行原子操作

```java
// 计算总价
AtomicReference<BigDecimal> sumPrice = new AtomicReference<>(new BigDecimal(0));
shopCarList.forEach(shopCar -> {
    if (shopCar.getShop().isDiscount()){
        sumPrice.set(sumPrice.get().add(shopCar.getShop().getDiscountPrice()
                .multiply(new BigDecimal(shopCar.getNum()))));
    }else {
        sumPrice.set(sumPrice.get().add(shopCar.getShop().getSelling_price()
                .multiply(new BigDecimal(shopCar.getNum()))));
    }
});
```

2.表t_order是订单，t_order_item中一行记录是某个订单中的一种商品，一个订单order可以有多个item

3.Order类在实例化时，UUID生成随机id，可以用类似雪花算法生成Id来改进。

4.confirmSuc.html的显示有问题，直接就显示支付成功了。应该还有下单先不支付，后面在order.html里面支付的情况。
5.Order.html界面的设计有点不合理，它一个订单有多个订单项，每个订单项的后面都有一个支付和删除按钮，会让人以为可以单独支付或删除一个订单里的一个订单项，这听起来就不合理，一个订单应该完整支付，或整个删除，不能只操作部分。
而实际上，从源代码分析来看，这里确实也是支付和删除整个订单，所以是前端设计问题（用也到能用）
![](D:\系统默认\图片\课程\屏幕截图 2023-04-02 214300.png)

## 阿里订单支付

1.使用支付宝沙箱进行模拟支付，不能在本地调试支付宝的异步通知方法，需要搞个内网穿透。  

需要使用的工具
    	内网穿透工具：natapp。
    	一台服务器需要装好nginx。

简单实现就直接在支付成功后的回调路径里加上订单id，在回调函数里直接更改订单的状态，即触发回调后就默认订单支付成功。

## 收货地址



功能补充：

1.使用ajax请求完善了删除收货地址功能

2.使用ajax请求完善了更改默认收货地址功能

3. /advanceOrder 方法中：

```java
// 查询用户收件地址
// 不能直接从session的user里面拿userAddressList，因为当数据更新后：比如添加了新的地址，或更改了默认地址后，
// user里面的数据就与数据库中数据不匹配了，所以应该重新查询
//List<ShippingAddress> userAddressList = ((User) session.getAttribute("user")).getAddresses();
List<ShippingAddress> userAddressList = shippingAddressService.findUserAllShippingAddress(((User)session.getAttribute("user")).getId());
```
