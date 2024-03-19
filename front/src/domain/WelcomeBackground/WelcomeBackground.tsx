import { Button } from "../../base_components/Button/Button";
import { ButtonSelector } from "../../base_components/ButtonSelector/ButtonSelector";
import { Input } from "../../base_components/Input/Input";
import styles from "./WelcomeBackground.module.css";
import { LoginInfo } from "../../hooks/LoginInfo";
import Select from "react-select";
import { EducationalProgramInfoDto } from "../../apis/dto/EducationalProgramInfoDto";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { AccessTokenDto } from "../../apis/api/authentication/AccessTokenDto";
import { useEffect, useState } from "react";

interface Props {
    saveLoginInfo: (loginInfo: LoginInfo) => void;
}

export function WelcomeScreen(props: Props) {
    const [selectedProgramId, setSelectedProgramId] = useState("");
    const accountTypes = ["Студент", "Администратор"]
    const [registrationToken, setRegistrationToken] = useState("");
    const [password, setPassword] = useState("");
    const [passwordAgain, setPasswordAgain] = useState("");
    const [group, setGroup] = useState("");
    const [educationalPrograms, setEducationalPrograms] = useState<EducationalProgramInfoDto[]>();

    const [isRegistration, setIsRegistration] = useState(false)
    const [registrationForm, setRegistrationForm] = useState(accountTypes[0])

    const apis = useApis();

    useEffect(() => {
        const loadEducationalPrograms = async () => {
            const programs = await apis.educationalProgramsApi.getEducationalProgramsList();
            setEducationalPrograms(programs);
        }
        loadEducationalPrograms().catch(console.error);
    }, [apis.educationalProgramsApi])

    const saveLoginInfo = (registeredToken: AccessTokenDto) => {
        props.saveLoginInfo({
            token: registeredToken.accessToken,
            userRole: registeredToken.userRole
        });
    }

    const registerStudent = async () => {
        const registeredToken = await apis.authenticationApi.registerStudent({
            token: registrationToken,
            programId: selectedProgramId,
            group: group,
            password: password,
            passwordAgain: passwordAgain
        });
        saveLoginInfo(registeredToken)
    }

    const registerAdministration = async () => {
        const registeredToken = await apis.authenticationApi.registerAdmin({
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
                <span>Логин<span className={styles.red}>*</span></span>
                <Input type={"email"} placeholder={""} isRequired={true} onChange={event => setRegistrationToken(event.target.value)} />
                <span>Пароль<span className={styles.red}>*</span></span>
                <Input type={"password"} placeholder={""} isRequired={true} onChange={event => setPassword(event.target.value)} />
                <Button id={styles.enterButton} onClick={login}>Войти</Button>
            </>
        )
    }

    const renderAccountTypeSelector = () => {
        return (
            <Select
                className={styles.select}
                placeholder={"Тип аккаунта"}
                isDisabled={false}
                defaultValue={accountTypes.map(x => ({ value: x, label: x }))[0]}
                options={accountTypes.map(x => ({ value: x, label: x }))}
                onChange={newValue => {
                    if (newValue) {
                        setSelectedProgramId(newValue.value)
                        setRegistrationForm(newValue.value)
                    }
                }}
            />)
    }

    const renderAdministratorRegisterForm = () => {
        return (
            <>
                <Input type={"text"} placeholder={"Токен регистрации"} isRequired={true} onChange={event => setRegistrationToken(event.target.value)} />
                <Input type={"password"} placeholder={"Пароль"} isRequired={true} onChange={event => setPassword(event.target.value)} />
                <Input type={"password"} placeholder={"Повторите пароль"} isRequired={true} onChange={event => setPasswordAgain(event.target.value)} />
                <Button onClick={registerAdministration}>Зарегистрироваться</Button>
            </>
        );
    }

    const renderStudentRegisterForm = () => {
        return (
            <>
                <Input type={"text"} placeholder={"Токен регистрации"} isRequired={true} onChange={event => setRegistrationToken(event.target.value)} />
                <Select
                    className={styles.select}
                    placeholder={"Образовательная программа"}
                    isDisabled={!educationalPrograms}
                    options={educationalPrograms!.map(x => ({ value: x.id, label: x.name }))}
                    onChange={newValue => {
                        if (newValue) {
                            setSelectedProgramId(newValue.value)
                        }
                    }}
                />
                <Input type={"text"} placeholder={"Группа"} isRequired={true} onChange={event => setGroup(event.target.value)} />
                <Input type={"password"} placeholder={"Пароль"} isRequired={true} onChange={event => setPassword(event.target.value)} />
                <Input type={"password"} placeholder={"Повторите пароль"} isRequired={true} onChange={event => setPasswordAgain(event.target.value)} />
                <Button onClick={registerStudent}>Зарегистрироваться</Button>
            </>
        );
    }

    const renderRegistrationFrom = () => {
        if (registrationForm === "Администратор") {
            return renderAdministratorRegisterForm()
        }
        if (registrationForm === "Студент") {
            return renderStudentRegisterForm()
        }
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
                    {isRegistration && renderAccountTypeSelector()}
                    {isRegistration && educationalPrograms && renderRegistrationFrom()}
               </div>
            </div>
        </div>
    )
}
