package com.example.quizapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //All necessary variables

    final int RESET_VALUE = 0;

    final String SAVED_SCORE = "Score";

    final String SAVED_QUES_ONE_SCORE_INCREASED = "Q1 Score Increased";
    final String SAVED_QUES_ONE_ANS_PRESSED = "Q1 Answer Pressed";

    final String SAVED_QUES_TWO_SCORE_INCREASE = "Q2 Score Increased";

    final String SAVED_QUES_THREE_STRINGS_MATCH = "Q3 Strings Match";
    final String SAVED_QUES_THREE_SCORE_INCREASED = "Q3 Score Increased";

    final String SAVED_QUES_FOUR_STRINGS_MATCH = "Q4 Strings Match";
    final String SAVED_QUES_FOUR_SCORE_INCREASED = "Q4 Score Increased";

    final String SAVED_QUES_FIVE_SCORE_INCREASED = "Q5 Scored Increased";
    final String SAVED_QUES_FIVE_ANS_PRESSED = "Q5 Button Pressed";

    int mScoreCounter;

    boolean hasQuestionOneScoreIncreased; //If user already got a point for the question
    boolean hasQuestionOneAnsBeenPressed; //Have they already selected the correct answer

    boolean hasQuestionTwoScoreIncreased; //Have they already scored a point for this question

    boolean doQuestionThreeStringsMatch; //does the user input match the correct answer string
    boolean hasQuestionThreeScoreIncreased; //has the user scored a point for this question

    boolean doQuestionFourStringsMatch; //does the user input match the correct answer string
    boolean hasQuestionFourScoreIncreased; //has the user scored a point for this question

    boolean hasQuestionFiveScoreIncreased; //If user already got a point for the question
    boolean hasQuestionFiveAnsBeenPressed; //Have they already selected the correct answer

    TextView questionNumberOneTextView;
    TextView questionNumberTwoTextView;
    TextView questionNumberThreeTextView;
    TextView questionNumberFourTextView;
    TextView questionNumberFiveTextView;

    RadioGroup questionOneRadioGroup;
    RadioGroup questionFiveRadioGroup;

    EditText questionThreeEditText;
    EditText questionFourEditText;

    CheckBox questionTwoAnswerTwoCheckBox;
    CheckBox questionTwoAnswerFiveCheckBox;
    CheckBox questionTwoAnswerThreeCheckBox;
    CheckBox questionTwoAnswerOneCheckBox;
    CheckBox questionTwoAnswerFourCheckBox;

    Button questionThreeOkButton;
    Button questionFourOkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Restores all saved values after screen rotation
        if (savedInstanceState != null) {
            mScoreCounter = savedInstanceState.getInt(SAVED_SCORE, RESET_VALUE);
            hasQuestionOneScoreIncreased = savedInstanceState.getBoolean(SAVED_QUES_ONE_SCORE_INCREASED, hasQuestionOneScoreIncreased);
            hasQuestionOneAnsBeenPressed = savedInstanceState.getBoolean(SAVED_QUES_ONE_ANS_PRESSED, hasQuestionOneAnsBeenPressed);
            hasQuestionTwoScoreIncreased = savedInstanceState.getBoolean(SAVED_QUES_TWO_SCORE_INCREASE, hasQuestionTwoScoreIncreased);
            doQuestionThreeStringsMatch = savedInstanceState.getBoolean(SAVED_QUES_THREE_STRINGS_MATCH, doQuestionThreeStringsMatch);
            hasQuestionThreeScoreIncreased = savedInstanceState.getBoolean(SAVED_QUES_THREE_SCORE_INCREASED, hasQuestionThreeScoreIncreased);
            doQuestionThreeStringsMatch = savedInstanceState.getBoolean(SAVED_QUES_FOUR_STRINGS_MATCH, doQuestionFourStringsMatch);
            hasQuestionFourScoreIncreased = savedInstanceState.getBoolean(SAVED_QUES_FOUR_SCORE_INCREASED, hasQuestionFourScoreIncreased);
            hasQuestionFiveAnsBeenPressed = savedInstanceState.getBoolean(SAVED_QUES_FIVE_ANS_PRESSED, hasQuestionFiveAnsBeenPressed);
            hasQuestionFiveScoreIncreased = savedInstanceState.getBoolean(SAVED_QUES_FIVE_SCORE_INCREASED, hasQuestionFiveScoreIncreased);
        }//End If

        questionNumberOneTextView = findViewById(R.id.question_number_1_header_id);
        questionNumberTwoTextView = findViewById(R.id.question_number_2_header_id);
        questionNumberThreeTextView = findViewById(R.id.question_number_3_header_id);
        questionNumberFourTextView = findViewById(R.id.question_number_4_header_id);
        questionNumberFiveTextView = findViewById(R.id.question_number_5_header_id);

        questionOneRadioGroup = findViewById(R.id.question_1_rg);
        questionFiveRadioGroup = findViewById(R.id.question_5_rg);

        questionThreeEditText = findViewById(R.id.question_3_edit_text);
        questionFourEditText = findViewById(R.id.question_4_edit_text);

        questionTwoAnswerOneCheckBox = findViewById(R.id.question_2_cb_id_1_correct);
        questionTwoAnswerTwoCheckBox = findViewById(R.id.question_2_cb_id_2);
        questionTwoAnswerThreeCheckBox = findViewById(R.id.question_2_cb_id_3);
        questionTwoAnswerFourCheckBox = findViewById(R.id.question_2_cb_id_4_correct);
        questionTwoAnswerFiveCheckBox = findViewById(R.id.question_2_cb_id_5);

        //All question number headings are numbered here
        questionNumberOneTextView.setText(getString(R.string.question_number_txt_format, 1));
        questionNumberTwoTextView.setText(getString(R.string.question_number_txt_format, 2));
        questionNumberThreeTextView.setText(getString(R.string.question_number_txt_format, 3));
        questionNumberFourTextView.setText(getString(R.string.question_number_txt_format, 4));
        questionNumberFiveTextView.setText(getString(R.string.question_number_txt_format, 5));

        questionThreeOkButton = findViewById(R.id.question_3_ok_button);
        questionFourOkButton = findViewById(R.id.question_4_ok_button);

        questionThreeEditText.addTextChangedListener(questionThreeAnswerFieldWatcher);
        questionFourEditText.addTextChangedListener(questionFourAnswerFieldWatcher);

    }//End onCreate

    //Saves all necessary values when screen is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_SCORE, mScoreCounter);
        outState.putBoolean(SAVED_QUES_ONE_SCORE_INCREASED, hasQuestionOneScoreIncreased);
        outState.putBoolean(SAVED_QUES_ONE_ANS_PRESSED, hasQuestionOneAnsBeenPressed);
        outState.putBoolean(SAVED_QUES_TWO_SCORE_INCREASE, hasQuestionTwoScoreIncreased);
        outState.putBoolean(SAVED_QUES_THREE_STRINGS_MATCH, doQuestionThreeStringsMatch);
        outState.putBoolean(SAVED_QUES_THREE_SCORE_INCREASED, hasQuestionThreeScoreIncreased);
        outState.putBoolean(SAVED_QUES_FOUR_STRINGS_MATCH, doQuestionFourStringsMatch);
        outState.putBoolean(SAVED_QUES_FOUR_SCORE_INCREASED, hasQuestionFourScoreIncreased);
        outState.putBoolean(SAVED_QUES_FIVE_ANS_PRESSED, hasQuestionFiveAnsBeenPressed);
        outState.putBoolean(SAVED_QUES_FIVE_SCORE_INCREASED, hasQuestionFiveScoreIncreased);
    }//End onSavedInstanceState

    //Changes the status of the OK button in Question 3 if the EditText is empty
    private TextWatcher questionThreeAnswerFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String questionThreeEditTextAnswer = questionThreeEditText.getText().toString();
            questionThreeOkButton.setEnabled(!questionThreeEditTextAnswer.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //Changes the status of the OK button in Question 4 if the EditText is empty
    private TextWatcher questionFourAnswerFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String questionFourEditTextAnswer = questionFourEditText.getText().toString();
            questionFourOkButton.setEnabled(!questionFourEditTextAnswer.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //Logic For Question One
    public void questionOneCheckAnswer(View view) {
        RadioButton questionOneAnswerRB = (RadioButton) findViewById(R.id.question_1_rb_2_correct);
        if (questionOneRadioGroup.getCheckedRadioButtonId() == questionOneAnswerRB.getId()) {

            //Stops the user from getting more than one point for the question
            if (!hasQuestionOneAnsBeenPressed) {
                hasQuestionOneAnsBeenPressed = true;
                if (!hasQuestionOneScoreIncreased) {
                    mScoreCounter++;
                    hasQuestionOneScoreIncreased = true;
                }//End If
            }//End If
        }//End If
        else {
            hasQuestionOneAnsBeenPressed = false;
            if (hasQuestionOneScoreIncreased) {
                //Prevents negative scoring
                if (mScoreCounter > 0) {
                    mScoreCounter--;
                }//End If
                hasQuestionOneScoreIncreased = false;
            }//End If
        }//End Else
    }//End questionOneCheckAnswer

    //Logic For Question Two
    public void questionTwoCheckAnswer(View view) {
        //If correct checkbox is checked
        if (questionTwoAnswerOneCheckBox.isChecked()) {
            //If incorrect checkboxes were chosen
            if (questionTwoAnswerTwoCheckBox.isChecked() || questionTwoAnswerThreeCheckBox.isChecked()
                    || questionTwoAnswerFiveCheckBox.isChecked()) {
                //Decrease score if they already scored a point for this question
                if (hasQuestionTwoScoreIncreased) {
                    hasQuestionTwoScoreIncreased = false;
                    mScoreCounter--;
                }//End If
            }//End If

            //If both correct answers are checked
            else if (questionTwoAnswerFourCheckBox.isChecked()) {
                //Prevents from getting more than one point for the question
                if (!hasQuestionTwoScoreIncreased) {
                    hasQuestionTwoScoreIncreased = true;
                    mScoreCounter++;
                }//End If
            }//End Else If

            //If the correct choice was deselected
            else if (!questionTwoAnswerFourCheckBox.isChecked()) {
                if (hasQuestionTwoScoreIncreased) {
                    hasQuestionTwoScoreIncreased = false;
                    mScoreCounter--;
                }//End If
            }//End Else If
        }//End If
        else if (hasQuestionTwoScoreIncreased) {
            hasQuestionTwoScoreIncreased = false;
            mScoreCounter--;
        }//End Else If
    }//End questionTwoCheckAnswer

    //Logic For Question Three
    public void questionThreeCheckAnswer(View view) {
        String questionThreeInputString = questionThreeEditText.getText().toString();

        //Answer without an apostrophe: " ' "
        String correctAnswerOneQuesThreeString = getString(R.string.question_3_edit_text_answer_one);

        //Answer with an apostrophe: " ' "
        String correctAnswerTwoQuesThreeString = getString(R.string.question_3_edit_text_answer_two);

        Toast.makeText(this, getString(R.string.confirmation_txt), Toast.LENGTH_SHORT).show();

        //Does user's input match either correct answer string in terms of length
        if (questionThreeInputString.equalsIgnoreCase(correctAnswerOneQuesThreeString) ||
                questionThreeInputString.equalsIgnoreCase(correctAnswerTwoQuesThreeString)) {
            doQuestionThreeStringsMatch = true;
        }//End If
        else {
            doQuestionThreeStringsMatch = false;

            //Check for spaces at the front or the back of the user's answer and alert them
            if (questionThreeInputString.charAt(0) == ' ' &&
                    questionThreeInputString.charAt(questionThreeInputString.length() - 1) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_one), Toast.LENGTH_LONG).show();
            }//End Else If

            //Check for spaces at the front of the user's answer
            else if (questionThreeInputString.charAt(0) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_two), Toast.LENGTH_LONG).show();
            }//End Else If

            //Check for spaces at the back of the user's answer
            else if (questionThreeInputString.charAt(questionThreeInputString.length() - 1) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_three), Toast.LENGTH_LONG).show();
            }//End Else If

            if (hasQuestionThreeScoreIncreased) {
                //Prevents negative scoring
                if (mScoreCounter > 0) {
                    mScoreCounter--;
                    hasQuestionThreeScoreIncreased = false;
                }//End If
            }//End Else
        }//End Else
        //Score a point for correct answer
        if (doQuestionThreeStringsMatch && !hasQuestionThreeScoreIncreased) {
            mScoreCounter++;
            hasQuestionThreeScoreIncreased = true;
        }//End If
        closeKeyBoard(view);
    }//End questionThreeCheckAnswer

    //Logic For Question Four, same as questionThreeCheckAnswer method
    public void questionFourCheckAnswer(View view) {
        String questionFourInputString = questionFourEditText.getText().toString();
        String correctAnswerQuesFourString = getString(R.string.question_4_edit_text_answer);

        Toast.makeText(this, getString(R.string.confirmation_txt), Toast.LENGTH_SHORT).show();

        //Do the strings match in length
        if (questionFourInputString.equalsIgnoreCase(correctAnswerQuesFourString)) {
            //Compare each character from user input to those of the correct answer
            doQuestionFourStringsMatch = true;
        }//End If
        else {
            doQuestionFourStringsMatch = false;

            //Check for spaces at the front or the back of the user's answer and alert them
            if (questionFourInputString.charAt(0) == ' ' &&
                    questionFourInputString.charAt(questionFourInputString.length() - 1) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_one), Toast.LENGTH_LONG).show();
            }//End If

            //Check for spaces at the front of the user's answer
            else if (questionFourInputString.charAt(0) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_two), Toast.LENGTH_LONG).show();
            }//End Else If

            //Check for spaces at the back of the user's answer
            else if (questionFourInputString.charAt(questionFourInputString.length() - 1) == ' ') {
                Toast.makeText(this, getString(R.string.edit_text_error_message_three), Toast.LENGTH_LONG).show();
            }//End If

            //Prevent negative scoring
            if (hasQuestionFourScoreIncreased) {
                if (mScoreCounter > 0) {
                    mScoreCounter--;
                    hasQuestionFourScoreIncreased = false;
                }//End If
            }//End If

        }//End Else

        //If they haven't scored a point for the correct answer yet
        if (doQuestionFourStringsMatch && !hasQuestionFourScoreIncreased) {
            mScoreCounter++;
            hasQuestionFourScoreIncreased = true;
        }//End If
        closeKeyBoard(view);
    }//End questionFourCheckAnswer

    //Logic For Question Five
    public void questionFiveCheckAnswer(View view) {
        RadioButton questionFiveAnswerRB = (RadioButton) findViewById(R.id.question_5_rb_1_correct);
        if (questionFiveRadioGroup.getCheckedRadioButtonId() == questionFiveAnswerRB.getId()) {

            //Stops the user from getting more than one point for the question
            if (!hasQuestionFiveAnsBeenPressed) {
                hasQuestionFiveAnsBeenPressed = true;
                if (!hasQuestionFiveScoreIncreased) {
                    mScoreCounter++;
                    hasQuestionFiveScoreIncreased = true;
                }//End If
            }//End Else
        }//End If
        else {
            hasQuestionFiveAnsBeenPressed = false;
            if (hasQuestionFiveScoreIncreased) {
                //Prevents negative scoring
                if (mScoreCounter > 0) {
                    mScoreCounter--;
                }//End If
                hasQuestionFiveScoreIncreased = false;
            }//End If
        }//End Else
    }//End questionFiveCheckAnswer

    //Check all answers and gives a tally of the score
    public void checkAnswers(View view) {
        closeKeyBoard(view);
        Toast.makeText(this, getString(R.string.score_toast_message, mScoreCounter, 5), Toast.LENGTH_LONG).show();
        resetAll();
    }//End checkAnswers

    //Collapses onscreen keyboard after user enters answer
//Also prevents screen from scrolling to edit text question after pressing 'Answer' button
    private void closeKeyBoard(View view) {
        //variable 'view' is necessary to prevent scrolling effect from happening
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }//End If
    }//End closeKeyBoard

    //Resets all variables and views to default setting
    private void resetAll() {
        mScoreCounter = RESET_VALUE;
        hasQuestionTwoScoreIncreased = false;
        hasQuestionFourScoreIncreased = false;
        doQuestionFourStringsMatch = false;
        doQuestionThreeStringsMatch = false;
        hasQuestionThreeScoreIncreased = false;
        hasQuestionFiveAnsBeenPressed = false;
        hasQuestionFiveScoreIncreased = false;
        hasQuestionOneScoreIncreased = false;
        hasQuestionOneAnsBeenPressed = false;
        questionOneRadioGroup.clearCheck();
        questionFiveRadioGroup.clearCheck();
        questionTwoAnswerOneCheckBox.setChecked(false);
        questionTwoAnswerTwoCheckBox.setChecked(false);
        questionTwoAnswerThreeCheckBox.setChecked(false);
        questionTwoAnswerFourCheckBox.setChecked(false);
        questionTwoAnswerFiveCheckBox.setChecked(false);
        questionThreeEditText.getText().clear();
        questionThreeEditText.clearFocus();
        questionFourEditText.getText().clear();
        questionFourEditText.clearFocus();
    }//End resetAll

}//End MainActivity
