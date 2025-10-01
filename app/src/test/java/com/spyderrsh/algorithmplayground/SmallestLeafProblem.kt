import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Represents a node in a tree structure.
 * It can either be a [Group] or a [Leaf].
 */
sealed interface Node

/**
 * Represents a group node in the tree.
 * A group node can have multiple children, which can be other [Group] nodes or [Leaf] nodes.
 * @property children The list of child nodes.
 */
data class Group(val children: List<Node>) : Node {
    companion object
}

/**
 * Represents a leaf node in the tree.
 * A leaf node holds an integer value.
 * @property value The integer value of the leaf. Must be non-negative.
 */
data class Leaf(val value: Int) : Node {
    init {
        require(value >= 0) { "Leaf value must be non-negative" }
    }
}

/**
 *
 * Example:
 * Groups are represented by `*` and leaves are represented by numbers.
 * In code, leaves are actually `Leaf(number)`.
 *
 *        *
 *       / \
 *      *   3
 *     / \
 *    1   *
 *       / \
 *      2   1
 *
 * Output: 1
 *
 * Traverses the tree starting at the supplied node and returns the integer value of the smallest
 * leaf in the tree.  If there are no leafs in the tree, return -1
 *
 * @param rootNode: the root node of the tree
 * @return Value of the smallest leaf in the tree, or -1 if there are no leaves.
 */
fun smallestLeaf(rootNode: Node): Int {
    return -1
}

/** DO NOT MODIFY CODE BELOW THIS LINE **/

class TestSuite() {
    @Test
    fun test01() {
        val tree = Group { }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(-1, smallest)
    }

    @Test
    fun test02() {
        val tree = Leaf(value = 1)
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(1, smallest)
    }

    @Test
    fun test03() {
        val tree = Group {
            1()
            2()
            3()
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(1, smallest)
    }

    @Test
    fun test04() {
        val tree = Group {
            Group {
                1()
                Group {
                    2()
                    1()
                }
            }
            3()
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(1, smallest)
    }

    @Test
    fun test05() {
        val tree = Group {
            Group {
                1()
                Group {
                    1()
                    1()
                }
            }
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(1, smallest)
    }

    @Test
    fun test06() {
        val tree = Group {
            Group {
                3()
            }
            Group {
                2()
            }
            Group {
                1()
            }
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(1, smallest)
    }

    @Test
    fun test07() {
        val tree = Group {
            9()
            Group {
                2()
                3()
                4()
                5()
            }
            6()
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(2, smallest)
    }

    @Test
    fun test08() {
        val tree = Group {
            55()
            Group {
                22()
                22()
                22()
                21()
            }
            29()
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(21, smallest)
    }

    @Test
    fun test09() {
        val tree = Group {
            1()
            2()
            Group {
                1()
                1()
                2()
                0()
            }
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(0, smallest)
    }

    @Test
    fun test10() {
        val tree = Group {
            Group {
                Group {
                    Group {
                        Group {
                            Group {
                                Group {
                                    3()
                                    9()
                                    8()
                                }
                            }
                        }
                    }
                }
            }
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(3, smallest)
    }

    @Test
    fun test11() {
        val tree = Group {
            Group {
                Group { }
            }
        }
        val smallest = smallestLeaf(rootNode = tree)
        assertEquals(-1, smallest)
    }

    // DSL Builder for creating groups with a tree-like syntax
    class GroupBuilder {
        internal val children = mutableListOf<Node>()

        operator fun Int.invoke(): Leaf {
            val leaf = Leaf(this)
            children.add(leaf)
            return leaf
        }

        operator fun Node.unaryPlus() {
            children.add(this)
        }

        fun build(): Group = Group(children.toList())
    }

    // Thread-local to track the current GroupBuilder context
    private val currentBuilder = ThreadLocal<GroupBuilder?>()

    operator fun Group.Companion.invoke(builder: GroupBuilder.() -> Unit): Group {
        val parentBuilder = currentBuilder.get()
        val groupBuilder = GroupBuilder()

        currentBuilder.set(groupBuilder)
        try {
            groupBuilder.builder()
            val group = groupBuilder.build()

            // If we have a parent builder, automatically add this group to it
            parentBuilder?.children?.add(group)

            return group
        } finally {
            currentBuilder.set(parentBuilder)
        }
    }
}
