package com.redhat.demo.function;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativePiFunctionIT extends PiFunctionTest {

    // Execute the same tests but in native mode.
}
