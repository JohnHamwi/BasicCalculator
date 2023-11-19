package datastructures;

/**
 * A generic node for chaining.
 *
 * @param <T> the object stored in the list.
 */
 public class Node<T>
 {
   private T item;          // The nodes data.
   private Node<T> next;    // The next reference.

   /**
    * The default constructor. It sets the next reference to null.
    */
    public Node()
    {
      this.next = null;
    }

    /**
     * This is the overloaded constructor that sets the item
     * to itm and the next reference to null.
     *
     * @param itm the item stored in the node.
     */
     public Node(T itm)
     {
        this.item = itm;
        this.next = null;
     }

     /**
      * This is the overloaded constructor that sets the item to itm and the
      * next reference to nxt.
      *
      * @param itm the item to be stored in the node.
      * @param nxt the reference to the next node in the chain.
      */
     public Node(T itm, Node<T> nxt)
     {
        this.item = itm;
        this.next = nxt;
     }

     /**
      * Set the item field of the node to item.
      *
      * @param item the item to store in the node.
      */
     public void setItem(T item)
     {
       this.item = item;
     }

     /**
      * Get the value stored in the item field of the node.
      *
      * @return the item stored in the node.
      */
     public T getItem()
     {
       return item;
     }

     /**
      * Set the value of the next reference.
      *
      * @param next the reference to the next node in the chain.
      */
     public void setNext(Node<T> next)
     {
       this.next = next;
     }

     /**
      * Return the reference to the next node in the chain.
      *
      * @return the reference to the next node in the chain.
      */
     public Node<T> getNext()
     {
       return this.next;
     }
 }
