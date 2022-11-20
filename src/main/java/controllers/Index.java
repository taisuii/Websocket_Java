package controllers;
import spark.Route;

import static spark.Spark.*;
public class Index {
    static Route index = ((request, response) -> {
        return  "Russell";
    });

    public static void Init(){
        get("/",index);
    }
}
