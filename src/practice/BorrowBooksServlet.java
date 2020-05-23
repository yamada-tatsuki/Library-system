
package practice;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Servlet implementation class BorrowBooksServlet
 */
@WebServlet("/BorrowBooksServlet")
public class BorrowBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBooksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//session情報
		 HttpSession session = request.getSession(true);
		String employeeId = (String) session.getAttribute("empId");
		String bookId = request.getParameter("bookId");
		String today = request.getParameter("today");
		String dueDate = request.getParameter("dueDate");
		int numberOfBooks;
		String bookTitle;
		String status;
		PrintWriter pw = response.getWriter();
		// JDBCドライバの準備
		try {
		    // JDBCドライバのロード
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
		    // ドライバが設定されていない場合はエラーになります
		    throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "wc";
		String pass = "wc";
		// 実行するSQL文
		String sql ="select \n" +
				"	bo.NUMBER_BOOKS, \n" +
				"	bo.TITLE, \n" +
				"	bo.STATUS \n" +
				"from \n" +
				"	MS_BOOKS bo \n" +
				"where \n" +
				"	1=1 \n" +
				"	and bo.BOOK_ID = '"+bookId+"' \n"
		;
		System.out.println(sql);
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
				// SQLの命令文を実行し、その結果をResultSetのrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行後の処理内容
			rs1.next();
				 numberOfBooks = rs1.getInt("NUMBER_BOOKS");
			     bookTitle = rs1.getString("TITLE");
			     status = rs1.getString("STATUS");
			}
		 catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		String sql3 = "";
		if(numberOfBooks != 0){
			sql3 += "insert into TR_RENTALS \n" +
					"(EMPLOYEE_ID, BOOK_ID, REND_ON, DEADLINE, TITLE) \n" +
					"values('"+employeeId+"', '"+bookId+"', '"+today+"', '"+dueDate+"', '"+bookTitle+"')";
		}else{
			sql3 += "select \n" +
					"	re.TITLE \n" +
					"from \n" +
					"	TR_RENTALS re \n" ;
		}
		System.out.println(sql3);
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
				// SQLの命令文を実行し、その結果をResultSetのrsに代入します
				) {
			// SQL実行後の処理内容
			int resultCount = stmt.executeUpdate(sql3);
			System.out.println(resultCount);
			}
		 catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
			String sql2 = "";
			if(numberOfBooks != 0){
					sql2 += "update \n" +
							"	MS_BOOKS bo \n" +
							"set \n" +
							"	bo.NUMBER_BOOKS = bo.NUMBER_BOOKS - 1 , \n" +
							"	bo.REND_DATA = bo.REND_DATA + 1 \n" +
							"where \n" +
							"	bo.BOOK_ID = '"+bookId+"' \n";
			}else{
				sql2 += "select \n" +
						"	re.TITLE \n" +
						"from \n" +
						"	TR_RENTALS re \n" ;
			}
			System.out.println(sql2);
			// エラーが発生するかもしれない処理はtry-catchで囲みます
			// この場合はDBサーバへの接続に失敗する可能性があります
			try (
					// データベースへ接続します
					Connection con = DriverManager.getConnection(url, user, pass);
					// SQLの命令文を実行するための準備をおこないます
					Statement stmt = con.createStatement();
					// SQLの命令文を実行し、その結果をResultSetのrsに代入します
					) {
				// SQL実行後の処理内容
				int resultCount = stmt.executeUpdate(sql2);
				System.out.println(resultCount);
				}
			 catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}
			String sql4 ="select \n" +
					"	bo.NUMBER_BOOKS, \n" +
					"	bo.TITLE, \n" +
					"	bo.STATUS \n" +
					"from \n" +
					"	MS_BOOKS bo \n" +
					"where \n" +
					"	1=1 \n" +
					"	and bo.BOOK_ID = '"+bookId+"' \n"
			;
			System.out.println(sql4);
			// エラーが発生するかもしれない処理はtry-catchで囲みます
			// この場合はDBサーバへの接続に失敗する可能性があります
			try (
					// データベースへ接続します
					Connection con = DriverManager.getConnection(url, user, pass);
					// SQLの命令文を実行するための準備をおこないます
					Statement stmt = con.createStatement();
					// SQLの命令文を実行し、その結果をResultSetのrsに代入します
					ResultSet rs1 = stmt.executeQuery(sql4);) {
				// SQL実行後の処理内容
				rs1.next();
					 numberOfBooks = rs1.getInt("NUMBER_BOOKS");
				     bookTitle = rs1.getString("TITLE");
				     status = rs1.getString("STATUS");
				}
			 catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}
			String sql5 = "";
			if(numberOfBooks == 0){
					sql5 += "update  \n" +
							"	MS_BOOKS bo \n" +
							"set \n" +
							"	bo.STATUS = '貸出中' \n" +
							"where \n" +
							"	bo.BOOK_ID = '"+bookId+"' \n" ;
			}else{
				sql5 += "select \n" +
						"	re.TITLE \n" +
						"from \n" +
						"	TR_RENTALS re \n" ;
			}
			System.out.println(sql5);
			// エラーが発生するかもしれない処理はtry-catchで囲みます
			// この場合はDBサーバへの接続に失敗する可能性があります
			try (
					// データベースへ接続します
					Connection con = DriverManager.getConnection(url, user, pass);
					// SQLの命令文を実行するための準備をおこないます
					Statement stmt = con.createStatement();
					// SQLの命令文を実行し、その結果をResultSetのrsに代入します
					) {
				// SQL実行後の処理内容
				int resultCount = stmt.executeUpdate(sql5);
				System.out.println(resultCount);
				}
			 catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}
		// アクセスした人に応答するためのJSONを用意する
		// JSONで出力する
		if(status.equals("貸出可")){
			pw.append(new ObjectMapper().writeValueAsString("貸出可"));
		}else if(status.equals("貸出中")){
			pw.append(new ObjectMapper().writeValueAsString("貸出中"));
		}
	}
}

