import {ApiBase} from "../ApiBase/ApiBase";
import {GetCoursesRequest} from "../dto/GetCoursesRequest";
import {CourseForEducationalProgram} from "../dto/CourseForEducationalProgram";
import {SelectCoursesRequest} from "../dto/SelectCoursesRequest";
import {GetSelectedCoursesRequest} from "../dto/GetSelectedCoursesRequest";
import {CoursesBySemesterDto} from "../dto/CoursesBySemesterDto";
import {SpecialCourse} from "../dto/SpecialCourse";
import {GetEducationalModuleCoursesRequest} from "../dto/GetEducationalModuleCoursesRequest";
import {CreateModuleSpecialCourseRequest} from "../dto/CreateModuleSpecialCourseRequest";
import {GetCourseByIdRequest} from "../dto/GetCourseByIdRequest";
import {DeleteSpecialCourseRequest} from "../dto/DeleteSpecialCourseRequest";
import {EditModuleSpecialCourseRequest} from "../dto/EditModuleSpecialCourseRequest";
import {CourseStatistics} from "../dto/CourseStatistics";
import {GetActualSpecialCoursesStatisticsRequest} from "../dto/GetActualSpecialCoursesStatisticsRequest";

export class SpecialCoursesApi extends ApiBase implements ISpecialCoursesApi {
    public async getCoursesByEducationalProgramAndSemesters(getCoursesRequest: GetCoursesRequest): Promise<CourseForEducationalProgram[]> {
        return await this.post("specialCourses", {}, {
            ...getCoursesRequest
        });
    }

    public async selectCourses(selectCoursesRequest: SelectCoursesRequest): Promise<void> {
        return await this.post("specialCourses/select", {}, {
            ...selectCoursesRequest
        });
    }

    public async getSelectedCoursesIds(getSelectedCoursesRequest: GetSelectedCoursesRequest): Promise<CoursesBySemesterDto[]> {
        return await this.post("specialCourses/selected", {}, {
            ...getSelectedCoursesRequest
        });
    }

    public async getAllCourses(): Promise<SpecialCourse[]> {
        return await this.get("specialCourses/allCourses", {}, {});
    }

    public async getEducationalModelCourses(getEducationalModuleCoursesRequest: GetEducationalModuleCoursesRequest): Promise<SpecialCourse[]> {
        return await this.get("specialCourses/educationalModuleCourses", {
            ...getEducationalModuleCoursesRequest
        }, {});
    }

    public async createModuleSpecialCourse(createModuleCourseRequest: CreateModuleSpecialCourseRequest): Promise<void> {
        return await this.post("specialCourses/educationalModuleCourses/create", {}, {
            ...createModuleCourseRequest
        });
    }

    public async getCourseById(getCourseByIdRequest: GetCourseByIdRequest): Promise<SpecialCourse> {
        return await this.get("specialCourses/course", {
            ...getCourseByIdRequest
        }, {});
    }

    public async deleteSpecialCourseById(deleteSpecialCourseRequest: DeleteSpecialCourseRequest): Promise<void> {
        return await this.delete("specialCourses/delete", {}, {
            ...deleteSpecialCourseRequest
        });
    }

    public async editModuleSpecialCourse(editModuleSpecialCourseRequest: EditModuleSpecialCourseRequest): Promise<void> {
        return await this.post("specialCourses/educationalModuleCourses/edit", {}, {
            ...editModuleSpecialCourseRequest
        });
    }

    public async getActualSpecialCoursesStatistics(getActualSpecialCoursesStatisticsRequest: GetActualSpecialCoursesStatisticsRequest): Promise<CourseStatistics[]> {
        return await this.get("specialCourses/statistics", {
            ...getActualSpecialCoursesStatisticsRequest
        }, {});
    }
}

export interface ISpecialCoursesApi {
    getCoursesByEducationalProgramAndSemesters(getCoursesRequest: GetCoursesRequest): Promise<CourseForEducationalProgram[]>;
    selectCourses(selectCoursesRequest: SelectCoursesRequest): Promise<void>;
    getSelectedCoursesIds(getSelectedCoursesRequest: GetSelectedCoursesRequest): Promise<CoursesBySemesterDto[]>;
    getAllCourses(): Promise<SpecialCourse[]>;
    getEducationalModelCourses(getEducationalModuleCoursesRequest: GetEducationalModuleCoursesRequest): Promise<SpecialCourse[]>;
    createModuleSpecialCourse(createModuleCourseRequest: CreateModuleSpecialCourseRequest): Promise<void>;
    getCourseById(getCourseByIdRequest: GetCourseByIdRequest): Promise<SpecialCourse>;
    deleteSpecialCourseById(deleteSpecialCourseRequest: DeleteSpecialCourseRequest): Promise<void>
    editModuleSpecialCourse(editModuleSpecialCourseRequest: EditModuleSpecialCourseRequest): Promise<void>
    getActualSpecialCoursesStatistics(getActualSpecialCoursesStatisticsRequest: GetActualSpecialCoursesStatisticsRequest): Promise<CourseStatistics[]>;
}
