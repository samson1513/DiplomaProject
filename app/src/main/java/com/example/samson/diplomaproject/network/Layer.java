package com.example.samson.diplomaproject.network;

public class Layer {
    private Neuron[] neurons;
    private int countNeuron;

    public Layer(int countNeuron, int countInput) {
        this.countNeuron = countNeuron;
        neurons = new Neuron[countInput];
        for (int i = 0; i < countNeuron; ++i) {
            neurons[i] = new Neuron(countInput);
        }
    }
}
