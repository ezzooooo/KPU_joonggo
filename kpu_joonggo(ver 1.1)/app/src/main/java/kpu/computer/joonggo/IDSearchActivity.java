package kpu.computer.joonggo;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class IDSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idsearch);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

       getFragmentManager().beginTransaction().add(R.id.fragment_container, new IDSearchFragment()).commit();

    }



   public void RadioButtonClicked(View view) {
        Fragment fr = null;
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.idSearch:
                if (checked) {
                    fr = new IDSearchFragment();
                }
                break;
            case R.id.passwordSearch:
                if (checked) {
                    fr = new PasswordSearchFragment();
                }
                break;
        }
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).commit();

    }
}
