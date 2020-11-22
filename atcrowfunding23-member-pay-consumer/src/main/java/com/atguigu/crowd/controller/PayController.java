package com.atguigu.crowd.controller;




import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.crowd.MYSQLremoteservice;
import com.atguigu.crowd.config.AlipayConfig;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.OrderProjectVO;
import com.atguigu.crowd.vo.OrderVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayController {

    @Resource
    private AlipayConfig alipayConfig;
    @Resource
    private MYSQLremoteservice mysqLremoteservice;

    @ResponseBody
    @RequestMapping("/return")
    public String returnUrlMethod(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException, AlipayApiException {
        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            // 保存到数据库
             // 取出order VO对象
            OrderVO orderVO = (OrderVO) session.getAttribute("orderVO");
            orderVO.setOrderNum(orderNum);
            orderVO.setPayOrderNum(payOrderNum);
            orderVO.setOrderAmount(new Double(orderAmount));
            ResultEntity<String> resultEntity=mysqLremoteservice.saveOrderVO(orderVO);

            return "trade_no:"+orderNum+"<br/>out_trade_no:"+payOrderNum+"<br/>total_amount:"+orderAmount;
        }else {
             return "验签失败";
        }
    }
    @ResponseBody
    @RequestMapping("/notify")
    public String notifyUrlMethod(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        /* *
         * 功能：支付宝服务器异步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
         * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
         * 如果没有收到该页面返回的 success
         * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
         */

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");


           return "success";

        }else {//验证失败
            return "error";

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

    }

    // 提交的确认订单的表单
    @ResponseBody  // 加这个注解是为了防止thymelaf误认为是 网页
    @RequestMapping("/generate/order")
    public String generateOrder(HttpSession session, OrderVO orderVO) throws AlipayApiException {

         OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

         // 将orderProjectVO对象和orderVO对象组装到一起
         orderVO.setOrderProjectVO(orderProjectVO);

        // 生成订单号，设置到orderVO对象里面
          String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
          String user= UUID.randomUUID().toString().replace("-","").toUpperCase();
          String orderNum=time+user;
          orderVO.setOrderNum(orderNum);

          // 计算订单的总金额，设置到orderVO对象里面
          Integer money= orderProjectVO.getSupportPrice()*orderProjectVO.getReturnCount()+orderProjectVO.getFreight();
          orderVO.setOrderAmount(money.doubleValue());
          session.setAttribute("orderVO",orderVO);
          System.out.println(orderVO);
          // 调用封装好的支付宝接口 并将页面数据进行返回
        return sendRequestToAlipay(orderNum,money.doubleValue(),orderProjectVO.getProjectName(),orderProjectVO.getReturnContent());

    }

    // 为了调用支付宝接口 专门封装的方法
  private String sendRequestToAlipay(String outTradeNo,Double totalAmount,String subject,String body) throws AlipayApiException {
      //获得初始化的AlipayClient
      AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json",alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());

      //设置请求参数
      AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
      alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
      alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());



      alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
              + "\"total_amount\":\""+ totalAmount +"\","
              + "\"subject\":\""+ subject +"\","
              + "\"body\":\""+ body +"\","
              + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

      //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
      //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
      //		+ "\"total_amount\":\""+ total_amount +"\","
      //		+ "\"subject\":\""+ subject +"\","
      //		+ "\"body\":\""+ body +"\","
      //		+ "\"timeout_express\":\"10m\","
      //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
      //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

      //请求
     return alipayClient.pageExecute(alipayRequest).getBody();


  }

}
