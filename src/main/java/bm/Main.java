package bm;
import controllers.Index;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args){
        port(8080);
        webSocket("/buy", MsgSrv.class);
        Index.Init();
    }

}
