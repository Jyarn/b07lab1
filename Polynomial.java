class Polynomial {
    double[] coefficients;

    public Polynomial () {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial (double[] newCoefficients) {
        coefficients = new double[newCoefficients.length];

        for (int i = 0; i < newCoefficients.length; i++) {
            coefficients[i] = newCoefficients[i];
        }
    }

    public Polynomial add (Polynomial addend) {
        double[] results = new double[Math.max(addend.coefficients.length, coefficients.length)];

        for (int i = 0; i < results.length; i++) {
            results[i] = 0;
            // handling out of bounds case for addend
            if (i < addend.coefficients.length) {
                results[i] += addend.coefficients[i];
            }

            // handling out of bounds case for current
            if (i < coefficients.length) {
                results[i] += coefficients[i];
            }
        }

        return new Polynomial(results);
    }

    public double evaluate (double x) {
        double ret = 0;

        for (int i = 0; i < coefficients.length; i++) {
            ret += coefficients[i] * Math.pow(x, i);
        }

        return ret;
    }

    public boolean hasRoot (double x) {
        return evaluate(x) == 0;
    }
}