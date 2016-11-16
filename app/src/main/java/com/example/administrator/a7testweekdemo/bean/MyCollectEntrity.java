package com.example.administrator.a7testweekdemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016-11-12.
 */
public class MyCollectEntrity {

    /**
     * data : [{"id":"8309","title":"没想到你竟然是这种茶！如果以茶喻人，《欢乐颂》五美都是什么茶？","source":"一茶一视界","description":"","wap_thumb":"","create_time":"05月06日17:34","nickname":"颜颜"},{"id":"8218","title":"认识凤凰单丛茶","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2016/01/06/20160106110314_22333_suolue3.jpg","create_time":"01月06日11:04","nickname":"bubu123"},{"id":"8207","title":"\u201c日照绿茶\u201d品牌价值8.85亿 居山东茶类之首","source":"转载","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2016/01/04/20160104104521_47289_suolue3.jpg","create_time":"01月04日10:47","nickname":"bubu123"},{"id":"8199","title":"茶 的再利用","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/30/20151230101639_55069_suolue3.jpg","create_time":"12月30日10:20","nickname":"bubu123"},{"id":"8195","title":"饮茶的禁忌（下）","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/29/20151229093519_45699_suolue3.jpg","create_time":"12月29日09:37","nickname":"bubu123"},{"id":"8191","title":"饮茶的禁忌（上）","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/28/20151228101137_72686_suolue3.jpg","create_time":"12月28日10:14","nickname":"bubu123"},{"id":"8166","title":"买买茶从手游到茶叶--用互联网思维设计商业模式","source":"百度","description":"买买茶的转变历程","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/21/20151221031250268_suolue3.jpg","create_time":"12月21日14:59","nickname":"bubu123"},{"id":"7285","title":" 【买买茶】回馈老茶友好茶限时9.9秒杀 买一送一","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/22/20140422153600_77732_suolue3.jpg","create_time":"04月22日15:36","nickname":"strategist"},{"id":"7267","title":"怎样识别春茶？","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/08/20140408161406_63748_suolue3.jpg","create_time":"04月08日16:14","nickname":"回忆的沙漏"},{"id":"7266","title":"春茶好在那里？","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/08/20140408160100_23936_suolue3.jpg","create_time":"04月08日16:06","nickname":"杯中茗"}]
     * errorMessage : success
     */

    private String errorMessage;
    /**
     * id : 8309
     * title : 没想到你竟然是这种茶！如果以茶喻人，《欢乐颂》五美都是什么茶？
     * source : 一茶一视界
     * description :
     * wap_thumb :
     * create_time : 05月06日17:34
     * nickname : 颜颜
     */

    private List<DataBean> data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String title;
        private String source;
        private String description;
        private String wap_thumb;
        private String create_time;
        private String nickname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWap_thumb() {
            return wap_thumb;
        }

        public void setWap_thumb(String wap_thumb) {
            this.wap_thumb = wap_thumb;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
