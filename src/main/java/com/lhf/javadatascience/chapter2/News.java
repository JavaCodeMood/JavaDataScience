package com.lhf.javadatascience.chapter2;

import lombok.Data;

/**
 * @ClassName News
 * @Desc TODO
 * @Author diandian
 * @Date 2022/1/6 18:20
 **/
@Data
public class News {

    private int id;//新闻id
    private String title;//新闻标题
    private String content;//新闻内容
    private int reply;//评论数

    public News() {

    }

    public News(int id, String title, String content, int reply) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.reply = reply;
    }
}