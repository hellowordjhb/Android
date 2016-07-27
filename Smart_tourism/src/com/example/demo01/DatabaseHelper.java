package com.example.demo01;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

//DatabaseHelper作为一个访问SQLite的助手类，提供两个方面的功能，
//第一，getReadableDatabase(),getWritableDatabase()可以获得SQLiteDatabse对象，通过该对象可以对数据库进行操作
//第二，提供了onCreate()和onUpgrade()两个回调函数，允许我们在创建和升级数据库时，进行自己的操作

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	// 在SQLiteOepnHelper的子类当中，必须有该构造函数
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		// 必须通过super调用父类当中的构造函数
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	// 该函数是在第一次创建数据库的时候执行,实际上是在第一次得到SQLiteDatabse对象的时候，才会调用这个方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		// execSQL函数用于执行SQL语句
		db.execSQL("create table frankfurt(id int,name varchar(20),location varchar(20),description text)");
		// //生成ContentValues对象
		// ContentValues values = new ContentValues();
		// //想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
		// values.put("id", 1);
		// values.put("name","罗马广场");
		// values.put("location","德国法兰克福罗马广场");
		// values.put("description","罗马广场是法兰克福现代化市容中，仍然保留着中古街道面貌的唯一广场；罗马广场旁有个罗马厅，实际上就是旧的市政厅，其阶梯状的人字形屋顶，别具特色。里面的皇帝殿（Kaisersaal）是许多罗马皇帝进行加冕的地方。罗马广场西侧的三个山形墙的建筑物，可以说是法兰克福的象征。1944年，该广场受到英国空军的猛烈空袭，基本被毁，战后重建。除了柏林的巴黎广场、汉堡的市政厅广场，和慕尼黑的玛利亚广场之外，这里是德国最重要的城市广场。");
		// //调用insert方法，就可以将数据插入到数据库当中
		// db.insert("frankfurt", null, values);
		db.execSQL("insert into frankfurt(id,name,location,description) values(1,'罗马广场','德国法兰克福罗马广场','罗马广场是法兰克福现代化市容中，仍然保留着中古街道面貌的唯一广场；罗马广场旁有个罗马厅，实际上就是旧的市政厅，其阶梯状的人字形屋顶，别具特色。里面的皇帝殿（Kaisersaal）是许多罗马皇帝进行加冕的地方。罗马广场西侧的三个山形墙的建筑物，可以说是法兰克福的象征。1944年，该广场受到英国空军的猛烈空袭，基本被毁，战后重建。除了柏林的巴黎广场、汉堡的市政厅广场，和慕尼黑的玛利亚广场之外，这里是德国最重要的城市广场。')");
		db.execSQL("insert into frankfurt(id,name,location,description) values(2,'夫子庙','江苏省南京市秦淮区夫子庙','夫子庙是一组规模宏大的古建筑群，历经沧桑，几番兴废，是供奉和祭祀孔子的地方，中国四大文庙之一，被誉为秦淮名胜而成为古都南京的特色景观区，也是蜚声中外的旅游胜地，是中国最大的传统古街市。夫子庙不仅是明清时期南京的文教中心，同时也是居东南各省之冠的文教建筑群。夫子庙始建于宋，位于秦淮河北岸的贡院街旁，庙前的秦淮河为泮池，南岸的石砖墙为照壁，全长110米，高10米，是全国照壁之最。北岸庙前有聚星亭、思乐亭；中轴线上建有棂星门、大成门、大成殿、明德堂、尊经阁等建筑；另外庙东还有魁星阁。范蠡、周瑜、王导、谢安、李白、杜牧、吴敬梓等数百位著名的军事家、政治家、文学家有这里创造了不朽的业绩，写下了千古传诵的篇章。')");

		// db.query("frankfurt", // table name
		// null, // columns
		// "name='" + name + "'" // selection
		// //...... 更多参数省略
		// );

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}