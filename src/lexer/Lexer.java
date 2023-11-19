package lexer;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import datastructures.list.LinkedList;

import java.io.IOException;

/**
 * This class provides basic lexical analysis for the caclulator.
 * A lexical analyzer is a program that converts a string in to a series
 * of tokens that can be understood by another program, in this case a
 * calculator.
 *
 */
 public class Lexer
 {
   private LinkedList<Token> tokList;

   /**
    * Constructs a new Lexer with an empty token list.
    */
   public Lexer()
   {
     tokList = new LinkedList<Token>();
   }

   /**
    * Gets the list of tokens from the lexer.
    *
    * @return the current token list. If lexical analysis has not been
    * run the list will be empty.
    */
   public LinkedList<Token> getTokenList()
   {
     return tokList;
   }

   /**
    * Lexically analyzes an expression {@code expr}.
    *
    * @param expr the expression as a string.
    * @return true if the expression is valid; otherwise false.
    */
   public boolean doLexicalAnalysis(String expr)
   {
     PushbackInputStream in = new PushbackInputStream(
         new ByteArrayInputStream(expr.getBytes()));
     Token currTok;

     // Make sure to clear out any previous lexical analysis.
     if (!tokList.isEmpty())
      tokList.clear();

    // Build the list of tokens.
    try
    {
      currTok = getNextToken(in);
    }
    catch(IOException ex)
    {
      return false;
    }
    while (currTok.getType() != TokenType.UNKNOWN &&
        currTok.getType() != TokenType.END)
    {
      tokList.insert(tokList.getLength() + 1, currTok);
      try
      {
        currTok = getNextToken(in);
      }
      catch(IOException ex)
      {
        return false;
      }
    }

    // If we find an unknown token we can't parse the expression -- return false.
    if (currTok.getType() == TokenType.UNKNOWN)
    {
        tokList.clear();     // expression is bad.
        return false;
    }

    return true;
  }

  /**
   * Return the lexer output as a string.
   *
   * @return the lexer output as a string.
   */
  @Override
  public String toString()
  {
    return tokList.toString();
  }

 /*****
  * Private Methods
  *****/

  /**
   * Tokenizes a stream {@code in}.
   *
   * @param in the InputStream containing the characters of the expression.
   * @throws IOException if the reading from the stream fails.
   */
  private Token getNextToken(PushbackInputStream in) throws IOException
  {
    String value = "";
    int currChar = 0;
    boolean usedDigit = false;

    do {
      currChar = in.read();
    } while (currChar != -1 && Character.isWhitespace(currChar));

    // Return special end of file token. We don't
    // want this labeled as unknown.
    if (currChar == -1)
     return new Token(TokenType.END, value);

    // Handle tokens that are one letter in size.
    switch((char)currChar)
    {
      case '(':
        return new Token(TokenType.LPAREN, value);
      case ')':
        return new Token(TokenType.RPAREN, value);
      case '+':
      case '-':
      case '/':
      case '*':
      case '^':
      case '%':
        value += Character.toString(currChar);
        return new Token(TokenType.OP, value);
      case 'M':
        value += "M";
        return new Token(TokenType.MEMORY, value);
      default:
        // empty -- handled below.
      break;
    }

    // Process a number
    if (Character.isDigit(currChar) || currChar == '.')
    {
      // Digits before the decimal point.
      while (Character.isDigit(currChar) && currChar != -1)
      {
        value += Character.toString(currChar);
        currChar = in.read();
        usedDigit = true;
      }

      // Handle the decimal point.
      if ((char)currChar == '.')
      {
        value += Character.toString(currChar);

        // Digits after the decimal point.
        currChar = in.read();
        while (Character.isDigit(currChar) && currChar != -1)
        {
          value += Character.toString(currChar);
          currChar = in.read();
          usedDigit = true;
        }
      }

      // Putback the character that caused us to stop
      // processing.
      if (currChar != -1)
        in.unread(currChar);

      // If we found a number return it otherwise return
      // it as an identifier with a value.
      if (usedDigit)
        return new Token(TokenType.NUM, value);
      else
        return new Token(TokenType.UNKNOWN, value);
    }

    // Handle any other keywords.
    if (Character.isAlphabetic(currChar))
    {
      while (Character.isAlphabetic(currChar) && currChar != -1)
      {
        value += Character.toString(currChar);
        currChar = in.read();
      }

      // Putback the character that caused us to stop
      // processing.
      if (currChar != -1)
        in.unread(currChar);

     // Check if its a value we expect as a function.
     if (value.equals("round"))
       return new Token(TokenType.OP, value);

   }

    // We don't recognize the token; mark it as unknown.
    return new Token(TokenType.UNKNOWN, value);
  }
}
