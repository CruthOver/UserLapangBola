package bola.wiradipa.org.lapanganbola;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bola.wiradipa.org.lapanganbola.helpers.adapters.InfoPagerAdapter;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.Info;
import bola.wiradipa.org.lapanganbola.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private User user;
    private Picasso picasso;
    private ImageView ivAvatar;
    private ViewPager viewPager;
    private InfoPagerAdapter adapter;
    private LinearLayout llSlider;
    private int dotsCount;
    private ImageView[] dots;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_beranda, container, false);

        picasso = Picasso.get();

        TextView tvUserName = view.findViewById(R.id.user_name);
        if(user!=null)
            tvUserName.setText(user.getName());

        ImageView rental = view.findViewById(R.id.rental);
        rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterVenuesActivity.class);
                startActivity(intent);
            }
        });

        ImageView request_match = view.findViewById(R.id.request_match);
        request_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AjakTandingActivity.class);
                startActivity(intent);
            }
        });

        ivAvatar = view.findViewById(R.id.profile);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView match = view.findViewById(R.id.match);
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MatchesActivity.class);
                startActivity(intent);
            }
        });

        ImageView order = view.findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intent);
            }
        });

        viewPager = view.findViewById(R.id.beranda_pager);

        llSlider = view.findViewById(R.id.slide_beranda);

        return view;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void refresh(){
        picasso.load(UtilsApi.BASE_URL+user.getPhotoUrl())
                .fit()
                .centerCrop()
                .error(R.drawable.user)
                .placeholder(R.drawable.user)
                .into(ivAvatar);
    }

    public void initSlider(List<Info> infos){
        adapter = new InfoPagerAdapter(getContext(), infos, null);

        viewPager.setAdapter(adapter);

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0 ; i < dotsCount; i++){
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(12,8,12,8);

            llSlider.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dots));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
