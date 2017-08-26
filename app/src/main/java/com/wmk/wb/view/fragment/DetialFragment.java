package com.wmk.wb.view.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.NameEvent;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.presenter.DetialFG;
import com.wmk.wb.presenter.adapter.PicListAdapter;
import com.wmk.wb.utils.ClickMovementMethod;
import com.wmk.wb.utils.NinePicLayout;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.Interface.IDetialFG;
import com.wmk.wb.view.ImageActivity;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetialFragment extends Fragment implements IDetialFG,NinePicLayout.PicClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private CircleImageView head;
    private TextView author;
    private TextView content;
    private TextView time;
    private TextView count;
    private RecyclerView list_pic;
    private NinePicLayout nine;

    private TextView ret_content;
    private TextView count_ret;

    private long id;

    private DetialFG instance=new DetialFG(this);
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
            //    list_pic=(RecyclerView)v.findViewById(R.id.list_pic);
                nine=(NinePicLayout)v.findViewById(R.id.nineLayout);
                content.setOnTouchListener(ClickMovementMethod.getInstance());
                instance.setData(getArguments().getInt("position"),getArguments().getBoolean("isRet"),getArguments().getBoolean("hasChild"));
                return v;
            }
            else if(getArguments().getBoolean("hasChild")){
                View v=inflater.inflate(R.layout.fragment_detial_ret, container, false);
                head=(CircleImageView)v.findViewById(R.id.imageView);
                author=(TextView)v.findViewById(R.id.txt_author);
                time=(TextView)v.findViewById(R.id.txt_time);
                content=(TextView)v.findViewById(R.id.txt_content);
                count=(TextView)v.findViewById(R.id.count);
                nine=(NinePicLayout)v.findViewById(R.id.nineLayout);
                ret_content=(TextView)v.findViewById(R.id.ret_content);
                count_ret=(TextView)v.findViewById(R.id.count_ret);
                content.setOnTouchListener(ClickMovementMethod.getInstance());
                ret_content.setOnTouchListener(ClickMovementMethod.getInstance());
                instance.setData(getArguments().getInt("position"),getArguments().getBoolean("isRet"),getArguments().getBoolean("hasChild"));
                return v;
            }
            else{
                View v=inflater.inflate(R.layout.fragment_detial, container, false);
                head=(CircleImageView)v.findViewById(R.id.imageView);
                author=(TextView)v.findViewById(R.id.txt_author);
                time=(TextView)v.findViewById(R.id.txt_time);
                content=(TextView)v.findViewById(R.id.txt_content);
                count=(TextView)v.findViewById(R.id.count);
                nine=(NinePicLayout)v.findViewById(R.id.nineLayout);
                content.setOnTouchListener(ClickMovementMethod.getInstance());
                instance.setData(getArguments().getInt("position"),getArguments().getBoolean("isRet"),getArguments().getBoolean("hasChild"));
                return v;
            }
        }
        return inflater.inflate(R.layout.fragment_detial, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
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

    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("Largeurl",pic_list_info.getLarg_url());
        intent.putExtra("position",pic_list_info.getPosition());
        intent.setClass(getActivity(),ImageActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),pic_list_info.getView(),"image").toBundle());
      //  startActivity(intent);
    }

    @Override
    public void updateData(boolean flag,String author, String content, String count, String time) {
        this.author.setText(author);
        this.content.setText(TextUtils.getWeiBoText(getContext(),content));
        this.count.setText(count);
        this.time.setText(time);
        final String au=author;
        this.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NameEvent(au.toString()));
            }
        });
    }
    @Override
    public void updateData(String author, String content, String count, String count_ret, String ret_content, String time) {
        this.author.setText(author);
        this.content.setText(TextUtils.getWeiBoText(getContext(),content));
        this.count.setText(count);
        this.time.setText(time);
        this.count_ret.setText(count_ret);
        this.ret_content.setText(TextUtils.getWeiBoText(getContext(),ret_content));
        final String au=author;
        this.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NameEvent(au.toString()));
            }
        });
    }
    @Override
    public void setPic(boolean flag,FinalViewData fdata) {
        if(flag) {
            Glide.with(getActivity()).load(fdata.getHeadurl()).into(head);
        }
        else {
            Glide.with(getActivity()).load(fdata.getRet_headurl()).into(head);
        }
        if (fdata.getPicurls().size() != 0) {
      //      LinearLayoutManager manager = new LinearLayoutManager(list_pic.getContext());
      //      manager.setOrientation(LinearLayoutManager.HORIZONTAL);
      //      list_pic.setLayoutManager(manager);
      //      list_pic.setAdapter(new PicListAdapter(getActivity(), fdata.getPicurls(), 0, instance.getPicSubscriber()));
            nine.setPicClickListener(this);
            nine.setImageUrlList(fdata.getPicurls());
            nine.loadPIC();
        }
        if (fdata.getRet_picurls().size() != 0) {
        //    LinearLayoutManager manager = new LinearLayoutManager(list_pic.getContext());
        //    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //    list_pic.setLayoutManager(manager);
        //    list_pic.setAdapter(new PicListAdapter(getActivity(), fdata.getRet_picurls(), 0, instance.getPicSubscriber()));
            nine.setPicClickListener(this);
            nine.setImageUrlList(fdata.getRet_picurls());
            nine.loadPIC();
        }
    }

    @Override
    public void click(final Pic_List_Info i) {
        Observable<Pic_List_Info> observable = Observable.create(new Observable.OnSubscribe<Pic_List_Info>() {
            @Override
            public void call(Subscriber<? super Pic_List_Info> subscriber) {subscriber.onNext(i);}
        });
        observable.subscribe(instance.getPicSubscriber());
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

}
