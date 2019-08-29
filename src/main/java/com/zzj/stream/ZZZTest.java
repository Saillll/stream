package com.zzj.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZZZTest {

    public static void main(String[] args) {

        List<Article> list = new ArrayList<Article>();

        list.add(new Article("小说AA",45000));
        list.add(new Article("小说BB",9000));
        list.add(new Article("小说CC",20000));
        list.add(new Article("小说DD",550000));
        list.add(new Article("小说EE",40000));
        list.add(new Article("小说EE",40000));
        list.add(new Article("小说FF",40000));

        Stream<Article> stream = list.stream();
        stream = list.parallelStream();//并行流方法，可以让数据集执行并行操作
        //循环list打印全部信息
        list.stream().forEach((listArticle) -> System.out.println(listArticle.getTitle()+ "," + listArticle.getWords()));
        System.out.println("---------------------------");
        //使用filter过滤调一些数据--除去words大于25000的Article，然后打印
        List<Article> filterList = list.stream().filter(article -> article.getWords() > 45000).collect(Collectors.toList());
        filterList.stream().forEach((filterArticle) -> System.out.println(filterArticle.getTitle()+ "," + filterArticle.getWords()));
        //
        List<Article> mutiList = null;
        long all = list.stream().count();
        long norepeat = list.stream().distinct().count();
        System.out.println("全部数量：" + all + "，去重之后的数量：" + norepeat);


    }
}
