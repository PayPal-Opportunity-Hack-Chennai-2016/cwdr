package in.devmetric.opportunityhackcwdr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import in.devmetric.opportunityhackcwdr.AppConfig;
import in.devmetric.opportunityhackcwdr.AppController;
import in.devmetric.opportunityhackcwdr.Hotline;
import in.devmetric.opportunityhackcwdr.MainActivity;
import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;
import in.devmetric.opportunityhackcwdr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotlinesPage extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Hotline> list;


    public HotlinesPage() {
        list = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotlines_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyalerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        getContent();

        adapter = new HotlineAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }


    class HotlineAdapter extends RecyclerView.Adapter
            <HotlineViewHolder> {

        ArrayList<Hotline> mDataSet;

        public HotlineAdapter(ArrayList<Hotline> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public HotlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotline_card, parent, false);
            return new HotlineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HotlineViewHolder holder, int position) {
            holder.tvName.setText(mDataSet.get(position).getName());
            holder.tvNumber.setText(mDataSet.get(position).getphone());
            holder.tvLocation.setText(mDataSet.get(position).getLocation());
            holder.tvInitial.setText(mDataSet.get(position).getName().charAt(0) + "");
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    class HotlineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber, tvLocation, tvInitial;

        public HotlineViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvInitial = (TextView) itemView.findViewById(R.id.tvInitial);
        }
    }


    private void getContent() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.CONTACTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                System.out.println(response1 + "");
                JsonArray response = new JsonParser().parse(response1).getAsJsonArray();
                for (int i = 0; i < response.size(); i++) {
                    Hotline item = new Gson().fromJson(response.get(i).getAsJsonObject().toString(), Hotline.class);
                    list.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getLocalizedMessage() + "No result found", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, "hotline");
    }

}
