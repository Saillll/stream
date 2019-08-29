package com.zzj.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZZZTest {

    public static void main(String[] args) {


        /**
         *      方法引用使用一对冒号 ::
                构造方法引用： 类::new
                静态方法引用：类::静态方法
                实例方法引用：类::实例方法  或者  对象::实例方法
         */
        List<Article> list = new ArrayList<Article>();

        list.add(new Article("小说AA",45000));
        list.add(new Article("小说BB",9000));
        list.add(new Article("小说CC",20000));
        list.add(new Article("小说DD",550000));
        list.add(new Article("小说EE",40000));
        list.add(new Article("小说EE",40000));
        list.add(new Article("小说EE",60000));
        list.add(new Article("小说FF",40000));

        Stream<Article> stream = list.stream();
        stream = list.parallelStream();//并行流方法，可以让数据集执行并行操作
        //循环list打印全部信息
        list.stream().forEach((listArticle) -> System.out.println(listArticle.getTitle()+ "," + listArticle.getWords()));

        System.out.println("------------filter---------------");
        //filter
        //使用filter过滤调一些数据：
        // 除去words大于25000的Article，然后打印
        List<Article> filterList = list.stream().filter(article -> article.getWords() > 25000).collect(Collectors.toList());
        filterList.stream().forEach((filterArticle) -> System.out.println(filterArticle.getTitle()+ "," + filterArticle.getWords()));

        System.out.println("------------distinct---------------");
        //distinct
        List<Article> mutiList = null;
        long all = list.stream().count();
        long norepeat = list.stream().distinct().count();
        System.out.println("全部数量：" + all + "，去重之后的数量：" + norepeat);

        System.out.println("-------------limit--------------");
        //limit
        long limitCount = list.stream().limit(3).count();
        List<Article> limitList = list.stream().limit(3).collect(Collectors.toList());
        System.out.println("limitCount is " + limitCount );
        limitList.stream().forEach(limitArticle -> System.out.println(limitArticle.getTitle() + " --normal-- " + limitArticle.getWords()));

        System.out.println("-------------sort--------------");
        //sort
        //从小到大： 通过 :: 选择符 选择对象的某个字段进行排序 ，此处选择的是words字数
        List<Article> sortList = list.stream().sorted(Comparator.comparing(Article::getWords)).collect(Collectors.toList());
        sortList.stream().forEach(sortedArticle -> System.out.println(sortedArticle.getTitle() + "--" + sortedArticle.getWords()));

        //倒序  从大到小
        sortList = list.stream().sorted(Comparator.comparing(Article::getWords).reversed()).collect(Collectors.toList());
        sortList.stream().forEach(sortedArticle -> System.out.println(sortedArticle.getTitle() + "--reverse--" + sortedArticle.getWords()));

        //按照文字/字母顺序排序 abcd....
        sortList = list.stream().sorted(Comparator.comparing(Article::getTitle)).collect(Collectors.toList());
        sortList.stream().forEach(sortedArticle -> System.out.println(sortedArticle.getTitle() + "--character--" + sortedArticle.getWords()));
        //插播一个单词释义
        /**
        在牛津高阶英语词典中，对于character的其中一个与letter有关的解释是：
         character means a letter, sign, mark or symbol used in writing。
         由此可见letter是包含在character中的，character可以表示中国的文字，
         也可以表示字母、符号、记号等。而字典中对于letter的定义是字母的意思，
         比如 ‘B’is the second letter of the alphabet. 这也从侧面证明了letter只是字母的意思，是character的一部分。
        比如：一句话里除了有字母或者文字还可以有标点符号，这些都成为character。 而letter只是字母。
        */
        System.out.println("-------------map--------------");
        List<String> mapList = list.stream().map(Article::getTitle).collect(Collectors.toList());
        mapList.stream().forEach(generalString -> System.out.println(generalString));

        System.out.println("-------------toMap--------------");
        //toMap 转换成map ,第一个是key，第二个是value，第三个参数表示如果存在相同的key，那么使用newValue作为值，或者使用表达式来判断
        Map<String,Object> mapObject = list.stream().collect(Collectors.toMap(Article::getTitle,Article::getWords,(oldValue,newValue) -> newValue));
        //默认的情况下，如果存在相同的key会报错
        //Map<String,Object> mapObject = list.stream().collect(Collectors.toMap(Article::getTitle,Article::getWords));
        //Function.identity 代表的是原型，此处代表把 List<Article> list这个循环体内的每一个Article对象实例作为value,，也可以换成对象指向article -> article
        // Map<String,Article> mapIdentityButMultiple = list.stream().collect(Collectors.toMap(Article::getTitle,Function.identity())); //key重复
        // Map<String,Article> mapIdentityOrigin = list.stream().collect(Collectors.toMap(Article::getTitle,article -> article));//key重复
        Map<String,Article> mapIdentityJudge = list.stream().collect(Collectors.toMap(Article::getTitle,Function.identity(),(oldArticle,newArticle) -> {
                //当key重复的时候，对比新旧value对象的words大小，取最大的作为value
            System.out.println("oldArticle.words is " + oldArticle.getWords());
            System.out.println("newArticle.words is " + newArticle.getWords());
                if(oldArticle.getWords()>newArticle.getWords()){
                    return oldArticle;
                }else{
                    return newArticle;
                }
                //或者直接三元表达式
                //return oldArticle.getWords()>newArticle.getWords()?oldArticle:newArticle;
            }
        ));


        System.out.println("-------------groupingBy--------------");
        //按照title字段 分组 把key相同的article对象放到一个list中，然后放入mapKeyList集合中去
        Map<String, List<Article>> mapKeyList = list.stream().collect(Collectors.groupingBy(Article::getTitle));
        //按照title字段 分组 统计key相同的数量，然后放入mapCount集合中去
        Map<String, Long> mapCount = list.stream().collect(Collectors.groupingBy(Article::getTitle, Collectors.counting()));
        //按照title字段 分组 然后把article的words字段放入set中去重复，然后把set结果集 作为value 放入到mapSet中
        Map<String, Set<Integer>> mapSet = list.stream().collect(Collectors.groupingBy(Article::getTitle, Collectors.mapping(Article::getWords, Collectors.toSet())));

        System.out.println("-------------summary--------------");
        //按照article的words统计数据，分别求出最大 最小 平均数 全部求和
        IntSummaryStatistics summaryStatistics = list.stream().mapToInt(Article::getWords).summaryStatistics();
        System.out.println(summaryStatistics.getMax());
        System.out.println(summaryStatistics.getMin());
        System.out.println(summaryStatistics.getAverage());
        System.out.println(summaryStatistics.getSum());

    }
}
