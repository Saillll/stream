package com.zzj.stream;

import java.util.Date;
import java.util.Objects;

public class Article {
    private String title;
    private String writer;
    private Date createtime;
    private int words;

    public Article(String title, int words) {
        this.title = title;
        this.words = words;
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }


    /**
     * 重写equals方法，用于stream的distinct去重对比
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Article article = (Article) obj;
        if (this == article) {
            return true;
        } else {
            return (this.title.equals(article.title) && this.words == article.words);
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, words);
    }

}
