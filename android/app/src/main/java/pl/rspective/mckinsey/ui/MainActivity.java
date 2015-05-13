package pl.rspective.mckinsey.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.form.FormFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main_fragment_container, FormFragment.newInstance())
                .commit();
    }
}
