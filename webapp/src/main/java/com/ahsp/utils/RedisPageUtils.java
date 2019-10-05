package com.ahsp.utils;

import com.ahsp.po.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：Dr.chen
 * @date：2019/8/21 15:07
 * @Description：用于redis分页
 */
public class RedisPageUtils {
    public static List<Expert> pageExpertList(@NotNull List<Expert> expertList, int pc, int ps) {
        List<Expert> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > expertList.size()) {
            return temp;
        } else if (lowerBound > expertList.size()) {
            lowerBound = expertList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(expertList.get(i));
        }
        return temp;
    }

    public static List<Article> pageArticleList(@NotNull List<Article> articleList, int pc, int ps) {
        List<Article> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > articleList.size()) {
            return temp;
        } else if (lowerBound > articleList.size()) {
            lowerBound = articleList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(articleList.get(i));
        }
        return temp;
    }

    public static List<News> pageNewsList(@NotNull List<News> newsList, int pc, int ps) {
        List<News> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > newsList.size()) {
            return temp;
        } else if (lowerBound > newsList.size()) {
            lowerBound = newsList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(newsList.get(i));
        }
        return temp;
    }

    public static List<Notice> pageNoticeList(@NotNull List<Notice> noticeList, int pc, int ps) {
        List<Notice> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > noticeList.size()) {
            return temp;
        } else if (lowerBound > noticeList.size()) {
            lowerBound = noticeList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(noticeList.get(i));
        }
        return temp;
    }

    public static List<Patent> pagePatentList(@NotNull List<Patent> patentList, int pc, int ps) {
        List<Patent> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > patentList.size()) {
            return temp;
        } else if (lowerBound > patentList.size()) {
            lowerBound = patentList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(patentList.get(i));
        }
        return temp;
    }

    public static List<AppNoticeDetails> pageNoticeDetailList(@NotNull List<AppNoticeDetails> apdList, int pc, int ps) {
        List<AppNoticeDetails> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > apdList.size()) {
            return temp;
        } else if (lowerBound > apdList.size()) {
            lowerBound = apdList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(apdList.get(i));
        }
        return temp;
    }

    public static List<Variety> pageVarietyList(@NotNull List<Variety> varietyList, int pc, int ps) {
        List<Variety> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > varietyList.size()) {
            return temp;
        } else if (lowerBound > varietyList.size()) {
            lowerBound = varietyList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(varietyList.get(i));
        }
        return temp;
    }

    public static List<VarietyRight> pageVrList(@NotNull List<VarietyRight> vrList, int pc, int ps) {
        List<VarietyRight> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > vrList.size()) {
            return temp;
        } else if (lowerBound > vrList.size()) {
            lowerBound = vrList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(vrList.get(i));
        }
        return temp;
    }

    public static List<MyProxy> pageProxyList(@NotNull List<MyProxy> proxyList,int pc,int ps){
        List<MyProxy> temp = new ArrayList<>();
        int upBound = (pc - 1) * ps;
        int lowerBound = upBound + ps;
        //判断lowerBound是否超出了size，如果超出了，那么lowerBound就是size
        if (upBound > proxyList.size()) {
            return temp;
        } else if (lowerBound > proxyList.size()) {
            lowerBound = proxyList.size();
        }
        for (int i = upBound; i < lowerBound; i++) {
            temp.add(proxyList.get(i));
        }
        return temp;
    }
}
