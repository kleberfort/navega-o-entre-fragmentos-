package com.example.navegandoentrefragmentoseadicionandotexto.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navegandoentrefragmentoseadicionandotexto.R;
import com.example.navegandoentrefragmentoseadicionandotexto.adapter.MonthRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MesesFragment extends Fragment {

    private static final String ARG_MONTH_NAME = "month_name";
    private static final String ARG_YEAR = "year";

    private String monthName;
    private int year;
    private RecyclerView recyclerView;
    private MonthRecyclerViewAdapter adapter;

    private List<String> monthData;

    private static Map<String, List<String>> storedTexts = new HashMap<>();

    public static MesesFragment newInstance(String monthName, int year) {
        MesesFragment fragment = new MesesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MONTH_NAME, monthName);
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monthName = getArguments().getString(ARG_MONTH_NAME);
            year = getArguments().getInt(ARG_YEAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar o layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_meses, container, false);

        // Inicializar a lista de dados
        monthData = new ArrayList<>();

        // Configura o TextView para exibir o nome do mês e o ano
        TextView monthTextView = view.findViewById(R.id.text_month);
        monthTextView.setText(String.format("%s %d", monthName, year)); // Exibe o mês e ano no TextView

        // Configura o RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MonthRecyclerViewAdapter(monthData); // Inicializa o adaptador com a lista de dados
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Configura o LayoutManager

        // Carrega dados previamente salvos
        loadFromPreferences(monthName, year);

        // Configurar o FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddTextDialog());

        return view;
    }

    // Método para mostrar o diálogo e adicionar um novo texto
    private void showAddTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Adicionar Texto");

        // Configurar o input de texto
        final EditText input = new EditText(getContext());
        builder.setView(input);

        // Configurar os botões do diálogo
        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String newText = input.getText().toString().trim();

            // Adicionar o texto à lista
            if (!newText.isEmpty()) { // Verifica se o texto não está vazio
                monthData.add(newText);
                adapter.notifyDataSetChanged(); // Notifica o adaptador para atualizar o RecyclerView

                // Salvar no SharedPreferences
                saveToPreferences(monthName, year, newText);

                // Exibir uma mensagem de Toast
                Toast.makeText(getContext(), "Texto adicionado para: " + monthName + " " + year, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        // Exibir o diálogo
        builder.show();
    }

    private void saveToPreferences(String month, int year, String text) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Chave única para armazenar os dados do mês e ano
        String key = month + "_" + year;

        // Adiciona o texto ao SharedPreferences
        String existingText = sharedPreferences.getString(key, ""); // Recupera o texto existente
        String newText = existingText.isEmpty() ? text : existingText + ";" + text; // Adiciona o novo texto

        editor.putString(key, newText); // Salva no SharedPreferences
        editor.apply(); // Aplica as mudanças
    }

    private void loadFromPreferences(String month, int year) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String key = month + "_" + year;
        String savedText = sharedPreferences.getString(key, "");

        if (!savedText.isEmpty()) {
            String[] items = savedText.split(";"); // Divide o texto armazenado
            monthData.clear(); // Limpa os dados existentes
            monthData.addAll(Arrays.asList(items)); // Adiciona os novos itens à lista
            adapter.notifyDataSetChanged(); // Notifica o adaptador para atualizar o RecyclerView
        }
    }

    // Retorna os textos armazenados para o mês e ano atuais
    private List<String> getStoredTextsForMonthAndYear() {
        String key = monthName + "-" + year;
        if (!storedTexts.containsKey(key)) {
            storedTexts.put(key, new ArrayList<>());
        }
        return storedTexts.get(key);
    }
}
