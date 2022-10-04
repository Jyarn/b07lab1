import java.util.LinkedList;
import java.util.ListIterator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial () {
        coefficients = null;
        exponents = null;
    }

    public Polynomial (double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial (int[] exponents, double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial (String fIn) {
        try{
            BufferedReader readIn = new BufferedReader(new FileReader(new File(fIn)));
            readString(readIn.readLine());
        }

        catch (FileNotFoundException error) {
            System.out.println("File Not Found: public Polynomial (String fIn)");
        }

        catch (IOException error) {
            System.out.println("I/O Error: public Polynomial (String fIn)");
        }
    }

    public Polynomial (double[] coefficients) {
        int count = 0; // count of non-zero elements
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) { count++; }
        }

        this.coefficients = new double[count];
        this.exponents = new int[count];

        int j = 0;
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                this.coefficients[j] = coefficients[i];
                this.exponents[j] = i;
                j++;
            }
        }
    }

	public void readString (String in) {
		// 5+6x
		// 5x^0+6x^1
		// -5x
		// +-+++5x
		// 6x+5

		LinkedList<String> processedStr = new LinkedList<String>();

		String buffer = "";

		char prev = ' ';
		// parse input String
		if (!in.isEmpty()) {
			for (char c: in.toCharArray()) {
				if (prev == 'x' || ( (int)prev >= (int)'0' && (int)prev <= (int)'9') ) {
					if (c == '+' || c == '-') {
						// flush buffer
						processedStr.add(buffer);
						buffer = "";
					}
				}

				buffer += c;
				prev = c;
			}
		}

		processedStr.add(buffer);

		// parse each individual element

		ListIterator<String> iter_str = processedStr.listIterator(0);
		double coef = 0;
		int expnt = 0;

		double[] poly_coefficients = new double[processedStr.size()];
		int[] poly_exponents = new int[processedStr.size()];

		for (int i = 0; iter_str.hasNext(); i++) {
			String str = iter_str.next();

			if (str.contains("x")) {
                String[] parsedString = str.split("x");

                try {
                    coef = Double.parseDouble(parsedString[0]);
                }

                catch (NumberFormatException error) {
                    coef = Double.parseDouble(parsedString[0]+"1");
                }

                catch (ArrayIndexOutOfBoundsException error) {
                    coef = 1;
                }

				if (parsedString.length <= 1) {
					expnt = 1;
				}

				else {
					expnt = Integer.parseInt(parsedString[1]);
				}
			}

			else {
				coef = Double.parseDouble(str);
				expnt = 0;
			}

			poly_coefficients[i] = coef;
			poly_exponents[i] = expnt;
		}

		coefficients = poly_coefficients;
		exponents = poly_exponents;
	}

	public Polynomial listToPolynomial (LinkedList<Double> coefficients, LinkedList<Integer> exponents) {
		int count = 0;

		for (int i = 0; i < coefficients.size(); i++) {
			if (coefficients.get(i) != 0.0) {
				count++;
			}
		}

		double[] ret_coefficients = new double[count];
		int[] ret_exponents= new int[count];

		int j = 0;
		for (int i = 0; j < count; i++) {
			if (coefficients.get(i) != 0.0) {
				ret_coefficients[j] = coefficients.get(i);
				ret_exponents[j] = exponents.get(i);
				j++;
			}
		}

		return new Polynomial(ret_coefficients, ret_exponents);
	}

    public String print () {
        String ret = "";
        for (int i = 0; i < exponents.length; i++) {
            String buffer = Double.toString(coefficients[i]);

            if (coefficients[i] >= 0 && i != 0) {
                buffer = "+" + buffer;
            }

            if (exponents[i] == 1) {
                buffer = buffer + "x";
            }

            else if (exponents[i] > 1) {
                buffer = buffer + "x" + Integer.toString(exponents[i]);
            }

            ret += buffer;
        }

        return ret;
    }

    public void saveToFile (String fOut) {
        try {
            FileWriter writeOut = new FileWriter(fOut, false);
            writeOut.write(print());
            writeOut.close();
        }

        catch (IOException error) {
            System.out.println("Something Happended: public void saveToFile (String fOut)");
        }
    }

    public Polynomial add (Polynomial addend) {
		try {
			if (exponents == null || addend.exponents == null) {
				return new Polynomial();

			}
		}

		catch (NullPointerException e) {
			return new Polynomial();
		}

		double[] combinedCoef = new double[addend.coefficients.length + coefficients.length];
		int[] combinedExp = new int[addend.exponents.length + exponents.length];

		System.arraycopy(coefficients, 0, combinedCoef, 0, coefficients.length);
		System.arraycopy(addend.coefficients, 0, combinedCoef, coefficients.length, addend.coefficients.length);

		System.arraycopy(exponents, 0, combinedExp, 0, exponents.length);
		System.arraycopy(addend.exponents, 0, combinedExp, exponents.length, addend.exponents.length);

		LinkedList<Double> resultsCoefficients = new LinkedList<Double>();
		LinkedList<Integer> resultsExponents = new LinkedList<Integer>();

		int expIndex = 0;

		for (int i = 0; i < combinedExp.length; i++) {
			expIndex = resultsExponents.indexOf(combinedExp[i]);

			if (expIndex == -1) {
				resultsCoefficients.add(combinedCoef[i]);
				resultsExponents.add(combinedExp[i]);
			}

			else {
				resultsCoefficients.set(expIndex, resultsCoefficients.get(expIndex) + combinedCoef[i]);
			}
		}

		return listToPolynomial(resultsCoefficients, resultsExponents);
	}

    public Polynomial multiply (Polynomial multiplend) {
		try {
			if (exponents == null || multiplend.exponents == null) {
				return new Polynomial();

			}
		}

		catch (NullPointerException e) {
			return new Polynomial();
		}


		LinkedList<Double> resultsCoefficients = new LinkedList<Double>();
		LinkedList<Integer> resultsExponents = new LinkedList<Integer>();

		int expIndex = 0;
		for (int i = 0; i < exponents.length; i++) {
			for (int j = 0; j < multiplend.exponents.length; j++) {
				expIndex = resultsExponents.indexOf(exponents[i] + multiplend.exponents[j]);

				if (expIndex == -1) {
					resultsCoefficients.add(coefficients[i]*multiplend.coefficients[j]);
					resultsExponents.add(exponents[i] + multiplend.exponents[j]);
				}

				else {
					resultsCoefficients.set(expIndex, resultsCoefficients.get(expIndex) + (coefficients[i]*multiplend.coefficients[j]));
				}
			}
		}

		return listToPolynomial(resultsCoefficients, resultsExponents);
    }

    public double evaluate (double x) {
		try {
			if (exponents == null) {
				return 0;
			}
		}

		catch (NullPointerException e) {
			return x;
		}


		double ret = 0;

        for (int i = 0; i < coefficients.length; i++) {
            ret += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return ret;
    }

    public boolean hasRoot (double x) {
        return evaluate(x) == 0;
    }
}
