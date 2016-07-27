package com.example.demo01;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

//DatabaseHelper��Ϊһ������SQLite�������࣬�ṩ��������Ĺ��ܣ�
//��һ��getReadableDatabase(),getWritableDatabase()���Ի��SQLiteDatabse����ͨ���ö�����Զ����ݿ���в���
//�ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ڴ������������ݿ�ʱ�������Լ��Ĳ���

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	// ��SQLiteOepnHelper�����൱�У������иù��캯��
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		// ����ͨ��super���ø��൱�еĹ��캯��
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	// �ú������ڵ�һ�δ������ݿ��ʱ��ִ��,ʵ�������ڵ�һ�εõ�SQLiteDatabse�����ʱ�򣬲Ż�����������
	@Override
	public void onCreate(SQLiteDatabase db) {
		// execSQL��������ִ��SQL���
		db.execSQL("create table frankfurt(id int,name varchar(20),location varchar(20),description text)");
		// //����ContentValues����
		// ContentValues values = new ContentValues();
		// //��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��
		// values.put("id", 1);
		// values.put("name","����㳡");
		// values.put("location","�¹������˸�����㳡");
		// values.put("description","����㳡�Ƿ����˸��ִ��������У���Ȼ�������йŽֵ���ò��Ψһ�㳡������㳡���и���������ʵ���Ͼ��Ǿɵ��������������״���������ݶ��������ɫ������Ļʵ۵Kaisersaal�����������ʵ۽��м���ĵط�������㳡���������ɽ��ǽ�Ľ��������˵�Ƿ����˸���������1944�꣬�ù㳡�ܵ�Ӣ���վ������ҿ�Ϯ���������٣�ս���ؽ������˰��ֵİ���㳡���������������㳡����Ľ��ڵ������ǹ㳡֮�⣬�����ǵ¹�����Ҫ�ĳ��й㳡��");
		// //����insert�������Ϳ��Խ����ݲ��뵽���ݿ⵱��
		// db.insert("frankfurt", null, values);
		db.execSQL("insert into frankfurt(id,name,location,description) values(1,'����㳡','�¹������˸�����㳡','����㳡�Ƿ����˸��ִ��������У���Ȼ�������йŽֵ���ò��Ψһ�㳡������㳡���и���������ʵ���Ͼ��Ǿɵ��������������״���������ݶ��������ɫ������Ļʵ۵Kaisersaal�����������ʵ۽��м���ĵط�������㳡���������ɽ��ǽ�Ľ��������˵�Ƿ����˸���������1944�꣬�ù㳡�ܵ�Ӣ���վ������ҿ�Ϯ���������٣�ս���ؽ������˰��ֵİ���㳡���������������㳡����Ľ��ڵ������ǹ㳡֮�⣬�����ǵ¹�����Ҫ�ĳ��й㳡��')");
		db.execSQL("insert into frankfurt(id,name,location,description) values(2,'������','����ʡ�Ͼ����ػ���������','��������һ���ģ���ĹŽ���Ⱥ��������ɣ�������˷ϣ��ǹ���ͼ�����ӵĵط����й��Ĵ�����֮һ������Ϊ�ػ���ʤ����Ϊ�Ŷ��Ͼ�����ɫ��������Ҳ���������������ʤ�أ����й����Ĵ�ͳ�Ž��С���������������ʱ���Ͼ����Ľ����ģ�ͬʱҲ�ǾӶ��ϸ�ʡ֮�ڵ��Ľ̽���Ⱥ��������ʼ�����Σ�λ���ػ��ӱ����Ĺ�Ժ���ԣ���ǰ���ػ���Ϊ���أ��ϰ���ʯשǽΪ�ձڣ�ȫ��110�ף���10�ף���ȫ���ձ�֮�������ǰ�о���ͤ��˼��ͤ���������Ͻ��������š�����š���ɵ�����á��𾭸�Ƚ��������������п��Ǹ󡣷�󻡢��褡�������л������ס��������⾴��������λ�����ľ��¼ҡ����μҡ���ѧ�������ﴴ���˲����ҵ����д����ǧ�Ŵ��е�ƪ�¡�')");

		// db.query("frankfurt", // table name
		// null, // columns
		// "name='" + name + "'" // selection
		// //...... �������ʡ��
		// );

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}