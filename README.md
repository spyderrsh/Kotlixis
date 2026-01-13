# Algorithm Playground - Tech Assessment

This is a standalone Kotlin project with a single test file for live coding interviews.

## Project Structure

```
src/test/kotlin/
└── SmallestLeafProblem.kt    # The assessment test file
```

## Building and Running Tests

### Run all tests:
```bash
./gradlew test
```

### Run a specific test:
```bash
./gradlew test --tests TestSuite.test01
```

### Clean build:
```bash
./gradlew clean build
```

## Assessment Details

The test file contains:
- **Data structures**: `Node` (sealed interface), `Group`, and `Leaf` classes
- **Function to implement**: `smallestLeaf(rootNode: Node): Int`
- **12 test cases** that verify the implementation

### What candidates need to do:

Implement the `smallestLeaf()` function to traverse a tree and find the smallest leaf value:
- Returns the integer value of the smallest leaf in the tree
- Returns -1 if there are no leaves in the tree
- The tree can have an arbitrary depth and structure

### Test DSL:

The tests use a convenient DSL for building trees:
```kotlin
val tree = Group {
    1()           // Creates Leaf(1)
    Group {
        2()       // Creates Leaf(2) in nested Group
        3()
    }
}
```

## Technology Stack

- **Language**: Kotlin 2.3.0
- **Build Tool**: Gradle (Kotlin DSL)
- **Testing**: JUnit 4
- **Java Target**: JVM 17

## Configuration Files Modified

- `build.gradle.kts` - Converted to Kotlin JVM plugin (removed Android)
- `settings.gradle.kts` - Removed Android repositories and module includes
- `gradle/libs.versions.toml` - Updated to use Kotlin JVM plugin only
- `gradle.properties` - Removed Android-specific properties

## Notes

- The old `app/` directory is still present but not used by the build
- All test infrastructure is configured and ready to use
- Candidates only need to implement the `smallestLeaf()` function
