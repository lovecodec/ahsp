package com.crawler.pageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 通用爬虫模板，用户可以输入各种参数解析不同的内容，完成不同的爬虫
 */
public class CommonPageProcessor implements PageProcessor{
    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }

    public static void main(String[] args) {
        Spider spider = new Spider(new CommonPageProcessor());

    }

    /**
     * 问题：
     * url :
     * 是否为多条件组合查询？
     *      不是 --> 一般解析
     *      是 --> 给出查询条件
     * 是否为分页界面？
     *      是 --> 给出分页的参数，深度
     * post？
     * get？
     * 返回类型是json？ --> jsoun解析
     *      1. json数据提取的是一个bean
     *          需要提取的内容,给出key
     *      2. json数据提取的是一个集合
     *          需要提取的内容，给出key
     * 返回类型是html文本 --> xpath,css...
     * 在控制台(前台)打印解析出来的数据
     * 是否要保存到数据库呢
     */

}
