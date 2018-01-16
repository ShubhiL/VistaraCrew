package com.example.sl.vistaracrew;

public class ContentFormat {


    public String seatNo;
    public String requestMade;

    public ContentFormat( String seatNo, String requestMade){
        this.seatNo = seatNo;
        this.requestMade = requestMade;

    }
    public String getSeatNo(){return seatNo;};
    public String getRequestMade(){return requestMade;};

}
