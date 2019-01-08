package vn.edu.tdmu.imuseum.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.view.WindowManager;

import vn.edu.tdmu.imuseum.adapter.LanguageAdapter;
import vn.edu.tdmu.imuseum.model.Language;
import vn.edu.tdmu.imuseum.ultils.ItemClickListener;
import vn.edu.tdmu.imuseum.ultils.LanguageUtils;
import vn.edu.tdmu.imuseum.views.databinding.ActivityLanguageSettingBinding;

public class LanguageSetting extends AppCompatActivity {

    private LanguageAdapter languageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLanguageSettingBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_language_setting);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        languageAdapter = new LanguageAdapter(LanguageUtils.getLanguageData());
        languageAdapter.setListener(new ItemClickListener<Language>() {
            @Override
            public void onClickItem(int position, Language language) {
                if (!language.getmCode().equals(LanguageUtils.getCurrentLanguage().getmCode())) {
                    onChangeLanguageSuccessfully(language);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(LanguageSetting.this);
        binding.recyclerViewLanguage.setLayoutManager(layoutManager);
        binding.recyclerViewLanguage.setAdapter(languageAdapter);
    }

    private void onChangeLanguageSuccessfully(Language language) {
        languageAdapter.setCurrentLanguage(language);
        LanguageUtils.changeLanguage(language);
        setResult(RESULT_OK, new Intent());
        finish();
    }
}
