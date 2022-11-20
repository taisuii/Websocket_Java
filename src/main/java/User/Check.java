package User;

import cn.hutool.json.JSONObject;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Check {
    public static void contribute(Session session, String json) throws IOException {
        JSONObject jsonObject = new JSONObject(json);
        String method = jsonObject.getStr("method");
        try {
            switch (method) {
                case "login":
                    Login.run(session, jsonObject.getStr("U_name"));
                    break;
                case "register":
                    Register.run(session, jsonObject.getStr("U_name"), jsonObject.getStr("U_type"));
                    break;
                case "buy":
                    Buy.run(session, jsonObject.getStr("U_id"), jsonObject.getStr("P_id"));
                    break;
                default:
                    session.getRemote().sendString("not null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String W_success(HashMap<String, Object> json) {
        json.put("message", "success");
        json.put("code", 200);
        String result = new JSONObject(json).toString();
        System.out.println("message send success->" + result);
        return result;
    }

    public static String W_fail(HashMap<String, Object> json, String message) {
        json.put("message", message);
        json.put("code", 400);
        String result = new JSONObject(json).toString();
        System.out.println("message send fail->" + result);
        return new JSONObject(json).toString();
    }
}
