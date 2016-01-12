package com.example.samson.diplomaproject.network;

public class Net {
    private Layer[] layers;
    private int countLayers;

    public Net(int... neuronsInLayer) {
        this.countLayers = neuronsInLayer.length;
        layers = new Layer[countLayers];
        layers[0] = new Layer(neuronsInLayer[0], neuronsInLayer[0]);
        for (int i = 1; i < countLayers; ++i) {
            layers[i] = new Layer(neuronsInLayer[i], neuronsInLayer[i - 1]);
        }
    }

    public void learn() {

    }

    public void execute() {

    }
}
