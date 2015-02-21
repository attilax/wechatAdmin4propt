package m.dataPkg;

@Deprecated
public class dateUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String trim(String text) {
		 
		return 	 	 text.trim(). replaceAll(",", "").replaceAll("[^\\d-]", "").trim();
	}

}
