package com.example.navegandoentrefragmentoseadicionandotexto.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.navegandoentrefragmentoseadicionandotexto.fragments.MesesFragment;

public class MonthPagerAdapter extends FragmentStateAdapter {

    // Array de nomes dos meses
    private static final String[] MONTHS = {
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };

    // Período de tempo (2018 a 2100)
    private static final int START_YEAR = 2018;
    private static final int END_YEAR = 2100;
    private static final int TOTAL_MONTHS = (END_YEAR - START_YEAR + 1) * 12;

    public MonthPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Calcula o ano e o mês com base na posição
        int year = START_YEAR + (position / 12);
        int monthIndex = position % 12;
        String monthName = MONTHS[monthIndex];

        // Passa o nome do mês e o ano para o fragmento
        return MesesFragment.newInstance(monthName, year);
    }

    @Override
    public int getItemCount() {
        return TOTAL_MONTHS; // Total de meses entre 2018 e 2100
    }
}
