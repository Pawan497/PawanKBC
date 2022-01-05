package com.pawanyadav497.kbcwithpawan.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pawanyadav497.kbcwithpawan.R;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentHomeBinding;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentScoreboardBinding;

import com.pawanyadav497.kbcwithpawan.ui.home.HomeFragment;
import com.pawanyadav497.kbcwithpawan.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ScoreboardFragment extends Fragment {
    private FragmentScoreboardBinding binding;

    private HomeViewModel homeViewModel;

    TextView tvdate,tvAmountInWords,tvAmountInNumbers,tvPlayerName,tvCongrats;
    Calendar calendar;
    Button btnPlayAgain;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
    private Integer score1;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    private String uid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);

//       homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        final NavController navController= Navigation.findNavController(container);

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        tvdate=binding.textdate;
        tvAmountInNumbers=binding.amountInNumbers;
        tvAmountInWords=binding.amountInWords;
        tvPlayerName=binding.playerName;
        tvCongrats=binding.tvCongats;
        btnPlayAgain=binding.btnPlayAgain;


        tvCongrats.setVisibility(View.INVISIBLE);
        //setting date
        calendar = Calendar.getInstance();
        tvdate.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));


        //setting score
//        homeViewModel.getScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(@Nullable Integer integer) {
//                score1= integer;
//                binding.tvCheck.setText(String.valueOf(integer));
//                switch(score1){
//                    case 1: tvAmountInNumbers.setText("1,000");
//                        tvAmountInWords.setText("One thousand"); break;
//                    case 2: tvAmountInNumbers.setText("2,000");
//                        tvAmountInWords.setText("Two thousand");break;
//                    case 3: tvAmountInNumbers.setText("3,000");
//                        tvAmountInWords.setText("Three thousand");break;
//                    case 4: tvAmountInNumbers.setText("5,000");
//                        tvAmountInWords.setText("Five thousand");break;
//                    case 5: tvAmountInNumbers.setText("10,000");
//                        tvAmountInWords.setText("Ten thousand");break;
//                    case 6: tvAmountInNumbers.setText("20,000");
//                        tvAmountInWords.setText("Twenty thousand");break;
//                    case 7: tvAmountInNumbers.setText("40,000");
//                        tvAmountInWords.setText("Forty thousand");break;
//                    case 8: tvAmountInNumbers.setText("80,000");
//                        tvAmountInWords.setText("Eighty thousand");break;
//                    case 9: tvAmountInNumbers.setText("1,60,000");
//                        tvAmountInWords.setText("One lakh sixty thousand");break;
//                    case 10: tvAmountInNumbers.setText("3,20,000");
//                        tvAmountInWords.setText("Three lakh twenty thousand");break;
//                    case 11: tvAmountInNumbers.setText("6,40,000");
//                        tvAmountInWords.setText("Six lakh forty thousand");break;
//                    case 12: tvAmountInNumbers.setText("12,50,000");
//                        tvAmountInWords.setText("Twelve lakh fifty thousand");break;
//                    case 13: tvAmountInNumbers.setText("25,00,000");
//                        tvAmountInWords.setText("Twenty five lakh");break;
//                    case 14: tvAmountInNumbers.setText("50,00,000");
//                        tvAmountInWords.setText("Fifty lakh");break;
//                    case 15: tvAmountInNumbers.setText("75,00,000");
//                        tvAmountInWords.setText("Seventy five lakh");break;
//                    case 16: tvAmountInNumbers.setText("1,00,00,000");
//                        tvAmountInWords.setText("One crore");
//                        tvCongrats.setVisibility(View.VISIBLE);
//                        homeViewModel.setScore(0);
//                        break;
//                    case 0: tvAmountInNumbers.setText("00");
//                        tvAmountInWords.setText("Eggs");
//                }
//            }
//        });

        score1= HomeFragment.correctCountScore;

        switch(score1){
            case 1: tvAmountInNumbers.setText("1,000");
                tvAmountInWords.setText("One thousand"); break;
            case 2: tvAmountInNumbers.setText("2,000");
                tvAmountInWords.setText("Two thousand");break;
            case 3: tvAmountInNumbers.setText("3,000");
                tvAmountInWords.setText("Three thousand");break;
            case 4: tvAmountInNumbers.setText("5,000");
                tvAmountInWords.setText("Five thousand");break;
            case 5: tvAmountInNumbers.setText("10,000");
                tvAmountInWords.setText("Ten thousand");break;
            case 6: tvAmountInNumbers.setText("20,000");
                tvAmountInWords.setText("Twenty thousand");break;
            case 7: tvAmountInNumbers.setText("40,000");
                tvAmountInWords.setText("Forty thousand");break;
            case 8: tvAmountInNumbers.setText("80,000");
                tvAmountInWords.setText("Eighty thousand");break;
            case 9: tvAmountInNumbers.setText("1,60,000");
                tvAmountInWords.setText("One lakh sixty thousand");break;
            case 10: tvAmountInNumbers.setText("3,20,000");
                tvAmountInWords.setText("Three lakh twenty thousand");break;
            case 11: tvAmountInNumbers.setText("6,40,000");
                tvAmountInWords.setText("Six lakh forty thousand");break;
            case 12: tvAmountInNumbers.setText("12,50,000");
                tvAmountInWords.setText("Twelve lakh fifty thousand");break;
            case 13: tvAmountInNumbers.setText("25,00,000");
                tvAmountInWords.setText("Twenty five lakh");break;
            case 14: tvAmountInNumbers.setText("50,00,000");
                tvAmountInWords.setText("Fifty lakh");break;
            case 15: tvAmountInNumbers.setText("75,00,000");
                tvAmountInWords.setText("Seventy five lakh");break;
            case 16: tvAmountInNumbers.setText("1,00,00,000");
                tvAmountInWords.setText("One crore");
                tvCongrats.setVisibility(View.VISIBLE);
//                homeViewModel.setScore(0);
                break;
            case 0: tvAmountInNumbers.setText("00");
                tvAmountInWords.setText("Eggs");
        }

        HomeFragment.correctCountScore=0;
      //  score1= HomeFragment.correctCountScore;
      //  binding.tvCheck.setText(String.valueOf(score1));




        //HomeFragment.correctCountScore=0;
       // homeViewModel.resetScore();


        //Player name
        auth=FirebaseAuth.getInstance();
        uid= Objects.requireNonNull(auth.getCurrentUser()).getUid();
        databaseReference=homeViewModel.getUserdatabaseReference().child("users");
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                binding.playerName.setText(snapshot.child("Name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.navigate_to_home);
            }
        });

        return root;
    }
}