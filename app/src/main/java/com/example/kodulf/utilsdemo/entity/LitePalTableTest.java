package com.example.kodulf.utilsdemo.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Kodulf on 2017/5/1.
 */

public class LitePalTableTest {

    static int num;

    public static void testCreate(){
        LitepalTableEntityExample album = new LitepalTableEntityExample();
        album.setName("hello world"+num);
        album.setPrice(num++);
        album.save();
    }

    /**
     删：对象等，等等，等等啊
     对象.delete(git上面没有写，但是的确是可以的) DataSupport.delete
     DataSupport.delete(Song.class, id);
     DataSupport.deleteAll(Song.class, "duration > ?" , "350");

     */
    public static void testDelete(){
        LitepalTableEntityExample album = DataSupport.find(LitepalTableEntityExample.class,1);
        album.delete();
    }

    /**
     *
     改：对象孤寡
     Album albumToUpdate = new Album();
     albumToUpdate.setPrice(20.99f); // raise the price
     albumToUpdate.update(id);

     Album albumToUpdate = new Album();
     albumToUpdate.setPrice(20.99f); // raise the price
     albumToUpdate.updateAll("name = ?", "album");
     */
    public static void testUpdate(){

    }


    /**
     *
     查（最重要）直接使用的就是DataSupport静态方法，
     查洞房df，洞房啊dfa，洞完房了哦哦dwfloo
     Song song = DataSupport.find(Song.class, id);
     List<Song> allSongs = DataSupport.findAll(Song.class);
     List<Song> songs = DataSupport.where("name like ?", "song%").order("duration”).offset(3).limit(10).find(Song.class);
     注意可以设置offset，就是从哪一个开始算，这个在翻页里面用到的，还有limit 限制
     */
    public static void testFind(){

    }
}
