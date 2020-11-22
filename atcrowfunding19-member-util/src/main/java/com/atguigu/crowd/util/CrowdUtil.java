package com.atguigu.crowd.util;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.crowd.aliyun.api.gateway.demo.util.HttpUtils;
import com.atguigu.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/*判断是否为ajax请求
@param：ruquest请求对象
return：
true ：是ajax请求
false： 不是ajax请求
*
* */
public class CrowdUtil {

//    public static void main(String[] args) throws FileNotFoundException {
//        FileInputStream fileInputStream = new FileInputStream("D:\\atcrowfunding07-member-parent\\atcrowfunding07-member-parent\\atcrowfunding13-member-project-consumer\\src\\main\\java\\11.JPG");
//        ResultEntity<String> resultEntity = uploadFileToOss("http://oss-cn-beijing.aliyuncs.com", "LTAI4GJjXHnBpLT7g726552z", "K8LUgdQFlkRLmoYqUtirT3igaLG0m3",
//                fileInputStream, "atguigu190803", "http://atguigu190803.oss-cn-beijing.aliyuncs.com", "11.JPG");
//    }
    /**
     * 专门负责上传文件到OSS服务器的工具方法
     * @param endpoint			OSS参数
     * @param accessKeyId		OSS参数
     * @param accessKeySecret	OSS参数
     * @param inputStream		要上传的文件的输入流
     * @param bucketName		OSS参数
     * @param bucketDomain		OSS参数
     * @param originalName		要上传的文件的原始文件名
     * @return	包含上传结果以及上传的文件在OSS上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(
            String endpoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用UUID生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;

        try {
            // 调用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);

            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();

            // 根据响应状态码判断请求是否成功
            if(responseMessage == null) {

                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();

                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                // 当前方法返回失败
                return ResultEntity.failed("当前响应状态码="+statusCode+" 错误消息="+errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();

            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {

            if(ossClient != null) {

                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

    }


    /*
    产生并发送验证码
    host 第三方地址
    path  路径
    method  请求方法
    phoneNum  电话号码
    * appcode   第三方的appCode
    * skin  模板编号  TP1711063
    * */
     public static ResultEntity<String> sendCodeByShortMessage(
             String host,
             String path,
             String method,
             String phoneNum,
             String appcode,
             String skin

     ){
         StringBuilder stringBuilder=new StringBuilder();
         for (int i=0;i<4;i++){
             int random= (int) (Math.random()*10);
             stringBuilder.append(random);
         }
         String string = stringBuilder.toString();


         Map<String, String> headers = new HashMap<String, String>();
         //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
         headers.put("Authorization", "APPCODE " + appcode);
         Map<String, String> querys = new HashMap<String, String>();
         querys.put("mobile", phoneNum);
         querys.put("param", "code:"+string);
         querys.put("tpl_id", skin);
         Map<String, String> bodys = new HashMap<String, String>();


         try {
             /**
              * 重要提示如下:
              * HttpUtils请从
              * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
              * 下载
              *
              * 相应的依赖请参照
              * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
              */
             HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
             //获取response的body
             StatusLine statusLine = response.getStatusLine();
             // 状态码
             int statusCode = statusLine.getStatusCode();
              //
             String reasonPhrase = statusLine.getReasonPhrase();
             if(statusCode == 200){
                 // 如果成功 则将验证码返回
                 return  ResultEntity.successWithData(string);
             }
             return ResultEntity.failed(reasonPhrase);


         } catch (Exception e) {
             e.printStackTrace();
             return ResultEntity.failed(e.getMessage());
         }


     }




     /*对字符串进行md5加密
     **/
     public static String md5(String resource){
          if(resource==null||resource.length()==0){
              throw  new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
          }

          try {
               // 1.获取MessageDigest对象
               String algorithm="md5";
              // 2.将resource 转换为字节数组
               MessageDigest instance = MessageDigest.getInstance(algorithm);

               byte[] input = resource.getBytes();
               // 3.执行加密
               byte[] output = instance.digest(input);
               // 4.创建bigInteger
               int signum=1;
               BigInteger bigInteger = new BigInteger(signum,output);
               // 5.按照16禁进制 转换为字符串
               int radix=16;
               String encoding = bigInteger.toString(radix).toUpperCase();

               return encoding;
          } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
          }
          return null;
     }



     public static  boolean judgeRequestAjax(HttpServletRequest httpServletRequest){

          String accept = httpServletRequest.getHeader("Accept");
          String header = httpServletRequest.getHeader("X-Requested-With");
          return (accept != null && (accept.contains("application/json")))
                  ||
                  (header != null && (header.equals("XMLHttpRequest")));
     }
}
