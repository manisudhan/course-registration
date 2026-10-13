package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCourseIsAtCapacity() {
        Student student1 = new Student("S101", "Alice");
        Student student2 = new Student("S102", "Bob");

        // Course with capacity of 1
        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                1,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        // Fill the only available seat
        assertTrue(service.register(student1, course));
        assertEquals(1, course.getCurrentEnrollment());

        // Attempt to register second student when full
        boolean result = service.register(student2, course);

        assertFalse(result);
        assertFalse(student2.isRegistered("CS301"));
        assertEquals(1, course.getCurrentEnrollment());
        assertEquals(0, student2.getTotalCredits());
    }

    @Test
    void shouldAllowRegistrationWhenOneSeatRemaining() {
        Student student1 = new Student("S101", "Alice");
        Student student2 = new Student("S102", "Bob");

        // Course with capacity of 2
        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                2,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        // Enroll first student
        assertTrue(service.register(student1, course));
        assertEquals(1, course.getCurrentEnrollment());

        // Enroll second student (filling the last remaining seat)
        boolean result = service.register(student2, course);

        assertTrue(result);
        assertTrue(student2.isRegistered("CS301"));
        assertEquals(2, course.getCurrentEnrollment());
    }
    @Test
    void shouldHandleCaseInsensitiveRegistrationCheck() {
        Student student = new Student("S101", "Anita");

        Course course1 = new Course("CS301", "Data Structures", 4, 30, true, null);
        Course course2 = new Course("cs301", "Data Structures Copy", 4, 30, true, null);

        RegistrationService service = new RegistrationService();

        // Register with uppercase course code
        assertTrue(service.register(student, course1));

        // Attempt duplicate registration with lowercase course code representation
        boolean duplicateAttempt = service.register(student, course2);

        assertFalse(duplicateAttempt);
        assertEquals(4, student.getTotalCredits());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }
}