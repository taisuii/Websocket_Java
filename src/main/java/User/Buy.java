package User;

import Util.Mysql;
import Util.Redis;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

public class Buy {
    public static void run(Session session, String U_id, String P_id) throws IOException, InterruptedException {
        HashMap<String, Object> json = new HashMap<>();
        json.put("method", "buy");

        if (!CheckUsers(U_id)) {
            session.getRemote().sendString(Check.W_fail(json, "用户未注册"));
        } else {
            if (!CheckP(U_id, P_id)) {
                session.getRemote().sendString(Check.W_fail(json, "用户权限不足"));
            } else {
                if (!CheckRedis(U_id)) {
                    session.getRemote().sendString(Check.W_fail(json, "正在购买中，请不要频繁请求"));
                } else {
                    session.getRemote().sendString(Check.W_fail(json, "正在购买中....."));

                    Redis.setValue(U_id, String.valueOf(System.currentTimeMillis()));
                    Thread.sleep(1000);
                    if ((Math.random() * 10) > 3){
                        session.getRemote().sendString(Check.W_success(json));
                    }else{
                        session.getRemote().sendString(Check.W_fail(json, "手滑了没抢到"));
                    }
                }

            }
        }
    }

    public static Boolean CheckUsers(String U_id) {
        try {
            ResultSet resultSet = Mysql.executeSQL("select * from Users where U_id = " + U_id);
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean CheckP(String U_id, String P_id) {
        try {
            ResultSet resultSet = Mysql.executeSQL("select * from Identitys where U_id = " + U_id + " and P_id = " + P_id);
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean CheckRedis(String U_id) {
        if (Redis.getValue(U_id) == null) {
            return true;
        } else {
            return false;
        }
    }
}
