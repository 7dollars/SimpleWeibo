package com.wmk.wb.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.entity.FinalViewData;
import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.presenter.adapter.PicListAdapter;
import com.wmk.wb.view.ImageActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private CircleImageView head;
    private TextView author;
    private TextView content;
    private TextView time;
    private TextView count;
    private RecyclerView list_pic;

    private TextView ret_content;
    private TextView count_ret;

    private long id;

    private OnFragmentInteractionListener mListener;

    public DetialFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetialFragment newInstance(int position, boolean isRet,boolean hasChild) {
        DetialFragment fragment = new DetialFragment();
        Bundle args = new Bundle();
        args.putBoolean("isRet", isRet);
        args.putInt("position",position );
        args.putBoolean("hasChild",hasChild );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            if(getArguments().getBoolean("isRet")) {
                View v=inflater.inflate(R.layout.fragment_detial, container, false);
                head=(CircleImageView)v.findViewById(R.id.imageView);
                author=(TextView)v.findViewById(R.id.txt_author);
                time=(TextView)v.findViewById(R.id.txt_time);
                content=(TextView)v.findViewById(R.id.txt_content);
                count=(TextView)v.findViewById(R.id.count);
                list_pic=(RecyclerView)v.findViewById(R.id.list_pic);
                setData();
                return v;
            }
            else if(getArguments().getBoolean("hasChild")){
                View v=inflater.inflate(R.layout.fragment_detial_ret, container, false);
                head=(CircleImageView)v.findViewById(R.id.imageView);
                author=(TextView)v.findViewById(R.id.txt_author);
                time=(TextView)v.findViewById(R.id.txt_time);
                content=(TextView)v.findViewById(R.id.txt_content);
                count=(TextView)v.findViewById(R.id.count);
                list_pic=(RecyclerView)v.findViewById(R.id.list_pic);
                ret_content=(TextView)v.findViewById(R.id.ret_content);
                count_ret=(TextView)v.findViewById(R.id.count_ret);
                setData();
                return v;
            }
            else{
                View v=inflater.inflate(R.layout.fragment_detial, container, false);
                head=(CircleImageView)v.findViewById(R.id.imageView);
                author=(TextView)v.findViewById(R.id.txt_author);
                time=(TextView)v.findViewById(R.id.txt_time);
                content=(TextView)v.findViewById(R.id.txt_content);
                count=(TextView)v.findViewById(R.id.count);
                list_pic=(RecyclerView)v.findViewById(R.id.list_pic);
                setData();
                return v;
            }
        }
        return inflater.inflate(R.layout.fragment_detial, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setData()
    {
        int position=getArguments().getInt("position");
        boolean isRet=getArguments().getBoolean("isRet");
        boolean hasChild=getArguments().getBoolean("hasChild");
        FinalViewData fdata=StaticData.getInstance().data.get(position);

        id=fdata.getId();
        if(isRet) {
            author.setText(fdata.getRet_name());
            content.setText(fdata.getRet_text());
            count.setText(fdata.getReposts_count_ret() + "转发 | " + fdata.getComments_count_ret() + "回复");
            time.setText(fdata.getRet_time());

            Glide.with(getActivity()).load(fdata.getRet_headurl()).into(head);
            if(fdata.getRet_picurls()!=null)
            {
                LinearLayoutManager manager = new LinearLayoutManager(list_pic.getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                list_pic.setLayoutManager(manager);
                list_pic.setAdapter(new PicListAdapter(getActivity(), fdata.getRet_picurls(),0,setPicSubscriber()));
            }
        }
        else if(hasChild)
        {
            author.setText(fdata.getName());
            content.setText(fdata.getText());
            count.setText(fdata.getReposts_count() + "转发 | " + fdata.getComments_count() + "回复");
            count_ret.setText(fdata.getReposts_count_ret() + "转发 | " + fdata.getComments_count_ret() + "回复");
            ret_content.setText("@"+fdata.getRet_name()+":"+fdata.getRet_text());
            time.setText(fdata.getTime());


            Glide.with(getActivity()).load(fdata.getHeadurl()).into(head);
            if(fdata.getRet_picurls()!=null)
            {
                LinearLayoutManager manager = new LinearLayoutManager(list_pic.getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                list_pic.setLayoutManager(manager);
                list_pic.setAdapter(new PicListAdapter(getActivity(), fdata.getRet_picurls(),0,setPicSubscriber()));
            }
        }
        else
        {
            author.setText(fdata.getName());
            content.setText(fdata.getText());
            count.setText(fdata.getReposts_count() + "转发 | " + fdata.getComments_count() + "回复");
            time.setText(fdata.getTime());


            Glide.with(getActivity()).load(fdata.getHeadurl()).into(head);
            if(fdata.getPicurls()!=null)
            {
                LinearLayoutManager manager = new LinearLayoutManager(list_pic.getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                list_pic.setLayoutManager(manager);
                list_pic.setAdapter(new PicListAdapter(getActivity(), fdata.getPicurls(),0,setPicSubscriber()));
            }
        }

    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public Subscriber<String> setPicSubscriber()
    {
        final Subscriber<String> mSubscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Intent intent=new Intent();
                intent.putExtra("Largeurl",s);
                intent.setClass(getActivity(),ImageActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        return mSubscriber;
    }

}
