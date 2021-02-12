package com.laioffer.tinnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.FragmentHomeBinding;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;
import com.mindorks.placeholderview.SwipeDecor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        -Call the static inflate() method included in the generated binding class.
         This creates an instance of the binding class for the fragment to use.
        -Get a reference to the root view by either calling the getRoot() method
        -Return the root view from the onCreateView() method to make it the active view on the screen.
         */
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getBuilder() method to modify the default swipeView configurations
        //we are adding 3 cards in the display
        //SwipeDecor class is used to adjust the visual elements of the view.
        //Here paddingTop and relativeScale gives the perception of a card being placed in stack
        binding
                .swipeView
                .getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(
                        new SwipeDecor()
                                .setPaddingTop(20)
                                .setRelativeScale(0.01f));

        binding.rejectBtn.setOnClickListener(v -> binding.swipeView.doSwipe(false));
        binding.acceptBtn.setOnClickListener(v -> binding.swipeView.doSwipe(true));

        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                //Create the TinNewsCard from Retrofit Response and add to the PlaceHolderView
                                for (Article article : newsResponse.articles) {
                                    TinNewsCard tinNewsCard = new TinNewsCard(article);
                                    binding.swipeView.addView(tinNewsCard);
                                }

                            }
                        });

    }

}