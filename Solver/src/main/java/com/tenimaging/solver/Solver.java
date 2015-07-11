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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Button;
import android.text.method.ScrollingMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class Solver extends ActionBarActivity {
    private TextView textView;
    private Button solveCircularButton;
    private NumberPicker startNumber, endNumber;
    private int startNum, endNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solver);

        textView = (TextView) findViewById(com.tenimaging.solver.R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        startNumber = (NumberPicker) findViewById(R.id.numberPickerStart);
        endNumber = (NumberPicker) findViewById(R.id.numberPickerEnd);

        startNumber.setMinValue(1);
        startNumber.setMaxValue(24);
        startNumber.setValue(1);
        startNumber.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        endNumber.setMinValue(1);
        endNumber.setMaxValue(24);
        endNumber.setValue(16);
        endNumber.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
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

    public void solve(View view) {
        int startNum = startNumber.getValue();
        int endNum = endNumber.getValue();

        textView.setText("");
        if (startNum == endNum) {
            textView.append("Start must not equal End.\n");
            return;
        }

        if (startNum > endNum) {
            int temp;
            temp = startNum;
            startNum = endNum;
            endNum = temp;
        }

        com.tenimaging.solver.squarePairSolver.solve(textView, startNum, endNum);
    }
}
