package lexer;

/**
 * An enumeration of token types.
 */
public enum TokenType {
  /**
   * A number token.
   */
  NUM,

  /**
   * An operation token.
   */
  OP,

  /**
   * A memory token.
   */
  MEMORY,

  /**
   * A left parenthesis.
   */
  LPAREN,

  /**
   * A right parenthesis
   */
  RPAREN,

  /**
   * An unknown token.
   */
  UNKNOWN,

  /**
   * The end of the line token.
   */ 
  END
}
