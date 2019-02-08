package com.sourcey.materiallogindemo.Utils;

public class AppConstants {

    public static final String ACCESS_TOKEN="assess_token";
    public static final String ACCESS_TOKEN_EMAIL="access_token_email";
    public static final String NAME ="name" ;
    public static final String EMAILID ="emailid" ;
    public static final String LoginCheck ="logincheck" ;

    public static final String DB_NAME="SAMPLE";
    public static final int DB_VERSION=1;
    public static final String STD_TABLE="Location";

    //Student table columns

    public static final String LOC_ID="id";
    public static final String LOC_LATTTUDE="latitude";
    public static final String LOC_LONGITUDE="longitude";
    public static final String LOC_TIME="time";



    public static final String STD_QUERY="create table "+STD_TABLE+"(" +
            LOC_ID +" INTEGER PRIMARY KEY, "+
            LOC_LATTTUDE +" TEXT, "+
            LOC_LONGITUDE +" TEXT "+
            LOC_TIME +" TEXT "+
            ")";


  //  public static final String STD_ALREADY_EXIST_QUERY="select * from "+STD_TABLE+" where "+LOC_LON+"=";

}
