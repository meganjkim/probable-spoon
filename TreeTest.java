import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TreeTest {

    private Tree tree;

    @BeforeEach
    void setUp() {
        tree = new Tree();
         // Create the "./objects/" directory if it doesn't exist
         File objectsDir = new File("./objects");
         objectsDir.mkdirs();
    }

    @Test
    void testAdd() {
        tree.add("Entry 1");
        tree.add("Entry 2");

        ArrayList<String> blobTree = tree.getBlobTree();
        assertEquals(2, blobTree.size());

        // Test adding a duplicate entry
        tree.add("Entry 1");
        assertEquals(2, blobTree.size());
    }

    @Test
    void testRemove() {
        tree.add("Entry 1");
        tree.add("Entry 2");

        ArrayList<String> blobTree = tree.getBlobTree();
        tree.remove("Entry 1");

        assertEquals(1, blobTree.size());

        // Test removing a non-existing entry
        tree.remove("Entry 3");
        assertEquals(1, blobTree.size());
    }

    @Test
    void testHashFromString() {
        String input = "Test String";
        String expectedHash = "a5103f9c0b7d5ff69ddc38607c74e53d4ac120f2";

        String hash = Tree.hashFromString(input);
        assertEquals(expectedHash, hash);
    }

    @Test
    void testSave() {
        tree.add("Entry 1");
        tree.add("Entry 2");

        try {
            tree.save();
            ArrayList<String> blobTree = tree.getBlobTree();

            // Check if the file was created
            String hash = Tree.hashFromString("Entry 1\nEntry 2");
            File tempFile = new File("./objects/" + hash);
            assertTrue(tempFile.exists());

            // Clean up: Delete the file
            tempFile.delete();
        } catch (IOException e) {
            fail("IOException occurred during save: " + e.getMessage());
        }
    }
}
