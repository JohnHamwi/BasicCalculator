import datastructures.list.LinkedList;
import datastructures.stack.LinkedStack;
import lexer.Token;
import lexer.TokenType;
import static java.lang.Math.pow;

/**
 * A class for evaluating mathematical expressions in postfix notation.
 */
public class PostfixCalculator {
    private double preVal; // Stores the previously evaluated value
    private boolean preValValid; // Flag to check if preVal is valid
    private LinkedStack<Double> stack; // Stack to hold operands during calculation

    /**
     * Constructor for PostfixCalculator.
     */
    public PostfixCalculator() {
        preValValid = false; // Initially, there is no previous value
        stack = new LinkedStack<Double>(); // Initialize the stack
    }

    /**
     * Evaluates a tokenized postfix expression.
     *
     * @param tokList the list of tokens (postfix expression).
     * @return true if evaluation is successful.
     */
    public boolean evaluate(LinkedList<Token> tokList) {
        Token currentToken;
        for (int i = 1; i <= tokList.getLength(); i++) {
            currentToken = tokList.getEntry(i);
            TokenType tempType = currentToken.getType();

            // If the token is a number, push it onto the stack
            if (tempType == TokenType.NUM)
                stack.push(Double.valueOf(currentToken.getValue()));

            // If the token is an operator, process the operation
            else if (tempType == TokenType.OP) {
                processOperation(currentToken.getValue());
            }

            // If the token is a memory recall and previous value is valid, push it onto the stack
            else if (tempType == TokenType.MEMORY) {
                if (preValValid)
                    stack.push(preVal);
            }
        }

        // Pop the result from the stack and update preVal
        double result = stack.pop();
        preVal = result;
        preValValid = true;
        return true;
    }

    /**
     * Gets the result of the last evaluated expression.
     *
     * @return The result of the expression.
     */
    public double getResult() {
        if (preValValid) {
            return preVal;
        } else {
            throw new IllegalStateException(); // Exception if no valid result is available
        }
    }

    /**
     * Processes a mathematical operation.
     *
     * @param op The operation to be performed.
     * @return true if the operation is successful.
     */
    private boolean processOperation(String op) {
        double operand2 = stack.pop(); // Pop operand 2
        double operand1 = stack.pop(); // Pop operand 1
        double result = 0;

        // Perform the operation based on the operator
        switch (op) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            case "^":
                result = pow(operand1, operand2);
                break;
        }

        // Push the result back onto the stack
        stack.push(result);
        return true;
    }
}
