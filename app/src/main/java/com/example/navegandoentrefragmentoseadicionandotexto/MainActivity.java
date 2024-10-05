package com.example.navegandoentrefragmentoseadicionandotexto;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.navegandoentrefragmentoseadicionandotexto.adapter.MonthPagerAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MonthPagerAdapter adapter;
    private ImageButton btnPrevious, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);

        adapter = new MonthPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Obtém o mês e ano atuais
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);  // Mês atual (0-11)
        int currentYear = calendar.get(Calendar.YEAR);    // Ano atual

        // Calcula a posição inicial do ViewPager com base no mês e ano atuais
        int startPosition = (currentYear - 2018) * 12 + currentMonth;
        viewPager.setCurrentItem(startPosition, false);

        // Botão de voltar mês
        btnPrevious.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        // Botão de próximo mês
        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });


    }//fim do método onCreate
}