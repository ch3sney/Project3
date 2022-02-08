import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Luke Chesney / Ian Shemo
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test of constructor with no arguments.
     */
    @Test
    public final void testConstructorNoArgs() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test of constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test of add method with empty map.
     */
    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("blue", "three");
        /*
         * Call method under test
         */
        m.add("blue", "three");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test of add method with non-empty map.
     */
    @Test
    public final void testAdd() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        m.add("blue", "three");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test of remove method leaving empty map.
     */
    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Pair<String, String> x = m.remove("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(x.key(), "blue");
        assertEquals(x.value(), "three");
    }

    /**
     * Test of remove method leaving non-empty map.
     */
    @Test
    public final void testRemove() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "blue", "three");
        /*
         * Call method under test
         */
        Pair<String, String> x = m.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("green", x.key());
        assertEquals("two", x.value());
    }

    /**
     * Test of removeAny method leaving empty map.
     */
    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one");
        /*
         * Call method under test
         */
        Pair<String, String> x = m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(x.key()));
        mExpected.remove(x.key());
        assertEquals(mExpected, m);
    }

    /**
     * Test of removeAny method leaving non-empty map.
     */
    @Test
    public final void testRemoveAny() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsTest("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        Pair<String, String> x = m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(x.key()));
        mExpected.remove(x.key());
        assertEquals(mExpected, m);
    }

    /**
     * Test of value method.
     */
    @Test
    public final void testValue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        String x = m.value("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("two", x);
    }

    /**
     * Test of hasKey method with true result.
     */
    @Test
    public final void testHasKeyTrue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        boolean x = m.hasKey("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(true, x);
    }

    /**
     * Test of hasKey method with false result.
     */
    @Test
    public final void testHasKeyFalse() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        boolean x = m.hasKey("scarlet");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    /**
     * Test of hasKey method with empty map.
     */
    public final void testHasKeyEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean x = m.hasKey("scarlet");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    /**
     * Test of size method with empty map.
     */
    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int x = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(0, x);
    }

    /**
     * Test of size method.
     */
    @Test
    public final void testSize() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "one", "green",
                "two", "blue", "three");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one",
                "green", "two", "blue", "three");
        /*
         * Call method under test
         */
        int x = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(3, x);
    }

}
