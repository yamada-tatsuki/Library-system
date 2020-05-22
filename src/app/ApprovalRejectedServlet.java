package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ApprovalRejectedServlet
 */
@WebServlet("/ApprovalRejectedServlet")
public class ApprovalRejectedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalRejectedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String result = request.getParameter("result");
System.out.println(result);
		// JDBCドライバの準備
		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "wc";
		String pass = "wc";
		String sql ="";
		// 実行するSQL文
		if(result.equals("rejected")){
		 sql +="UPDATE TR_REQUEST_BOOKS SET STATUS ='却下' where 1=1 and TITLE='"+title+"' ";
		}else{
			 sql +="UPDATE TR_REQUEST_BOOKS SET STATUS ='承認' where 1=1 and TITLE='"+title+"' ";
		}
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();

		) {
			// SQLの命令文を実行し、その件数を代入
			int updateNum = stmt.executeUpdate(sql);
			System.out.println(updateNum);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		// 画面へレスポンスを返却する処理
		PrintWriter pw = response.getWriter();
		// 受注情報リスト（orderList）をJSON型にして返却
		pw.append(new ObjectMapper().writeValueAsString("ok"));//
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
