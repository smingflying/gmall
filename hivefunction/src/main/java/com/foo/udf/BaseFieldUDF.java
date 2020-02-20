package com.foo.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseFieldUDF extends UDF {
    public String evaluate(String line,String jsonkeysString){
        StringBuilder sb = new StringBuilder();
        String[] fields = jsonkeysString.split(",");
        String[] content = line.split("\\|");
        if (content.length!=2 || StringUtils.isBlank(content[1])){
            return "";
        }
        try {
            JSONObject jsonObject = new JSONObject(content[1]);
            JSONObject cm = jsonObject.getJSONObject("cm");
            for (String field : fields) {
                if (cm.has(field)){
                    sb.append(cm.getString(field)).append("\t");
                }else{
                    sb.append("\t");
                }
            }
            sb.append(jsonObject.getString("et")).append("\t");
            sb.append(content[0]).append("\t");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "1578240006352|{\"cm\":{\"ln\":\"-37.8\",\"sv\":\"V2.4.7\",\"os\":\"8.0.1\",\"g\":\"IGP7G75N@gmail.com\",\"mid\":\"m152\",\"nw\":\"3G\",\"l\":\"en\",\"vc\":\"10\",\"hw\":\"750*1134\",\"ar\":\"MX\",\"uid\":\"u721\",\"t\":\"1578239516572\",\"la\":\"16.0\",\"md\":\"Huawei-0\",\"vn\":\"1.1.4\",\"ba\":\"Huawei\",\"sr\":\"C\"},\"ap\":\"gmall\",\"et\":[{\"ett\":\"1578163332487\",\"en\":\"display\",\"kv\":{\"newsid\":\"n040\",\"action\":\"1\",\"extend1\":\"1\",\"place\":\"2\",\"category\":\"70\"}},{\"ett\":\"1578144142165\",\"en\":\"newsdetail\",\"kv\":{\"entry\":\"2\",\"newsid\":\"n533\",\"news_staytime\":\"5\",\"loading_time\":\"32\",\"action\":\"4\",\"showtype\":\"2\",\"category\":\"58\",\"type1\":\"102\"}},{\"ett\":\"1578194730770\",\"en\":\"ad\",\"kv\":{\"entry\":\"3\",\"show_style\":\"3\",\"action\":\"2\",\"detail\":\"\",\"source\":\"2\",\"behavior\":\"2\",\"content\":\"1\",\"newstype\":\"3\"}},{\"ett\":\"1578200771629\",\"en\":\"notification\",\"kv\":{\"ap_time\":\"1578187559154\",\"action\":\"4\",\"type\":\"4\",\"content\":\"\"}},{\"ett\":\"1578144628970\",\"en\":\"active_background\",\"kv\":{\"active_source\":\"3\"}},{\"ett\":\"1578180664809\",\"en\":\"error\",\"kv\":{\"errorDetail\":\"java.lang.NullPointerException\\\\n    at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\\n at cn.lift.dfdf.web.AbstractBaseController.validInbound\",\"errorBrief\":\"at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)\"}},{\"ett\":\"1578190629652\",\"en\":\"praise\",\"kv\":{\"target_id\":8,\"id\":9,\"type\":1,\"add_time\":\"1578140527778\",\"userid\":6}}]}";
        String jsonKeys="mid,uid,vc,vn,l,sr,os,ar,md,ba,sv,g,hw,nw,ln,la,t";
        String evaluate = new BaseFieldUDF().evaluate(line, jsonKeys);
        System.out.println(evaluate);
    }

} 