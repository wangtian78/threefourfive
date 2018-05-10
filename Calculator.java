/**
 * Driver code for unlimited precision (limited-usefulness) calculator.
 *
 * @author Andrew Predoehl
 * 25 Aug. 2016
 * Compiled and tested using Java 1.6 and 1.7.
 *
 * This program works like a stack-based (a/k/a "reverse polish notation")
 * calculator, supporting only nonnegative integer data, with addition and
 * multiplication operations.  However, the integers may be arbitrarily large.
 *
 * This program reads from standard input and writes to standard output.
 * It understands three commands, "push," "add," and "multiply,"
 * which are case-sensitive.
 *
 * The "push" command should be followed by one or more decimal digits
 * representing a nonnegative integer represented in decimal.  The
 * value can be any size, i.e., it is not limited to 32 bits like the
 * int type.  The "push" command will push the given number onto an
 * internal stack.  You must push at least two numbers onto this stack
 * before using the other commands.
 *
 * The "add" command pops the top two numbers off the stack, adds them,
 * pushes the sum back onto the stack, and also prints the sum, in decimal,
 * to standard output.
 *
 * The "multiply" command works similarly to "add," but multiplies the
 * numbers and prints the product.
 *
 * Whitespace (space characters, tabs, or newlines) must separate commands
 * from each other and from numbers.
 *
 * This program will abruptly terminate (possibly throwing an exception)
 * if the input deviates from the above rules; it does not try to recover.
 */

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/**
 * Basic command-line interface to StackCalc class, which does the real work.
 *
 * This handles reading from standard input, and writing to standard output
 * or standard error.
 */
class Calculator {

    public static void main(String[] args)
        throws java.io.IOException, java.lang.InstantiationException
    {
        // The calculator object does all the interesting work:
        StackCalc calculator = new StackCalc();

        // This flag is true iff the next input token should be a number.
        // In the language of automata, this is called the "state."
        boolean expecting_number = false;

        // Read from standard input
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in));

        // Loop through the lines of input, and divide each line at whitespace:
        for (String line = br.readLine(); line != null; line = br.readLine())
            for (StringTokenizer tz = new StringTokenizer(line);
                    tz.hasMoreTokens(); ) {

                // Next word or number to be handled:
                final String token = tz.nextToken();

                // What we do next depends on the token and the state:
                if (expecting_number) {
                    /*
                     * If "token" can be interpreted as a nonnegative integer,
                     * then push it on the stack.
                     * Otherwise, throw an exception.
                     */
                    calculator.push(token);
                    expecting_number = false;
                }
                else if (0 == token.compareTo("push"))
                    expecting_number = true;
                else if (0 == token.compareTo("add"))
                    System.out.println(calculator.add());
                else if (0 == token.compareTo("multiply"))
                    System.out.println(calculator.multiply());

                // Bad input, e.g., "maltiply" -- sorry, no second chances!!
                else {
                    System.err.println("Error:  unexpected input (received " +
                        token + ", expected one of {push,add,multiply}).");
                    System.exit(1);
                }
            }
    }
}