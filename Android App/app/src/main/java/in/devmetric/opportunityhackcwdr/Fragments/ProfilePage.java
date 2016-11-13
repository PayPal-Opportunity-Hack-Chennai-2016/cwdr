package in.devmetric.opportunityhackcwdr.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import in.devmetric.opportunityhackcwdr.ProfileEditActivity;
import in.devmetric.opportunityhackcwdr.R;
import in.devmetric.opportunityhackcwdr.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePage extends Fragment {

    private TextView tvName, tvEmail, tvbio, tvinterest;
    private CircleImageView image;
    private View view;

    public ProfilePage() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvbio = (TextView) view.findViewById(R.id.tvBio);
        tvinterest = (TextView) view.findViewById(R.id.tvInterest);
        image = (CircleImageView) view.findViewById(R.id.profile_image);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "default");
        String fullName = sharedPreferences.getString("fullName", "default");
        String preferences = sharedPreferences.getString("qualification", "default");
        String bio = sharedPreferences.getString("ae", "default");

        tvName.setText(fullName);
        tvEmail.setText(email);
        tvbio.setText(bio);
        tvinterest.setText(preferences);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startActivity(new Intent(getContext(), ProfileEditActivity.class));
                break;
            case R.id.signout:
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getContext(), WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
