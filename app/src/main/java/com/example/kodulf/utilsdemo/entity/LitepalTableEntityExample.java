package com.example.kodulf.utilsdemo.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by Kodulf on 2017/5/1.
 */

public class LitepalTableEntityExample extends DataSupport {
    /*

 数据库的操作可以通过DataSupport 的静态方法来操作，也可以通过类的变量的特定的方法来操作。

 litepal：非常方便：https://github.com/LitePalFramework/LitePal
 save
 删：对象等，等等，等等啊
 改：对象孤寡
 查洞房df，洞房啊dfa，洞完房了哦哦dwfloo

 最主要的就是要记住的是增删改查的部分，其他的只是配置而已

 如果需要添加一个新的表，直接创建这个类继承DataSupport，然后在配置文件中添加，增加数据库的版本的数量，就可以了

 增：对象.save，就是增

 删：对象等，等等，等等啊
 对象.delete(git上面没有写，但是的确是可以的) DataSupport.delete
 DataSupport.delete(Song.class, id);
 DataSupport.deleteAll(Song.class, "duration > ?" , "350");

 改：对象孤寡
 Album albumToUpdate = new Album();
 albumToUpdate.setPrice(20.99f); // raise the price
 albumToUpdate.update(id);

 Album albumToUpdate = new Album();
 albumToUpdate.setPrice(20.99f); // raise the price
 albumToUpdate.updateAll("name = ?", "album");

 查（最重要）直接使用的就是DataSupport静态方法，
 查洞房df，洞房啊dfa，洞完房了哦哦dwfloo
 Song song = DataSupport.find(Song.class, id);
 List<Song> allSongs = DataSupport.findAll(Song.class);
 List<Song> songs = DataSupport.where("name like ?", "song%").order("duration”).offset(3).limit(10).find(Song.class);
 注意可以设置offset，就是从哪一个开始算，这个在翻页里面用到的，还有limit 限制



 http://blog.csdn.net/guolin_blog/article/details/38556989


 0: 导入jar 包
 dependencies {
 compile 'org.litepal.android:core:1.5.1'
 }
 1: 配置litepal.xml
 右击这个项目，New->Folder->Assets Folder就会创建一个assets的文件夹
 接着在项目的assets目录下面新建一个litepal.xml文件，并将以下代码拷贝进去：
 <?xml version="1.0" encoding="utf-8"?>
 <litepal>
 <dbname value="demo" ></dbname>

 <version value="1" ></version>

 <list>
 <mapping class="org.litepal.litepalsample.model.Album" />
 <mapping class="org.litepal.litepalsample.model.Song" />
 </list>
 </litepal>
 配置文件相当简单，<dbname>用于设定数据库的名字，<version>用于设定数据库的版本号，<list>用于设定所有的映射模型，我们稍后就会用到。

 2: 我们一共12张表所以就是12个list的mapping

 3: 配置LitePalApplication,我们只要Application 继承LitePalApplication 就可以了
 或者如果是其他的Application,那么直接在onCreate里面
 LitePal.initialize(this);

 4: 创建表, 在类中,id 可以不写,因为默认的是有这个的.  可以是下面的类型.
 int、short、long、float、double、boolean、String和Date
 1. Create tables

 Define the models first. For example you have two models, Album and Song. The models can be defined as below:

 public class Album extends DataSupport {

@Column(unique = true, defaultValue = "unknown")
private String name;

private float price;

private byte[] cover;

private List<Song> songs = new ArrayList<Song>();

// generated getters and setters.
...
}
 public class Song extends DataSupport {

@Column(nullable = false)
private String name;

private int duration;

@Column(ignore = true)
private String uselessField;

private Album album;

// generated getters and setters.
...
}
 Then add these models into the mapping list in litepal.xml:

 <list>
 <mapping class="org.litepal.litepalsample.model.Album" />
 <mapping class="org.litepal.litepalsample.model.Song" />
 </list>
 OK! The tables will be generated next time you operate database. For example, gets the SQLiteDatabase with following codes:

 SQLiteDatabase db = LitePal.getDatabase();

 有三种情况会清空数据的：
 But there are some upgrading conditions that LitePal can't handle and all data in the upgrading table will be cleaned:

 Add a field which annotated as unique = true.
 Change a field's annotation into unique = true.
 Change a field's annotation into nullable = false.



 +++++++++++++++++++++++++++++++++++++++
 注意了：Date 这个 类型的 包含的
 Integer 也是可以的
 +++++++++++++++++++++++++++++++++++++++
 然后将这个类放到litepal的文件的list 里面.
 <list>
 <mapping class="com.example.databasetest.model.News"></mapping>
 </list>

 5: 现在对数据库有任何操作的时候,上面的类就会被创建了,例如如果获取SQLiteDatabase的实例
 SQLiteDatabase db = Connector.getDatabase();

 6: 如果数据库有更新的话，只需要增加litepal里面的version value 就可以了
 如果有新的表的话，就要在list 里面添加

 7：一对一关联的实现方式是用外键，多对一关联的实现方式也是用外键，多对多关联的实现方式是用中间表。
 外联：可以直接使用类就可以了
 private Introduction introduction;

 如果是一个对多个：
 private List<Comment> commentList = new ArrayList<Comment>();

 8：所有的类里面要继承DataSupport，数据支持。
 public class News extends DataSupport{

 继承了DataSupport类之后，这些实体类就拥有了进行CRUD操作的能力，那么比如想要存储一条数据到news表当中，就可以这样写：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 News news = new News();
 news.setTitle("这是一条新闻标题");
 news.setContent("这是一条新闻内容");
 news.setPublishDate(new Date());
 news.save();

 如果要插入只要save 就可以了。

 9：怎么保证那一个是not null？


 10： save 有返回值，可以通过返回值来判断这个操作的结果

 而这个save()方法自然就是从DataSupport类中继承而来的了。
 除此之外，save()方法还是有返回值的，我们可以根据返回值来判断存储是否成功，比如说这样写：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 if (news.save()) {
 Toast.makeText(context, "存储成功", Toast.LENGTH_SHORT).show();
 } else {
 Toast.makeText(context, "存储失败", Toast.LENGTH_SHORT).show();
 }  ：


 11：还可以通过svaeThrows()方法来存储数据，就可以将异常跑出来了

 12： 之前在insert 的时候返回的id 在这里可以通过getId 得到，但是这个市要在save 之后

 News news = new News();
 news.setTitle("这是一条新闻标题");
 news.setContent("这是一条新闻内容");
 news.setPublishDate(new Date());
 Log.d("ACTIVITY_NAME", "news id is " + news.getId());
 news.save();
 Log.d("ACTIVITY_NAME", "news id is " + news.getId());

 13：
 Comment comment1 = new Comment();
 comment1.setContent("好评！");
 comment1.setPublishDate(new Date());
 comment1.save();
 Comment comment2 = new Comment();
 comment2.setContent("赞一个");
 comment2.setPublishDate(new Date());
 comment2.save();
 News news = new News();
 news.getCommentList().add(comment1);
 news.getCommentList().add(comment2);
 news.setTitle("第二条新闻标题");
 news.setContent("第二条新闻内容");
 news.setPublishDate(new Date());
 news.setCommentCount(news.getCommentList().size());
 news.save();

 14：更新一个数据的操作
 ContentValues values = new ContentValues();
 values.put("title", "今日iPhone6发布");
 DataSupport.update(News.class, values, 2);

 更新所有的
 ContentValues values = new ContentValues();
 values.put("title", "今日iPhone6 Plus发布");
 DataSupport.updateAll(News.class, values, "title = ?", "今日iPhone6发布");

 DataSupport.updateAll(News.class, values, "title = ? and commentcount > ?", "今日iPhone6发布", "0");


 public static int updateAll(Class<?> modelClass, ContentValues values, String... conditions)
 后面的是条件的判断，就是和原来到数据库的操作的一样的。

 如果没有判断，那么就是所有的都改
 DataSupport.updateAll(News.class, values);


 +++++++++++++++++++++++++
 注意这里的变量是DataSupport，然后里面指定的是相应的表的类。
 +++++++++++++++++++++++++

 15：还有一种更新方法是通过
 就是不通过ContentValues，而直接使用类的变量来做
 News updateNews = new News();
 updateNews.setTitle("今日iPhone6发布");
 updateNews.update(2);

 News updateNews = new News();
 updateNews.setTitle("今日iPhone6发布");
 updateNews.updateAll("title = ? and commentcount > ?", "今日iPhone6发布", "0");


 16：如果要将某一列修改为默认的值的时候，需要用到setToDefault()

 17：删除的时候注意了，
 删除了外键的数据以后，和外键关联的数据也会删除的。
 所以如果删除了，用户的信息，那么用户帐单的信息当然也会删除了的。
 就像下面的例子里面，删除了学生的信息了以后，那么学生的成绩信息也是会被删除掉的

 DataSupport.delete(News.class, 2);

 删除的操作返回的是删除的个数，例如删除的时候，相关联的表里面删除的也是算进来的，所以可能是3个数据

 int deleteCount = DataSupport.delete(News.class, 2);

 public static int deleteAll(Class<?> modelClass, String... conditions)

 DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");

 将News 表中的所有的数据都删除了
 DataSupport.deleteAll(News.class);

 18：删除除了可以通过DataSupport 的静态方法来操作，还可以通过类的变量来操作的。
 但是要注意的就是一定要save 以后才能够进行delete()就所谓的进行持久化以后才能够删除的
 在删除之前可以通过news.isSaved（）来判断是否已经保存了。然后再进行删除
 News news = new News();
 news.setTitle("这是一条新闻标题");
 news.setContent("这是一条新闻内容");
 news.save();
 ...
 news.delete();


 News news;
 ...
 if (news.isSaved()) {
 news.delete();
 }

 19：查询的语句：News news = DataSupport.find(News.class, 1);
 如果想要返回第一条记录的话就是使用的：
 News firstNews = DataSupport.findFirst(News.class);
 获取最后的一条数据的记录
 News lastNews = DataSupport.findLast(News.class);


 也可以查询多个数据的。
 List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7);


 long[] ids = new long[] { 1, 3, 5, 7 };
 List<News> newsList = DataSupport.findAll(News.class, ids);

 如果要查询所有的数据的时候
 List<News> allNews = DataSupport.findAll(News.class);

 为了避免冗长的参数列表，LitePal采用了一种非常巧妙的解决方案，叫作连缀查询，这种查询很灵活，可以根据我们实际的查询需求来动态配置查询参数。 那这里举个简单的例子，比如我们想查询news表中所有评论数大于零的新闻，就可以这样写：
 List<News> newsList = DataSupport.where("commentcount > ?", "0").find(News.class);
 ++++++++++++++++++++++++++++++
 注意了这里的查询时候还是使用的find 但是加入了条件判断了where
 ++++++++++++++++++++++++++++++
 如果只需要其中的两个列的时候可以使用：
 但是这样会将news表中所有的列都查询出来，也许你并不需要那么多的数据，而是只要title和content这两列数据。那么也很简单，我们只要再增加一个连缀就行了，如下所示：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 List<News> newsList = DataSupport.select("title", "content")
 .where("commentcount > ?", "0").find(News.class);


 还可以进行排序：
 List<News> newsList = DataSupport.select("title", "content")
 .where("commentcount > ?", "0")
 .order("publishdate desc").find(News.class);
 这里面的排序如果是desc 是倒叙，asc 就是正序 asc表示正序排序，desc表示倒序排序，


 然后可以选择出来的数据的数量，通过limit 来做
 然后呢，也许你并不希望将所有条件匹配的结果一次性全部查询出来，因为这样数据量可能会有点太大了，而是希望只查询出前10条数据，那么使用连缀同样可以轻松解决这个问题，代码如下所示：
 List<News> newsList = DataSupport.select("title", "content")
 .where("commentcount > ?", "0")
 .order("publishdate desc").limit(10).find(News.class);


 刚才我们查询到的是所有匹配条件的前10条新闻，那么现在我想对新闻进行分页展示，翻到第二页时，展示第11到第20条新闻，这又该怎么实现呢？没关系，在LitePal的帮助下，这些功能都是十分简单的，只需要再连缀一个偏移量就可以了，如下所示：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 List<News> newsList = DataSupport.select("title", "content")
 .where("commentcount > ?", "0")
 .order("publishdate desc").limit(10).offset(10)
 .find(News.class);

 偏移量为10


 20：联表的查询

 News news = DataSupport.find(News.class, 1, true);
 List<Comment> commentList = news.getCommentList();
 可以看到，这里并没有什么复杂的用法，也就是在find()方法的最后多加了一个true参数，就表示使用激进查询了。这会将和news表关联的所有表中的数据也一起查出来，那么comment表和news表是多对一的关联，所以使用激进查询一条新闻的时候，那么该新闻所对应的评论也就一起被查询出来了。

 上面的方法其实还是不推荐的，还是推荐使用方法里面的。
 但是这种查询方式LitePal并不推荐，因为如果一旦关联表中的数据很多，查询速度可能就会非常慢。而且激进查询只能查询出指定表的关联表数据，但是没法继续迭代查询关联表的关联表数据。因此，这里我建议大家还是使用默认的懒加载更加合适，至于如何查询出关联表中的数据，其实只需要在模型类中做一点小修改就可以了。修改News类中的代码，如下所示：

 返回的
 public List<Comment> getComments() {
 return DataSupport.where("news_id = ?", String.valueOf(id)).find(Comment.class);
 }


 21：原生查询：
 Cursor cursor = DataSupport.findBySQL("select * from news where commentcount>?", "0");


 22：聚合函数：
 int result = DataSupport.count(News.class);
 int result = DataSupport.where("commentcount = ?", "0").count(News.class);
 int result = DataSupport.sum(News.class, "commentcount", int.class);
 double result = DataSupport.average(News.class, "commentcount");
 int result = DataSupport.max(News.class, "commentcount", int.class);
 int result = DataSupport.min(News.class, "commentcount", int.class);




































 ++++++++++++++++++++++++++++++++++++++++++++++++++++++



 创建和打开数据库的命令: .open teacher.db
 查看当前的数据库信息：.database
 查看当前的表：.table



 create table stuinfo(id integer primary key,name text,sex char(1),address text);
 create table stuscore(id integer primary key,xuehao integer,kemu text,score int,foreign key(xuehao) references stuinfo(id));



 insert into stuinfo values(1,'王宝强','男','北京');
 insert into stuinfo values(2,'李晨','男','北京');
 insert into stuinfo values(3,'陈赫','男','上海');


 .schema stuscore
 insert into stuscore values(1,1,'大学',98);
 insert into stuscore values(2,1,'中庸',97);
 insert into stuscore values(3,1,'美术',97);
 insert into stuscore values(4,2,'美术',100);
 insert into stuscore values(5,3,'大学',100);
 insert into stuscore values(6,4,'大学',100);
 pragma foreign_keys=on;
 insert into stuscore values(7,5,'大学',100);
 FOREIGN KEY constraint failed


 select stuinfo.id,name,score from stuinfo join stuscore on stuinfo.id=stuscore.xuehao;
 .schema stuinfo
 insert into stuinfo values(5,'邓超','男','北京');
 insert into stuinfo values(6,'孙俪','女','北京');
 select stuinfo.id,name,score from stuinfo left join stuscore on stuinfo.id=stuscore.xuehao;

 多表连接查询，可以同时查询两张表，三张表，四张表。
 了解一下，android 里面用的不多，但是掌握了很好。

 join 内连接
 select stuinfo.id,name,score from stuinfo join stuscore on stuinfo.id=stuscore.xuehao;
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 left join 左外连接 会把左表中的数据全部显示出来。不管左表它漫步满足。
 right join 右外连接。  会把右表中的数据全部显示出来。------当前不支持。

 select stuinfo.id,name,score from stuinfo left join stuscore on stuinfo.id=stuscore.xuehao;


 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

 */

    public LitepalTableEntityExample() {
    }

    @Override
    public String toString() {
        return "LitepalTableEntityExample{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", priceFloat=" + priceFloat +
                ", cover='" + cover + '\'' +
                ", songs=" + songs +
                '}';
    }

    public LitepalTableEntityExample(String name, int price, float priceFloat, String cover, ArrayList<Song> songs) {
        this.name = name;
        this.price = price;
        this.priceFloat = priceFloat;
        this.cover = cover;
        this.songs = songs;
    }

    public LitepalTableEntityExample(String name, int price, String cover, ArrayList<Song> songs) {
        this.name = name;
        this.price = price;
        this.cover = cover;
        this.songs = songs;
    }

    @Column(unique = true,defaultValue = "unknown")
    private String name;

    private int price;

    public float getPriceFloat() {
        return priceFloat;
    }

    public void setPriceFloat(float priceFloat) {
        this.priceFloat = priceFloat;
    }

    private float priceFloat;

    private String cover;

    private ArrayList<Song> songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

}
