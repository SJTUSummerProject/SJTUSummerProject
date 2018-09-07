package sjtusummerproject.reportmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sjtusummerproject.reportmicroservice.DataModel.Dao.AnnuallyReportRepository;
import sjtusummerproject.reportmicroservice.DataModel.Dao.DailyReportRepository;
import sjtusummerproject.reportmicroservice.DataModel.Dao.MonthlyReportRepository;
import sjtusummerproject.reportmicroservice.DataModel.Dao.WeeklyReportRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.AnnuallyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.DailyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.MonthlyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.WeeklyReportEntity;
import sjtusummerproject.reportmicroservice.Service.ReportService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    DailyReportRepository dailyReportRepository;
    @Autowired
    WeeklyReportRepository weeklyReportRepository;
    @Autowired
    MonthlyReportRepository monthlyReportRepository;
    @Autowired
    AnnuallyReportRepository annuallyReportRepository;

    @Override
    public Page<DailyReportEntity> queryDailyReportAll(Pageable pageable) {
        return dailyReportRepository.findAll(pageable);
    }

    @Override
    public DailyReportEntity queryDailyByTicketidAndDate(Long ticketid, String dateInString) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date= sdf.parse(dateInString);
        return dailyReportRepository.findAllByTicketIdAndDate(ticketid,date);
    }

    @Override
    public Page<DailyReportEntity> queryDailyByCityAndDate(String city, String dateInString, Pageable pageable) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date= sdf.parse(dateInString);
        return dailyReportRepository.findAllByCityAndDate(city,date,pageable);
    }

    @Override
    public Page<WeeklyReportEntity> queryWeeklyReportAll(Pageable pageable) {
        return weeklyReportRepository.findAll(pageable);
    }

    @Override
    public WeeklyReportEntity queryWeeklyByTicketidAndWeek(Long ticketid, int year, int month, int week) {
        return weeklyReportRepository.findAllByTicketIdAndYearAndMonthAndWeek(ticketid,year,month,week);
    }

    @Override
    public Page<WeeklyReportEntity> queryWeeklyByCityAndWeek(String city, int year, int month, int week, Pageable pageable) {
        return weeklyReportRepository.findAllByCityAndYearAndMonthAndWeek(city, year, month, week, pageable);
    }

    @Override
    public Page<MonthlyReportEntity> queryMonthlyReportAll(Pageable pageable) {
        return monthlyReportRepository.findAll(pageable);
    }

    @Override
    public MonthlyReportEntity queryMonthlyByTicketidAndMonth(Long ticketid, int year, int month) {
        return monthlyReportRepository.findAllByTicketIdAndYearAndMonth(ticketid, year, month);
    }

    @Override
    public Page<MonthlyReportEntity> queryMonthlyByCityAndMonth(String city, int year, int month, Pageable pageable) {
        return monthlyReportRepository.findAllByCityAndYearAndMonth(city,year,month,pageable);
    }

    @Override
    public Page<AnnuallyReportEntity> queryAnnuallyReportAll(Pageable pageable) {
        return annuallyReportRepository.findAll(pageable);
    }

    @Override
    public AnnuallyReportEntity queryAnnuallyByTicketidAndYear(Long ticketid, int year) {
        return annuallyReportRepository.findAllByTicketIdAndYear(ticketid, year);
    }

    @Override
    public Page<AnnuallyReportEntity> queryAnnuallyByCityAndYear(String city, int year, Pageable pageable) {
        return annuallyReportRepository.findAllByCityAndYear(city,year,pageable);
    }
}
