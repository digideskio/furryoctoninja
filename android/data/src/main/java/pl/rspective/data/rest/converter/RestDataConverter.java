package pl.rspective.data.rest.converter;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

public class RestDataConverter extends GsonConverter {

    private static final String TAG = "RestDataConverter";

    public RestDataConverter(Gson gson) {
        super(gson);
    }

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