package sjtusummerproject.ticketmicroservice.Service;

import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.List;

public interface InputDataService {
    public String inputdata(List<String> list);
    public String traversedir(List<String> citylist);
    public String readdirfile(String filename,String cityName,String type);
    public String parsejson(String string,String cityName,String type);
    public String ParseDate(String date);
    public String ParseVenue(String venue);
    public double ParseLowPrice(String price);
    public double ParseHighPrice(String price);
}
