
public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String oi = "This is a test";

		for(int i = 0; i < 10; i++) {
			System.out.println(oi + " = " + oi.hashCode());
			System.out.println(hash(oi));
			System.out.println(step(oi));
		}

	}

	private static int hash(String key) {
		int hashed = (key.hashCode() & 0x7fffffff);
		return (hashed);
	}


	private static int step(String key) {
		int hashed = (key.hashCode() & 0x7fffffff);
		return ((hashed % 97) + 1);
	}

}
