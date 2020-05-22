package practice;

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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class BooksSearchServlet
 */
@WebServlet("/BooksSearchServlet")
public class BooksSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=UTF-8");
		//セッション情報の取得

		 HttpSession session = request.getSession(true);
		//String loginStatus = (String) session.getAttribute("login");
		String role = (String) session.getAttribute("userRole");

		System.out.println(role);

		//検索情報の入力値
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String genre = request.getParameter("genre");

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
				String sql = "select \n" +
						"	bo.BOOK_ID, \n" +
						"	bo.AUTHOR, \n" +
						"	bo.TITLE, \n" +
						"	bo.PUBLISHER, \n" +
						"	bo.STATUS, \n" +
						"	bo.GENRE, \n" +
						"   bo.REND_DATA \n" +
						"from  \n" +
						"	MS_BOOKS bo \n" +
						"where \n" +
						"	1=1 \n" ;
						if(!title.equals("")){
							sql += " AND  bo.TITLE like '%"+title+"%'";
						}
						if(!author.equals("")){
								sql += "AND bo.AUTHOR like '%"+author+"%'";
						}

						if(!genre.equals("") && !genre.equals(null)){
								sql += " AND bo.GENRE = '"+genre+"'";
						}

						if(role.equals("MANAGER")){
							String frequency = request.getParameter("frequency");
							if(frequency.equals("降順")){
								sql += "order by REND_DATA desc" ;
							}else{
								sql+= "order by REND_DATA asc" ;
							}
						}else if(role.equals("MEMBER")){

							sql += "order by BOOK_ID" ;
						}


				System.out.println(sql);

				List<BooksInfo> booksList = new ArrayList<>();

				// エラーが発生するかもしれない処理はtry-catchで囲みます
				// この場合はDBサーバへの接続に失敗する可能性があります
				try (
						// データベースへ接続します
						Connection con = DriverManager.getConnection(url, user, pass);

						// SQLの命令文を実行するための準備をおこないます
						Statement stmt = con.createStatement();

						// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
						ResultSet rs1 = stmt.executeQuery(sql);
						// SQL実行後の処理内容
					) {


					while(rs1.next()) {

						BooksInfo books = new BooksInfo();

						books.setBookId(rs1.getInt("BOOK_ID"));
						books.setTitle(rs1.getString("TITLE"));
						books.setAuthor(rs1.getString("AUTHOR"));
						books.setPublisher(rs1.getString("PUBLISHER"));
						books.setStatus(rs1.getString("STATUS"));

						booksList.add(books);
					}


				} catch (Exception e) {
					throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
				}
					// アクセスした人に応答するためのJSONを用意する
					PrintWriter pw = response.getWriter();
				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString(booksList));
			}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
