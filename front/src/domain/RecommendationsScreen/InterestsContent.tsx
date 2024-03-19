import {FC, useEffect, useState} from "react";
import styles from "./interestsContent.module.css";
import {useApis} from "../../apis/ApiBase/ApiProvider";
import {Button} from "../../base_components/Button/Button";
import {Text} from "../../base_components/Text/Text";
import {SkillDto} from "../../apis/api/recommendation/SkillDto";
import {SkillLevel} from "../../apis/dto/SkillLevel";


interface Skill {
    id: string;
    name: string;
    isEnabled: boolean;
}

interface Props {
    onGoBack: () => void;
    onContinue: () => void;
}

export const InterestsContent: FC<Props> = (props) => {
    const apis = useApis();
    const [skills, setSkills] = useState<Skill[]>([]);
    // todo Сделать имитацию радикнопок, кнопки "получить рекомендацию" и "назад"

    useEffect(() => {
        const loadData = async () => {
            const allSkills = await apis.skillsApi.getAllSkills();
            const savedDesiredSkills = await apis.skillsApi.getDesiredSkills();
            const skillIdToSkill = new Set<string>(savedDesiredSkills.map(x => x.id));
            const resultSkills: Skill[] = [];
            for (const skill of allSkills) {
                resultSkills.push({
                    id: skill.id,
                    name: skill.name,
                    isEnabled: skillIdToSkill.has(skill.id)
                });
            }
            setSkills(resultSkills);
        };
        loadData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const chooseSkill = (skillId: string) => {
        const newSkills = [...skills];
        const chosenSkill = newSkills.find(x => x.id === skillId)!;
        chosenSkill.isEnabled = !chosenSkill.isEnabled;
        setSkills(newSkills);
    };

    const calculateDesirableSkillsWithLevels = async () => {
        const desirableSkills = skills.filter(x => x.isEnabled);
        const actualSkills = await apis.skillsApi.getActualSkills();
        const desirableSkillsWithLevel: SkillDto[] = [];
        for (const desirableSkill of desirableSkills) {
            const skill = actualSkills.find(y => y.id === desirableSkill.id);
            if (!skill) {
                desirableSkillsWithLevel.push({
                    id: desirableSkill.id,
                    level: SkillLevel.Beginner,
                    name: desirableSkill.name,
                });
            } else {
                try {
                    const nextSkillLevel = getNextSkillLevel(skill.level);
                    desirableSkillsWithLevel.push({
                        id: desirableSkill.id,
                        level: nextSkillLevel,
                        name: desirableSkill.name,
                    });
                } catch (e) {
                    // ignore
                }
            }
        }
        return desirableSkillsWithLevel;
    };

    const saveSkillsAndContinue = async () => {
        const desirableSkills = await calculateDesirableSkillsWithLevels();
        await apis.skillsApi.saveDesiredSkills({ skills: desirableSkills });
        props.onContinue();
    };

    return (
        <>
            <Text className={styles.helpText} size={2}>
                Выберите интересующие навыки
            </Text>
            <div className={styles.wrap}>
                <div className={styles.skills}>
                    {skills.length > 0 && skills.map(x => (
                        <SkillElement
                            key={x.id}
                            skill={x}
                            onEnabled={() => chooseSkill(x.id)}
                        />
                    ))}
                </div>
            </div>
            <div className={styles.buttons}>
                <Button
                    className={styles.button}
                    onClick={() => props.onGoBack()}
                >
                    <Text size={2.5}>Назад</Text>
                </Button>
                <Button
                    className={styles.button}
                    onClick={saveSkillsAndContinue}
                    isEnabled={skills.some(x => x.isEnabled)}
                >
                    <Text size={2.5}>Получить рекомендации</Text>
                </Button>
            </div>
        </>
    );
}

const getNextSkillLevel = (currentLevel: SkillLevel) => {
    switch (currentLevel) {
        case SkillLevel.Beginner:
            return SkillLevel.Intermediate;
        case SkillLevel.Intermediate:
            return SkillLevel.Advanced;
        case SkillLevel.Advanced:
            throw new Error("Advanced is max SkillLevel available");
    }
}

interface SkillElementProps {
    skill: Skill;
    onEnabled: () => void;
}

const SkillElement: FC<SkillElementProps> = props => {
    let className = styles.simpleCard;
    if (props.skill.isEnabled) {
        className += " " + styles.enabled;
    }
    return (
        <div
            className={className}
            onClick={() => props.onEnabled()}
        >
            {props.skill.name}
        </div>
    );
}
