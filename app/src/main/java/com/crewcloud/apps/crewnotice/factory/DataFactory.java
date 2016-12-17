package com.crewcloud.apps.crewnotice.factory;

import com.crewcloud.apps.crewnotice.data.Comment;
import com.crewcloud.apps.crewnotice.data.Photo;
import com.crewcloud.apps.crewnotice.data.Reply;
import com.crewcloud.apps.crewnotice.dtos.Notice;
import com.crewcloud.apps.crewnotice.event.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tunglam on 12/15/16.
 */

public class DataFactory {
    public static List<Notice> getListNotice() {
        List<Notice> newsInfoList = new ArrayList<>();

        Notice notice = new Notice();
        notice.setId("1");
        notice.setAuthor("Tran Dao Manh");
        notice.setContent("Annotations on the interface methods and its parameters indicate how a request will be handled.");
        notice.setNumber_comment("3");
        notice.setTime("today, 2:43 pm");

        Notice notice1 = new Notice();
        notice1.setAuthor("Harri won");
        notice1.setId("2");
        notice1.setContent("90% các gia đình Việt Nam quen thuộc với việc đun sôi nước máy để có nước uống sạch");
        notice1.setNumber_comment("0");
        notice1.setTime("14/12/2016, 5:43 pm");

        Notice notice2 = new Notice();
        notice2.setAuthor("Ngoc Trinh");
        notice2.setId("3");
        notice2.setContent("Khong tien cap dat ma an");
        notice2.setNumber_comment("1");
        notice2.setTime("11/12/2016, 5:43 pm");

        newsInfoList.add(notice);
        newsInfoList.add(notice1);
        newsInfoList.add(notice2);

        return newsInfoList;
    }

    public static List<MenuItem> getMenuItem() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Menu 1"));
        menuItems.add(new MenuItem(2, "Menu 2"));
        menuItems.add(new MenuItem(3, "Menu 3"));
        menuItems.add(new MenuItem(4, "Menu 4"));
        menuItems.add(new MenuItem(4, "Menu 5"));
        return menuItems;
    }

    public static Notice getNotice() {
        Notice notice = new Notice();
        notice.setId("1");
        notice.setAuthor("Tran Dao Manh");
        notice.setContent("Annotations on the interface methods and its parameters indicate how a request will be handled.");
        notice.setNumber_comment("3");
        notice.setTime("today, 2:43 pm");
        return notice;
    }

    public static List<Photo> getPhoto() {
        List<Photo> lstPhoto = new ArrayList<>();
        lstPhoto.add(new Photo("1", "http://photos.thegioisao.net/anh-dep/chan-dung/ngoc-trinh-08-6332-1434679400.jpg", "ngoctrinh"));
        lstPhoto.add(new Photo("2", "http://www.xaluan.com/images/news/Image/2015/08/27/nu-hoang-noi-y-ngoc-trinh-tu-khai-guong-mat-dao-keo-hinh-4.jpg", "ngoctrinh2"));
        lstPhoto.add(new Photo("3", "http://kenh14cdn.com/thumb_w/600/2016/5-1454435948579.jpg", "ao dai"));
        return lstPhoto;
    }

    public static List<Comment> getComments() {
        List<Comment> lstComment = new ArrayList<>();
        List<Reply> lstReply = new ArrayList<>();
        lstReply.add(new Reply("1", "that ak", "Ngoc Trinh", "http://www.xaluan.com/images/news/Image/2015/08/27/nu-hoang-noi-y-ngoc-trinh-tu-khai-guong-mat-dao-keo-hinh-4.jpg"));
        lstReply.add(new Reply("2", "eo tin", "Ronaldo", "http://photos.thegioisao.net/anh-dep/chan-dung/ngoc-trinh-08-6332-1434679400.jpg"));
        lstComment.add(new Comment("1", "Tran Dao Manh", "03:00 am", "http://kenh14cdn.com/thumb_w/600/2016/5-1454435948579.jpg", "good Detail", lstReply));
        lstComment.add(new Comment("2", "Abc", "05:00 am", "http://kenh14cdn.com/thumb_w/600/2016/5-1454435948579.jpg", "good Detail"));
        lstComment.add(new Comment("3", "xyz", "01:00 pm", "http://kenh14cdn.com/thumb_w/600/2016/5-1454435948579.jpg", "good Detail"));

        return lstComment;
    }
}
