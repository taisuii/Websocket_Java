package User;

import Util.Mysql;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Login {
    public static void run(Session session, String U_name) throws IOException, SQLException {

        ResultSet resultSet = Mysql.executeSQL("select U_id from Users where U_name = '" + U_name + "'");
        HashMap<String, Object> json = new HashMap<>();
        json.put("method", "login");

        if (resultSet.next()) {
            json.put("U_id", resultSet.getString(1));
            session.getRemote().sendString(Check.W_success(json));
        } else {
            session.getRemote().sendString(Check.W_fail(json, "未注册"));

        }
    }


}
