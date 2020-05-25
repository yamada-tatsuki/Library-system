package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginLogoutServlet
 */
@WebServlet("/LoginLogoutServlet")
public class LoginLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginLogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(true);
		String status = (String) session.getAttribute("empId");
		System.out.println(status);
		String loginRequest = request.getParameter("loginRequest");
		PrintWriter pw = response.getWriter();
		Map<String, String> responseData = new HashMap<>();
		if (status == null) {
			System.out.println("ログインして");
			// pw.append(new ObjectMapper().writeValueAsString("ログインして下さい。"));
			responseData.put("result", "ok");

		} else {
			if (loginRequest != null && loginRequest.equals("logout")) {
				session.removeAttribute("empId");
				session.removeAttribute("userRole");
				System.out.println("ログアウト完");
				// pw.append(new ObjectMapper().writeValueAsString("ログアウト完了。"));
				responseData.put("result", "ng");
			}

		}
		pw.append(new ObjectMapper().writeValueAsString(responseData));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 入力されたユーザーIDとパスワードを取得
				String empId = request.getParameter("empId");
				String password = request.getParameter("password");

				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();
				System.out.println(empId + password);
				response.setContentType("text/html;charset=UTF-8");


				Map<String, String> responseData = new HashMap<>();

				// JDBCドライバの準備
				try {

					// JDBCドライバのロード
					Class.forName("oracle.jdbc.driver.OracleDriver");

				} catch (ClassNotFoundException e) {
					// ドライバが設定されていない場合はエラーになります
					throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
				}

				// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
				String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";

				String dbUser = "wc";
				String dbPass = "wc";
				String sql = "select EMPLOYEE_ID,PASS,ROLE from MS_EMPLOYEES where 1=1 and EMPLOYEE_ID='"+empId+"' and PASS='"+password+"'";

				// DBへ接続してSQLを実行
				try (
						// データベースへ接続します
						Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

						// SQLの命令文を実行するための準備をおこないます
						// Statement stmt = con.createStatement();
						Statement stmt = con.createStatement();

						// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
						// ResultSet rs1 = stmt.executeQuery(sql);
						ResultSet rs1 = stmt.executeQuery(sql);

				) {
					// SQLの取得結果がある時（ユーザIDとパスワードが一致しているユーザーがいる）は「ok」という文字列を画面に返却
					// そうでないときは「ng」を返却
					// 返却データを作成

					if (rs1.next()) {
						HttpSession session = request.getSession(true);
						String userRole = rs1.getString("ROLE");
						System.out.println(userRole);
						session.setAttribute("empId", rs1.getString("EMPLOYEE_ID"));
						session.setAttribute("userRole", userRole);

						System.out.println("ログインしました");

						responseData.put("result", "ok");
						responseData.put("userRole", userRole);
						responseData.put("empId", empId);
						System.out.println(responseData);
					} else {
						System.out.println("パスワードまたはユーザー名が違います");
						responseData.put("result", "ng");

					}
					pw.append(new ObjectMapper().writeValueAsString(responseData));

				} catch (Exception e) {
					throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
				}

			}
	}


