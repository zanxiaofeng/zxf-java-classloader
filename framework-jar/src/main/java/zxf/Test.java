package zxf;

import com.google.gson.Gson;
import zxf.common.Common;

public class Test {
    public static void main(String[] args) {
        String json = "{'code':'000','message':'Success','properties':{'A':'a','2':2}}";
        Common common = new Gson().fromJson(json, Common.class);
        System.out.println(common.getCode() + ": " + common.getMessage());
    }
}
