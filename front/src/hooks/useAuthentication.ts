import {useEffect, useState} from "react";
import {useApis} from "../apis/ApiBase/ApiProvider";
import {LoginInfo} from "./LoginInfo";

export function useAuthentication() {
    const getLoginInfo: () => LoginInfo | null = () => {
        const savedLoginInfo = sessionStorage.getItem('loginInfo');
        if (!savedLoginInfo) {
            return null
        }
        return JSON.parse(savedLoginInfo) as LoginInfo
    };

    const [loginInfo, setLoginInfo] = useState(getLoginInfo());
    const apis = useApis();

    const saveLoginInfo = (loginInfo: LoginInfo) => {
        sessionStorage.setItem('loginInfo', JSON.stringify(loginInfo));
        setLoginInfo(loginInfo);
    };

    useEffect(() => {
        if (!loginInfo) {
            return;
        }
        const validateToken = async () => {
            try {
                await apis.authenticationApi.validateToken({token: loginInfo.token});
            } catch (error: unknown) {
                if (error && typeof error === 'object') {
                    sessionStorage.removeItem('loginInfo');
                    setLoginInfo(null);
                }
            }
        }

        validateToken().catch(console.error);

    }, [loginInfo, apis])

    return {
        saveLoginInfo: saveLoginInfo,
        loginInfo: loginInfo
    }
}
