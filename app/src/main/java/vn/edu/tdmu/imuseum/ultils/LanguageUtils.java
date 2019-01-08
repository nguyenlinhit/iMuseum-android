package vn.edu.tdmu.imuseum.ultils;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import vn.edu.tdmu.imuseum.model.Language;
import vn.edu.tdmu.imuseum.views.MyApplication;
import vn.edu.tdmu.imuseum.views.R;

/**
 * Created by nvulinh on 3/2/18.
 *
 */

public class LanguageUtils {
    private static Language currentLanguage = null;

    public static Language getCurrentLanguage(){
        if (currentLanguage == null){
            currentLanguage = initCurrentLanguage();
        }

        return currentLanguage;
    }

    private static Language initCurrentLanguage() {
        Language currentLanguage = SharePrefs.getInstance().get(SharePrefs.LANGUAGE,Language.class);
        if (currentLanguage != null){
            return currentLanguage;
        }

        currentLanguage = new Language(Constant.Value.DEFAULT_LANGUAGE_ID,
                MyApplication.self().getString(R.string.language_english),
                MyApplication.self().getString(R.string.language_english_code));
        SharePrefs.getInstance().put(SharePrefs.LANGUAGE, currentLanguage);

        return currentLanguage;
    }

    public static List<Language> getLanguageData(){
        List<Language> languageList = new ArrayList<>();
        List<String> languageNames = Arrays.asList(MyApplication.self().getResources().getStringArray(R.array.language_names));
        List<String> languageCodes = Arrays.asList(MyApplication.self().getResources().getStringArray(R.array.language_codes));

        if (languageNames.size() != languageCodes.size()){
            return languageList;
        }

        for (int i = 0; i < languageNames.size(); i++){
            languageList.add(new Language(i, languageNames.get(i), languageCodes.get(i)));
        }

        return languageList;
    }

    public static void loadLocale(){
        changeLanguage(initCurrentLanguage());
    }

    @SuppressWarnings("deprecation")
    public static void changeLanguage(Language language) {
        SharePrefs.getInstance().put(SharePrefs.LANGUAGE, language);
        currentLanguage = language;
        Locale locale = new Locale(language.getmCode());
        Resources resources = MyApplication.self().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
