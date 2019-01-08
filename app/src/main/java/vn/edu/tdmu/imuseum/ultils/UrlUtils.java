package vn.edu.tdmu.imuseum.ultils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by nvulinh on 11/12/17.
 *
 */

public class UrlUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";
    private static UrlUtils instance;

    public static UrlUtils getInstance(){
        return instance;
    }

    private String decode(String content, String encoding) {
        if (encoding == null) {
            encoding = "UTF-8";
        }
        try {
            return URLDecoder.decode(content, encoding);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    private String encode(String content, String encoding) {
        if (encoding == null) {
            encoding = "UTF-8";
        }
        try {
            return URLEncoder.encode(content, encoding);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    public String formart(Map<String, String> parameters, String encoding){
        StringBuilder results = new StringBuilder();
        if (!parameters.isEmpty()){
            for (Map.Entry<String, String> parameter : parameters.entrySet()){
                String encodeName = encode(parameter.getKey(), encoding);
                String value = parameter.getValue();
                String encodeValue = value != null ? encode(value, encoding) : "";
                if (results.length() > 0){
                    results.append(PARAMETER_SEPARATOR);
                }

                results.append(encodeName);
                results.append(NAME_VALUE_SEPARATOR);
                results.append(encodeValue);
            }
        }

        return results.toString();
    }
}
