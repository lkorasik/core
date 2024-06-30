export class GetNewPlan {
    first: Desc;
    second: Desc;
    third: Desc;
    fourth: Desc;

    constructor(first: Desc, second: Desc, third: Desc, fourth: Desc) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }
}

export class Desc {
    semesterId: string
    requiredCourses: string[]
    availableCourses: string[]
    scienceWorks: string[]

    constructor(semesterId: string, requiredCourses: string[], availableCourses: string[], scienceWorks: string[]) {
        this.semesterId = semesterId
        this.requiredCourses = requiredCourses
        this.availableCourses = availableCourses
        this.scienceWorks = scienceWorks
    }
}