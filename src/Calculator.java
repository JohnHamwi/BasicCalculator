import java.util.Scanner;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import datastructures.list.LinkedList;
import datastructures.stack.LinkedStack;

/**
 * This class provides the interface to the calculator.
 */
public class Calculator {

  /**
   * Returns the relative precedence of the operators.
   * @param tok the token to check the precedence of.
   * @return an integer value between -1 and 3 representing the operator's precedence.
   */
  public static int getPrec(Token tok) {
    TokenType tokType = tok.getType();

    // Precedence for numbers and parentheses
    if (tokType == TokenType.NUM || tokType == TokenType.LPAREN || tokType == TokenType.RPAREN)
      return 0;
    else if (tokType == TokenType.OP) {
      String tokVal = tok.getValue();
      // Precedence for various operators
      if (tokVal.equals("^"))
        return 3;
      if (tokVal.equals("*") || tokVal.equals("/") || tokVal.equals("%"))
        return 2;
      if (tokVal.equals("+") || tokVal.equals("-"))
        return 1;
    }
    return -1;
  }

  /**
   * Converts an infix expression to postfix notation.
   *
   * @param tokenizedInput the list of tokens in infix notation.
   * @return the list of tokens in postfix notation or null if unsuccessful.
   */
  public static LinkedList<Token> convertToPostfix(LinkedList<Token> tokenizedInput) {
    LinkedList<Token> postFixExpr = new LinkedList<>();
    LinkedStack<Token> stack = new LinkedStack<>();
    int size = 1;
    for (int i = 1; i <= tokenizedInput.getLength(); i++) {
      Token currentToken = tokenizedInput.getEntry(i);
      TokenType currentType = currentToken.getType();

      // Handling memory or number tokens
      if (currentType == TokenType.MEMORY || currentType == TokenType.NUM) {
        postFixExpr.insert(size, currentToken);
        size++;
      }
      // Handling left parenthesis
      else if (currentType == TokenType.LPAREN) {
        stack.push(currentToken);
      }
      // Handling right parenthesis
      else if (currentType == TokenType.RPAREN) {
        Token top;
        do {
          top = stack.pop();
          if (top.getType() != TokenType.LPAREN) {
            postFixExpr.insert(size, top);
            size++;
          }
        } while (top.getType() != TokenType.LPAREN);
      }
      // Handling operators
      else if (currentType == TokenType.OP) {
        if (stack.isEmpty())
          stack.push(currentToken);
        else {
          int topPrec, currentPrec;
          do {
            Token top = stack.peek();
            topPrec = getPrec(top);
            currentPrec = getPrec(currentToken);
            if (topPrec >= currentPrec) {
              postFixExpr.insert(size, top);
              size++;
              stack.pop();
            }
          } while (topPrec >= currentPrec && !stack.isEmpty());
          stack.push(currentToken);
        }
      }
      // Handling unknown tokens
      if (currentType == TokenType.UNKNOWN)
        return null;
    }

    // Emptying the stack after processing the entire expression
    while (!stack.isEmpty()) {
      Token topToken = stack.pop();
      postFixExpr.insert(size, topToken);
      size++;
    }

    return postFixExpr;
  }

  /**
   * Runs the Read, Evaluate, Print, Loop (REPL) for the calculator.
   * Continuously prompts the user for input, processes it, and prints the result.
   */
  public static void doREPL() {
    String expression = "";
    Scanner scan = new Scanner(System.in);
    Lexer lex = new Lexer();
    LinkedList<Token> tokenizedInput;
    PostfixCalculator calc = new PostfixCalculator();

    // Loop for reading and evaluating expressions
    while (!expression.equals("quit")) {
      System.out.print("> ");
      expression = scan.nextLine();

      // Process the expression if it's not empty or 'quit'
      if (!expression.equals("") && !expression.equals("quit")) {
        // Lexical analysis and expression evaluation
        if (lex.doLexicalAnalysis(expression)) {
          tokenizedInput = convertToPostfix(lex.getTokenList());
          if (tokenizedInput == null)
            System.out.println("Failed to convert to postfix");
          else {
            if (calc.evaluate(tokenizedInput))
              System.out.println(calc.getResult());
            else
              System.out.println("Failed to evaluate expression");
          }
        } else {
          System.out.println("Error: Invalid expression.");
        }
      }
    }
  }

  /**
   * The main method that starts the calculator application.
   */
  public static void main(String[] args) {
    System.out.println("Welcome to the Stack Calculator");
    System.out.println("Type 'quit' to exit.\n");
    doREPL();
  }
}
