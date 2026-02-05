package com.example.flippuzzlegame;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
public class MainActivity extends AppCompatActivity {

    ImageView card1, card2, card3, card4;

    int[] images = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img2,
            R.drawable.img1
    };

    ImageView firstCard = null;
    int firstImage = -1;
    boolean isBusy = false;
    int matchedPairs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        ImageView[] cards = {card1, card2, card3, card4};

        // Shuffle images
        shuffleArray(images);

        for (int i = 0; i < cards.length; i++) {
            final ImageView card = cards[i];
            final int image = images[i];

            card.setOnClickListener(v -> handleCardClick(card, image));
        }
    }

    private void handleCardClick(ImageView card, int image) {

        if (isBusy || card.getTag() != null) return;

        card.setImageResource(image);

        if (firstCard == null) {
            firstCard = card;
            firstImage = image;
            card.setTag("opened");
        } else {
            isBusy = true;

            if (firstImage == image) {
                // MATCH FOUND
                matchedPairs++;
                card.setTag("opened");
                firstCard = null;
                isBusy = false;

                if (matchedPairs == 2) {
                    showWinnerDialog();
                }

            } else {
                // NOT MATCH
                new Handler().postDelayed(() -> {
                    card.setImageResource(R.drawable.card_back);
                    firstCard.setImageResource(R.drawable.card_back);
                    firstCard.setTag(null);
                    firstCard = null;
                    isBusy = false;
                }, 800);
            }
        }
    }

    private void showWinnerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("🎉 You Win!")
                .setMessage("All pairs matched successfully!")
                .setCancelable(false)
                .setPositiveButton("Play Again", (d, w) -> recreate())
                .show();
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
