package com.wmk.wb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wmk.wb.R;
import com.wmk.wb.presenter.DataManager;
import com.wmk.wb.model.entity.FinalCommentsData;
import com.wmk.wb.model.entity.RetJson.CommentsData;
import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.presenter.adapter.MyCommentsRecyclerViewAdapter;
import com.wmk.wb.utils.ConvertDate;


import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CommentsFragment extends Fragment{

    private MyCommentsRecyclerViewAdapter recAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters

    private long id;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CommentsFragment newInstance(int columnCount,long id) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.id=getArguments().getLong("id");
        }
        StaticData.getInstance().cdata.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments_list, container, false);


        recAdapter=new MyCommentsRecyclerViewAdapter(getActivity());
        RecyclerView list=(RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        list.setAdapter(recAdapter);
       /* EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(StaticData.getInstance().cdata.size()>0)
                {
                    getComments(StaticData.getInstance().cdata.get(StaticData.getInstance().cdata.size()-1).getId());
                }
            }
        };
        list.addOnScrollListener(end);*/
        list.setNestedScrollingEnabled(false);
        getComments(0);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    public void getComments(final long max_id)
    {
        Subscriber<CommentsData> mSubscribe;

        mSubscribe=new Subscriber<CommentsData>() {
            @Override
            public void onCompleted() {
                recAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommentsData commentsdata) {
                FinalCommentsData fdata;
                List<FinalCommentsData>data=new ArrayList<>();
                for (int i = 0; i < commentsdata.comments.size(); i++) {
                    if(i==0&&max_id!=0)
                        i=1;
                    fdata=new FinalCommentsData();
                    fdata.setHeadurl(commentsdata.comments.get(i).user.getAvatar_large());
                    fdata.setName(commentsdata.comments.get(i).user.getName());
                    fdata.setText(commentsdata.comments.get(i).text);
                    fdata.setTime(ConvertDate.calcDate(commentsdata.comments.get(i).created_at));
                    fdata.setId(commentsdata.comments.get(i).id);
                    fdata.setSource(commentsdata.comments.get(i).source);
                    data.add(fdata);
                }
                StaticData.getInstance().cdata.addAll(data);
            }
        };
        DataManager.getInstance().getComments(mSubscribe,id,max_id,null);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
