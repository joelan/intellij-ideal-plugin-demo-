package com.lrq.hhh.utils;

import com.lrq.hhh.entity.FieldEntity;

import javax.print.attribute.standard.Fidelity;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
public class ParseUtils {


    public List<FieldEntity> fiellist;

    public static List<FieldEntity> parseString(String paramsstr)
    {
        List<FieldEntity> fiellist=new ArrayList<FieldEntity>();

        String a =paramsstr;
        ByteArrayInputStream is=new ByteArrayInputStream(a.getBytes());
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
       // br.readLine()

        int totolline=0;
        String line;

        // 读取文件，并对读取到的文件进行操作
        try {
            while ((line = br.readLine()) != null) {
                totolline++;
//				Log.i("linemsg", line);
                String[] data_temp=line.split("\\|");
//				for (int i=0;i< data_temp.length;i++) {
//					Log.i("msgsub", data_temp[i]);
//				}
                //	Log.i("ErroTipsManager", "datalenth:"+data_temp.length);
                if(data_temp.length>=5)
                {
                    String fieldname=Trim(data_temp[1]);
                    String isforepost=Trim(data_temp[2]);
                    String fieldtype=Trim(data_temp[3]);
                    String fieldexplaincn=Trim(data_temp[4]);
                    //	Log.i("en", en);
                    //	Log.i("intro", intro);
                    //Log.i("cn", cn);
                    FieldEntity entity=new FieldEntity();

                    entity.setFielName(fieldname);

                   // if("string".equals(fieldtype))
                    entity.setFielType("String");
                    fiellist.add(entity);

                }
                else
                {

                    continue;
                    //return null;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();


        }
        finally
        {
            try {

                br.close();
                is.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


        if(totolline!=fiellist.size())
            fiellist=new ArrayList<FieldEntity>();


        return fiellist;

    }



    public static String Trim(String str)
    {
        if(!"".equals(str)) {
            String str2 = str.replaceAll("\\s*", "");
            return str2;
        }
        else
            return  "";

    }

}
