package com.hassanmashraful.firebasecrud.key;

/**
 * Created by Hassan M.Ashraful on 3/16/2017.
 */

public interface key_url {

    public static final String KEY_LIST = "list";
    public static final String KEY_NAME = "name";
    public static final String KEY_WIND = "wind";
    public static final String KEY_SPEED = "speed";
    public static final String KEY_DEG = "deg";
    public static final String KEY_LAT = "lat=";
    public static final String KEY_LONG = "&lon=";

    public static final String APP_ID = "&appid=1efc0fc4375a4a874205759effcc7e36&cnt=1";

    //lat=21.465895&lon=91.955731&appid=1efc0fc4375a4a874205759effcc7e36

    //http://api.openweathermap.org/data/2.5/find?lat=21.465895&lon=91.955731&appid=1efc0fc4375a4a874205759effcc7e36

    public static final String URL_GET_DATA = "http://api.openweathermap.org/data/2.5/find?";
    public static final String URL_SEND_RESPOSE = "http://104.171.117.35/sms/sendsms/responce";

    public static final String TurbineDetails = "TurbineDetails" ;
    public static final String numOfTurbineOne = "numOfTurbineOne";
    public static final String oneTurbineOutputOne = "oneTurbineOutputOne";
    public static final String totalTurbineOutputOne = "totalTurbineOutputOne";

    public static final String numOfTurbineTwo = "numOfTurbineTwo";
    public static final String oneTurbineOutputTwo = "oneTurbineOutputTwo";
    public static final String totalTurbineOutputTwo = "totalTurbineOutputTwo";

    public static final String numOfTurbineThree = "numOfTurbineThree";
    public static final String oneTurbineOutputThree = "oneTurbineOutputThree";
    public static final String totalTurbineOutputThree = "totalTurbineOutputThree";

}
