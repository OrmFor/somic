package com.yinmimoney.web.p2pnew.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * Created by dyf on 2018/6/26 16:05
 * aliyun身份证照片解析工具类
 */
public class IdCardRecognitionUtil {

    private static final Logger LOGGER = LogManager.getLogger(IdCardRecognitionUtil.class);
    /**
     * @param host    阿里云地址   可放在配置文件中
     * @param path    阿里云ocr身份证识别地址 可放在配置文件中
     * @param appcode 阿里云订单认证码  可放在配置文件中
     * @param content 图片字节码   注意：实测发现，字节数组的大小要与img文件大小一致
     * @param side    face:正面,back:反面
     * @return
     * @description 根据图片信息获取解析后的数据
     */
    public static JSONObject getInfo(String host, String path, String appcode, byte[] content, String side) {
        //如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        Boolean is_old_format = false;
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", side);
        String config_str = configObj.toString();
        //            configObj.put("min_size", 5);
        //            String config_str = "";

        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64 = "";
        imgBase64 = new String(encodeBase64(content));
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if (is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if (config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            } else {
                requestObj.put("image", imgBase64);
                if (config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            LOGGER.error("阿里云ocr识别身份证时出现异常："+e,e);
        }
        String bodys = requestObj.toString();

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
            HttpResponse response = AliyunHttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if (stat != 200) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", stat);
                jsonObject.put("msg", EntityUtils.toString(response.getEntity()));
                jsonObject.put("header-msg",response.getFirstHeader("X-Ca-Error-Message"));
                LOGGER.info("阿里云ocr识别身份证照片时返回错误："+jsonObject.toJSONString());
                return jsonObject;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if (is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
                LOGGER.info("阿里云ocr识别身份证照片时返回成功："+out.toJSONString());
                return out;
            }
            LOGGER.info("阿里云ocr识别身份证照片时返回成功："+res_obj.toJSONString());
            return res_obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    /**
     * test
     */
    public static void main(String[] args) {
        String host = "http://dm-51.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_idcard.json";
        String appcode = "fc984a7c03f8488396a0591b90767c6d";
        //String side = "face";
        String side="back";
        try {
            File file = new File("d://test/test/idCard/IMG20180626154939.jpg");
            FileInputStream fis = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            fis.read(content);
            fis.close();
            JSONObject info = IdCardRecognitionUtil.getInfo(host, path, appcode, content, side);
            System.out.println(info.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

