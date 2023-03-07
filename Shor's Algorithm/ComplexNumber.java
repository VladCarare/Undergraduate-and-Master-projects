/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */

/**
 * This class represents complex numbers, and defines methods for performing
 * arithmetic on complex numbers.
 */
public class ComplexNumber {
  // These are the instance variables. Each ComplexNumber object holds
  // two double values, known as x and y. They are private, so they are
  // not accessible from outside this class. Instead, they are available
  // through the real() and imaginary() methods below.
  private double x, y;

  /** This is the constructor. It initializes the x and y variables */
  public ComplexNumber(double real, double imaginary) {
    this.x = real;
    this.y = imaginary;
  }
  
  
  /** This is the constructor */
  public ComplexNumber(ComplexNumber a) {
    this.x = a.x;
    this.y = a.y;
  }

  
    /** This is the default constructor. */
  public ComplexNumber() {
    this.x = 0;
    this.y = 0;
  }
  
  /**
   * An accessor method. Returns the real part of the complex number. Note that
   * there is no setReal() method to set the real part. This means that the
   * ComplexNumber class is "immutable".
   */
  public double real() {
    return x;
  }

  /** An accessor method. Returns the imaginary part of the complex number */
  public double imaginary() {
    return y;
  }

  /** Compute the magnitude of a complex number */
  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  /**
   * This method converts a ComplexNumber to a string. This is a method of
   * Object that we override so that complex numbers can be meaningfully
   * converted to strings, and so they can conveniently be printed out with
   * System.out.println() and related methods
   */
  public String toString() {
    return "(" + x + "," + y + ")";
  }

  /**
   * This is a static class method. It takes two complex numbers, adds them, and
   * returns the result as a third number. Because it is static, there is no
   * "current instance" or "this" object. Use it like this: ComplexNumber c =
   * ComplexNumber.add(a, b);
   */
  public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
    return new ComplexNumber(a.x + b.x, a.y + b.y);
  }

  /**
   * This is a non-static instance method by the same name. It adds the
   * specified complex number to the current complex number. Use it like this:
   * ComplexNumber c = a.add(b);
   */
  public ComplexNumber plus(ComplexNumber a) {
    return new ComplexNumber(this.x + a.x, this.y + a.y);
  }
  
  
  /** A static class method to multiply complex numbers */
  public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
    return new ComplexNumber(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
  }

  /** An instance method to multiply complex numbers */
  public ComplexNumber multiply(ComplexNumber a) {
    return new ComplexNumber(x * a.x - y * a.y, x * a.y + y * a.x);
  }
  
    /** A static class method to multiply complex numbers by scalars*/
  public static ComplexNumber multiply(ComplexNumber a, double b) {
    return new ComplexNumber(a.x * b, a.y * b);
  }
  
      /** A static class method to multiply complex numbers by scalars*/
  public static ComplexNumber multiply(ComplexNumber a, int b) {
    return new ComplexNumber(a.x * b, a.y * b);
  }
  
       /** A static class method to multiply two real numbers and return 
       them in the form of a ComplexNumber object*/
  public static ComplexNumber multiply(double a, double b) {
    return new ComplexNumber(a * b, 0. );
  }
  
    /** An instance method to multiply complex numbers */
  public ComplexNumber conjugate() {
    return new ComplexNumber(x , - y);
  }
  
  
  
    /**
   * This is a non-static instance method. It equates two
   * specified complex numbers. 
   */
  public void equateTo(ComplexNumber a) {
     this.x = a.x;
     this.y = a.y;
  }
  
  
    
  
    /**
   * This is a non-static instance method. It sets complex number to 0.
   */
  public void setTo0() {
     this.x = 0;
     this.y = 0;
  }
  
 
  
    
    /**
   * This is a non-static instance method. It equates two
   * specified complex numbers. 
   */
  public void print() {
     System.out.println(this.toString());
  }
  
  
    /**
   * This is a non-static instance method by the same name. It adds the
   * specified complex number to the current complex number.
   */
  public void add(ComplexNumber a) {
    this.x += a.x;
    this.y += a.y;
  }

  
}

 