package lexer;

/**
 * Implements a basic token class. A token is an operation or number for
 * the calculator.
 *
 */
 public class Token
 {
    private String val;       // The value of the token.
    private TokenType type;   // The type of token represented.

    /**
     * This is the default constructor.
     */
    public Token()
    {
      val = "";
      type = TokenType.UNKNOWN;

    }

    /**
     * This is the overloaded constructor it sets the value and
     * the token type.
     *
     * @param type the type of the token.
     * @param val the value stored in the token.
     */
    public Token(TokenType type, String val)
    {
      this.type = type;
      this.val = val;
    }

    /**
     * Get the current value associated with the token.
     *
     * @return the string representing the value of the token.
     */
    public String getValue()
    {
      return val;
    }

    /**
     * Get the current type associated with the token.
     *
     * @return the type of token.
     */
    public TokenType getType()
    {
      return type;
    }

    /**
     * Set the value associated with the token.
     *
     * @param val the value of the token.
     */
    public void setValue(String val)
    {
      this.val = val;
    }

    /**
     * Sets the type of token.
     *
     * @param type the type of token.
     */
    public void setType(TokenType type)
    {
      this.type = type;
    }

    /**
     * Return a String representation of the Token.
     *
     * @return a string representing the token.
     */
    @Override
    public String toString()
    {
      switch(type)
      {
        case UNKNOWN:
          return "UNKNOWN(" + val + ")";
        case NUM:
          return "NUM(" + val + ")";
        case OP:
          return "OP(" + val + ")";
        case LPAREN:
          return "LPAREN";
        case RPAREN:
          return "RPAREN";
        case MEMORY:
          return "MEMORY";
        case END:
          return "END";
      }
      return "";
    }
 }
