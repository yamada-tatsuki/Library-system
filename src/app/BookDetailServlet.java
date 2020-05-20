package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.BookDetail;

@WebServlet("/api/bookdetail")
public class BookDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// アクセス元のHTMLでｑに設定された値を取得して、String型の変数idに代入

		String title = request.getParameter("title");
		System.out.println("title="+title);

		String sql = " select " + " BOOK_ID , " + " BOUGHT_ON , " + " BOUGHT_BY , " + " AUTHOR , "  + " TITLE , " + " PUBLISHER , " + " GENRE , " +
		" NUMBER_BOOKS , " + " STATUS , " + " REND_DATA " + " from " + " MS_BOOKS " +  " where 1=1 " + " and TITLE = '" + title + "' ";

		List<BookDetail> list = new ArrayList<>();

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "wc", "wc");

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();

				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);){


			while (rs1.next()) {
				BookDetail book = new BookDetail();

				book.setBookId(rs1.getString("BOOK_ID"));
				book.setBoughtOn(rs1.getString("BOUGHT_ON"));
				book.setBoughtBy(rs1.getString("BOUGHT_BY"));
				book.setAuthor(rs1.getString("AUTHOR"));
				book.setTitle(rs1.getString("TITLE"));
				book.setPublisher(rs1.getString("PUBLISHER"));
				book.setGenre(rs1.getString("GENRE"));
				book.setNumberBooks(rs1.getString("NUMBER_BOOKS"));
				book.setStatus(rs1.getString("STATUS"));
				book.setRendData(rs1.getString("REND_DATA"));

				list.add(book);
			}

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(list));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		doGet(request ,response);
	}

}

