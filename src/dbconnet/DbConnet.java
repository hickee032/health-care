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

	// �����ͺ��̽� ���� ��й�ȣ
	private Connection conn; // DB Ŀ�ؼ�(����) ��ü
	private static final String USERNAME = "root"; // DB ���ӽ� ID
	private static final String PASSWORD = "1396"; // DB ���ӽ� �н�����
	// DB���� ��� ���� ���࿡ Timezone exception �߻��ÿ��� ?serverTimezone=UTC �߰�
	private static final String URL = "jdbc:mysql://localhost:3307/healthcare?serverTimezone=UTC";

	public DbConnet() {
		// connection��ü�� �����ؼ� DB�� ������.
		try {
			System.out.println("������");
			// ���� ��ü�� �������
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("����̹� �ε� ����!!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����̹� �ε� ����!!");
		}
	}

//UserTbl//////////////////////////////////////////////////////////////////////////////////////////////////
	// ȸ������
	public void insertUserTbl(String id, String name, String pw) {
		// ������ �غ�
		String sql = "insert into UserTbl values(?,?,?,default);";

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // ù ��° ? ����
			pstmt.setString(2, name); // �� ��° ? ����
			pstmt.setString(3, pw);// �� ��° ? ����
			// ������ �����϶�.
			pstmt.executeUpdate();
			System.out.println("������ ���� ����!");
		} catch (SQLException e) {
			System.out.println("������ ���� ����!");
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

	// ���̵� �ߺ��˻� -- ���Խ�
	// ���̵� �ߺ� ���� �Ű����� string -> label -> permit -> btn
	public void selectUserTbl(String id, Label label, Boolean permit, Button btn) {
		String sql = "select * from UserTbl where Uid = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // ������ ���� ������ true, ������ false
				permit = false;
				String setstr = "�����ϴ� ���̵� �Դϴ�.";
				label.setText(setstr);

			} else {
				permit = true;
				String setstr = "��� ������ ���̵� �Դϴ�.";
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

	// �α��ν� id pw name weight height ������
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
	// userInformation table ���� weight height �� ������
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

			while (re.next()) { // �����ð� �ִ���?
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
			// pstmt.setString(2, id); //and ������ ���� ������ �߰��Ѵ�.
			ResultSet rs = pstmt.executeQuery();
			// select�� ����� ResultSet�� ��� ���ϵȴ�.
			if (rs.next()) { // ������ ���� ������ true, ������ false
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

			while (re.next()) { // �����ð� �ִ���?
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

			while (re.next()) { // �����ð� �ִ���?
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
