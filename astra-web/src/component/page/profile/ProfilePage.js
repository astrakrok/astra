import TextHeader from "../../TextHeader/TextHeader";
import InfoText from "../../InfoText/InfoText";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import {getUser} from "../../../handler/user.handler";
import {useEffect, useState} from "react";
import {getExaminationsStatistic, getStepsStatistic} from "../../../service/statistic.service";
import ExamStatisticItem from "../../ExamStatisticItem/ExamStatisticItem";
import withTitle from "../../hoc/withTitle/withTitle";
import "./ProfilePage.css";
import TabPanel from "../../TabPanel/TabPanel";
import StepSatisticItem from "../../StepStatisticItem/StepStatisticItem";

const ProfilePage = () => {
    const [mode, setMode] = useState("examinations");
    const [subjectsStatistic, setSubjectsStatistic] = useState(null);
    const [examinationsStatistic, setExaminationsStatistic] = useState(null);

    useEffect(() => {
        const fetchExaminationsStatistic = async () => {
            const result = await getExaminationsStatistic();
            setExaminationsStatistic(result);
        }

        const fetchSubjectsStatistic = async () => {
            const result = await getStepsStatistic();
            setSubjectsStatistic(result);
        }

        if (mode === "examinations" && examinationsStatistic == null) {
            fetchExaminationsStatistic();
        } else if (mode === "subjects" && subjectsStatistic == null) {
            fetchSubjectsStatistic();
        }
    }, [mode]);

    const user = getUser();

    const renderExaminationStatisticItem = statistic => (
        <ExamStatisticItem key={statistic.testing.id} statistic={statistic} />
    )

    const renderSubjectStatisticItem = statistic => (
        <StepSatisticItem key={statistic.id} statistic={statistic} />
    )

    const getTabs = () => {
        return [
            {
                title: "По іспитам",
                onClick: () => {
                    setMode("examinations");
                }
            },
            {
                title: "По предметам",
                onClick: () => {
                    setMode("subjects");
                }
            }
        ];
    }

    return (
        <div className="ProfilePage container">
            <div className="row">
                <div className="s-vflex">
                    <div className="s-hflex-center profile-information">
                        <div className="s-vflex">
                            <div className="full-name center">{user.name} {user.surname}</div>
                            <div className="email center">{user.email}</div>
                        </div>
                    </div>
                    <div className="statistic">
                        <TextHeader text="Статистика"/>
                        <div className="full-width">
                            <TabPanel type="secondary" tabs={getTabs()} stretch={true} />
                        </div>
                        {
                            (mode === "examinations") ? (
                                <LoaderBoundary condition={examinationsStatistic == null} className="s-hflex-center">
                                    {
                                        (examinationsStatistic && examinationsStatistic.length > 0) ? (
                                            examinationsStatistic.map(renderExaminationStatisticItem)
                                        ) : (
                                            <InfoText className="center">
                                                Ви не пройшли жодного іспиту
                                            </InfoText>
                                        )
                                    }
                                </LoaderBoundary>
                            ) : (
                                <LoaderBoundary condition={subjectsStatistic == null} className="s-hflex-center">
                                    {
                                        (subjectsStatistic && subjectsStatistic.length > 0) ? (
                                            subjectsStatistic.map(renderSubjectStatisticItem)
                                        ) : (
                                            <InfoText className="center">
                                                Ви не пройшли жодного іспиту
                                            </InfoText>
                                        )
                                    }
                                </LoaderBoundary>
                            )
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default withTitle(ProfilePage, "Профіль");
