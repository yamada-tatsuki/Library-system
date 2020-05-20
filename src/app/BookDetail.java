package app;

public class BookDetail {
	public BookDetail() {
		super();
	}

	/** BOOKID */
	private String bookid;

	/** 購入日 */
	private String boughton;

	/** 購入者 */
	private String boughtby;

	/** 作者 */
	private String author;

	/** タイトル */
	private String title;

	/** 出版社 */
	private String publisher;

	/** ジャンル */
	private String genre;

	/** 冊数 */
	private String numberbooks;

	/** ステータス */
	private String status;

	/** 貸し出され回数 */
	private String renddata;


	public String getBookId() {
		return bookid;
	}

	public void setBookId(String bookid) {
		this.bookid = bookid;
	}

	public String getBoughtOn() {
		return boughton;
	}

	public void setBoughtOn(String boughton) {
		this.boughton = boughton;
	}

	public String getBoughtBy() {
		return boughtby;
	}

	public void setBoughtBy(String boughtby) {
		this.boughtby = boughtby;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getNumberBooks() {
		return numberbooks;
	}

	public void setNumberBooks(String numberbooks) {
		this.numberbooks = numberbooks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRendData() {
		return renddata;
	}

	public void setRendData(String renddata) {
		this.renddata = renddata;
	}



	@Override
	public String toString() {
		return "BookDetail [bookid=" + bookid + ", boughton=" + boughton +  ", boughtby=" + boughtby +  ""
				+ ", author=" + author +  ", title=" + title +  ", publisher=" + publisher +  ""
						+ ", genre=" + genre +  ", numberbooks=" + numberbooks +  ", status=" + status +  ""
								+ ", renddata=" + renddata +  "]";
	}

}
