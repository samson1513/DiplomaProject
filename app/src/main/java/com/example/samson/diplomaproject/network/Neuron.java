package com.example.samson.diplomaproject.network;

import com.example.samson.diplomaproject.global.Complex;

public class Neuron {
    private Complex[] weights;
    private int countInputs;

    public Neuron(int countInputs) {
        this.countInputs = ++countInputs;
        createWeights();
    }

    private void createWeights(){
        weights = new Complex[countInputs];
        for (int i = 0; i < countInputs; ++i) {
            weights[i] = Complex.random();
        }
    }

}
