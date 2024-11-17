package com.example.notemanager.application.db;

public abstract class SchoolDbSchema {


    public static final class ClassTable {
        public static final String NAME = "class";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
        }
    }

    public static final class CoursTable {
        public static final String NAME = "course";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String CREDIT = "credit";
            public static final String CLASS_ID = "class_id";

        }
    }

    public static final class StudentTable {
        public static final String NAME = "students";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String EMAIL ="email";
            public static final String CLASS_ID = "class_id";
        }
    }

    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String STUDENT_ID = "student_id";
            public static final String CLASS_ID = "class_id";
            public static final String NOTE = "note";
        }
    }

    public static final class EvaluationTable {
        public static final String NAME = "evaluations";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String ParantId = "parantId";
            public static final String PointMax = "pointMax";
            public static final String CLASS_ID = "class_id";
        }
    }

}