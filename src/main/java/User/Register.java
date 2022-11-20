package User;

import Util.Mysql;
import cn.hutool.json.JSONObject;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Register {
    public static void run(Session session, String U_name, String U_type) throws IOException, SQLException {

        Mysql.executeSQL(
                "insert into Users(U_name,U_type) values ('" + U_name + "'," + U_type + ")"
        );
        ResultSet resultSet = Mysql.executeSQL("select U_id from Users where U_name = '" + U_name + "'");

        String id = resultSet.next() ? String.valueOf(resultSet.getInt(1)) : "1";

        if (U_type.equals("2")) {
            Mysql.executeSQL(
                    "insert into Identitys(U_id,P_id) values (" + id + ",1)"
            );
            Mysql.executeSQL(
                    "insert into Identitys(U_id,P_id) values (" + id + ",2)"
            );
            Mysql.executeSQL(
                    "insert into Identitys(U_id,P_id) values (" + id + ",3)"
            );
        } else {
            Mysql.executeSQL(
                    "insert into Identitys(U_id,P_id) values (" + id + ",1)"
            );
        }
        HashMap<String, Object> json = new HashMap<>();
        json.put("method", "register");
        session.getRemote().sendString(Check.W_success(json));
    }
}

