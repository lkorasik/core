import { Button } from "../../base_components/Button/Button";
import { ButtonSelector } from "../../base_components/ButtonSelector/ButtonSelector";
import { Input } from "../../base_components/Input/Input";
import styles from "./WelcomeBackground.module.css";
import { LoginInfo } from "../../hooks/LoginInfo";
import Select from "react-select";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { AccessTokenDto } from "../../apis/api/authentication/AccessTokenDto";
import { useState } from "react";

interface Props {
    saveLoginInfo: (loginInfo: LoginInfo) => void;
}

export function WelcomeScreen(props: Props) {
    const accountTypes = ["Студент", "Администратор"]
    const [registrationToken, setRegistrationToken] = useState("");
    const [password, setPassword] = useState("");
    const [passwordAgain, setPasswordAgain] = useState("");
    const [isRegistration, setIsRegistration] = useState(false)

    const apis = useApis();

    const saveLoginInfo = (registeredToken: AccessTokenDto) => {
        props.saveLoginInfo({
            token: registeredToken.accessToken,
            userEntityRole: registeredToken.userEntityRole
        });
    }

    const register = async () => {
        const registeredToken = await apis.authenticationApi.register({
            token: registrationToken,
            password: password,
            passwordAgain: passwordAgain
        });
        saveLoginInfo(registeredToken)
    }

    const login = async () => {
        const loginToken = await apis.authenticationApi.login({
            token: registrationToken,
            password: password,
        });
        saveLoginInfo(loginToken)
    }

    const renderLoginForm = () => {
        return (
            <>
                <span className={styles.field_title}>Логин</span>
                <Input type={"email"} placeholder={"15bf0ff0-61ca-4658-8a28-9b649adee5f3"} isRequired={true} onChange={event => setRegistrationToken(event.target.value)} />
                <span className={styles.field_title}>Пароль</span>
                <Input type={"password"} placeholder={""} isRequired={true} onChange={event => setPassword(event.target.value)} />
                <Button className={styles.enterButton} onClick={login}>Войти</Button>
            </>
        )
    }

    const renderRegistrationForm = () => {
        return (
            <>
                <span className={styles.field_title}>Токен</span>
                <Input type={"text"} placeholder={"2f744d2f-3309-4c9c-896e-fba14a4e5a15"} isRequired={true} onChange={event => setRegistrationToken(event.target.value)} />
                <span className={styles.field_title}>Пароль</span>
                <Input type={"password"} placeholder={""} isRequired={true} onChange={event => setPassword(event.target.value)} />
                <span className={styles.field_title}>Повторите пароль</span>
                <Input type={"password"} placeholder={""} isRequired={true} onChange={event => setPasswordAgain(event.target.value)} />
                <Button className={styles.enterButton} onClick={register}>Зарегистрироваться</Button>
            </>
        );
    }

    return (
        <div className={styles.container}>
            <div className={styles.img_container}>
                <a href="https://urfu.ru/">
                    <img src={process.env.PUBLIC_URL + "/logo_urfu_white.svg"} alt="urfu logo" />
                </a>
            </div>
            <img id={styles.curve} src={process.env.PUBLIC_URL + "/curve.svg"} alt={""} />
            <div id={styles.title}>
                <span>Автоматизация</span>
                <span>управления</span>
                <span>магистратурой</span>
            </div>

            <div className={styles.formContainer}>
                <div className={styles.form}>
                    <ButtonSelector groupName={"formSelector"} buttons={[{ label: "Вход", onClick: () => { } }, { label: "Регистрация", onClick: () => {}}]} checkedIndex={0} onChange={(e) => {
                        if (e === "Регистрация") {
                            setIsRegistration(true)
                        }
                        if (e === "Вход") {
                            setIsRegistration(false)
                        }
                    }} />

                    {!isRegistration && renderLoginForm()}
                    {isRegistration && renderRegistrationForm()}
               </div>
            </div>
        </div>
    )
}
