package app;

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
 * Servlet implementation class MypageServlet
 */
@WebServlet("/MypageServlet")
public class MypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    	//String itemEmployeesId= request.getParameter("itemEmployeesId");
    	HttpSession session = request.getSession(true);
    	String itemEmployeesId=(String)session.getAttribute("empId");


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
    											"MB.TITLE, \n" +
    											"MB.AUTHOR, \n" +
    											"MB.GENRE, \n" +
    											"MB.PUBLISHER, \n" +
    											"TR.DEADLINE, \n" +
    											"MB.BOOK_ID \n" +
    											"from \n" +
    											"MS_BOOKS MB, \n" +
    											"TR_RENTALS TR, \n" +
    											"MS_EMPLOYEES ME \n" +
    											"where 1=1 \n" +
    											"and ME.EMPLOYEE_ID=TR.EMPLOYEE_ID \n" +
    											"and TR.BOOK_ID=MB.BOOK_ID \n" +
    											"and ME.EMPLOYEE_ID='"+itemEmployeesId+"' \n");){


    			List<Rental> RentalList=new ArrayList<Rental>();

    			while(rs1.next()) {
    				Rental rental = new Rental();

    				rental.setTitle(rs1.getString("TITLE"));
    				rental.setAuthor(rs1.getString("AUTHOR"));
    				rental.setGenre(rs1.getString("GENRE"));
    				rental.setPublisher(rs1.getString("PUBLISHER"));
    				rental.setDeadline(rs1.getString("DEADLINE"));
    				rental.setBookId(rs1.getString("BOOK_ID"));


    				RentalList.add(rental);
    			}



    			// アクセスした人に応答するためのJSONを用意する


    			// JSONで出力する
    			PrintWriter pw = response.getWriter();

    			pw.append(new ObjectMapper().writeValueAsString(RentalList));

    		} catch (Exception e) {
    			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
    		}

    		}

    		// -- ここまで --


    	@Override
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    		// TODO 任意機能「趣味投稿機能に挑戦する場合はこちらを利用して下さい」

    		// -- ここまで --
    	}

    }

