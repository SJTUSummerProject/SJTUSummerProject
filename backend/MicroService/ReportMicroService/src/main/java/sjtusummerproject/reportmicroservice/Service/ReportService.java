package sjtusummerproject.reportmicroservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sjtusummerproject.reportmicroservice.DataModel.Domain.AnnuallyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.DailyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.MonthlyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.WeeklyReportEntity;

import java.text.ParseException;

public interface ReportService {
    public DailyReportEntity queryDailyByTicketidAndDate(Long ticketid, String dateInString) throws ParseException;
    public Page<DailyReportEntity> queryDailyByCityAndDate(String city, String dateInString, Pageable pageable) throws ParseException;
    public WeeklyReportEntity queryWeeklyByTicketidAndWeek(Long ticketid, int year, int month, int week);
    public Page<WeeklyReportEntity> queryWeeklyByCityAndWeek(String city, int year, int month, int week, Pageable pageable);
    public MonthlyReportEntity queryMonthlyByTicketidAndMonth(Long ticketid, int year, int month);
    public Page<MonthlyReportEntity> queryMonthlyByCityAndMonth(String city, int year, int month, Pageable pageable);
    public AnnuallyReportEntity queryAnnuallyByTicketidAndYear(Long ticketid, int year);
    public Page<AnnuallyReportEntity> queryAnnuallyByCityAndYear(String city, int year, Pageable pageable);
}
