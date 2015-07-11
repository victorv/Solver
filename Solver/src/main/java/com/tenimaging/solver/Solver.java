// Copyright 2015 10 Imaging Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.tenimaging.solver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.text.method.ScrollingMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class Solver extends ActionBarActivity {
    private TextView textView;
    private Button solveCircularButton, solveLinearButton;
    private Button startNumberButton, endNumberButton;
    private EditText startNumber, endNumber;
    private int startNum, endNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);

        startNumberButton = (Button) findViewById(com.tenimaging.solver.R.id.button);
        endNumberButton = (Button) findViewById(com.tenimaging.solver.R.id.button2);
        startNumberButton.setEnabled(false);
        endNumberButton.setEnabled(false);

        textView = (TextView) findViewById(com.tenimaging.solver.R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        startNumber = (EditText) findViewById(com.tenimaging.solver.R.id.startText);
        endNumber = (EditText) findViewById(com.tenimaging.solver.R.id.endText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.tenimaging.solver.R.menu.menu_solver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.tenimaging.solver.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void solveCircular(View view) {
        int startNum = Integer.parseInt(startNumber.getText().toString());
        int endNum = Integer.parseInt(endNumber.getText().toString());

        textView.setText("");
        if (startNum == endNum) {
            textView.append("Start must not equal End\n");
            return;
        }

        if ((startNum < 1) || (endNum < 1)) {
            textView.append("Start and End must be positive, non-zero\n");
            return;
        }

        if ((startNum > 24) || (endNum > 24)) {
            textView.append("Start and End must be smaller than 25\n");
            return;
        }

        if (startNum > endNum) {
            textView.append("Start must be less than End. Swapping numbers.\n");
            int temp;
            temp = startNum;
            startNum = endNum;
            endNum = temp;
        }

        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        com.tenimaging.solver.squarePairSolver.solve(textView, startNum, endNum);
    }
}
