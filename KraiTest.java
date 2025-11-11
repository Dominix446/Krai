import java.util.*;

/**
 * Test suite for Krai data structure
 * Run with: javac KraiTest.java && java KraiTest
 */
public class KraiTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=== KRAI TEST SUITE ===\n");
        
        // Basic Construction Tests
        testDefaultConstructor();
        testIntConstructor();
        testDoubleConstructor();
        testBooleanConstructor();
        testStringConstructor();
        testCharConstructor();
        
        // Getter Tests
        testGetters();
        
        // Link Tests
        testSingleLink();
        testMultipleLinks();
        testEmptyLinks();
        
        // Protocol Tests
        testProtocolReference();
        testProtocolEquals();
        
        // Advanced Operations
        testProtocolROLARI();
        testProtocolDiffsim();
        
        // Integration Tests
        testLinkedChain();
        testTreeStructure();
        testGraphStructure();
        
        // Edge Cases
        testNullProtocol();
        testDeepCopying();
        
        // Print Results
        System.out.println("\n=== TEST RESULTS ===");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Total:  " + (testsPassed + testsFailed));
        
        if (testsFailed == 0) {
            System.out.println("\n🎉 ALL TESTS PASSED! 🎉");
        } else {
            System.out.println("\n❌ Some tests failed. Review output above.");
        }
    }
    
    // ===== BASIC CONSTRUCTION TESTS =====
    
    static void testDefaultConstructor() {
        Krai node = new Krai();
        assertTrue("Default int should be 0", node.getDataInt() == 0);
        assertTrue("Default double should be 0.0", node.getDataDouble() == 0.0);
        assertTrue("Default bool should be false", node.getDataBool() == false);
        assertTrue("Default string should be empty", node.getDataString().equals(""));
        assertTrue("Default char should be null char", node.getDataChar() == '\0');
        assertTrue("Default protocol should be null", node.getProtocol() == null);
        assertTrue("Default links should be empty array", node.getLinks().length == 0);
        pass("Default constructor");
    }
    
    static void testIntConstructor() {
        Krai protocol = new Krai();
        Krai[] links = new Krai[0];
        Krai node = new Krai(42, links, protocol);
        
        assertTrue("Int value should be 42", node.getDataInt() == 42);
        assertTrue("Protocol should be set", node.getProtocol() == protocol);
        pass("Int constructor");
    }
    
    static void testDoubleConstructor() {
        Krai protocol = new Krai();
        Krai[] links = new Krai[0];
        Krai node = new Krai(3.14, links, protocol);
        
        assertTrue("Double value should be 3.14", Math.abs(node.getDataDouble() - 3.14) < 0.001);
        assertTrue("Protocol should be set", node.getProtocol() == protocol);
        pass("Double constructor");
    }
    
    static void testBooleanConstructor() {
        Krai protocol = new Krai();
        Krai[] links = new Krai[0];
        Krai node = new Krai(true, links, protocol);
        
        assertTrue("Boolean value should be true", node.getDataBool() == true);
        assertTrue("Protocol should be set", node.getProtocol() == protocol);
        pass("Boolean constructor");
    }
    
    static void testStringConstructor() {
        Krai protocol = new Krai();
        Krai[] links = new Krai[0];
        Krai node = new Krai("test", links, protocol);
        
        assertTrue("String value should be 'test'", node.getDataString().equals("test"));
        assertTrue("Protocol should be set", node.getProtocol() == protocol);
        pass("String constructor");
    }
    
    static void testCharConstructor() {
        Krai protocol = new Krai();
        Krai[] links = new Krai[0];
        Krai node = new Krai('A', links, protocol);
        
        assertTrue("Char value should be 'A'", node.getDataChar() == 'A');
        assertTrue("Protocol should be set", node.getProtocol() == protocol);
        pass("Char constructor");
    }
    
    // ===== GETTER TESTS =====
    
    static void testGetters() {
        Krai node = new Krai(100, new Krai[0], null);
        
        assertTrue("getDataInt should return correct value", node.getDataInt() == 100);
        assertTrue("getLinks should return array", node.getLinks() != null);
        assertTrue("getProtocol should work", node.getProtocol() == null);
        pass("Getter methods");
    }
    
    // ===== LINK TESTS =====
    
    static void testSingleLink() {
        Krai node1 = new Krai(1, new Krai[0], null);
        Krai node2 = new Krai(2, new Krai[0], null);
        
        node1.link = new Krai[]{node2};
        
        assertTrue("Should have 1 link", node1.getLinks().length == 1);
        assertTrue("Link should point to node2", node1.getLinks()[0] == node2);
        pass("Single link");
    }
    
    static void testMultipleLinks() {
        Krai node1 = new Krai(1, new Krai[0], null);
        Krai node2 = new Krai(2, new Krai[0], null);
        Krai node3 = new Krai(3, new Krai[0], null);
        
        node1.link = new Krai[]{node2, node3};
        
        assertTrue("Should have 2 links", node1.getLinks().length == 2);
        assertTrue("First link correct", node1.getLinks()[0] == node2);
        assertTrue("Second link correct", node1.getLinks()[1] == node3);
        pass("Multiple links");
    }
    
    static void testEmptyLinks() {
        Krai node = new Krai(1, new Krai[0], null);
        assertTrue("Empty links should have length 0", node.getLinks().length == 0);
        pass("Empty links");
    }
    
    // ===== PROTOCOL TESTS =====
    
    static void testProtocolReference() {
        Krai protocol = new Krai(999, new Krai[0], null);
        Krai node = new Krai(1, new Krai[0], protocol);
        
        assertTrue("Protocol should be referenced", node.getProtocol() == protocol);
        assertTrue("Protocol data should be accessible", node.getProtocol().getDataInt() == 999);
        pass("Protocol reference");
    }
    
    static void testProtocolEquals() {
        Krai protocol = new Krai(42, new Krai[0], null);
        Krai node = new Krai(42, new Krai[0], null);
        
        Krai result = node.protocol_equals(protocol);
        
        assertTrue("Should return protocol when equal", result == protocol);
        pass("Protocol equals (matching)");
        
        Krai node2 = new Krai(100, new Krai[0], null);
        Krai result2 = node2.protocol_equals(protocol);
        
        assertTrue("Should return null when not equal", result2 == null);
        pass("Protocol equals (non-matching)");
    }
    
    // ===== ADVANCED OPERATIONS =====
    
    static void testProtocolROLARI() {
        // Create protocol with links
        Krai child1 = new Krai(10, new Krai[0], null);
        Krai child2 = new Krai(20, new Krai[0], null);
        Krai protocol = new Krai(0, new Krai[]{child1, child2}, null);
        
        // Create new node to swap in
        Krai newNode = new Krai(99, new Krai[0], null);
        
        // Perform ROLARI (replace child1 with newNode)
        Krai result = newNode.protocol_ROLARI(0, protocol);
        
        assertTrue("Should return protocol", result == protocol);
        assertTrue("New node should replace old at index 0", protocol.link[0] == newNode);
        pass("Protocol ROLARI");
    }
    
    static void testProtocolDiffsim() {
        // Create protocol with multiple children
        Krai child1 = new Krai(10, new Krai[0], null);
        Krai child2 = new Krai(50, new Krai[0], null);
        Krai child3 = new Krai(100, new Krai[0], null);
        Krai protocol = new Krai(0, new Krai[]{child1, child2, child3}, null);
        
        // Create node with link to compare
        Krai compareNode = new Krai(45, new Krai[0], null);
        Krai parentNode = new Krai(0, new Krai[]{compareNode}, null);
        
        // Find most similar (should be child2 with value 50)
        Krai result = parentNode.protocol_diffsim(protocol, 0);
        
        assertTrue("Should find most similar node", result != null);
        pass("Protocol diffsim");
    }
    
    // ===== INTEGRATION TESTS =====
    
    static void testLinkedChain() {
        // Create a chain: 1 -> 2 -> 3
        Krai node3 = new Krai(3, new Krai[0], null);
        Krai node2 = new Krai(2, new Krai[]{node3}, null);
        Krai node1 = new Krai(1, new Krai[]{node2}, null);
        
        assertTrue("First node has value 1", node1.getDataInt() == 1);
        assertTrue("First node links to second", node1.getLinks()[0].getDataInt() == 2);
        assertTrue("Second node links to third", node1.getLinks()[0].getLinks()[0].getDataInt() == 3);
        pass("Linked chain structure");
    }
    
    static void testTreeStructure() {
        // Create a tree:
        //       root
        //      /    \
        //    left  right
        Krai left = new Krai(1, new Krai[0], null);
        Krai right = new Krai(2, new Krai[0], null);
        Krai root = new Krai(0, new Krai[]{left, right}, null);
        
        assertTrue("Root has 2 children", root.getLinks().length == 2);
        assertTrue("Left child is 1", root.getLinks()[0].getDataInt() == 1);
        assertTrue("Right child is 2", root.getLinks()[1].getDataInt() == 2);
        pass("Tree structure");
    }
    
    static void testGraphStructure() {
        // Create a graph with cycle: A -> B -> C -> A
        Krai[] tempLinks = new Krai[0];
        Krai nodeA = new Krai(1, tempLinks, null);
        Krai nodeB = new Krai(2, tempLinks, null);
        Krai nodeC = new Krai(3, tempLinks, null);
        
        nodeA.link = new Krai[]{nodeB};
        nodeB.link = new Krai[]{nodeC};
        nodeC.link = new Krai[]{nodeA};
        
        assertTrue("A links to B", nodeA.getLinks()[0] == nodeB);
        assertTrue("B links to C", nodeB.getLinks()[0] == nodeC);
        assertTrue("C links to A (cycle)", nodeC.getLinks()[0] == nodeA);
        pass("Graph structure with cycle");
    }
    
    // ===== EDGE CASES =====
    
    static void testNullProtocol() {
        Krai node = new Krai(1, new Krai[0], null);
        assertTrue("Null protocol should be allowed", node.getProtocol() == null);
        pass("Null protocol handling");
    }
    
    static void testDeepCopying() {
        Krai child = new Krai(1, new Krai[0], null);
        Krai[] originalLinks = new Krai[]{child};
        Krai parent = new Krai(0, originalLinks, null);
        
        // Modify original array
        originalLinks[0] = null;
        
        // Parent's links should be unchanged (deep copy)
        assertTrue("Links should be deep copied", parent.getLinks()[0] != null);
        assertTrue("Link value should be preserved", parent.getLinks()[0].getDataInt() == 1);
        pass("Deep copying of links");
    }
    
    // ===== TEST UTILITIES =====
    
    static void assertTrue(String message, boolean condition) {
        if (!condition) {
            System.out.println("   FAILED: " + message);
            testsFailed++;
        }
    }
    
    static void pass(String testName) {
        System.out.println(" " + testName);
        testsPassed++;
    }
}
