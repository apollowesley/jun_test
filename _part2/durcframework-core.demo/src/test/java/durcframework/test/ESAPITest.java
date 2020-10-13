package durcframework.test;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.OracleCodec;

public class ESAPITest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OracleCodec codec = new OracleCodec();
		MySQLCodec mySQLCodec = new MySQLCodec(MySQLCodec.Mode.STANDARD);
		String s = ESAPI.encoder().encodeForSQL(mySQLCodec, "'sdf");
		System.out.println(s);
	}

}
