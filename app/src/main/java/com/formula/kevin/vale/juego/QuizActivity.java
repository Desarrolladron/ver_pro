package com.formula.kevin.vale.juego;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.formula.kevin.vale.R;
import com.mrntlu.toastie.Toastie;
import com.scwang.wave.MultiWaveHeader;


import java.util.Random;




public class QuizActivity extends AppCompatActivity {

    private RetainedFragment dataFragment;
    TextView operationText;
    TextView answerText;
    Button submitButton;
    Button clearButton;
    CountDownTimer cdt;
    TextView num1Text;
    TextView num2Text;
    TextView timer;
    TextView questionNumber;
    Dialog epicDialog;
     ImageView cerrar;
     TextView resultado;
    long millisLeft;
    Button botonvamo;
      LottieAnimationView animationWithLottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionNumber = (TextView) findViewById(R.id.qNumber);
        operationText = (TextView) findViewById(R.id.operation);
        num1Text = (TextView) findViewById(R.id.numText1);
        num2Text = (TextView) findViewById(R.id.numText2);
        timer = (TextView) findViewById(R.id.timer);
        answerText = (TextView) findViewById(R.id.answer);
        submitButton = (Button) findViewById(R.id.enter);
        clearButton = (Button) findViewById(R.id.clear);
        animationWithLottie =(LottieAnimationView)findViewById(R.id.Animacion);
        MultiWaveHeader waveHeader;
  /*
        Setting up toolbar with back button
         */
/*
        Toolbar myToolbar = (Toolbar) findViewById(R.id.quizToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setContentInsetsAbsolute(0, 0);
        myToolbar.setBackground(new ColorDrawable(Color.LTGRAY));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


 */
        /*
        Ancestral Navigation by clicking home button.
        A Dialog is generated on clicking home button, confirming to exit
        */
/*
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitQuizDialog();
            }
        });


 */

        waveHeader = findViewById(R.id.wave_header);

        waveHeader.setVelocity(6);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(70);
        waveHeader.setStartColor(Color.argb(100,61,126,254));
        waveHeader.setCloseColor(Color.argb(100,97,200,180));

        //Get operation from launcher activity
        Bundle extras = getIntent().getExtras();
        final String operation = extras.getString("operation");

        if (!isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null) {

            QuizContext quizData = (QuizContext) savedInstanceState.getSerializable("data");

            if (quizData != null) {
                System.err.println("quizdata " + quizData.getMillisLeft());
            } else {
                System.err.println("quizdata null");
            }

            dataFragment = (RetainedFragment) fm.findFragmentByTag("data");
            QuizContext instance = dataFragment.getData();

            QuizContext.getInstance().updateInstance(instance);
            System.err.println("instance " + instance.getMillisLeft() + " quizContext " + QuizContext.getInstance().getMillisLeft());
            if (QuizContext.getInstance().getMillisLeft() != 0) {
                millisLeft = QuizContext.getInstance().getMillisLeft();
                cdt = new CountDownTimer(millisLeft, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setOnTick(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        nextQuestion();
                    }
                }.start();
            }
        } else {
            dataFragment = new RetainedFragment();
            getSupportFragmentManager().beginTransaction().add(dataFragment, "data").commit();
            dataFragment.setData(QuizContext.getInstance());
            QuizContext.getInstance().setOperation(operation);

            Random randomNumberGenerator = new Random();
            //Generate Random Numbers
            int num1 = randomNumberGenerator.nextInt((25) + 1) + 1;
            QuizContext.getInstance().setNum1(num1);


            if (operation.equals("sub")) {
                //For subtraction generate number smaller than num1
                int num2 = randomNumberGenerator.nextInt(num1) + 1;
                QuizContext.getInstance().setNum2(num2);
            } else {
                int num2 = randomNumberGenerator.nextInt((25) + 1) + 1;
                QuizContext.getInstance().setNum2(num2);
            }


            //Count Down Timer for 5 seconds
            cdt = new CountDownTimer(4700, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setOnTick(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    nextQuestion();
                }
            }.start();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerText.getText().toString();
                if (answer.equals("")) {
                    Toastie.centerInfo(QuizActivity.this,"Vacio",Toast.LENGTH_SHORT).show();

                } else {
                    QuizContext.getInstance().setAnswer(Integer.parseInt(answer));
                    int solution = 0;
                    String operation = QuizContext.getInstance().getOperation();
                    switch (operation) {
                        case "add":
                            solution = QuizContext.getInstance().getNum1() + QuizContext.getInstance().getNum2();
                            break;
                        case "mul":
                            solution = QuizContext.getInstance().getNum1() * QuizContext.getInstance().getNum2();
                            break;
                        case "sub":
                            solution = QuizContext.getInstance().getNum1() - QuizContext.getInstance().getNum2();
                            break;
                    }

                    if (solution == QuizContext.getInstance().getAnswer()) {
                        int points = QuizContext.getInstance().getPoints();
                        QuizContext.getInstance().setPoints(++points);
                        Toastie.centerSuccess(QuizActivity.this,"Correcto",Toast.LENGTH_SHORT).show();

                        nextQuestion();
                    } else {

                        Toastie.centerInfo(QuizActivity.this,"Incorrecto",Toast.LENGTH_SHORT).show();
                        nextQuestion();
                    }
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerText.setText("");
            }
        });

    }

    //Generate a Dialog on back key pressed
    @Override
    public void onBackPressed() {
        showExitQuizDialog();
    }

    public void showExitQuizDialog() {


        cdt.cancel();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder.setMessage("¿SALIR DEL JUEGO?");


        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QuizContext.getInstance().resetContext();
                cdt.cancel();

                 Intent intent = new Intent(QuizActivity.this, LauncherActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cdt = new CountDownTimer(millisLeft, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setOnTick(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        nextQuestion();
                    }
                }.start();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        lockDialogBackground(alertDialog);
        alertDialog.show();


    }

    public void setOnTick(long millisUntilFinished) {
        millisLeft = millisUntilFinished;
        int timeLeft = (int) millisUntilFinished / 1000;


        timer.setText(String.valueOf(String.valueOf(timeLeft)));
        switch (QuizContext.getInstance().getOperation()) {
            case "add":
                operationText.setText("+");
                break;
            case "sub":
                operationText.setText("-");
                break;
            case "mul":
                operationText.setText("x");
                break;
        }
        String answer = answerText.getText().toString();
        if (!answer.equals("")) {
            QuizContext.getInstance().setAnswer(Integer.parseInt(answer));
            int solution = 0;
            String operation = QuizContext.getInstance().getOperation();
            switch (operation) {
                case "add":
                    solution = QuizContext.getInstance().getNum1() + QuizContext.getInstance().getNum2();
                    break;
                case "mul":
                    solution = QuizContext.getInstance().getNum1() * QuizContext.getInstance().getNum2();
                    break;
                case "sub":
                    solution = QuizContext.getInstance().getNum1() - QuizContext.getInstance().getNum2();
                    break;
            }

            if (solution == QuizContext.getInstance().getAnswer()) {
                int points = QuizContext.getInstance().getPoints();
                QuizContext.getInstance().setPoints(++points);

         Toastie.centerSuccess(QuizActivity.this,"Correcto",Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        }
        if (!questionNumber.getText().equals("#10"))
            questionNumber.setText("#" + QuizContext.getInstance().getNumberOfQuestions());
        num1Text.setText(String.valueOf(QuizContext.getInstance().getNum1()));
        num2Text.setText(String.valueOf(QuizContext.getInstance().getNum2()));

    }

    //Set Answer Text
    public void setValue(View v) {
        String answer = answerText.getText().toString();
        if (answer.length() <= 2)
            answerText.append(v.getTag().toString());
    }

    //Go to next question
    public void nextQuestion() {
        cdt.cancel(); //Cancel current timer and move to next question
        QuizContext.getInstance().setNumberOfQuestions(QuizContext.getInstance().getNumberOfQuestions() + 1);
        if (QuizContext.getInstance().getNumberOfQuestions() <= 10) {
            //Still Questions left, Reload this activity
            finish();
             startActivity(getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {
            animationWithLottie.cancelAnimation();
            /*
            //Al questions finished, Go to Result Activity
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            alertDialogBuilder.setMessage("Your Score: " + QuizContext.getInstance().getPoints() + "\n" + "Play Again?");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    QuizContext.getInstance().resetContext();
                    Intent intent = new Intent(QuizActivity.this, LauncherActivity2.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            lockDialogBackground(alertDialog);
            alertDialog.show();

             */
            mostrarInfo();
        }
    }

/*
    //Generate a Toast for half millisecond
    private void generateSmallToast(String info) {
        final Toast toast = Toast.makeText(getBaseContext(), info, Toast.LENGTH_SHORT);
        toast.show();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },
                500);
    }

 */

    //Lock background screen on dialog
    private void lockDialogBackground(AlertDialog alertDialog) {
        Window window = alertDialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cdt.cancel();
        if (millisLeft != 0) {
            QuizContext.getInstance().setMillisLeft(millisLeft);
        }
        dataFragment.setData(QuizContext.getInstance());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cdt = new CountDownTimer(millisLeft, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                int timeLeft = (int) millisUntilFinished / 1000;
                timer.setText(String.valueOf(String.valueOf(timeLeft)));
                switch (QuizContext.getInstance().getOperation()) {
                    case "add":
                        operationText.setText("+");
                        break;
                    case "sub":
                        operationText.setText("-");
                        break;
                    case "mul":
                        operationText.setText("x");
                        break;
                }
                String answer = answerText.getText().toString();
                if (!answer.equals("")) {
                    QuizContext.getInstance().setAnswer(Integer.parseInt(answer));
                    int solution = 0;
                    String operation = QuizContext.getInstance().getOperation();
                    switch (operation) {
                        case "add":
                            solution = QuizContext.getInstance().getNum1() + QuizContext.getInstance().getNum2();
                            break;
                        case "mul":
                            solution = QuizContext.getInstance().getNum1() * QuizContext.getInstance().getNum2();
                            break;
                        case "sub":
                            solution = QuizContext.getInstance().getNum1() - QuizContext.getInstance().getNum2();
                            break;
                    }

                    if (solution == QuizContext.getInstance().getAnswer()) {
                        int points = QuizContext.getInstance().getPoints();
                        QuizContext.getInstance().setPoints(++points);
                          //mostrar animaciones

                         nextQuestion();
                    }
                }
                if (!questionNumber.getText().equals("#10"))
                    questionNumber.setText("#" + QuizContext.getInstance().getNumberOfQuestions());
                num1Text.setText(String.valueOf(QuizContext.getInstance().getNum1()));
                num2Text.setText(String.valueOf(QuizContext.getInstance().getNum2()));
            }

            @Override
            public void onFinish() {
                nextQuestion();
            }
        }.start();

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("data", QuizContext.getInstance());
        outState.putInt("testInt", 99);

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        QuizContext quizData = (QuizContext) savedInstanceState.getSerializable("data");

        if (quizData != null) {
            System.err.println("quizdata " + quizData.getMillisLeft());
        } else {
            System.err.println("quizdata null");
        }

        System.err.println("testInt " + savedInstanceState.getInt("testInt"));

    } public void mostrarInfo(){
        epicDialog = new Dialog(this);
        epicDialog.setContentView(R.layout.custom_resultado);
        cerrar = (ImageView)epicDialog.findViewById(R.id.cerrarVentana);
         resultado =(TextView)epicDialog.findViewById(R.id.txtResultado);
        resultado.setText("Puntaje Obtenido: " + QuizContext.getInstance().getPoints() );
        botonvamo =(Button)epicDialog.findViewById(R.id.botonvamo);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizContext.getInstance().resetContext();
                Intent intent = new Intent(QuizActivity.this, LauncherActivity2.class);
                startActivity(intent);
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         epicDialog.setCancelable(false);
         epicDialog.setCanceledOnTouchOutside(false);
        epicDialog.show();
        botonvamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizContext.getInstance().resetContext();
                Intent intent = new Intent(QuizActivity.this, LauncherActivity2.class);
                startActivity(intent);
            }
        });
    }



}

