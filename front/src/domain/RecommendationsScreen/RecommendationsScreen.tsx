import React, {FC, useState} from "react";
import styles from "./recommendationsScreen.module.css"
import {Tabs} from "../../base_components/Tabs/Tabs";
import {Text} from "../../base_components/Text/Text";
import {SkillsContent} from "./SkillsContent";
import {RecommendationsContent} from "./RecommendationsContent";
import {InterestsContent} from "./InterestsContent";


enum TabType {
    Skills,
    Interests,
    Recommendations
}

const tabElements = [TabType.Skills, TabType.Interests, TabType.Recommendations];

export const RecommendationsScreen: FC = () => {
    const [currentTab, setCurrentTab] = useState<TabType>(TabType.Skills);

    const renderTab = () => {
        switch (currentTab) {
            case TabType.Skills:
                return <SkillsContent onContinue={() => setCurrentTab(TabType.Interests)} />;
            case TabType.Interests:
                return (
                    <InterestsContent
                        onGoBack={() => setCurrentTab(TabType.Skills)}
                        onContinue={() => setCurrentTab(TabType.Recommendations)}
                    />
                );
            case TabType.Recommendations:
                return <RecommendationsContent />;
            default:
                throw new Error("Unknown tab type " + currentTab);
        }
    }

    return (
        <>
            <div className={styles.header}>
                <Text size={3} fontWeight={"bold"} align={"center"}>
                    Рекомендации спецкурсов
                </Text>
            </div>
            <Tabs
                className={styles.tabs}
                elements={tabElements.map(convertTabTypeToName)}
                activeTabIndex={tabElements.indexOf(currentTab)}
                elementWidth={"340px"}
            />
            {renderTab()}
        </>
    );
}

const convertTabTypeToName = (tabType: TabType) => {
    switch (tabType) {
        case TabType.Skills:
            return "Навыки";
        case TabType.Recommendations:
            return "Рекомендации";
        case TabType.Interests:
            return "Интересы";
        default:
            throw new Error("Unknown tab type " + tabType);
    }
}
