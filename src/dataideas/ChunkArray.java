package dataideas;

import java.util.LinkedList;

/**
 * A data structure that contains a LinkedList of Chunks. Chunks are
 * encapsulated 2D generic arrays which store an x and y value indicating their
 * location in a plane, and are implemented as an inner class. This structure
 * uses Chunks to create a functionally infinite 2D array of generics.
 * 
 * @author Wade Foster
 * @version 2016.11.21
 * 
 * @param T Generic data type.
 */
public class ChunkArray<T>
{
    private final int chunkSize = 50;

    private LinkedList<Chunk> chunks;

    /**
     * Constructs a new ChunkArray.
     */
    public ChunkArray()
    {
        chunks = new LinkedList<Chunk>();
    }

    /**
     * Gets the element at the specified coordinates.
     * 
     * @param x The x coordinate of the entry.
     * @param y The y coordinate of the entry.
     * @return A T object.
     */
    public T getEntry(int x, int y)
    {
        int[] clx = toCL(x);
        int[] cly = toCL(y);
        return getChunk(clx[0], cly[0]).getEntry(clx[1], cly[1]);
    }

    /**
     * Sets the value at the given global coordinate to the given generic
     * object.
     * 
     * @param data The data to store.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setEntry(T data, int x, int y)
    {
        if (data == null)
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        int[] clx = toCL(x);
        int[] cly = toCL(y);
        getChunk(clx[0], cly[0]).setEntry(data, clx[1], cly[1]);
    }

    public void clear()
    {
        chunks = new LinkedList<Chunk>();
    }

    /**
     * A helper method which gets the chunk at a specified location. If the
     * Chunk does not already exist, this method creates a new one, adds it to
     * the ChunkArray, and returns it. This method also removes any empty Chunks
     * during its pass through all the Chunks in the ChunkArray.
     * 
     * @param x The x coordinate of the Chunk.
     * @param y The y coordinate of the Chunk.
     * @return The Chunk at the specified coordinate.
     */
    private Chunk getChunk(int x, int y)
    {
        for (Chunk c : chunks)
        {
            if (c.getX() == x && c.getY() == y)
            {
                return c;
            }
            if (c.isEmpty())
            {
                // The number one rule of for-each loops
                // DON'T EDIT THE THING BEING INCREMENTED OVER.
                chunks.remove(c);
            }
        }
        Chunk c = new Chunk(x, y);
        chunks.add(c);
        return c;
    }

    /**
     * Converts a global address (for example, 45) to a Chunk-Local address
     * (chunk 5, index 6).
     * 
     * @param a The global address.
     * @return an Integer array of length 2, containing the chunk address and
     *         local address.
     */
    private int[] toCL(int a)
    {
        int ac;

        if (a >= 0 && a < chunkSize)
        {
            ac = 0;
        }
        if (a >= chunkSize)
        {
            ac = a / chunkSize;
        }
        else
        {
            ac = 0;
            while (a < ac * chunkSize)
            {
                ac--;
            }
        }

        int al = a - ac * chunkSize;

        return new int[] { ac, al };
    }

    /**
     * Private implementation of a Chunk. Chunks are container classes for
     * generic arrays of a default size with stored location data. They are
     * created and discarded as needed by ChunkArray.
     * 
     * @author Wade Foster
     * @version 2016.11.21
     */
    private class Chunk
    {
        int x;
        int y;
        T[][] grid;

        /**
         * Creates an empty Chunk at the specified location.
         * 
         * @param x The x coordinate.
         * @param y The y coordinate.
         */
        @SuppressWarnings("unchecked")
        public Chunk(int x, int y)
        {
            this.x = x;
            this.y = y;
            grid = (T[][]) new Object[chunkSize][chunkSize];
        }

        /**
         * Gets this Chunk's x coordinate.
         * 
         * @return an Integer x.
         */
        public int getX()
        {
            return x;
        }

        /**
         * Gets this Chunk's y coordinate.
         * 
         * @return an Integer y.
         */
        public int getY()
        {
            return y;
        }

        /**
         * Returns the data stored at a specified coordinate.
         * 
         * @param x The x coordinate.
         * @param y The y coordinate.
         * @return The T object stored here.
         */
        public T getEntry(int x, int y)
        {
            return grid[x][y];
        }

        /**
         * Stores a generic object at the specified location.
         * 
         * @param data The T data to store.
         * @param x The x coordinate.
         * @param y The y coordinate.
         */
        public void setEntry(T data, int x, int y)
        {
            grid[x][y] = data;
        }

        /**
         * Checks if this chunk is not currently storing anything.
         * 
         * @return true if empty, false if it contains any data.
         */
        public boolean isEmpty()
        {
            for (int i = 0; i < chunkSize; i++)
            {
                for (int j = 0; j < chunkSize; j++)
                {
                    if (grid[i][j] != null)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
