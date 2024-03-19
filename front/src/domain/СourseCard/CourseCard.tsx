import React from "react";
import {Text} from "../../base_components/Text/Text";
import styles from "./CourseCard.module.css";
import {Flex} from "../../base_components/Flex/Flex";
import {Icon, IconType} from "../../icons/Icon";
import {Control} from "../../apis/dto/Control";
import {connect, ConnectedProps} from "react-redux";
import {RootState} from "../../index";
import {CoursesStoreActionCreator} from "../../storing/coursesStore/coursesStore.actionCreator";
import {CourseForEducationalProgram} from "../../apis/dto/CourseForEducationalProgram";
import {ModuleDto} from "../../apis/api/modules/ModuleDto";

interface CourseCardProps extends BaseProps {
    cardType: CardType.Course;
    specialCourse: CourseForEducationalProgram;
    semesterId: string;
}

interface ModuleCardProps extends BaseProps {
    cardType: CardType.Module;
    containedCourses: CourseForEducationalProgram[];
    module: ModuleDto;
}

interface BaseProps {
    cardColor: "lightBlue" | "darkBlue" | "indigo";
    cardButtonAction: CardButtonAction;
    disableShowAdditionalInfo?: boolean;
}

type Props = CourseCardProps | ModuleCardProps
type ExtendedProps = Props & ConnectedProps<typeof reduxConnector>;


interface State {
    showAdditionalInfo: boolean;
}

export enum CardType {
    Course = "Course",
    Module = "Module",
}

export enum CardButtonAction {
    AddCourse = "AddCourse",
    RemoveCourse = "RemoveCourse",
    Ignore = "Ignore",
}

class CourseCardClear extends React.Component<ExtendedProps, State> {
    constructor(props: ExtendedProps) {
        super(props);
        this.state = {
            showAdditionalInfo: false,
        };
    }

    public render() {
        let iconType: IconType | undefined;
        switch (this.props.cardButtonAction) {
            case CardButtonAction.AddCourse:
                iconType = IconType.PlusIcon;
                break;
            case CardButtonAction.RemoveCourse:
                iconType = IconType.MinusIcon;
                break;
            case CardButtonAction.Ignore:
                iconType = undefined;
                break;
        }

        return (
            <div>
                <Flex
                    direction={"row"}
                    alignItems={"center"}
                    justifyContent={"center"}
                    className={styles.primaryCard + " " + this.getColorClass()}
                    onClick={() => this.switchShowAdditionalInfo()}
                >
                    <Text className={styles.name} size={1.5} align={"center"} fontWeight={"bold"} color={"white"}>
                        {
                            this.props.cardType === CardType.Course
                                ? this.props.specialCourse.name.toUpperCase()
                                : this.props.module.name.toUpperCase()
                        }
                    </Text>
                    <Text className={styles.creditsCount} size={1.75} fontWeight={"bold"} color={"rgba(255, 255, 255, 0.75)"}>
                        {
                            `${this.props.cardType === CardType.Course
                                ? this.props.specialCourse.creditsCount
                                : this.props.containedCourses.reduce((prev,curr) => prev + curr.creditsCount, 0)} з.е.`
                        }
                    </Text>
                    {iconType && (
                        <div onClick={event => this.onAddOrRemoveButtonClick(event)}>
                            <Icon type={iconType} width={2.5} height={2.5} marginLeft={0.5} />
                        </div>
                    )}
                    {!this.props.disableShowAdditionalInfo
                        && <Icon
                            className={styles.triangleIcon}
                            type={IconType.TriangleIcon}
                            width={2.5}
                            height={2.5}
                            marginLeft={0.5}
                            upsideDown={!this.state.showAdditionalInfo}
                        />
                    }
                </Flex>
                {this.state.showAdditionalInfo && this.renderAdditionalInfo()}
            </div>
        );
    }

    private renderAdditionalInfo() {
        return this.props.cardType === CardType.Course
            ? this.renderCourseAdditionalInfo()
            : this.renderModuleAdditionalInfo();
    }

    private renderCourseAdditionalInfo() {
        if (this.props.cardType === CardType.Module) {
            throw new Error("Cannot render course card if it's type is CardType.Module");
        }

        return (
            <Flex direction={"column"} className={styles.additionalInfo} gap={1}>
                <div>
                    <Text size={1.25} fontWeight={"bold"}>ОПИСАНИЕ КУРСА:</Text>
                    <Flex direction={"column"} gap={1}>
                        {this.props.specialCourse.description
                            .split("\n\n")
                            .map(paragraph => <Text size={1}>{paragraph}</Text>)}
                    </Flex>
                </div>
                <Flex direction={"row"}>
                    <Text size={1.25} fontWeight={"bold"}>ФОРМА КОНТРОЛЯ:&nbsp;</Text>
                    <Text size={1.25}>{this.getControlText()}</Text>
                </Flex>
            </Flex>
        );
    }

    private renderModuleAdditionalInfo() {
        if (this.props.cardType === CardType.Course) {
            throw new Error("Cannot render module card if it's type is CardType.Course");
        }

        // Если в модуле есть такие курсы, которые могут вестись в разных семестрах
        if (this.props.containedCourses.filter(x => x.semesters.length !== 1).length !== 0) {
            throw new Error("Courses inside module can only be present in 1 semester at once");
        }

        return (
            <Flex direction={"column"} className={styles.additionalInfo} gap={1}>
                {
                    this.props.containedCourses.map(x => (
                        <CourseCard
                            key={x.id}
                            cardType={CardType.Course}
                            cardColor={"indigo"}
                            cardButtonAction={CardButtonAction.Ignore}
                            semesterId={x.semesters[0].id}
                            specialCourse={x}
                        />
                    ))
                }
            </Flex>
        );
    }

    private onAddOrRemoveButtonClick(event: React.MouseEvent<HTMLDivElement, MouseEvent>) {
        event.stopPropagation();
        switch (this.props.cardButtonAction) {
            case CardButtonAction.AddCourse:
                if (this.props.cardType === CardType.Course) {
                    this.props.addCourse(this.props.semesterId, this.props.specialCourse);
                } else {
                    this.props.addModuleCourses(this.props.containedCourses);
                }
                break;
            case CardButtonAction.RemoveCourse:
                if (this.props.cardType === CardType.Course) {
                    this.props.removeCourse(this.props.specialCourse.id);
                } else {
                    throw new Error("It is not supposed to remove module card, remove corresponding courses instead");
                }
                break;
            case CardButtonAction.Ignore:
                // ignore
                break;
            default:
                throw new Error(`Unknown card button action ${this.props.cardButtonAction}`)
        }

        this.setState({ showAdditionalInfo: false });
    }

    private getControlText(): string {
        if (this.props.cardType !== CardType.Course) {
            throw new Error("Should never happen");
        }

        switch (this.props.specialCourse.control) {
            case Control.Test:
                return "ЗАЧЕТ";
            case Control.Exam:
                return "ЭКЗАМЕН";
            default:
                throw new Error(`Unknown control type: ${this.props.specialCourse.control}`)
        }
    }

    private getColorClass() {
        switch (this.props.cardColor) {
            case "lightBlue":
                return styles.lightBlue;
            case "darkBlue":
                return styles.darkBlue;
            case "indigo":
                return styles.indigo;
            default:
                throw new Error(`Unknown color ${this.props.cardColor}`);
        }
    }

    private switchShowAdditionalInfo() {
        if (!this.props.disableShowAdditionalInfo) {
            this.setState({ showAdditionalInfo: !this.state.showAdditionalInfo });
        }
    }
}

const reduxConnector = connect(
    (state: RootState) => ({
        chosenCourses: state.courses
    }),
    {
        addCourse: CoursesStoreActionCreator.addCourse,
        addModuleCourses: CoursesStoreActionCreator.addModuleCourses,
        removeCourse: CoursesStoreActionCreator.removeCourse,
    }
);

export const CourseCard = reduxConnector(CourseCardClear);
