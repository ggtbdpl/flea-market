package com.shumei.DAO;

import com.shumei.pojo.Report;
import java.util.List;

public interface ReportDAO {
    boolean addReport(Report report);
    List<Report> getAllReports();
    boolean handleReport(int reportId, int handlerId, String feedback);
    boolean rejectReport(int reportId);
    boolean deleteReport(int reportId);
}