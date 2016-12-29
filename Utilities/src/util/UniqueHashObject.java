package util;

import java.util.HashMap;
import java.util.Map;

public class UniqueHashObject {
	private static final Map<String, Integer> hashCodes = new HashMap<>();

	private final int hash;


	public UniqueHashObject() {
		String name = getClass().getName();
		Integer code = hashCodes.get(name);
		if (code == null)
			code = 0;
		hash = code;
		hashCodes.put(name, code + 1);
	}

	@Override
	public int hashCode() {
		return hash;
	}


}
