public class Driver {
	public static void testPolynomial (Polynomial tst) {
		System.out.println("f(x) = " + tst.print());

		for (int i = 0; i < 5; i++) {
			System.out.println("f(" + Integer.toString(i) + ") = " + Double.toString(tst.evaluate(i)));
		}
	}

	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));

		Polynomial p1 = new Polynomial(new double[] {6, 5}, new int[] {0, 3});
		Polynomial p2 = new Polynomial(new double[] {-2, -9}, new int[] {1, 4});

		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		
		else
			System.out.println("1 is not a root of s");

		// extension to test additional functionality
		// since the base constructor with 2 arrays as input is so simple, it's better to test with Strings since it's more complicated

		System.out.println("\ntest 1: ---------------------------------------------");
		p1 = new Polynomial();
		p1.readString("x+1"); // test edge case with no exponents/coefficients
		testPolynomial(p1);

		System.out.println("\ntest 2: ---------------------------------------------");
		p1.readString("x1+x0"); // test edge case with no coefficients
		testPolynomial(p1);

		System.out.println("\ntest 3: ---------------------------------------------");
		p1 = new Polynomial();
		p1.readString("1x+1"); // test edge case with no exponents
		testPolynomial(p1);

		System.out.println("\ntest 4: ---------------------------------------------");
		p1 = new Polynomial();
		p1.readString("x+1"); // test edge case with no exponents/coefficients
		testPolynomial(p1);

		System.out.println("\ntest 5: ---------------------------------------------");
		p1 = new Polynomial();
		p1.readString("1"); // single constant (no x in string)
		testPolynomial(p1);

		System.out.println("\ntest 6: ---------------------------------------------");
		p1 = new Polynomial();
		p1.readString("3x5-2x4-1-x6-3"); // general case
		testPolynomial(p1);
	
		
		System.out.println("\ntest 7: ---------------------------------------------");
		System.out.println("*Check to see if they're the same*");
		System.out.println(p1.print());
		p1.saveToFile("test.txt"); // test File I/0
		p2 = new Polynomial("test.txt");
		System.out.println(p2.print());


		System.out.println("\ntest 8: ---------------------------------------------");
		p1.readString("x-1"); // tests if elements with coefficient = 0 are removed
		p2.readString("x+1");
		System.out.println(p1.multiply(p2).print());
		System.out.println(p2.multiply(p1).print());

		System.out.println("\ntest 9: ---------------------------------------------");
		p1.readString("x+1"); // simple case
		p2.readString("x+1");
		System.out.println(p1.multiply(p2).print());
		System.out.println(p2.multiply(p1).print());
		
		System.out.println("\ntest 10: ---------------------------------------------");
		p1.readString("x+1"); // general case
		p2.readString("x2+2x+1");
		System.out.println(p1.multiply(p2).print());
		System.out.println(p2.multiply(p1).print());
	
		
		System.out.println("\ntest 11: ---------------------------------------------");
		p1.readString("x+1");
		p2.readString("-x-1"); // test if elements with coefficient = 0 are removed
		System.out.println(p1.add(p2).print());
	
		System.out.println("\ntest 12: ---------------------------------------------");
		p1.readString("x2+2");
		p2.readString("x"); // test if elements with coefficient = 0 are removed
		System.out.println(p1.add(p2).print());
	}
		
}
