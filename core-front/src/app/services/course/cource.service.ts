import { Injectable } from '@angular/core';
import { CreateCourseDto } from './dtos';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { Callback } from '../callback';
import { Observer } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CourceService {
    constructor(private authorizedClient: AuthorizedHttpClient) { }

    public createCourse(callback: Callback<void>, createModuleCourseRequest: CreateCourseDto) {
        return this.authorizedClient.post<void>("api/courses/create", createModuleCourseRequest).subscribe(this.wrapCallback(callback));
    }


    private wrapCallback(callback: Callback<void>): Observer<void> {
        return {
            next: (dto: void) => callback?.success?.(dto),
            error: (err: any) => callback?.error?.(err),
            complete: () => { }
        }
    }
}
