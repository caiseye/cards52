package com.hanabank.kaist.cards52;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView cardImageView;
    private TextView cardTextView;

    private ImageButton btnReStart;
    private String[] cardVal = {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
    private String[] cardFace = {"spades", "diamonds", "hearts", "clubs"};
    private List<String> usedCard = new ArrayList<>();
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReStart = findViewById(R.id.btnReStart);
        cardImageView = findViewById(R.id.imageView);
        cardTextView = findViewById(R.id.textView);
        random = new Random();

        btnReStart.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              usedCard.clear();
              int cardBackId = getResources().getIdentifier("back", "drawable", getPackageName());
              cardImageView.setImageResource(cardBackId);
              cardTextView.setText(usedCard.size() + "/52");
          }
        });

        cardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardName = getRandomUnusedCard();

                int cardResId = getResources().getIdentifier(cardName, "drawable", getPackageName());

                cardImageView.setImageResource(cardResId);
                cardTextView.setText(usedCard.size() + "/52");

            }
        });

        if (savedInstanceState != null) {
            usedCard = savedInstanceState.getStringArrayList("usedCard");
        }
        if (usedCard.size() > 0) {
            String lastCard = usedCard.get(usedCard.size() - 1);
            int lastCardResId = getResources().getIdentifier(lastCard, "drawable", getPackageName());
            cardImageView.setImageResource(lastCardResId);
            cardTextView.setText(usedCard.size() + "/52");
        }
    }

    private String getRandomUnusedCard() {

        String cardName;

        if (usedCard.size() == 52) {
            cardName = "back";
            usedCard.clear();
            return cardName;
        }

        do {
            int cardValue = random.nextInt(13);
            int cardSuit = random.nextInt(4);

            cardName = cardVal[cardValue] + "_of_" + cardFace[cardSuit];
        } while (usedCard.contains(cardName));

        usedCard.add(cardName);

        return cardName;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("usedCard", (ArrayList<String>) usedCard);
    }
}
