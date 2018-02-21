/**
 * 
 */
package odbmsPrdAttributeExtract;

/**
 * @author itkd
 *
 */
import java.sql.*;
import java.io.*;

public class OdbmsExtract {

	private static final String FILENAME = "D:\\keith.devlin\\java_output\\out.csv";
	private static final String FILEHDR = "\"productNumber\",\"productDesc\",\"attributeTypeDesc\","
			+ "\"attributeSubType\",\"attributeCode\",\"attributeCodeName\",\n";
	private static final String DRIVERNAME = "oracle.jdbc.OracleDriver";
	private static final String DBNAME = "jdbc:oracle:thin:@rlcmstd:1523:cmsdev";
	private static final String DBUSER = "itkd";
	private static final String DBPWD = "tesco04";
	private static final String SQL = "SELECT RTRIM(p.prd_lvl_number), " + "p.prd_name_full,"
			+ "x.attribute_type_desc, " + "x.attribute_sub_type, " + "x.attribute_code, " + "x.attribute_code_name "
			+ "from prdmstee p, TABLE(prd_attr.get_attrs(p.prd_lvl_child)) x  " + "WHERE  p.prd_lvl_active = 'T' "
			+ "AND    p.prd_lvl_child = 2098450 " + "AND    p.prd_lvl_id = 1";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start of program");

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			// load the driver
			Class.forName(DRIVERNAME);
			System.out.println("Loaded the driver");

			// Create the connection object
			Connection con = DriverManager.getConnection(DBNAME, DBUSER, DBPWD);
			System.out.println("Created the connection object");

			// create the statement object

			Statement stmt = con.createStatement();
			System.out.println("Create the statement");

			// create the new output file
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);

			// write the header

			System.out.println(FILEHDR);
			bw.write(FILEHDR);

			// execute the query

			ResultSet rs = stmt.executeQuery(SQL);
			
			//Write the output to screen & file

			while (rs.next()) {
				System.out.println("\"" + rs.getString(1) + "\",\"" + rs.getString(2) + "\",\"" + rs.getString(3)
						+ "\",\"" + rs.getString(4) + "\",\"" + rs.getString(5) + "\",\"" + rs.getString(6) + "\",\n");
				bw.write("\"" + rs.getString(1) + "\",\"" + rs.getString(2) + "\",\"" + rs.getString(3) + "\",\""
						+ rs.getString(4) + "\",\"" + rs.getString(5) + "\",\"" + rs.getString(6) + "\",\n");
			}
			System.out.println("Executed the statement");

			// close the connection

			con.close();

			// close the output file

			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
			

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
