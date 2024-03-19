import * as React from "react";
import {PropsWithChildren, useContext} from "react";
import {EducationalProgramsApi, IEducationalProgramsApi} from "../SpecCoursesApi/EducationalProgramsApi";
import {ISemestersApi, SemestersApi} from "../SpecCoursesApi/SemestersApi";
import {ISpecialCoursesApi, SpecialCoursesApi} from "../SpecCoursesApi/SpecialCoursesApi";
import {AuthenticationApi, IAuthenticationApi} from "../api/authentication/AuthenticationApi";
import {ModulesApi, IModulesApi} from "../api/modules/EducationalModulesApi";
import {LoginInfo} from "../../hooks/LoginInfo";
import {DocumentsApi, IDocumentsApi} from "../SpecCoursesApi/DocumentsApi";
import {ISkillsApi, SkillsApi} from "../SpecCoursesApi/SkillsApi";
import {IRecommendationsApi, RecommendationsApi} from "../SpecCoursesApi/RecommendationsApi";

const apiPrefix: string = "/api/";

export interface IAllApis {
    educationalProgramsApi: IEducationalProgramsApi;
    semestersApi: ISemestersApi;
    specialCoursesApi: ISpecialCoursesApi;
    authenticationApi: IAuthenticationApi;
    educationalModulesApi: IModulesApi;
    documentsApi: IDocumentsApi;
    skillsApi: ISkillsApi;
    recommendationsApi: IRecommendationsApi;
}

export interface IAllApisProp {
    apis: IAllApis;
}

// В идеале бы переиспользовать хук useToken, но здесь работать не будет,
// поэтому пока такой костыль
const getToken = () => {
    const loginInfoStr = sessionStorage.getItem('loginInfo');
    if (!loginInfoStr) {
        return "";
    }
    return (JSON.parse(loginInfoStr) as LoginInfo).token;
}

export const RealApi: IAllApis = {
    educationalProgramsApi: new EducationalProgramsApi(apiPrefix, getToken),
    semestersApi: new SemestersApi(apiPrefix, getToken),
    specialCoursesApi: new SpecialCoursesApi(apiPrefix, getToken),
    authenticationApi: new AuthenticationApi(apiPrefix, getToken),
    educationalModulesApi: new ModulesApi(apiPrefix, getToken),
    documentsApi: new DocumentsApi(apiPrefix, getToken),
    skillsApi: new SkillsApi(apiPrefix, getToken),
    recommendationsApi: new RecommendationsApi(apiPrefix, getToken),
};

export const ApiContext = React.createContext(RealApi);

export const useApis = () => useContext(ApiContext);

export const withApis = <TProps, TState>(
    ComponentClass: new (props: PropsWithChildren<TProps> & IAllApisProp) => React.Component<
        TProps & IAllApisProp,
        TState
        >
    // eslint-disable-next-line react/display-name
) => (props: PropsWithChildren<TProps>) => (
    <ApiContext.Consumer>{apis => <ComponentClass {...props} apis={apis} />}</ApiContext.Consumer>
);

export const ApiProvider = (props: PropsWithChildren<IAllApisProp>) => (
    <ApiContext.Provider value={props.apis}>{props.children}</ApiContext.Provider>
);
