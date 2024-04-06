import {ApiBase} from "../../ApiBase/ApiBase";
import {GetCoursesRequest} from "./GetCoursesRequest";
import {CourseForEducationalProgram} from "./CourseForEducationalProgram";
import {SelectCoursesRequest} from "./SelectCoursesRequest";
import {GetSelectedCoursesRequest} from "./GetSelectedCoursesRequest";
import {CoursesBySemesterDto} from "./CoursesBySemesterDto";
import {SpecialCourse} from "./SpecialCourse";
import {GetEducationalModuleCoursesRequest} from "./GetEducationalModuleCoursesRequest";
import {CreateModuleSpecialCourseRequest} from "./CreateModuleSpecialCourseRequest";
import {GetCourseByIdRequest} from "./GetCourseByIdRequest";
import {DeleteSpecialCourseRequest} from "./DeleteSpecialCourseRequest";
import {EditModuleSpecialCourseRequest} from "./EditModuleSpecialCourseRequest";
import {CourseStatistics} from "./CourseStatistics";
import {GetActualSpecialCoursesStatisticsRequest} from "./GetActualSpecialCoursesStatisticsRequest";
import { GetSelectedCourseNamesBySemesterRequest } from "./GetSelectedCourseNamesBySemesterRequest";
import { GetSelectedCourseNamesBySemesterResponse } from "./GetSelectedCourseNamesBySemesterResponse";

export class CoursesApi extends ApiBase implements ICoursesApi {
    public async getSelectedCourseNamesBySemester(getSelectedCourseNamesBySemester: GetSelectedCourseNamesBySemesterRequest): Promise<GetSelectedCourseNamesBySemesterResponse[]> {
        return await this.get("courses/selectedCourseName", {}, getSelectedCourseNamesBySemester);
    }

    public async getCoursesByEducationalProgramAndSemesters(getCoursesRequest: GetCoursesRequest): Promise<CourseForEducationalProgram[]> {
        return await this.post("courses", {}, {
            ...getCoursesRequest
        });
    }

    public async selectCourses(selectCoursesRequest: SelectCoursesRequest): Promise<void> {
        return await this.post("courses/select", {}, {
            ...selectCoursesRequest
        });
    }

    public async getSelectedCoursesIds(getSelectedCoursesRequest: GetSelectedCoursesRequest): Promise<CoursesBySemesterDto[]> {
        return await this.post("courses/selected", {}, {
            ...getSelectedCoursesRequest
        });
    }

    public async getAllCourses(): Promise<SpecialCourse[]> {
        return await this.get("courses/allCourses", {}, {});
    }

    public async getEducationalModelCourses(getEducationalModuleCoursesRequest: GetEducationalModuleCoursesRequest): Promise<SpecialCourse[]> {
        return await this.get("courses/moduleCourses", {
            ...getEducationalModuleCoursesRequest
        }, {});
    }

    public async createModuleSpecialCourse(createModuleCourseRequest: CreateModuleSpecialCourseRequest): Promise<void> {
        return await this.post("courses/moduleCourses/create", {}, {
            ...createModuleCourseRequest
        });
    }

    public async getCourseById(getCourseByIdRequest: GetCourseByIdRequest): Promise<SpecialCourse> {
        return await this.get("courses/course", {
            ...getCourseByIdRequest
        }, {});
    }

    public async deleteSpecialCourseById(deleteSpecialCourseRequest: DeleteSpecialCourseRequest): Promise<void> {
        return await this.delete("courses/delete", {}, {
            ...deleteSpecialCourseRequest
        });
    }

    public async editModuleSpecialCourse(editModuleSpecialCourseRequest: EditModuleSpecialCourseRequest): Promise<void> {
        return await this.post("courses/moduleCourses/edit", {}, {
            ...editModuleSpecialCourseRequest
        });
    }

    public async getActualSpecialCoursesStatistics(getActualSpecialCoursesStatisticsRequest: GetActualSpecialCoursesStatisticsRequest): Promise<CourseStatistics[]> {
        return await this.get("courses/statistics", {
            ...getActualSpecialCoursesStatisticsRequest
        }, {});
    }
}

export interface ICoursesApi {
    getCoursesByEducationalProgramAndSemesters(getCoursesRequest: GetCoursesRequest): Promise<CourseForEducationalProgram[]>;
    selectCourses(selectCoursesRequest: SelectCoursesRequest): Promise<void>;
    getSelectedCoursesIds(getSelectedCoursesRequest: GetSelectedCoursesRequest): Promise<CoursesBySemesterDto[]>;
    getAllCourses(): Promise<SpecialCourse[]>;
    getEducationalModelCourses(getEducationalModuleCoursesRequest: GetEducationalModuleCoursesRequest): Promise<SpecialCourse[]>;
    createModuleSpecialCourse(createModuleCourseRequest: CreateModuleSpecialCourseRequest): Promise<void>;
    getCourseById(getCourseByIdRequest: GetCourseByIdRequest): Promise<SpecialCourse>;
    deleteSpecialCourseById(deleteSpecialCourseRequest: DeleteSpecialCourseRequest): Promise<void>;
    editModuleSpecialCourse(editModuleSpecialCourseRequest: EditModuleSpecialCourseRequest): Promise<void>;
    getActualSpecialCoursesStatistics(getActualSpecialCoursesStatisticsRequest: GetActualSpecialCoursesStatisticsRequest): Promise<CourseStatistics[]>;
    getSelectedCourseNamesBySemester(getSelectedCourseNamesBySemester: GetSelectedCourseNamesBySemesterRequest): Promise<GetSelectedCourseNamesBySemesterResponse[]>;
}
