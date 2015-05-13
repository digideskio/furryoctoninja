package pl.rspective.data.rest.converter;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class RestDataConverter implements Converter {

    private static final String TAG = "RestDataConverter";

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        String jsonData = null;

        try {
            jsonData = fromStream(typedInput.in());
        } catch (IOException ioe) {
            Log.e(TAG, "There was a problem to parse data", ioe);
        }

        return jsonData;
    }

    @Override
    public TypedOutput toBody(Object o) {
        return null;
    }

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}