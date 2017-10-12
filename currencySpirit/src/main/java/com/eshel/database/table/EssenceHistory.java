package com.eshel.database.table;

import com.eshel.model.EssenceModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by guoshiwen on 2017/10/12.
 */
@DatabaseTable(tableName = "EssenceHistory")
public class EssenceHistory {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField(columnName = "new_type")
	public int new_type;
	@DatabaseField(columnName = "title")
	public String title;
	@DatabaseField(columnName = "update_time")
	public long update_time;
	@DatabaseField(columnName = "abstracts")
	public String abstracts;
	@DatabaseField(columnName = "url")
	public String url;
	@DatabaseField(columnName = "imageurl")
	public String imageurl;
	@DatabaseField(columnName = "webicon")
	public String webicon;
	@DatabaseField(columnName = "webname")
	public String webname;
	public void set(EssenceModel model){
		id = model.id;
		new_type = model.new_type;
		title = model.title;
		update_time = model.update_time;
		abstracts = model.abstracts;
		url = model.url;
		imageurl = model.imageurl;
		webicon = model.webicon;
		webname = model.webname;
	}
}
