
package app;
import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class SessionJudgServlet
 */
@WebServlet("/SessionJudgServlet")
public class SessionJudgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SessionJudgServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(true);
		String status = (String) session.getAttribute("empId");
		System.out.println(status);
		Map<String, Object> responseData = new HashMap<>();
		if (status == null) {
			responseData.put("result", "no");
			System.out.println("dousite");
		} else {
			System.out.println("わからん");
		}
		// 画面へレスポンスを返却する処理
		PrintWriter pw = response.getWriter();
		// 受注情報リスト（orderList）をJSON型にして返却
		pw.append(new ObjectMapper().writeValueAsString(responseData));//
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}