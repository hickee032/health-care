package dbconnet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import views.MainController;

public class DbConnet extends MainController {

	// 데이터베이스 계정 비밀번호
	private Connection conn; // DB 커넥션(연결) 객체
	private static final String USERNAME = "root"; // DB 접속시 ID
	private static final String PASSWORD = "1396"; // DB 접속시 패스워드
	// DB접속 경로 설정 만약에 Timezone exception 발생시에는 ?serverTimezone=UTC 추가
	private static final String URL = "jdbc:mysql://localhost:3307/healthcare?serverTimezone=UTC";

	public DbConnet() {
		// connection객체를 생성해서 DB에 연결함.
		try {
			System.out.println("생성자");
			// 동적 객체를 만들어줌
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("드라이버 로딩 성공!!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패!!");
		}
	}

//UserTbl//////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입
	public void insertUserTbl(String id, String name, String pw) {
		// 쿼리문 준비
		String sql = "insert into UserTbl values(?,?,?,default);";

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // 첫 번째 ? 매핑
			pstmt.setString(2, name); // 두 번째 ? 매핑
			pstmt.setString(3, pw);// 세 번째 ? 매핑
			// 쿼리문 실행하라.
			pstmt.executeUpdate();
			System.out.println("데이터 삽입 성공!");
		} catch (SQLException e) {
			System.out.println("데이터 삽입 실패!");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 아이디 중복검사 -- 가입시
	// 아이디 중복 여부 매개변수 string -> label -> permit -> btn
	public void selectUserTbl(String id, Label label, Boolean permit, Button btn) {
		String sql = "select * from UserTbl where Uid = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 가져올 행이 있으면 true, 없으면 false
				permit = false;
				String setstr = "존재하는 아이디 입니다.";
				label.setText(setstr);

			} else {
				permit = true;
				String setstr = "사용 가능한 아이디 입니다.";
				label.setText(setstr);

			}

			if (permit) {
				btn.setVisible(true);
			} else {
				btn.setVisible(false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 로그인시 id pw name weight height 가져옴
	public boolean LoginDbUserTbl(String id, String pw) {
		String sql = "select Uid,Uname,Upw,initdate from UserTbl where Uid =" + "'" + id + "';";

		String DBid = null;
		String DBpw = null;
		String DBname = null;
		Date DBdate = null;

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				DBid = rs.getString("Uid");
				DBpw = rs.getString("Upw");
				DBname = rs.getString("Uname");
				DBdate = rs.getDate("initdate");

				System.out.println("id = " + DBid + ",DBpw = " + DBpw + ",DBname = " + DBname + ",DBdate = " + DBdate);
				if (!(DBid.equals(id)) || !(pw.equals(DBpw))) {
					return false;
				} else {
					main.MainApp.setUid(DBid);
					main.MainApp.setUpw(DBpw);
					main.MainApp.setUname(DBname);
					main.MainApp.setUinidate(DBdate);

					selectUserInfoWeightHeight(DBid);

					System.out.println("stid = " + main.MainApp.getUid() + ",stname = " + main.MainApp.getUname()
							+ ",stdate = " + main.MainApp.getUinidate());
					System.out.println("weight" + main.MainApp.getUweight() + "height" + main.MainApp.getUheight()
							+ "bmi" + main.MainApp.getUbmi());
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

//userInformation//////////////////////////////////////////////////////////////////////////////////////////////////
	// userInformation table 에서 weight height 를 가져옴
	public void selectUserInfoWeightHeight(String id) {
		String sql = "select Uweight,Uheight,Ubmi from userInformation where Userid = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				main.MainApp.setUweight(rs.getDouble("Uweight"));
				main.MainApp.setUheight(rs.getDouble("Uheight"));
				main.MainApp.setUbmi(rs.getDouble("Ubmi"));
			} else {
				main.MainApp.setUweight(0.0);
				main.MainApp.setUheight(0.0);
				main.MainApp.setUbmi(0.0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(String id, Double weight, Double height, Double bmi) {
		// String sql = "UPDATE userInformation set Uweight = "+weight+",Uheight =
		// "+height+",Ubmi = "+bmi+"where Userid = ?;";
		String sql = "update userInformation set Uweight=?, Uheight=?, Ubmi = ? where Userid = ?;";

		// String sql = "update student set name=?, grade=? where id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, weight);
			pstmt.setDouble(2, height);
			pstmt.setDouble(3, bmi);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> selectdate(String Uid, String table) {
		String sql = "select Uinidate from " + table + " where Userid = ?;";
		PreparedStatement pstmt = null;
		List<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Uid);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) { // 가져올게 있느냐?
				String date = re.getString("Uinidate");
				// String result = date.substring(date.lastIndexOf(" ") + 1);
				String result = date.substring(0, date.indexOf(" "));
				list.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int selectdateAmount(String table, String colum, String date) {
		int AmountThing = 0;
		String sql = "select " + colum + " from " + table + " where Uinidate = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			// pstmt.setString(2, id); //and 조건이 붙을 때마다 추가한다.
			ResultSet rs = pstmt.executeQuery();
			// select한 결과는 ResultSet에 담겨 리턴된다.
			if (rs.next()) { // 가져올 행이 있으면 true, 없으면 false
				AmountThing = rs.getInt(colum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return AmountThing;
	}

	public List<String> GraphDate(String Uid, String table) {

		String sql = "select Uinidate from " + table + " where Uinidate >= (curdate()-interval 30 day);";
		PreparedStatement pstmt = null;

		List<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Uid);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) { // 가져올게 있느냐?
				String date = re.getString("Uinidate");
				// String result = date.substring(date.lastIndexOf(" ") + 1);
				String result = date.substring(0, date.indexOf(" "));
				list.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<String> GraphThing(String Uid, String table, String thing) {

		String sql = "select " + thing + " from " + table + " where Uinidate >= (curdate()-interval 30 day);";
		PreparedStatement pstmt = null;

		List<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Uid);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) { // 가져올게 있느냐?
				String date = re.getString("Uinidate");
				// String result = date.substring(date.lastIndexOf(" ") + 1);
				String result = date.substring(0, date.indexOf(" "));
				list.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
