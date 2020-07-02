import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/*
 * author: Alp Furkan ÜRKMEZ
 * Simple Library Database User Program
 * last edited: 01.02.2019 by Alp Furkan ÜRKMEZ
 */

public class Demo {

	static String myDriver;
	static String db;
	static Connection conn;
	static Statement st;
	static Scanner keyboard;

	public static void main(String[] args) {

		keyboard = new Scanner(System.in);

		try {
			myDriver = "com.mysql.jdbc.Driver"; // connecting to database
			db = "jdbc:mysql://localhost/project";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(db, "root", "tobb1234");
			st = conn.createStatement();
			int sel = 0;
			do {
				System.out.println("Welcome to Library Database !\n" + "Admin --> Press 1 !\n"
						+ "Student --> Press 2 !\nExit --> Press 0 !\n");
				sel = keyboard.nextInt();
				String selection = "";
				int selectedProcess = 0;
				if (sel == 1) {
					System.out.print("password: ");
					String password = keyboard.next();
					if (password.equals("tobb1234")) {
						System.out.println(
								"Select a process which you want to do !\nDelete --> Press 1\nInsert --> Press 2\nExit --> Press 0");
						selectedProcess = keyboard.nextInt();
						do {
							System.out.println(
									"Write table name which you want to modify it ! (copy, person, university, city, tel, writer, student, regular, exchange, book, library)");
							selection = keyboard.next().toLowerCase();
							switch (selection) {
							case "copy":
								copyInsertDelete(selectedProcess);
								break;
							case "person":
								personInsertDelete(selectedProcess);
								break;
							case "university":
								universityInsertDelete(selectedProcess);
								break;
							case "city":
								cityInsertDelete(selectedProcess);
								break;
							case "tel":
								telInsertDelete(selectedProcess);
								break;
							case "writer":
								writerInsertDelete(selectedProcess);
								break;
							case "student":
								studentInsertDelete(selectedProcess);
								break;
							case "regular":
								regularInsertDelete(selectedProcess);
								break;
							case "exchange":
								exchangeInsertDelete(selectedProcess);
								break;
							case "book":
								bookInsertDelete(selectedProcess);
								break;
							case "library":
								libraryInsertDelete(selectedProcess);
								break;
							}
							System.out.println(
									"Select a process which you want to do !\nDelete --> Press 1\nInsert --> Press 2\nExit --> Press 0");
							selectedProcess = keyboard.nextInt();
						} while (selectedProcess != 0);

					} else {
						System.out.println("Wrong password !");
					}
				} else if (sel == 2) {
					do {
						System.out.print(
								"List of all copies --> Press 1\nSpecial query --> Press 2\nExit --> Press 0 !\n");
						selectedProcess = keyboard.nextInt();
						if (selectedProcess == 1) {
							listAll();
						} else if (selectedProcess == 2) {
							specialQuery();
						}
					} while (selectedProcess != 0);
				}
			} while (sel != 0);
			System.out.println("Bye !");
			st.close();
		} catch (Exception e) {
			System.err.println("Error ! ");
			System.err.println(e.getMessage());
		}
	}

	static void libraryInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from library where (library_id) = (?)");
			System.out.print("\nInformatios of library\nlibrary id: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into library values (?, ?, ?, ?)");
			System.out.print("\nInformatios of library\nlibrary id: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("capacity: ");
			ps.setInt(2, keyboard.nextInt());
			System.out.print("number of books: ");
			ps.setInt(3, keyboard.nextInt());
			System.out.print("university id: ");
			ps.setInt(4, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void bookInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from book where (isbn, name, fk_writer) = (?, ?, ?)");
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into book values (?, ?, ?)");
			break;
		}

		System.out.print("\nInformatios of book\nISBN: ");
		ps.setInt(1, keyboard.nextInt());
		System.out.print("name: ");
		ps.setString(2, keyboard.next());
		System.out.print("writer id: ");
		ps.setInt(3, keyboard.nextInt());

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void exchangeInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement(
					"delete from exchange where (durationOfStay, fk_student_id, fk_university_id) = (?, ?, ?)");
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into exchange values (?, ?, ?)");
			break;
		}

		System.out.print("\nInformatios of exchange\nduration of stay: ");
		ps.setInt(1, keyboard.nextInt());
		System.out.print("student id: ");
		ps.setInt(2, keyboard.nextInt());
		System.out.print("university id: ");
		ps.setInt(3, keyboard.nextInt());

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void regularInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement(
					"delete from regular where (yearOfStudy, fk_student_id, fk_university_id) = (?, ?, ?)");
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into regular values (?, ?, ?)");
			break;
		}

		System.out.print("\nInformatios of regular\nyear of study: ");
		ps.setInt(1, keyboard.nextInt());
		System.out.print("student id: ");
		ps.setInt(2, keyboard.nextInt());
		System.out.print("university id: ");
		ps.setInt(3, keyboard.nextInt());

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void studentInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from student where (fk_ssn) = (?)");
			System.out.print("\nInformatios of student\nssn: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into student values (?, ?, ?, ?)");
			System.out.print("\nInformatios of student\nstudent id: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("department: ");
			ps.setString(2, keyboard.next());
			System.out.print("university id: ");
			ps.setInt(3, keyboard.nextInt());
			System.out.print("ssn: ");
			ps.setInt(4, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void writerInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from writer where (fk_ssn) = (?)");
			System.out.print("\nInformatios of writer\nssn: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into writer values (?, ?, ?)");
			System.out.print("\nInformatios of writer\nwriter id: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("number of books: ");
			ps.setInt(2, keyboard.nextInt());
			System.out.print("ssn: ");
			ps.setInt(3, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void telInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from tel where (telNumber, fk_ssn) = (?, ?)");
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into tel values (?, ?)");
			break;
		}

		System.out.print("\nInformatios of tel\ntel number: ");
		ps.setString(1, keyboard.next());
		System.out.print("ssn: ");
		ps.setInt(2, keyboard.nextInt());

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void cityInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from city where (city_id) = (?)");
			System.out.print("\nInformatios of city\ncity id: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into city values (?, ?, ?)");
			System.out.print("\nInformatios of city\ncity id: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("name");
			ps.setString(2, keyboard.next());
			System.out.print("population: ");
			ps.setInt(3, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void universityInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from university where (university_id) = (?)");
			System.out.print("\nInformatios of university\nuniversity id: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into university values (?, ?, ?, ?)");
			System.out.print("\nInformatios of university\nuniversity id: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("name");
			ps.setString(2, keyboard.next());
			System.out.print("number of students: ");
			ps.setInt(3, keyboard.nextInt());
			System.out.print("city id: ");
			ps.setInt(4, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void personInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from person where (ssn) = (?)");
			System.out.print("\nInformatios of person\nssn: ");
			ps.setInt(1, keyboard.nextInt());
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into person values (?, ?, ?, ?, ?, ?)");
			System.out.print("\nInformatios of person\nssn: ");
			ps.setInt(1, keyboard.nextInt());
			System.out.print("age: ");
			ps.setInt(2, keyboard.nextInt());
			System.out.print("name: ");
			ps.setString(3, keyboard.next());
			System.out.print("surname: ");
			ps.setString(4, keyboard.next());
			System.out.print("gender: ");
			ps.setString(5, keyboard.next());
			System.out.print("city id: ");
			ps.setInt(6, keyboard.nextInt());
			break;
		}

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	static void copyInsertDelete(int selectedProcess) throws SQLException {
		PreparedStatement ps = null;

		switch (selectedProcess) {
		case 1: // delete
			ps = conn.prepareStatement("delete from copy where (copy_id, fk_library_id, fk_isbn) = (?, ?, ?)");
			break;
		case 2: // insert
			ps = conn.prepareStatement("insert into copy (copy_id, fk_library_id, fk_isbn) values (?, ?, ?)");
			break;
		}

		System.out.print("\nInformatios of copy\ncopy id: ");
		ps.setInt(1, keyboard.nextInt());
		System.out.print("library id: ");
		ps.setInt(2, keyboard.nextInt());
		System.out.print("isbn: ");
		ps.setInt(3, keyboard.nextInt());

		ps.executeUpdate();

		System.out.println("Operation has been done");
	}

	// list all copies exist in all libraries
	static void listAll() throws SQLException {

		String query = "select c.copy_id, c.statusOfBook, l.library_id, u.name as university, k.name as city "
				+ "from copy c join library l on c.fk_library_id = l.library_id "
				+ "join university u on l.fk_university_id = u.university_id "
				+ "join city k on u.fk_city_id = k.city_id";

		ResultSet rs = st.executeQuery(query);

		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = rsmd.getColumnLabel(i);
			System.out.print(columnName + " ");
		}
		System.out.println("");
		while (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) {
				String columnValue = rs.getString(i);
				System.out.print(columnValue + " ");
			}
			System.out.println("");
		}
	}

	// search book
	static void specialQuery() throws SQLException {
		PreparedStatement copyQ = conn
				.prepareStatement("select statusOfBook from copy where (copy_id, fk_isbn, fk_library_id) = (?, ?, ?)");

		System.out.print("\ninformations of the book\n\ncopy_id: ");
		int copyid = keyboard.nextInt();
		System.out.print("isbn: ");
		int isbn = keyboard.nextInt();
		System.out.print("library_id: ");
		int libraryid = keyboard.nextInt();

		copyQ.setInt(1, copyid);
		copyQ.setInt(2, isbn);
		copyQ.setInt(3, libraryid);

		ResultSet statusOfBook = copyQ.executeQuery();

		if (statusOfBook.next())
			if (statusOfBook.getString(1).equals("+")) {
				System.out.println("Book you searched is available ! Do you want to reserve it ? (Y/N): ");
				String answer = keyboard.next();
				if (answer.equalsIgnoreCase("y"))
					makeProcess(isbn, copyid);
			} else {
				System.out.println("Book is not present in the catalogue or is borrowed by someone !");
			}
	}

	// add process
	static void makeProcess(int isbn, int copyid) throws SQLException {

		int[] data = new int[5];
		int i = 0;
		System.out.print("\nYour informations\nssn: ");
		data[i++] = keyboard.nextInt();
		System.out.print("student id: ");
		data[i++] = keyboard.nextInt();
		System.out.print("university id: ");
		data[i++] = keyboard.nextInt();
		data[i++] = isbn;
		data[i++] = copyid;

		PreparedStatement processInsert = conn.prepareStatement("insert into process "
				+ "(typeOfProcess, dateOfProcess, fk_ssn, fk_student_id, fk_university_id, fk_isbn, fk_copy_id) values"
				+ "(\"B\", ?, ?, ?, ?, ?, ?)");

		processInsert.setDate(1, getCurrentDate());
		for (i = 2; i < data.length + 2; i++)
			processInsert.setInt(i, data[i - 2]);

		processInsert.execute();

		PreparedStatement checkProcess = conn.prepareStatement("select isSucceed from process where "
				+ "(typeOfProcess, dateOfProcess, fk_ssn, fk_student_id, fk_university_id, fk_isbn, fk_copy_id) = (\"B\", ?, ?, ?, ?, ?, ?)");

		checkProcess.setDate(1, getCurrentDate());
		for (i = 2; i < data.length + 2; i++)
			checkProcess.setInt(i, data[i - 2]);

		ResultSet isSucceed = checkProcess.executeQuery();

		if (isSucceed.next())
			if (isSucceed.getString(1).equals("+"))
				System.out.println("Book has been reserved !");
			else
				System.out.println("Process is not succeed !");
	}

	// cast Java date format to SQL date format
	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

}