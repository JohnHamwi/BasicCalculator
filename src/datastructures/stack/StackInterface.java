package datastructures.stack;

import java.util.EmptyStackException;

/**
 * A basic stack interface.
 */
public interface StackInterface<T>
{
  /**
   * Returns true if the stack is empty and false otherwise.
   *
   * @return true if stack is empty; otherwise false.
   */
  public boolean isEmpty();

  /**
   * Pushes an element onto the top of the stack.
   *
   * @param newEntry the entry to add to the top of the stack.
   */
  public void push(T newEntry);

  /**
   * Removes the top element from the stack and returns it.
   *
   * @return the top element of the stack.
   * @throws EmptyStackException if called on an empty stack.
   */
  public T pop() throws EmptyStackException;

  /**
   * Returns a copy of the top element of the stack.
   *
   * @return the top elment on the stack.
   * @throws EmptyStackException if called on an empty stack.
   */
  public T peek() throws EmptyStackException;

  /**
   * Removes all entries from the stack.
   */
  public void clear();
}
