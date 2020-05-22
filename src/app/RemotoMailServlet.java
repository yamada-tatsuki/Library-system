package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import practice.RemoteMail;

/**
 * Servlet implementation class RemotoMailServlet
 */
@WebServlet("/RemotoMailServlet")
public class RemotoMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemotoMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(cal.getTime());
		System.out.println(strDate);
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
						"select r.TITLE,e.MAIL_ADDRESS from TR_RENTALS r, MS_EMPLOYEES e where 1=1 and r.EMPLOYEE_ID = e.EMPLOYEE_ID and DEADLINE < '"
								+ strDate + "'");) {
			System.out.println(rs1);

			// list型
			// List<String> emplist = new ArrayList<String>();
			Map<String, List<String>> map = new HashMap<>();

			while (rs1.next()) {
				List<String> books;
				String mailAddress = rs1.getString("MAIL_ADDRESS");
				if (map.get(mailAddress) == null) {
					// 1冊目
					books = new ArrayList<String>();
				} else {
					// 2冊目以降
					books = map.get(mailAddress);
				}
				books.add(rs1.getString("TITLE"));
				map.put(mailAddress, books);

				// // sendMainlのtoを書き換える
				// sendMail.setTo("zidongmeru@gmail.com");
				// sendMail.send("Commons Email テストメール", "これはテストです。");

			}
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				RemoteMail sendMail = new RemoteMail();
				sendMail.setTo(entry.getKey());
				String body = "あなたは以下の書籍を借りています。";
				List<String> list = entry.getValue();
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.size());
					System.out.println(list.get(i) + ",");
					if (list.size() < 2) {

						body += ""+System.lineSeparator()+"・" + list.get(i) + "\n";
					} else {

						body += "" +System.lineSeparator()+"・" +list.get(i) + "\n";
					}
				}
				body += "返却期限が切れているので至急返してください。" + System.lineSeparator();

				sendMail.send("返却お願いします", body);

			}

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(map));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// -- ここまで --
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
