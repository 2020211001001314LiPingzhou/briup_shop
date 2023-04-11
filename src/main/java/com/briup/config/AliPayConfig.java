package com.briup.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AliPayConfig {
    /*
     * 服务网关  ---  沙箱环境
     */

    private static String serverURL = "https://openapi.alipaydev.com/gateway.do";

    /*
     * 应用id,
     */

    private static String APP_ID = "2021000122673388";

    /*
     * 用户私钥,可以替换成你们的自己的私钥
     *
     */

    private static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJpx7DDefjXxGhbuWx2bbJF/rKgTpQfAGxrOKGjOsAe4rbDtWt/65Vx8Mw464MSvznVyudipdqvfe5+NTdJNMaEfiMZkhaagL/n+IN0RhFJDcnBDwq1g2SNM4GCFjOxfIiN3/wDHYbA4CHHO7K4ZWlFnBoGSVAuibtoRZp6RiECcaDKAQ6Wy+DTL6wvqe2FZdVBibjgl75lInejeVIOlrNb4GjiUN7HEQvL7SOBhoa3hz4fnGYU43mF952rQgKGv/3Q9oJnhawSvfVyqCZkFPCOmsdKzutZFKzEvbJ0bGte4cbpnR6T1XF802BjswK4YqKvDKl7nsZLgkXJ5gqpxK7AgMBAAECggEAGmdnHC0frNqAjEG0Z5AGYr5pycxN/kA7JvStoveG4FnSQcCvtieULjoaM9eIfC0qI8nxwM9jygLUcOTNF/pHT/ngfjXkbQtmtdfaSaIxEJooxiAm1mSmx8O/PSL+vfL0RiH1NXt+cCIU1lXzbBEnXjpWp3EjVy4d53j9zqT6oeDiFIR/eVhA1EtAp/SG9vYVEjhIXMCVaWbcwPns16EcQZI16UkiyLeE3J1MXChvz8xzmtn6Q56wY9LS+NGfKjvUibyJfv5a36VLomd8WTJcp22FKzCHXbOef4LYJFEMLunPpBXkVfSAKmYyn+q22viAx2WyIUpm/bNTLmoFlCQ6QQKBgQDDA/iXP047a97Ljt+XHyZJxdvSC03hNn8ddBvTegozP01TCtKnQYGSp2xXx3U2NMIJcyOly4PI3/DO5dK4oOpdy0sCW1goBG6hbGdsfcK3+FKr0SuP9ZumzXLaFcnsFt/RXr/LNFlnKlsoXmfnBPuzuQF8bg1OlatJtd0PFOD5GwKBgQC0svXBDD5fyY373UfSD6dQfE0AAYu1VitvJd10URdKwFJ0vdYnNu4s6kMbvjiCeJr1S0i7By094DrWt0SnLyGhLsfhxUmz0YLKNZk8MyBPppcIAOF/+HtNpfBvpNWvjg4qoZoaZncpYZwTLHOXYVjCmXkwYllG5174SIYkXBiG4QKBgAjhHWiFkWJTPwm1r6iJ2oxxXdjqetlOSetlZf7zB5Pdxf+J9p129hdfWCeHBWc6b7tOaskZwx5aU/SXskiFyyYKI5gv4Iux5/ehn/Popf0KaXk5ROqBHF0z7Y7QfykowXgfAEyYTZnM6+miosh6CZvdSJwJxsFVPz9T8ITHRpcRAoGALTlEzEXWLn7xLAHbgbUtOLWDR+cbIg0o3a5qWDwbSjgL6u+/8xyKS29t0DmxRuYMUiu/Th+jsQK3dxly8yyWjBJ7MAQCSCC9TQJREYHyKj0VO4mM5kw3/9zx6d8lI8S3wu+i88yFMjQdrabIXzCb2Eg0zmku0tIBML5l5B+we8ECgYBKNp9iAvVIpiYLvaMwfabCnjsxuqWj/5EK9HHESuBptCxkcCtmoPnorIa66QDWTqZQuL8OmX6yRgrIn9r4X+wDb2bvnf4FJVg5JZgbba5NnFMywd4JMkmbUweBbouXHP5vMHHF8Av7reax2UV1/QUIutTSuswiH2hBY++1DLSwnQ==";
    /*
     * 发送数据的格式,  目前为json
     *
     */

    private static String format = "json";

    /*
     * 设置字符集的编码格式 utf-8
     */

    private static String CHARSET = "utf-8";

    /*
     * 支付宝公钥,  可以替换成你们自己的
     */

    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjN8SU7Opjf9LjvrK0/nr/44Z1d2zFfL3n5Q+hzKAizkGBgaCC1e25ZY3PwbnJiG4fSeLpsJGTwS7KVM1yuUk3szD5iUUQ62YsUZuEnsn57XFgQDzbXSOm/iOKXXqgc5DKLdf8/4hURPnqup+fgz8zE2J43iTZYuSifUqYVMzmdTgziLLQh/SES5xYOF8YbFttQTHpi4UYZBnlRO5k2MjqjUwDzb8nU8cleGfBIbryOQ0KR1KqbTZbYr/pivfrdPqnS+E3Q4LholrTc3NnjS3FTdRnGOMhuRfo5QIbd55qwJiBQ/ZSokHGBa//861MZuQbJXe+IDzQj9AEI3FcJ6bVwIDAQAB";
    /*
     * 支付宝签名  目前采用的是RSA2
     */

    private static String signType = "RSA2";
    /*
     * 页面跳转同步通知页面   http      ?12345464.外网可以访问的地址
     */



    public static AlipayClient getAlipayClient() {
        return new DefaultAlipayClient(serverURL,APP_ID,APP_PRIVATE_KEY,format,
                CHARSET,ALIPAY_PUBLIC_KEY,signType);
    }
}
