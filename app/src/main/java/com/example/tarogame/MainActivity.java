package com.example.tarogame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextQuestion;
    private Button buttonBayCard;
    private Button buttonCross;
    private Button buttonThreeCards;
    private int alignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        Button buttonGoGame = findViewById(R.id.buttonGoGame);
        buttonBayCard = findViewById(R.id.buttonВayCard);
        buttonCross = findViewById(R.id.buttonCross);
        buttonThreeCards = findViewById(R.id.buttonThreeCards);
        Button buttonExitGo = findViewById(R.id.buttonExitGo);


        buttonGoGame.setOnClickListener(view -> {
            if (editTextQuestion.getText()!=null && editTextQuestion.getText().length()>0){
                if (alignment!= 0){
                    switch (alignment){
                        case 1:Intent intent = new Intent(getApplicationContext(), BayCard.class);
                            startActivity(intent);
                            break;
                        case 2:Intent intent2 = new Intent(getApplicationContext(), Cross.class);
                            startActivity(intent2);
                            break;
                        case 3:Intent intent3 = new Intent(getApplicationContext(), ThreeCards.class);
                            startActivity(intent3);
                            break;
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Выберите расклад", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(MainActivity.this, "Введите ваш вопрос.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonExitGo.setOnClickListener(view -> finishAffinity());

    }

    public void clickDayCard(View view) {
        alignment = 1;
        buttonBayCard.setBackgroundColor(getResources().getColor(R.color.click));
        buttonCross.setBackgroundColor(getResources().getColor(R.color.button));
        buttonThreeCards.setBackgroundColor(getResources().getColor(R.color.button));
    }

    public void clickCross(View view) {
        alignment = 2;
        buttonCross.setBackgroundColor(getResources().getColor(R.color.click));
        buttonBayCard.setBackgroundColor(getResources().getColor(R.color.button));
        buttonThreeCards.setBackgroundColor(getResources().getColor(R.color.button));
    }

    public void clickThreeCards(View view) {
        alignment = 3;
        buttonThreeCards.setBackgroundColor(getResources().getColor(R.color.click));
        buttonCross.setBackgroundColor(getResources().getColor(R.color.button));
        buttonBayCard.setBackgroundColor(getResources().getColor(R.color.button));
    }

    public void onClickInfoCard(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Карта дня");
        builder.setMessage("В зависимости от того, что тебя интересует, делаются разные расклады. Есть очень простое гадание Таро на самое ближайшее будущее «Карта дня» — для того, чтобы узнать, что тебя ждет в ближайшие 24 часа, понадобится всего одна карта. Она расскажет о том, на что тебе обратить внимание сегодня. Главное — правильная интерпретация. Новички часто подходят слишком прямолинейно к трактовке значения. Попробую прочитать толкование несколько раз, прежде чем сделаешь выводы.");
        builder.setPositiveButton("Закрыть", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickInfoCross(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Крест");
        builder.setMessage("Один из самых простых раскладов Таро позволяет получить четкие ответы на вопросы о будущем. Его часто используют в своих гаданиях цыгане.\n" +
                "Перед гаданием важно правильно настроиться: можно предварительно медитировать или просто побыть в одиночестве и тишине.\n" +
                "Из колоды вынимаются 4 карты. Значение позиций карт в раскладе:\n" +
                "\n" +
                "Карта №1 (левая) — описывает ситуацию, о которой идет речь;\n" +
                "\n" +
                "Карта №2 (правая) — то, что сейчас делать не нужно;\n" +
                "\n" +
                "Карта №3 (вверху)  — то, что нужно обязательно сделать;\n" +
                "\n" +
                "Карта №4 (внизу) — какой результат тебя ждет.");
        builder.setPositiveButton("Закрыть", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickInfoThreeCards(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Три карты");
        builder.setMessage("Пожалуй, самый простой способ узнать будущее — сделать расклад «Три карты». Он позволяет получить конкретные ответы на ситуацию в будущем. Важно не относиться к этом раскладу поверхностно. Магия Таро требует серьезной подготовки к работе с картами.\n" +
                "В гадании участвую карты всех Арканов. Вытягиваются три карты.\n" +
                "\n" +
                "Карта №1— прошлое: карта, которая раскладывает о событиях в прошлом, которые привели к текущему состоянию дел.\n" +
                "\n" +
                "Карта №2 — настоящее: эта карта расскажет о том, как ситуация объективно выглядит со стороны сейчас.\n" +
                "\n" +
                "Карта №3 — будущее: эта карта предскажет возможный вариант развития событий.");
        builder.setPositiveButton("Закрыть", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}