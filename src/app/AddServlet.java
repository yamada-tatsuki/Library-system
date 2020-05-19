package app;

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
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ResultSet = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    	String itemTitle = request.getParameter("itemTitle");
		String itemAuthor = request.getParameter("itemAuthor");
		String itemBoughtOn = request.getParameter("itemBoughtOn");
		String itemBoughtBy = request.getParameter("itemBoughtBy");
		String itemPublisher = request.getParameter("itemPublisher");
		String itemGenre = request.getParameter("itemGenre");
		String itemNumberBooks = request.getParameter("itemNumberBooks");



		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
			}

		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "wc";
		String pass = "wc";


					try (
							// データベースへ接続します
							Connection con = DriverManager.getConnection(url, user, pass);

							// SQLの命令文を実行するための準備をおこないます
							Statement stmt = con.createStatement();

							// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
							ResultSet rs1 = stmt.executeQuery(
									"select \n" +
											"NUMBER_BOOKS \n" +
											"from \n" +
											"MS_BOOKS \n" +
											"where 1=1 \n" +
											"and TITLE='"+itemTitle+"' \n" +
											"and AUTHOR='"+itemAuthor+"' \n");){
						System.out.println(rs1);


			int numberOfBooks=0;

			while(rs1.next()) {
				numberOfBooks=rs1.getInt("NUMBER_BOOKS");

			}
			if(numberOfBooks==0){


			ResultSet rs2 = stmt.executeQuery(
					"insert into MS_BOOKS \n" +
							"(BOUGHT_ON, BOUGHT_BY, AUTHOR, TITLE, PUBLISHER, GENRE,NUMBER_BOOKS, STATUS, REND_DATA) \n" +
							"values('"+itemBoughtOn+"', '"+itemBoughtBy+"', '"+itemAuthor+"', '"+itemTitle+"', '"+itemPublisher+"', '"+itemGenre+"','1', '貸出可', '0') \n");



			}
			else if (numberOfBooks!=0){
				ResultSet rs3 = stmt.executeQuery(
								"UPDATE MS_BOOKS  \n" +
										"SET NUMBER_BOOKS =numberOfBooks+1 \n" +
										"WHERE 1=1 \n" +
										"and TITLE='"+itemTitle+"' \n" +
										"and AUTHOR='"+itemAuthor+"' \n");

			}
			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(numberOfBooks));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}



		// -- ここまで --
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO 任意機能「趣味投稿機能に挑戦する場合はこちらを利用して下さい」

		// -- ここまで --
	}

}
