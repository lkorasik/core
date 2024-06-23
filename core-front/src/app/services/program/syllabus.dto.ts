import { SemesterPlan } from "./semesterPlan.dto";

export class Syllabus {
    id: string;
    firstSemesterPlan: SemesterPlan;
    secondSemesterPlan: SemesterPlan;
    thirdSemesterPlan: SemesterPlan;
    fourthSemesterPlan: SemesterPlan;

    constructor(
        id: string, 
        firstSemesterPlan: SemesterPlan, 
        secondSemesterPlan: SemesterPlan, 
        thirdSemesterPlan: SemesterPlan, 
        fourthSemesterPlan: SemesterPlan
    ){
        this.id = id
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
    }
}