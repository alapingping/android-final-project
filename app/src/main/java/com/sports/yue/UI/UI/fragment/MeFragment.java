package com.sports.yue.UI.UI.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Adapter.GridAdapter;
import com.sports.yue.UI.UI.activity.CharatorActivity;
import com.sports.yue.UI.UI.activity.MainActivity;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private ListView listView;
    private View view;
    private RecyclerView recyclerView;
    private TextView coach_name_text;
    private TextView coach_introduction_text;
    private LinearLayout Charator;
    public MeFragment() {
        // Required empty public constructor

    }

    public static MeFragment newInstance(@DrawableRes int drawableRes) {
        MeFragment fragment = new MeFragment();
        Bundle argument = new Bundle();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //获取当前View
        View view = inflater.inflate(R.layout.fragment_me,container,false);

        coach_name_text = view.findViewById(R.id.coach_name_text);
        coach_name_text.setText(CurrentUser.getInstance(getContext()).getUserName());

        Charator = view.findViewById(R.id.Charator);
        Charator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CharatorActivity.class);
                startActivity(intent);
            }
        });

//        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setAdapter(new GridAdapter(this));

//
//        prepareTransitions();
//        postponeEnterTransition();
        return view;
    }



//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        scrollToPosition();
//    }
//

//    private void scrollToPosition() {
//        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v,
//                                       int left,
//                                       int top,
//                                       int right,
//                                       int bottom,
//                                       int oldLeft,
//                                       int oldTop,
//                                       int oldRight,
//                                       int oldBottom) {
//                recyclerView.removeOnLayoutChangeListener(this);
//                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                View viewAtPosition = layoutManager.findViewByPosition(MainActivity.currentPosition);
//                // Scroll to position if the view for the current position is null (not currently part of
//                // layout manager children), or it's not completely visible.
//                if (viewAtPosition == null || layoutManager
//                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
//                    recyclerView.post(() -> layoutManager.scrollToPosition(MainActivity.currentPosition));
//                }
//            }
//        });
//    }
//
//    private void prepareTransitions() {
//        setExitTransition(TransitionInflater.from(getContext())
//                .inflateTransition(R.transition.grid_exit_transition));
//
//        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
//        setExitSharedElementCallback(
//                new SharedElementCallback() {
//                    @Override
//                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                        // Locate the ViewHolder for the clicked position.
//                        RecyclerView.ViewHolder selectedViewHolder = recyclerView
//                                .findViewHolderForAdapterPosition(MainActivity.currentPosition);
//                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
//                            return;
//                        }
//
//                        // Map the first shared element name to the child ImageView.
//                        sharedElements
//                                .put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
//                    }
//                });
//    }



}
