package ehb.be.comictourbrussels;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {


    public AboutFragment() {

     TextView tvAboutTitle, tvAboutAuthors, tvAboutJorn, tvAboutPieter, tvAboutCarine,
             tvAboutPartners, tvAboutEhb, tvAboutVdab;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);

        //tvAboutTitle = getView().findViewById()

    }

}
