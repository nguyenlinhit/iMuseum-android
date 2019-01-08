package vn.edu.tdmu.imuseum.ultils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by nvulinh on 8/26/17.
 * @author nvulinh
 */

public class UnixEpochDateTypeAdapter extends TypeAdapter<Date>{

    private static final TypeAdapter<Date> unixEpochDateTypeAdapter = new UnixEpochDateTypeAdapter();

    private UnixEpochDateTypeAdapter(){

    }

    public static TypeAdapter<Date> getUnixEpochDateTypeAdapter(){
        return unixEpochDateTypeAdapter;
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        out.value(String.valueOf(value.getTime()));
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return new Date(in.nextLong());
    }
}
