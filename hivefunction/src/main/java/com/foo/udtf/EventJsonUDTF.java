package com.foo.udtf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EventJsonUDTF extends GenericUDTF {
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs)
            throws UDFArgumentException {
      if(argOIs.length<1){
          throw new IllegalStateException("Argument num must more than one");
      }
        List<String> fileNames = new ArrayList<>();
        List<ObjectInspector> fileTypes = new ArrayList<>();
        fileNames.add("event_name");
        fileNames.add("event_json");
        fileTypes.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fileTypes.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fileNames,fileTypes);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String input = args[0].toString();
        if (StringUtils.isBlank(input)){
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(input);
            if (jsonArray==null){
                return ;
            }
            for (int i=0;i<jsonArray.length();i++){
                String[] result = new String[2];
                try {

                    result[0]=jsonArray.getJSONObject(i).getString("en");
                    result[1]=jsonArray.getString(i);
                }catch (Exception e){
                    continue;
                }
                forward(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
