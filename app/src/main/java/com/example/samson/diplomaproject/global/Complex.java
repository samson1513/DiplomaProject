package com.example.samson.diplomaproject.global;

import java.util.Random;

public class Complex {
    private double re;   // the real part
    private double im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        switch ((int) Math.signum(im)){
            case -1:
                return re + " - " + (-im) + "i";
            case 0:
                return im + "i";
            default:
                return re + " + " + im + "i";
        }
    }

    // return abs/modulus/magnitude
    public double getAbs() { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)

    // return angle/phase/argument
    public double getArg() { return Math.atan2(im, re); }  // between -pi and pi

    // return a new Complex object whose value is the conjugate of this
    public Complex conjugate() {  return new Complex(re, -im); }

    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // return the real or imaginary part
    public double getRe() { return this.re; }
    public double getIm() { return this.im; }

    // return a new Complex object whose value is the complex exponential of this
    public static Complex exp(Complex a) {
        return new Complex(Math.exp(a.re) * Math.cos(a.im), Math.exp(a.re) * Math.sin(a.im));
    }

    // return a new Complex object whose value is the complex sine of this
    public static Complex sin(Complex a) {
        return new Complex(Math.sin(a.re) * Math.cosh(a.im), Math.cos(a.re) * Math.sinh(a.im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public static Complex cos(Complex a) {
        return new Complex(Math.cos(a.re) * Math.cosh(a.im), -Math.sin(a.re) * Math.sinh(a.im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public static Complex tan(Complex a) {
        return divides(sin(a),cos(a));
    }

    // a + b
    public static Complex plus(Complex a, Complex b) {
        return new Complex(a.re + b.re, a.im + b.im);
    }

    // a - b
    public static Complex minus(Complex a, Complex b) {
        return new Complex(a.re - b.re, a.im - b.im);
    }

    // a * b
    public static Complex multi(Complex a, Complex b) {
        return new Complex(a.re * b.re - a.im * b.im, a.re * b.im + a.im * b.re);
    }

    // a / b
    public static Complex divides(Complex a, Complex b) {
        return multi(a,b.reciprocal());
    }

    public static Complex random() {
        Random rdm = new Random();
        return new Complex(rdm.nextDouble(), rdm.nextDouble());
    }

}
