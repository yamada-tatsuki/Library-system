package app;

import java.io.Serializable;

public class Approval implements Serializable {

	public  Approval() {
		// 何もしない
	}

	// Shaincod
	private String title;
	// なまえ
	private String name;
	private String id;





	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}





}