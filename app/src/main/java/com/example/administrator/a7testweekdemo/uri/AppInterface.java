package com.example.administrator.a7testweekdemo.uri;

/**
 * Created by Administrator on 2016-11-12.
 */
public class AppInterface {
    //头条头视图的URL
    public static final String HEADERIMAGE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
    //头条的URL
    public static final String HEADLINE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&rows=15&page=%d";
    //百科的URL
    public static final String BAI_KE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=16&rows=15&page=%d";
    //资讯、经营、数据的URL
    public static final String BASE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=%d&rows=15&page=%d";
    //搜索的URL
    public static final String SEARCH_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&search=%s";
    //内容详情的URL
    public static final String CONTENT_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%s";

}
