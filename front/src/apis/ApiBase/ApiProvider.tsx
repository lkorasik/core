import * as React from "react";
import {PropsWithChildren, useContext} from "react";
import {ProgramsApi, IProgramsApi} from "../api/programs/EducationalProgramsApi";
import {ISemestersApi, SemestersApi} from "../api/semester/SemestersApi";
import {ISpecialCoursesApi, SpecialCoursesApi} from "../SpecCoursesApi/SpecialCoursesApi";
import {AuthenticationApi, IAuthenticationApi} from "../api/authentication/AuthenticationApi";
import {ModulesApi, IModulesApi} from "../api/modules/ModulesApi";
import {LoginInfo} from "../../hooks/LoginInfo";
import {DocumentsApi, IDocumentsApi} from "../api/document/DocumentsApi";
import {ISkillsApi, SkillsApi} from "../api/skill/SkillsApi";
import {IRecommendationsApi, RecommendationsApi} from "../api/recommendation/RecommendationsApi";

const apiPrefix: string = "/api/";

export interface IAllApis {
    educationalProgramsApi: IProgramsApi;
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
    educationalProgramsApi: new ProgramsApi(apiPrefix, getToken),
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
