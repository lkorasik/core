import React, {FC, useEffect, useState} from "react";
import {useApis} from "../../apis/ApiBase/ApiProvider";
import {SkillLevel} from "../../apis/api/SkillLevel";
import Select from "react-select";
import styles from "./skillsContent.module.css";
import {Button} from "../../base_components/Buttons/Button/Button";
import {Text} from "../../base_components/Text/Text";


interface Props {
    onContinue: () => void;
}

interface Skill {
    id: string;
    name: string;
    level: SkillLevel | null;
}

export const SkillsContent: FC<Props> = (props) => {
    const apis = useApis();
    const [skills, setSkills] = useState<Skill[]>([]);

    useEffect(() => {
        const prepareData = async () => {
            const allSkills = await apis.skillsApi.getAllSkills();
            const actualSkills = await apis.skillsApi.getActualSkills();
            const skillIdToSkill = new Map<string, Skill>();
            actualSkills.forEach(x => skillIdToSkill.set(x.id, x));

            const resultSkills: Skill[] = [];
            for (const skill of allSkills) {
                if (skillIdToSkill.has(skill.id)) {
                    resultSkills.push(skillIdToSkill.get(skill.id)!);
                } else {
                    resultSkills.push({
                       id: skill.id,
                       name: skill.name,
                       level: null,
                    });
                }
            }
            setSkills(resultSkills);
        };
        prepareData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const renderElement = (skill: Skill, index: number) => {
        const options = [
            {value: null, label: "Не выбрано"},
            {value: SkillLevel.Beginner, label: "Начальный"},
            {value: SkillLevel.Intermediate, label: "Средний"},
            {value: SkillLevel.Advanced, label: "Продвинутый"},
        ];
        return (
            <div className={styles.elementContainer} key={skill.id}>
                <div className={styles.simpleCard}>
                    {skill.name}
                </div>
                <Select
                    className={styles.select}
                    options={options}
                    value={options.find(x => x.value === skills[index].level)}
                    onChange={newValue => {
                        if (newValue) {
                            const newSkills = [...skills];
                            newSkills[index].level = newValue.value;
                            setSkills(newSkills);
                        }
                    }}
                />
            </div>
        );
    }

    const saveInitialSkills = async () => {
        await apis.skillsApi.saveActualSkills({
            skills: skills
                .filter(x => !!x.level)
                .map(x => ({id: x.id, name: x.name, level: x.level!}))
        });
        props.onContinue();
    }

    return (
        <>
            <div className={styles.wrap}>
                <Text className={styles.helpText} size={2}>
                    Оцените уровень ваших навыков
                </Text>
                {skills.length > 0 && skills.map(renderElement)}
            </div>
            <Button
                className={styles.continueButton}
                onClick={saveInitialSkills}
                isEnabled={skills.some(x => x.level)}
            >
                <Text size={2}>Продолжить</Text>
            </Button>
        </>
    );
}
