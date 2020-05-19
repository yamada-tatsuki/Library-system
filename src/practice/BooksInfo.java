package practice;

import java.io.Serializable;

public class BooksInfo implements Serializable {
	private int bookId;
	private String boughtOn;
	private String boughtBy;
	private String author;
	private String title;
	private String publisher;
	private String genre;
	private int numberBooks;
	private String status;
	private int rend_data;
//書籍ID
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
//書籍購入日
	public String getBoughtOn() {
		return boughtOn;
	}
	public void setBoughtOn(String boughtOn) {
		this.boughtOn = boughtOn;
	}
//書籍購入者
	public String getBoughtBy() {
		return boughtBy;
	}
	public void setBoughtBy(String boughtBy) {
		this.boughtBy = boughtBy;
	}
//作者
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
//書籍タイトル
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//出版社
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
//書籍ジャンル
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
//書籍の冊数
	public int getNumberBooks() {
		return numberBooks;
	}
	public void setNumberBooks(int numberBooks) {
		this.numberBooks = numberBooks;
	}
//ステータス（貸出状況）
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//貸し出され回数
	public int getRend_data() {
		return rend_data;
	}
	public void setRend_data(int rend_data) {
		this.rend_data = rend_data;
	}



}
