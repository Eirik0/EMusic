package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileUtilities {
	public static <T> List<T> loadFromStream(InputStream stream, Function<String, T> onSuccess, Supplier<List<T>> onFailure) {
		try {
			return streamToList(stream, onSuccess);
		} catch (Exception e) {
			return onFailure.get();
		}
	}

	public static <T> List<T> streamToList(InputStream stream, Function<String, T> function) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			List<T> result = new ArrayList<>();

			String line;
			while ((line = reader.readLine()) != null) {
				result.add(function.apply(line));
			}

			return result;
		}
	}
}
