package com.example.subject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText Edit1, Edit2;
    Button btnCalculate;
    TextView textResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("BMI 계산기");
        Edit1 = findViewById(R.id.Edit1); // 몸무게
        Edit2 = findViewById(R.id.Edit2); // 키
        btnCalculate = findViewById(R.id.btnCalculate);
        textResult = findViewById(R.id.TextResult);
        ImageView bmiImage = findViewById(R.id.bmiImage);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String weightInput = Edit1.getText().toString().trim();
                    String heightInput = Edit2.getText().toString().trim().toLowerCase();

                    if (weightInput.isEmpty() || heightInput.isEmpty()) {
                        textResult.setText("몸무게와 키를 입력하세요.");
                        return;
                    }

                    double weight = Double.parseDouble(weightInput);
                    double height;

                    // 키 단위 판별 및 처리
                    if (heightInput.contains("m")) {
                        heightInput = heightInput.replace("m", "").trim();
                        height = Double.parseDouble(heightInput);
                    } else if (heightInput.contains("cm")) {
                        heightInput = heightInput.replace("cm", "").trim();
                        height = Double.parseDouble(heightInput) / 100.0;
                    } else {
                        // 숫자만 입력한 경우: 자릿수로 추정
                        double rawHeight = Double.parseDouble(heightInput);
                        height = (rawHeight > 3) ? rawHeight / 100.0 : rawHeight;
                    }

                    if (height <= 0) {
                        textResult.setText("키는 0보다 커야 합니다.");
                        return;
                    }

                    double bmi = weight / (height * height);
                    String status;

                    if (bmi < 18.5) {
                        status = "저체중";
                    } else if (bmi < 23) {
                        status = "정상";
                        bmiImage.setImageResource(R.drawable.bow);
                        bmiImage.setVisibility(View.VISIBLE);
                    } else if (bmi < 25) {
                        status = "과체중";
                    } else if (bmi < 30) {
                        status = "비만";
                    } else {
                        status = "고도비만";
                    }

                    String resultText = String.format("당신의 BMI는 %.2f입니다.\n건강 상태: %s", bmi, status);
                    textResult.setText(resultText);

                } catch (NumberFormatException e) {
                    textResult.setText("입력 오류: 숫자만 입력해주세요.");
                }
            }

        });
    }
}
