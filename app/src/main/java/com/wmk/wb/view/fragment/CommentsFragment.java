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
import com.wmk.wb.model.StaticData;
import com.wmk.wb.presenter.CommentsFG;
import com.wmk.wb.presenter.adapter.MyCommentsRecyclerViewAdapter;
import com.wmk.wb.view.Interface.ICommentsFG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CommentsFragment extends Fragment implements ICommentsFG{

    private MyCommentsRecyclerViewAdapter recAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters

    private long id;
    private CommentsFG instance;


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


        instance=new CommentsFG(this,id);
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
       // getComments(0);
        return view;
    }

    @Override
    public void onResume() {
        instance.getComments(0);
        super.onResume();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void notifyListChange() {
        recAdapter.notifyDataSetChanged();
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
