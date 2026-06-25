package com.shumei.DAO;

import com.shumei.pojo.Announcement;
import java.util.List;

public interface AnnouncementDAO {
    boolean addAnnouncement(Announcement announcement);
    boolean deleteAnnouncement(Integer id);
    boolean updateAnnouncement(Announcement announcement);
    Announcement getAnnouncementById(Integer id);
    List<Announcement> getAllAnnouncements();
}