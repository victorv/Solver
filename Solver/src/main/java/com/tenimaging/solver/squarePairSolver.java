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

import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

public class squarePairSolver {
    static TextView output;
    static int A, B, MAX, count, linears, circles;
    static List<Integer> squares = new ArrayList<Integer>();
    static List<ArrayList<Integer>> squarePairs = new ArrayList<ArrayList<Integer>>();

    static void solve(TextView out, int a, int b) {
        A = a;
        B = b;
        MAX = B + B - 1;
        linears = circles = count = 0;
        output = out;
        squares.clear();
        squarePairs.clear();
        output.append(String.format("Solving for pairs summing to perfect squares from %d to %d\n", A, B));
        makeSquares();
        output.append(String.format("Possible squares = %s\n", squares.toString()));
        makeSquarePairs();
        output.append(String.format("Possible pairs from 0 = %s\n", squarePairs.toString()));
        for (int i=1; i<squarePairs.size(); i++)
            if (squarePairs.get(i).size() == 1)
                output.append(String.format("Single %d pair makes circular solution not possible.\n", i));
        generatePermutations();
        output.append(String.format("%d solutions found. %d linear and %d circular\n", count, linears, circles));
    }

    static void makeSquares() {
        int i, sqrtMax = (int)Math.sqrt(MAX);

        for (i = sqrtMax; i > A; i--)
            squares.add(i * i);
    }

    static void makeSquarePairs() {
        int i, j, pair;

        // create pair map for each number in sequence
        for (i = 0; i <= B; i++)
            squarePairs.add(new ArrayList<Integer>());

        // for each number in sequence, generate the pairs possible
        for (i = A; i <= B; i++)
            for (j = 0; j < squares.size(); j++) {
                pair = squares.get(j) - i;
                if ((pair >= A) && (pair <= B) && (pair != i))
                    squarePairs.get(i).add(pair);
            }
    }

    static void generatePermutations() {
        List<Integer> allNumbers = new ArrayList<Integer>();
        Integer i;

        for (i = A; i <= B; i++)
            allNumbers.add(i);

        for (i = A; i <= B; i++) {
            List<Integer> numbers = new ArrayList<Integer>(allNumbers);
            List<Integer> permutations = new ArrayList<Integer>();

            numbers.remove(i);
            permutations.add(i);
            recursion(permutations, numbers);
        }
    }

    static void recursion(List<Integer> permutations, List<Integer> remaining) {
        List<Integer> nextones;

        if (remaining.isEmpty()) {
            count++;
            Integer firstLast = permutations.get(0) + permutations.get(permutations.size()-1);

            if (squares.contains(firstLast)) {
                output.append("circular: ");
                circles++;
            }
            else {
                output.append("linear: ");
                linears++;
            }

            output.append(String.format(count + " " + permutations + "\n"));
            return;
        }

        nextones = squarePairs.get(permutations.get(permutations.size() - 1));
        for (Integer next : nextones)
            if (remaining.contains(next)) {
                List<Integer> nextRemaining = new ArrayList<Integer>(remaining);
                List<Integer> nextPermutation = new ArrayList<Integer>(permutations);

                nextRemaining.remove(next);
                nextPermutation.add(next);
                recursion(nextPermutation, nextRemaining);
            }
    }
}
