package test.java;
import common.Student;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestStudent {
    Student student = new Student(5, "Bill");
    @Test
    void testID() {
        assertEquals(student.getID(), 5);
    }

}
