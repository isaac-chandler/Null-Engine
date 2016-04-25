package sandbox;

import java.io.IOException;
import java.util.HashMap;

public class Test {
	public static void main(String[] args) throws IOException {
		new HashMap<Character, Float>().get(null);
	}
}
