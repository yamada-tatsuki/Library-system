package app;

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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ReturnServlet
 */
@WebServlet("/ReturnServlet")
public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		//String itemTitle = request.getParameter("itemTitle");
		//String itemAuthor = request.getParameter("itemAuthor");
		String bookId = request.getParameter("bookId");
=======
		String itemTitle = request.getParameter("itemTitle");
		String itemAuthor = request.getParameter("itemAuthor");
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628





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
<<<<<<< HEAD
=======
		String sql ="delete from TR_RENTALS \n" +
				"where \n" +
				"TITLE='"+itemTitle+"'" ;
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628


		boolean result=true;

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
			) {
			// SQLの命令文を実行し、その件数をint型のresultCountに代入します
<<<<<<< HEAD
			String sql ="delete from TR_RENTALS \n" +
					"where \n" +
					"BOOK_ID='"+bookId+"'" ;
			int resultCount = stmt.executeUpdate(sql);


=======
			int resultCount = stmt.executeUpdate(sql);
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628
			if(resultCount!=1){
				result=false;
			}
			ResultSet rs1 = stmt.executeQuery(
					"select \n" +
							"NUMBER_BOOKS \n" +
							"from \n" +
							"MS_BOOKS \n" +
							"where 1=1 \n" +
<<<<<<< HEAD
							"and BOOK_ID='"+bookId+"' \n");

=======
							"and TITLE='"+itemTitle+"' \n" +
							"and AUTHOR='"+itemAuthor+"' \n");
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628
			int numberOfBooks=0;

			while(rs1.next()) {
				numberOfBooks=rs1.getInt("NUMBER_BOOKS");
			}
<<<<<<< HEAD

			int resultCount2= stmt.executeUpdate(
=======
			ResultSet rs2 = stmt.executeQuery(
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628
					"UPDATE MS_BOOKS \n" +
							"SET NUMBER_BOOKS ='"+numberOfBooks+"'+1, \n" +
							" STATUS='貸出可' \n" +
							"WHERE 1=1 \n" +
<<<<<<< HEAD
							"and BOOK_ID='"+bookId+"' \n" );
			System.out.println(resultCount2);
=======
							"and TITLE='\"+itemTitle+\"' \n" +
							"and AUTHOR='\"+itemAuthor+\"' \n" );
>>>>>>> 66b89afa87054db97d7a22b86c5807db0049a628

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}