package ru.urfu.mm.controller

/**
 * Entrypoint for API endpoints
 */
object Endpoints {
    private const val API = "/api"

    /**
     * Authentication API
     */
    object Authentication {
        const val BASE = "$API/authentication"

        const val LOGIN = "/login"
        @JvmStatic
        fun login() = "$BASE$LOGIN"

        const val REGISTER = "/register"
        @JvmStatic
        fun register() = "$BASE$REGISTER"
    }

    /**
     * Course API
     */
    object Course {
        const val BASE = "$API/courses"
        @JvmStatic
        fun courses() = BASE

        const val SELECTED = "/selected"
        @JvmStatic
        fun selected() = "$BASE$SELECTED"

        const val SELECT = "/select"
        @JvmStatic
        fun select() = "$BASE$SELECT"

        const val STATISTICS = "/statistics"
        @JvmStatic
        fun statistics() = "$BASE$STATISTICS"

        const val ALL_COURSES = "/all"
        @JvmStatic
        fun allCourses() = "$BASE$ALL_COURSES"

        const val MODULE_COURSES = "/moduleCourses"
        @JvmStatic
        fun moduleCourses() = "$BASE$MODULE_COURSES"

        const val COURSE = "/course"
        @JvmStatic
        fun course() = "$BASE$COURSE"

        const val CREATE = "/create"
        @JvmStatic
        fun create() = "$BASE$CREATE"

        const val DELETE = "/delete"
        @JvmStatic
        fun delete() = "$BASE$DELETE"

        const val MODULE_COURSES_EDIT = "/moduleCourses/edit"
        @JvmStatic
        fun moduleCoursesEdit() = "$BASE$MODULE_COURSES_EDIT"

        const val SELECTED_COURSE_NAME = "/selectedCourseName"
        @JvmStatic
        fun selectedCourseName() = "$BASE$SELECTED_COURSE_NAME"

        const val AVAILABLE = "/available"
        @JvmStatic
        fun available() = "$BASE$AVAILABLE"
    }

    /**
     * Document API
     */
    object Document {
        const val BASE = "$API/document"

        const val GENERATE = "/generate"
        @JvmStatic
        fun generate() = "$BASE$GENERATE"
    }

    /**
     * Group API
     */
    object Group {
        const val BASE = "$API/groups"

        const val CREATE = "/create"
        @JvmStatic
        fun create() = "$BASE$CREATE"

        const val GROUP_BY_ID = "/groupById"
        @JvmStatic
        fun groupById() = "$BASE$GROUP_BY_ID"

        const val TOKEN = "/token"
        @JvmStatic
        fun token() = "$BASE$TOKEN"

        const val TOKEN_FILE = "/token_file"
        @JvmStatic
        fun tokenFile() = "$BASE$TOKEN_FILE"
    }

    /**
     * Module API
     */
    object Module {
        const val BASE = "$API/modules"
        @JvmStatic
        fun modules() = BASE

        const val ALL = "/all"
        @JvmStatic
        fun all() = "$BASE$ALL"

        const val ALL_WITH_COURSES = "/allWithCourses"
        @JvmStatic
        fun allWithCourses() = "$BASE$ALL_WITH_COURSES"

        const val MODULE = "/module"
        @JvmStatic
        fun module() = "$BASE$MODULE"

        const val CREATE = "/create"
        @JvmStatic
        fun create() = "$BASE$CREATE"

        const val DELETE = "/delete"
        @JvmStatic
        fun delete() = "$BASE$DELETE"
    }

    /**
     * Program API
     */
    object Program {
        const val BASE = "$API/program"

        const val PROGRAM = "/program"
        @JvmStatic
        fun program() = "$BASE$PROGRAM"

        const val CREATE = "/create"
        @JvmStatic
        fun create() = "$BASE$CREATE"

        const val ALL = "/all"
        @JvmStatic
        fun all() = "$BASE$ALL"
    }

    object Syllabus {
        const val BASE = "$API/syllabus"

        const val CREATE = "/create"
        @JvmStatic
        fun create() = "${Program.BASE}$CREATE"

        const val PLAN = "/plan"
        @JvmStatic
        fun plan() = "${Program.BASE}$PLAN"
    }

    /**
     * Recommendations API
     */
    object Recommendation {
        const val BASE = "$API/recommendations"
        @JvmStatic
        fun recommendations() = BASE
    }

    /**
     * Semester API
     */
    object Semester {
        const val BASE = "$API/semesters"

        const val ACTUAL = "/actual"
        @JvmStatic
        fun actual() = "$BASE$ACTUAL"
    }

    /**
     * Skill API
     */
    object Skill {
        const val BASE = "$API/skills"
        @JvmStatic
        fun skills() = BASE

        const val ACTUAL = "/actual"
        @JvmStatic
        fun actual() = "$BASE$ACTUAL"

        const val DESIRED = "/desired"
        @JvmStatic
        fun desired() = "$BASE$DESIRED"
    }
}